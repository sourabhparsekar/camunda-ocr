package com.example.workflow.controller

import com.example.workflow.utils.Constants
import com.example.workflow.utils.WorkflowLogger
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.responses.ApiResponse
import org.camunda.bpm.engine.RuntimeService
import org.camunda.bpm.engine.variable.impl.value.FileValueImpl
import org.camunda.bpm.engine.variable.type.FileValueType
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.multipart.MultipartFile
import java.nio.charset.Charset
import javax.activation.MimetypesFileTypeMap
import javax.servlet.http.HttpServletResponse
import javax.validation.constraints.NotNull

@RestController
class DocumentOcrController {

    private val logger = LoggerFactory.getLogger(this.javaClass)

    @Autowired
    private lateinit var runtimeService: RuntimeService

    @PostMapping(
        value = ["/document"],
        consumes = ["multipart/form-data", MediaType.APPLICATION_OCTET_STREAM_VALUE]
    )
    @Operation(summary = "Perform Document OCR")
    @ApiResponse
    fun ocrDocuments(
        @Parameter(
            required = true,
            name = "document",
            description = "Upload Document Image to be OCRed"
        ) document: @NotNull(message = "Document/Image is mandatory") MultipartFile
    ): ResponseEntity<*>? {

        val documentName: String = document.originalFilename

        WorkflowLogger.info(logger, "OCR Request received", "File: $documentName")

        var documentFileValue = FileValueImpl(
            document.bytes,
            FileValueType.FILE,
            documentName,
            MimetypesFileTypeMap().getContentType(documentName),
            Charset.defaultCharset().name()
        )

        var instance = runtimeService
            .createProcessInstanceByKey("OcrDocument")
            .setVariable(Constants.DOCUMENT, documentFileValue)
            .executeWithVariablesInReturn()

        val processInstanceId = instance.processInstanceId
        WorkflowLogger.info(logger, "Camunda Workflow Completed", "Instance Id: $processInstanceId")

        val responseVariables = instance.variables

        return if (responseVariables.containsKey(Constants.`OCR RESPONSE STATUS`)
            && responseVariables[Constants.`OCR RESPONSE STATUS`] as Boolean
        ) {
            ResponseEntity.ok().body(responseVariables[Constants.`OCR RESPONSE`])
        } else {
            ResponseEntity.unprocessableEntity().body(responseVariables[Constants.`OCR RESPONSE`])
        }

    }
}