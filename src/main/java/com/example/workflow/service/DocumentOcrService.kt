package com.example.workflow.service

import org.camunda.bpm.engine.delegate.DelegateExecution
import org.camunda.bpm.engine.delegate.JavaDelegate
import org.springframework.stereotype.Service

@Service("documentOcrService")
class DocumentOcrService : JavaDelegate {
    override fun execute(p0: DelegateExecution?) {
        // TODO("Not yet implemented")
    }
}