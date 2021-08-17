package com.example.workflow.controller

import com.example.workflow.ProcessFlowTest
import com.example.workflow.utils.Constants
import com.example.workflow.utils.TestUtils
import org.camunda.bpm.engine.RuntimeService
import org.camunda.bpm.engine.test.assertions.bpmn.BpmnAwareTests
import org.camunda.bpm.scenario.ProcessScenario
import org.camunda.bpm.scenario.Scenario
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.ArgumentMatchers
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.HttpEntity
import org.springframework.http.MediaType
import org.springframework.mock.web.MockMultipartFile
import org.springframework.test.context.TestPropertySource
import org.springframework.web.client.RestTemplate
import org.springframework.web.multipart.MultipartFile
import java.net.URI
import java.net.URISyntaxException
import java.util.*

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(locations = ["classpath:/application-test.properties"])
class DocumentOcrControllerProcessTest {
    var variables: MutableMap<String, Any>? = null

    @Mock
    private lateinit var scenario: ProcessScenario

    @MockBean
    private lateinit var restTemplate: RestTemplate

    @BeforeEach
    fun setup() {
        MockitoAnnotations.openMocks(this)

        variables = HashMap()
      //  variables.set(Constants.DOCUMENT, "test")

        val documentFile: MultipartFile =
                MockMultipartFile("test-document-file.png", "test-document-file.png", MediaType.IMAGE_PNG_VALUE, "THIS IS A TEMPORARY DOCUMENT FILE CREATED.".toByteArray())
        (variables as HashMap<String, Any>)[Constants.DOCUMENT] = documentFile.getResource()

    }

    @Test
    fun testOcrDocuments() {
        Mockito.`when`(restTemplate!!.postForObject(
                ArgumentMatchers.any(URI::class.java),
                ArgumentMatchers.any(HttpEntity::class.java),
                ArgumentMatchers.eq(String::class.java))).thenReturn(TestUtils.getOcrResponse())

        val handler = Scenario.run(scenario).startByKey("OcrDocument", variables).execute()
      //  BpmnAwareTests.assertThat(handler.instance(scenario)).variables().containsEntry(Constants.REQUEST_STATUS, Status.SUCCESS)
    }
}