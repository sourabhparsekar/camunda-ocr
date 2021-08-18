package com.example.workflow.service

import com.example.workflow.utils.Constants
import com.example.workflow.utils.TestUtils
import org.camunda.bpm.engine.delegate.DelegateExecution
import org.camunda.bpm.extension.mockito.CamundaMockito
import org.json.JSONObject
import org.junit.BeforeClass
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.mockito.MockitoAnnotations

internal class ProcessOcrResponseTest {

    val delegateExecution: DelegateExecution = CamundaMockito.delegateExecutionFake()

    @BeforeEach
    fun setUp() {
        val response = JSONObject(TestUtils.ocrResponse)
        delegateExecution.setVariable(Constants.`OCR RESPONSE STATUS`, true)
        delegateExecution.setVariable(Constants.`OCR RESPONSE`, response.toMap())
    }

    @Test
    fun execute() {
        val processOcrResponse = ProcessOcrResponse()
        processOcrResponse.execute(delegateExecution)

        val parsedResults: List<MutableMap<String, Any>> = delegateExecution.getVariable(Constants.`OCR RESPONSE`) as List<MutableMap<String, Any>>

        for (parsedResult in parsedResults) {
            Assertions.assertFalse(parsedResult.containsKey("TextOrientation"))
            Assertions.assertFalse(parsedResult.containsKey("ParsedText"))
            Assertions.assertFalse(parsedResult.containsKey("FileParseExitCode"))
            Assertions.assertFalse(parsedResult.containsKey("ErrorDetails"))
            Assertions.assertFalse(parsedResult.containsKey("ErrorMessage"))
        }
    }
}