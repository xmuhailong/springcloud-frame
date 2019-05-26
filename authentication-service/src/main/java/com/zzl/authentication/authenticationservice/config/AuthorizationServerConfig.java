package com.zzl.authentication.authenticationservice.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.client.JdbcClientDetailsService;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JdbcTokenStore;

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
在这里进行 配置客户端，配置token存储方式等
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

    /**
     * @Title: tokenStore
     * @Description: 用户验证信息的保存策略
     * @param
     * @return TokenStore
     * @throws
     */
//    @Bean
//    public TokenStore tokenStore(){
//        return new JdbcTokenStore(dataSource);
//    }

    @Bean // 声明 ClientDetails实现
    public ClientDetailsService clientDetails() {
        return new JdbcClientDetailsService(dataSource);
    }


    /**
     *
     * 这个方法主要是用于校验注册的第三方客户端的信息，
     */
    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception{
        clients.withClientDetails(clientDetails());
        clients.jdbc(dataSource);
    }


    /**
     * 这个方法主要的作用用于控制token的端点等信息
     */
    @Override // 配置框架应用上述实现
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        //endpoints.tokenStore(tokenStore()).authenticationManager(authenticationManager);

        endpoints.authenticationManager(authenticationManager);
        //endpoints.tokenStore(tokenStore());
        endpoints.setClientDetailsService(clientDetails());

        // 配置TokenServices参数
        DefaultTokenServices tokenServices = new DefaultTokenServices();
        tokenServices.setTokenStore(endpoints.getTokenStore());
        tokenServices.setSupportRefreshToken(true);
        tokenServices.setClientDetailsService(endpoints.getClientDetailsService());
        tokenServices.setTokenEnhancer(endpoints.getTokenEnhancer());
        tokenServices.setAccessTokenValiditySeconds( (int) TimeUnit.DAYS.toSeconds(30)); // 30天
        endpoints.tokenServices(tokenServices);
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
    public void configure(AuthorizationServerSecurityConfigurer oauthServer) throws Exception {
        //enable client to get the authenticated when using the /oauth/token to get a access token
        //there is a 401 authentication is required if it doesn't allow form authentication for clients when access /oauth/token
        oauthServer.tokenKeyAccess("permitAll()").checkTokenAccess(
                "permitAll()");
        oauthServer.allowFormAuthenticationForClients();
    }

}