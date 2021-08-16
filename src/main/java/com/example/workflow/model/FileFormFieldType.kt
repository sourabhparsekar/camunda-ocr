package com.example.workflow.model

import org.camunda.bpm.engine.impl.form.type.SimpleFormFieldType
import org.camunda.bpm.engine.variable.Variables
import org.camunda.bpm.engine.variable.value.FileValue
import org.camunda.bpm.engine.variable.value.TypedValue
import java.io.File

class FileFormFieldType : SimpleFormFieldType() {

    private val formType = "file"

    override fun getName(): String {
        return formType
    }

    override fun convertFormValueToModelValue(propertyValue: Any?): Any? {
        return null
    }

    override fun convertModelValueToFormValue(modelValue: Any?): String? {
        return null
    }

    override fun convertValue(propertyValue: TypedValue?): TypedValue {
        return if (propertyValue is FileValue) {
            propertyValue
        } else {
            val value = propertyValue!!.value
            if (value == null) {
                Variables.fileValue("null").create()
            } else {
                Variables.fileValue(value as File)
            }
        }
    }
}