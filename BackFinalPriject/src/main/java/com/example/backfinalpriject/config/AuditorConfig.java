package com.example.backfinalpriject.config;

import com.example.backfinalpriject.entity.Member;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.core.userdetails.User;

@Configuration
public class AuditorConfig {

    @Bean
    public AuditorAware<String> customAwareAudit() {
        return new CustomAwareAudit();
    }
}
