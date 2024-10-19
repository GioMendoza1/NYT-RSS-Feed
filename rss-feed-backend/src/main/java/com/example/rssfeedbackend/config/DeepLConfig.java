package com.example.rssfeedbackend.config;

import com.deepl.api.Translator;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

@Configuration
@RequiredArgsConstructor
public class DeepLConfig {
    private final Environment env;

    @Bean
    public Translator deepLTranslator() {
        return new Translator(env.getProperty("deepl.auth.key"));
    }
}
