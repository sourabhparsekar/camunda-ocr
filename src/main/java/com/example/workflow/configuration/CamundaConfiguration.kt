package com.example.workflow.configuration

import com.example.workflow.model.FileFormFieldType
import org.camunda.bpm.engine.spring.SpringProcessEngineConfiguration
import org.camunda.bpm.spring.boot.starter.configuration.impl.AbstractCamundaConfiguration
import org.springframework.stereotype.Component

@Component
class CamundaConfiguration : AbstractCamundaConfiguration(){

    override fun preInit(processEngineConfiguration: SpringProcessEngineConfiguration?) {
        super.preInit(processEngineConfiguration)
        processEngineConfiguration?.customFormTypes?.add(FileFormFieldType())
    }

}