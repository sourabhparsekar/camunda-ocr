package com.example.workflow.service

import org.camunda.bpm.engine.delegate.DelegateExecution
import org.camunda.bpm.engine.delegate.JavaDelegate
import org.springframework.stereotype.Service

@Service("setLineResponse")
class ProcessOcrResponse : JavaDelegate{
    override fun execute(execution: DelegateExecution?) {
        println("SUCCESS RESPONSE")
    }
}