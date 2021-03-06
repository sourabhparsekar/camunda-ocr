package com.example.workflow.controller

import com.example.workflow.utils.Constants
import com.example.workflow.utils.TestUtils
import org.json.JSONArray
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.ArgumentMatchers.any
import org.mockito.ArgumentMatchers.eq
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.core.io.ByteArrayResource
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.mock.web.MockMultipartFile
import org.springframework.test.context.TestPropertySource
import org.springframework.util.LinkedMultiValueMap
import org.springframework.util.MultiValueMap
import org.springframework.web.client.RestTemplate
import org.springframework.web.multipart.MultipartFile
import java.net.URI


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(locations = ["classpath:/application-test.properties"])
class DocumentOcrControllerProcessTest {

    @Value("\${local.server.port}")
    private var randomServerPort: Int = 0

    @Value("\${server.servlet.context-path}")
    private lateinit var contextPath: String
//
//    @Mock
//    private lateinit var scenario: ProcessScenario

    @MockBean
    private lateinit var restTemplate: RestTemplate

    private val formData: MultiValueMap<String, Any> = LinkedMultiValueMap()

    @BeforeEach
    fun setup() {
        MockitoAnnotations.openMocks(this)

        val documentFile: MultipartFile =
            MockMultipartFile(
                "test-document-file.png",
                "test-document-file.png",
                MediaType.IMAGE_PNG_VALUE,
                "THIS IS A TEMPORARY DOCUMENT FILE CREATED.".toByteArray()
            )

        formData.add(Constants.DOCUMENT, object : ByteArrayResource(documentFile.bytes) {
            // This is required to be set as the byte array doesn't have the file name, thus
            // failing the upload
            override fun getFilename(): String {
                return documentFile.originalFilename
            }
        }
        )

        Mockito.`when`(
            restTemplate.postForObject(
                eq("http://127.0.0.1/parse/image"),
                any(HttpEntity::class.java),
                eq(String::class.java)
            )
        ).thenReturn(TestUtils.ocrResponse)

        println("PORT :: $randomServerPort")
    }

    @Test
    fun testOcrDocuments() {


        val headers: HttpHeaders = HttpHeaders()
        headers.contentType = MediaType.MULTIPART_FORM_DATA

        val entity: HttpEntity<MultiValueMap<String, Any>> = HttpEntity(formData, headers)

        val url: URI = URI("http://127.0.0.1:$randomServerPort$contextPath/document")
        println(url)

        val responseEntity: ResponseEntity<String> = TestRestTemplate().postForEntity(
            url,
            entity,
            String::class.java
        )

        val response: String = responseEntity.body
        System.out.println("RESPONSE - $response")
        val responseJsonArray = JSONArray(response)

        Assertions.assertEquals(1, responseJsonArray.length())

        val responseJson = responseJsonArray.getJSONObject(0)
        Assertions.assertEquals("Total lines: 7", responseJson.getJSONObject("TextOverlay").getString("Message"))


    }
}