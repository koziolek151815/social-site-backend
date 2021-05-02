package com.socialsitebackend.socialsite.config;

import com.socialsitebackend.socialsite.factory.PostFactory;
import com.socialsitebackend.socialsite.factory.UserFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
public class Config {

    @Bean
    public PostFactory postFactory() {
        return new PostFactory(userFactory() );
    }

    @Bean
    public UserFactory userFactory() {
        return new UserFactory(new BCryptPasswordEncoder());
    }
}
