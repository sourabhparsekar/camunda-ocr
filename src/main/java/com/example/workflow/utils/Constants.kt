package com.example.workflow.utils

class Constants private constructor() {

    companion object {
        const val `OCR RESPONSE` = "ocr-response"
        const val DOCUMENT = "document"
        const val `DOCUMENT OCR FAILED` = "document ocr failed"
        const val `INTERNAL SERVE ERROR` = "internal server error"
    }

    init {
        throw IllegalStateException("Constants Utility Class. Cannot be instantiated")
    }
}
