package com.example.workflow.controller

import com.example.workflow.utils.Constants
import org.camunda.bpm.engine.variable.impl.value.FileValueImpl
import org.camunda.bpm.engine.variable.type.FileValueType
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.multipart.MultipartFile
import java.nio.charset.Charset
import javax.activation.MimetypesFileTypeMap
import javax.validation.constraints.NotEmpty
import javax.validation.constraints.NotNull
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.responses.ApiResponse
import org.camunda.bpm.engine.HistoryService
import org.camunda.bpm.engine.RuntimeService
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity

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
    fun ocrDocuments(
            @Parameter(
                    required = true,
                    name = "document",
                    description = "Upload Document Image to be OCRed"
            ) document: @NotNull(message = "Document/Image is mandatory") MultipartFile
    ): ResponseEntity<*>? {

        val documentName: String = document.originalFilename;

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

        val responseVariables = instance.variables

        val data = processInstanceId

        return if (data != null) {
            ResponseEntity.ok().body("OCR Completed Successfully.")
        } else {
            ResponseEntity.unprocessableEntity().body("OCR processing failed")
        }

    }
}