package com.zzl.authentication.authenticationservice.config;

import com.zzl.authentication.authenticationservice.security.MyRedisTokenStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.approval.ApprovalStore;
import org.springframework.security.oauth2.provider.approval.TokenApprovalStore;
import org.springframework.security.oauth2.provider.client.JdbcClientDetailsService;
import org.springframework.security.oauth2.provider.code.AuthorizationCodeServices;
import org.springframework.security.oauth2.provider.code.JdbcAuthorizationCodeServices;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.web.AuthenticationEntryPoint;

import java.util.concurrent.TimeUnit;
import javax.sql.DataSource;

/**
 *  声明一个授权服务器：
 *
 *	OAuth2认证的核心配置类，在这个类中，配置了OAuth Client的信息，这里有几个地方需要注意：
 *
 *	@EnableAuthorizationServer 这个注解告诉 Spring 这个应用是 OAuth2 的授权服务器
 *	必须配置 authorizedGrantTypes ，它代表了OAuth Client允许认证的类型，其值主要有:
 *	authorization_code
 *	password
 *	client_credentials
 *	implicit
 *	refresh_token
 *	配置授权的相关信息，配置的核心都在这里
 *	在这里进行 配置客户端，配置token存储方式等
 * @author zhaozhonglong
 */
@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {
    @Autowired
    @Qualifier("authenticationManagerBean")
    private AuthenticationManager authenticationManager;

    @Autowired
    private DataSource dataSource;

    @Autowired
    private RedisConnectionFactory redisConnectionFactory;

    @Bean
    public MyRedisTokenStore tokenStore() {
        return new MyRedisTokenStore(redisConnectionFactory);
    }

    @Bean // 声明 ClientDetails实现
    public ClientDetailsService clientDetails() {
        return new JdbcClientDetailsService(dataSource);
    }

    // 主要用于 "authorization_code" 授权码类型模式
    @Bean
    public AuthorizationCodeServices authorizationCodeServices() {
        return new JdbcAuthorizationCodeServices(dataSource);
    }

    @Bean
    public ApprovalStore approvalStore() {
        TokenApprovalStore store = new TokenApprovalStore();
        store.setTokenStore(tokenStore());
        return store;
    }

    /**
     * 这个方法主要是用于校验注册的第三方客户端的信息
     * 有两种:
     *  一种是基于JDBC JdbcClientDetailsService，
     *  一种是基于内存的InMemoryClientDetailsServiceBuilder
     */
    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception{
        // 通过JDBC去查询数据库oauth_client_details表验证clientId信息
        clients.withClientDetails(clientDetails());
        clients.jdbc(dataSource);
    }


    /**
     * @description 用来配置授权（authorization）以及令牌（token）的访问端点和令牌服务(token services)
     *
     * @param
     * @return
     * @author zhaozhonglong
     * @date  2020/1/31 16:58:31
     */
    @Override // 配置框架应用上述实现
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {

        endpoints.authenticationManager(authenticationManager) // 只有authenticationManager 才支持密码模式
                 .tokenStore(tokenStore()) // token存储方式
                 .setClientDetailsService(clientDetails());

        endpoints.tokenServices(defaultTokenServices());
    }

    @Primary
    @Bean
    public DefaultTokenServices defaultTokenServices(){
        // 配置TokenServices参数
        DefaultTokenServices tokenServices = new DefaultTokenServices();
        tokenServices.setTokenStore(tokenStore());
        tokenServices.setSupportRefreshToken(true);
        tokenServices.setClientDetailsService(clientDetails());

        // 设置token有效期30天
        tokenServices.setAccessTokenValiditySeconds( (int) TimeUnit.DAYS.toSeconds(30));
        //默认30天
        tokenServices.setRefreshTokenValiditySeconds(60 * 60 * 24 * 7);

        return tokenServices;
    }

    /**
     * http://localhost:xxxx/api/oauth/token?username=user&password=123456&grant_type=password&client_id=client&client_secret=123456
     * 添加这个是为了密码模式下post 请求获取token，请求方式如上所示
     * get请求下通过这种方式获取token会报405错误，oauth2 内部屏蔽了get方式获取请求
     * 认为get请求是不安全的。
     * HttpRequestMethodNotSupportedException, Request method 'GET' not supported
     *
     * 但是前段浏览器发送 basic auth 请求时无论是get还是post都能够得到tocken，这总方式也是oauth2默认的方式
     *
     */
    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {

        //enable client to get the authenticated when using the /oauth/token to get a access token
        //there is a 401 authentication is required if it doesn't allow form authentication for clients when access /oauth/token
        security
                    .tokenKeyAccess("permitAll()")
                   .checkTokenAccess("permitAll()")
                   .allowFormAuthenticationForClients();


    }



}
