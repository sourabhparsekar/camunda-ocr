package com.example.workflow.service

import org.camunda.bpm.engine.delegate.DelegateExecution
import org.camunda.bpm.engine.delegate.JavaDelegate
import org.springframework.stereotype.Service

@Service("setErrorResponse")
class ProcessErrorOcrResponse : JavaDelegate{
    override fun execute(execution: DelegateExecution?) {
        println("ERROR RESPONSE")
    }
}