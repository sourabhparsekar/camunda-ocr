package com.example.workflow.controller

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
import org.camunda.bpm.engine.HistoryService
import org.camunda.bpm.engine.RuntimeService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity

@RestController
class DocumentOcrController {

    @Autowired
    private lateinit var runtimeService: RuntimeService

    @Autowired
    private lateinit var historyService: HistoryService

    @PostMapping(
            value = ["/ocr-document"],
            consumes = ["multipart/form-data", MediaType.APPLICATION_OCTET_STREAM_VALUE]
    )
    @Operation(summary = "Submit Documents for Verification")
    fun verifyDocuments(
            @Parameter(
                    required = true,
                    name = "firstName",
                    description = "Enter mandatory First Name"
            ) firstName: @NotEmpty(message = "First Name is required field") String,
            @Parameter(required = true, name = "lastName", description = "Enter mandatory Last Name") lastName: @NotEmpty(
                    message = "Last Name is required field"
            ) String,
            @Parameter(
                    required = true,
                    name = "document",
                    description = "Upload mandatory Document File"
            ) document: @NotNull(message = "Document is mandatory") MultipartFile
    ): ResponseEntity<*>? {

        val documentName: String = document.originalFilename;

        val requestVariables = HashMap<String, Any>();
        requestVariables["firstname"] = firstName;
        requestVariables["lastname"] = lastName;

        var documentFileValue = FileValueImpl(
                document.bytes,
                FileValueType.FILE,
                documentName,
                MimetypesFileTypeMap().getContentType(documentName),
                Charset.defaultCharset().name()
        )
        requestVariables["document"] = documentFileValue

        var instance = runtimeService
                .createProcessInstanceByKey("OcrDocument")
                .setVariables(requestVariables)
                .executeWithVariablesInReturn()

        val processInstanceId = instance.processInstanceId
        val responseVariables = instance.variables

        val data = processInstanceId

        return if (data != null) {
            ResponseEntity.ok().body("OCR Completed")
        } else {
            ResponseEntity.unprocessableEntity().body("OCR processing failed")
        }

    }
}