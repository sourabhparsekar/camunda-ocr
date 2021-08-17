package com.example.workflow.configuration

import org.springframework.boot.web.client.RestTemplateBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.client.RestTemplate

/**
 * from Spring > 1.4 we need to override RestTemplate creation
 */
@Configuration
open class RestTemplateConfiguration {

    @Bean
    open fun restTemplate(restTemplateBuilder: RestTemplateBuilder): RestTemplate {
        return restTemplateBuilder.build()
    }

}