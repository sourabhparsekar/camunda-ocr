package com.example.workflow.service

import com.example.workflow.utils.Constants
import com.example.workflow.utils.WorkflowLogger
import org.camunda.bpm.engine.delegate.DelegateExecution
import org.camunda.bpm.engine.delegate.JavaDelegate
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service

@Service("setErrorResponse")
class ProcessErrorOcrResponse : JavaDelegate {

    private val logger = LoggerFactory.getLogger(this.javaClass)

    override fun execute(execution: DelegateExecution?) {

        WorkflowLogger.info(logger, "Prepare Error Response", "Process instance id ${execution!!.processInstanceId}")

        if (execution.hasVariable(Constants.`OCR RESPONSE`)) {
            var map: MutableMap<String, Any> =
                execution.getVariable(Constants.`OCR RESPONSE`) as MutableMap<String, Any>

            if (map != null) {

                val parsedResults: List<MutableMap<String, Any>> = map["ParsedResults"] as List<MutableMap<String, Any>>

                //clean up the json
                for (parsedResult in parsedResults) {
                    parsedResult.remove("TextOrientation")
                    parsedResult.remove("TextOverlay")
                }

                execution.setVariable(Constants.`OCR RESPONSE`, parsedResults)
            } else {
                val errorMap: MutableMap<String, Any> = mutableMapOf()
                errorMap.put("FileParseExitCode", HttpStatus.UNPROCESSABLE_ENTITY)
                errorMap.put("ErrorDetails", HttpStatus.UNPROCESSABLE_ENTITY.name)
                errorMap.put("ErrorMessage", Constants.`DOCUMENT OCR FAILED`)

                execution.setVariable(Constants.`OCR RESPONSE`, mutableListOf(errorMap))
            }
        } else if (execution.hasVariable(Constants.`OCR RESPONSE EXCEPTION`)) {
            val errorMap: MutableMap<String, Any> = mutableMapOf()
            errorMap.put("FileParseExitCode", HttpStatus.INTERNAL_SERVER_ERROR)
            errorMap.put("ErrorDetails", Constants.`INTERNAL SERVE ERROR`)
            errorMap.put("ErrorMessage", execution.getVariable(Constants.`OCR RESPONSE EXCEPTION`))

            execution.setVariable(Constants.`OCR RESPONSE`, mutableListOf(errorMap))
        } else {
            val errorMap: MutableMap<String, Any> = mutableMapOf()
            errorMap.put("FileParseExitCode", HttpStatus.INTERNAL_SERVER_ERROR)
            errorMap.put("ErrorDetails", Constants.`INTERNAL SERVE ERROR`)
            errorMap.put("ErrorMessage", Constants.`DOCUMENT OCR FAILED`)

            execution.setVariable(Constants.`OCR RESPONSE`, mutableListOf(errorMap))
        }


        WorkflowLogger.error(
            logger,
            "OCR Space Response Error",
            (execution.getVariable(Constants.`OCR RESPONSE`) as List<MutableMap<String, Any>>).toString()
        )

    }
}