package com.zzl.meetingroom.config;

import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingClass;
import org.springframework.boot.autoconfigure.security.oauth2.resource.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.client.OAuth2RestOperations;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerEndpointsConfiguration;
import org.springframework.security.oauth2.provider.token.ResourceServerTokenServices;

/**
 * @author zhaozhonglong
 * @version 1.0.0
 * @date 2021/2/16 12:09 下午
 */

@Configuration
@ConditionalOnMissingBean(AuthorizationServerEndpointsConfiguration.class)
public class MyUserInfoTokenServicesConfiguration {
    @Configuration
    @ConditionalOnMissingClass("org.springframework.social.connect.support.OAuth2ConnectionFactory")
    protected static class UserInfoTokenServicesConfiguration {

        private final ResourceServerProperties sso;

        private final OAuth2RestOperations restTemplate;

        private final AuthoritiesExtractor authoritiesExtractor;

        private final PrincipalExtractor principalExtractor;

        public UserInfoTokenServicesConfiguration(ResourceServerProperties sso,
                                                  UserInfoRestTemplateFactory restTemplateFactory,
                                                  ObjectProvider<AuthoritiesExtractor> authoritiesExtractor,
                                                  ObjectProvider<PrincipalExtractor> principalExtractor) {
            this.sso = sso;
            this.restTemplate = restTemplateFactory.getUserInfoRestTemplate();
            this.authoritiesExtractor = authoritiesExtractor.getIfAvailable();
            this.principalExtractor = principalExtractor.getIfAvailable();
        }

        @Bean
        @ConditionalOnMissingBean(ResourceServerTokenServices.class)
        public MyUserInfoTokenServices userInfoTokenServices() {
            MyUserInfoTokenServices services = new MyUserInfoTokenServices(
                    this.sso.getUserInfoUri(), this.sso.getClientId());
            services.setRestTemplate(this.restTemplate);
            services.setTokenType(this.sso.getTokenType());
            if (this.authoritiesExtractor != null) {
                services.setAuthoritiesExtractor(this.authoritiesExtractor);
            }
            if (this.principalExtractor != null) {
                services.setPrincipalExtractor(this.principalExtractor);
            }
            return services;
        }

    }

}
