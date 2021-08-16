package com.example.workflow.configuration

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Configuration

@Configuration
open class ApplicationProperties {

    @Value("\${ocr-space.key}")
    lateinit var ocrKey: String

    @Value("\${ocr-space.post-url}")
    lateinit var ocrUrl: String

}