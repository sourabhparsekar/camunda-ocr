package com.example.workflow.service

import com.example.workflow.utils.Constants
import com.example.workflow.utils.WorkflowLogger
import org.camunda.bpm.engine.delegate.DelegateExecution
import org.camunda.bpm.engine.delegate.JavaDelegate
import org.json.JSONObject
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

@Service("setLineResponse")
class ProcessOcrResponse : JavaDelegate{

    private val logger = LoggerFactory.getLogger(this.javaClass)

    override fun execute(execution: DelegateExecution?) {

        WorkflowLogger.info(logger, "Prepare & Cleanup Response", "Process instance id ${execution!!.processInstanceId}")

        var map:MutableMap<String, Any> = execution?.getVariable(Constants.`OCR RESPONSE`) as MutableMap<String, Any>

        val parsedResults: List<MutableMap<String, Any>> = map["ParsedResults"] as List<MutableMap<String, Any>>

        //clean up the json
        for(parsedResult in parsedResults){
//        for (page in 0 until parsedResultsJsonArray.length()) {
//            val parsedResult: MutableMap<String, Any> = parsedResults as MutableMap<String, Any>
            parsedResult.remove("TextOrientation")
            parsedResult.remove("ParsedText")
            if(parsedResult.containsKey("FileParseExitCode")
                && parsedResult["FileParseExitCode"] as Int == 1) {
                //success
                parsedResult.remove("FileParseExitCode")
                parsedResult.remove("ErrorDetails")
                parsedResult.remove("ErrorMessage")
            } else {
                parsedResult.remove("TextOverlay")
            }

        }

        execution.setVariable(Constants.`OCR RESPONSE`, parsedResults)

    }
}

