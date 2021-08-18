package com.example.workflow.service

import com.example.workflow.utils.Constants
import com.example.workflow.utils.TestUtils
import org.camunda.bpm.engine.delegate.DelegateExecution
import org.camunda.bpm.extension.mockito.CamundaMockito
import org.json.JSONObject
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Order
import org.junit.jupiter.api.Test
import org.mockito.MockitoAnnotations
import org.springframework.http.HttpStatus
import org.springframework.web.client.RestClientException

internal class ProcessErrorOcrResponseTest {

    private val execution: DelegateExecution = CamundaMockito.delegateExecutionFake()

    private val service: ProcessErrorOcrResponse = ProcessErrorOcrResponse()

    @BeforeEach
    fun setUp() {
        MockitoAnnotations.openMocks(this)
    }

    @Test
    @Order(1)
    fun `test ocr response set with message`() {

        val errorData = JSONObject(TestUtils.ocrErrorResponse)

        execution.setVariable(Constants.`OCR RESPONSE`, errorData.toMap())

        service.execute(execution)

        var responseMap =
            (execution.getVariable(Constants.`OCR RESPONSE`) as List<MutableMap<String, Any>>)[0]

        Assertions.assertEquals(4, responseMap.size)

        val expectedResponseMap = mutableMapOf<String,Any>(
            Pair("FileParseExitCode", 0),
            Pair("ErrorDetails", "Test Error Details"),
            Pair("ErrorMessage", "Test Error Message"),
            Pair("ParsedText", "CALIFORNIAA DRIVER LIC")
        )
        Assertions.assertEquals(expectedResponseMap, responseMap)
    }

    @Test
    @Order(2)
    fun `test ocr response set with null message`() {

        execution.setVariable(Constants.`OCR RESPONSE`, mutableMapOf<String, Any>())

        service.execute(execution)

        var responseMap =
            (execution.getVariable(Constants.`OCR RESPONSE`) as List<MutableMap<String, Any>>)[0]

        Assertions.assertEquals(3, responseMap.size)

        val expectedResponseMap = mutableMapOf<String,Any>(
            Pair("FileParseExitCode", HttpStatus.UNPROCESSABLE_ENTITY),
            Pair("ErrorDetails", HttpStatus.UNPROCESSABLE_ENTITY.name),
            Pair("ErrorMessage", Constants.`DOCUMENT OCR FAILED`)
        )
        println(responseMap.toString())
        Assertions.assertEquals(expectedResponseMap.toString(), responseMap.toString())
    }


    @Test
    @Order(3)
    fun `test ocr response set with exception message`() {

        execution.setVariable(Constants.`OCR RESPONSE EXCEPTION`, RestClientException("Test Rest Exception"))

        service.execute(execution)

        var responseMap =
            (execution.getVariable(Constants.`OCR RESPONSE`) as List<MutableMap<String, Any>>)[0]

        Assertions.assertEquals(3, responseMap.size)

        val expectedResponseMap = mutableMapOf<String,Any>(
            Pair("FileParseExitCode", HttpStatus.INTERNAL_SERVER_ERROR),
            Pair("ErrorDetails", Constants.`INTERNAL SERVE ERROR`),
            Pair("ErrorMessage", RestClientException("Test Rest Exception"))
        )
        println(responseMap.toString())
        Assertions.assertEquals(expectedResponseMap.toString(), responseMap.toString())
    }


    @Test
    @Order(4)
    fun `test ocr response set with nothing`() {

        service.execute(execution)

        var responseMap =
            (execution.getVariable(Constants.`OCR RESPONSE`) as List<MutableMap<String, Any>>)[0]

        Assertions.assertEquals(3, responseMap.size)

        val expectedResponseMap = mutableMapOf<String,Any>(
            Pair("FileParseExitCode", HttpStatus.INTERNAL_SERVER_ERROR),
            Pair("ErrorDetails", Constants.`INTERNAL SERVE ERROR`),
            Pair("ErrorMessage", Constants.`DOCUMENT OCR FAILED`)
        )
        println(responseMap.toString())
        Assertions.assertEquals(expectedResponseMap.toString(), responseMap.toString())
    }


}