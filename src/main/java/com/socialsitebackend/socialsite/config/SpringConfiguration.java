package com.socialsitebackend.socialsite.config;

import com.socialsitebackend.socialsite.post.PostFactory;
import com.socialsitebackend.socialsite.user.UserFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
public class SpringConfiguration {

    @Bean
    public PostFactory postFactory() {
        return new PostFactory(userFactory() );
    }

    @Bean
    public UserFactory userFactory() {
        return new UserFactory(new BCryptPasswordEncoder());
    }
}
