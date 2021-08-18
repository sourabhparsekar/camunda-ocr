package com.example.workflow.service

import com.example.workflow.configuration.ApplicationProperties
import com.example.workflow.utils.Constants
import com.example.workflow.utils.WorkflowLogger
import org.camunda.bpm.engine.delegate.DelegateExecution
import org.camunda.bpm.engine.delegate.JavaDelegate
import org.camunda.bpm.engine.variable.impl.value.FileValueImpl
import org.json.JSONObject
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.core.io.ByteArrayResource
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.stereotype.Service
import org.springframework.util.LinkedMultiValueMap
import org.springframework.util.MultiValueMap
import org.springframework.web.client.RestClientException
import org.springframework.web.client.RestTemplate


@Service("documentOcrService")
class DocumentOcrService : JavaDelegate {

    private val logger = LoggerFactory.getLogger(this.javaClass)

    @Autowired
    private lateinit var applicationProperties: ApplicationProperties

    @Autowired
    private lateinit var restTemplate: RestTemplate

    /**
     * Method will create document ocr request and set the response
     */
    override fun execute(execution: DelegateExecution?) {

        WorkflowLogger.info(logger, "Prepare & Send Request", "Process instance id ${execution!!.processInstanceId}")

        try {

            //1 get the document
            val documentTypedFileValue: FileValueImpl = execution.getVariableTyped(Constants.DOCUMENT) as FileValueImpl

            val formData: MultiValueMap<String, Any> = LinkedMultiValueMap()

            formData.add(
                "language",
                "eng"
            )

            formData.add(
                "isOverlayRequired",
                true
            )

            formData.add(
                "iscreatesearchablepdf",
                false
            )

            formData.add(
                "issearchablepdfhidetextlayer",
                false
            )

            formData.add(
                "file",
                object : ByteArrayResource(documentTypedFileValue.byteArray) {
                    // This is required to be set as the byte array doesn't have the file name, thus
                    // failing the upload
                    override fun getFilename(): String {
                        return documentTypedFileValue.filename
                    }
                }
            )

            logger.debug("Form Data in multipart is set")

            val headers = HttpHeaders()
            headers.set("apikey", applicationProperties.ocrKey)
            headers.contentType = MediaType.MULTIPART_FORM_DATA
            logger.debug("API Key and headers are set")

            val entity = HttpEntity(formData, headers)

            val resString: String =
                restTemplate.postForObject(
                    applicationProperties.ocrUrl,
                    entity,
                    String::class.java
                )
            logger.debug("Response received from OCR Space service")

            val response = JSONObject(resString)

            if (response.has(Constants.`IS ERRORED ON PROCESSING`)
                && !(response[Constants.`IS ERRORED ON PROCESSING`] as Boolean)
            ) {
                WorkflowLogger.info(
                    logger,
                    "Document OCR",
                    "Response Data received"
                )
                execution.setVariable(Constants.`OCR RESPONSE STATUS`, true)
            } else {
                WorkflowLogger.error(
                    logger,
                    Constants.`DOCUMENT OCR FAILED`,
                    "Failed to submit document for OCR. $resString"
                )
                execution.setVariable(Constants.`OCR RESPONSE STATUS`, false)
            }

            execution.setVariable(Constants.`OCR RESPONSE`, response.toMap())

        } catch (e: RestClientException) {
            WorkflowLogger.error(
                logger,
                Constants.`DOCUMENT OCR FAILED`,
                "Rest Client Exception.",
                e
            )
            execution.setVariable(Constants.`OCR RESPONSE STATUS`, false)
            execution.setVariable(Constants.`OCR RESPONSE EXCEPTION`, e.message)
        } catch (e: Exception) {
            WorkflowLogger.error(
                logger,
                Constants.`DOCUMENT OCR FAILED`,
                "Unknown Exception.",
                e
            )
            execution.setVariable(Constants.`OCR RESPONSE STATUS`, false)
            execution.setVariable(Constants.`OCR RESPONSE EXCEPTION`, e.message)
        } finally {
            //delete document
            execution.removeVariable(Constants.DOCUMENT)
        }
    }
}
