package com.example.workflow.utils

class Constants private constructor() {

    companion object {
        const val `IS ERRORED ON PROCESSING` = "IsErroredOnProcessing"
        const val `OCR RESPONSE STATUS` = "ocr_response_status"
        const val `OCR RESPONSE` = "ocr_response"
        const val DOCUMENT = "document"
        const val `DOCUMENT OCR FAILED` = "document ocr failed"
        const val `INTERNAL SERVE ERROR` = "internal server error"
    }

    init {
        throw IllegalStateException("Constants Utility Class. Cannot be instantiated")
    }
}
