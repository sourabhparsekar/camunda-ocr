/*
package com.example.workflow.controller;

import com.example.workflow.ProcessFlowTest;
import com.example.workflow.utils.Constants;
import com.example.workflow.utils.TestUtils;
import net.minidev.json.JSONArray;
import org.camunda.bpm.scenario.ProcessScenario;
import org.json.JSONObject;
import org.junit.jupiter.api.*;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockserver.integration.ClientAndServer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Map;
import java.util.UUID;

public class DocumentOcrControllerTest extends ProcessFlowTest {

    private static ClientAndServer mockServer;

    private final TestUtils testUtils = new TestUtils();

    @Value("${server.servlet.context-path}")
    String contextPath;

    @Value("${local.server.port}")
    int randomServerPort;

    private Integer port;

    @BeforeAll
    static void startServer() {
        mockServer = ClientAndServer.startClientAndServer(10005);
        System.out.println("SERVER RUNNING >> " + mockServer.isRunning());
    }

    @AfterAll
    static void stopServer() {
        mockServer.stop();
        System.out.println("SERVER RUNNING >> " + mockServer.isRunning());
    }

    @BeforeEach
    void init() {
        port = mockServer.getPort();
        System.out.println("TEST SERVER RUNNING >> " + randomServerPort);
        System.out.println("MOCK SERVER RUNNING >> " + port);
    }

    @Test
    void testOcrDocuments() throws URISyntaxException {

        try {
            testUtils.createExpectationForOcrApi(port);
            System.out.println("Ocr Response Expectation is set");
        } catch (Exception e) {
            System.err.println("ocr response " + e.getMessage());
            e.printStackTrace();
            Assertions.fail();
        }

        URI url = new URI("http://127.0.0.1:" + randomServerPort + contextPath + "/document");
        System.out.println(url);

        MultiValueMap<String, Object> formData = new LinkedMultiValueMap<>();

        MultipartFile documentFile = new MockMultipartFile("test-document-file.png", "test-document-file.png", MediaType.IMAGE_PNG_VALUE, "THIS IS A TEMPORARY DOCUMENT FILE CREATED.".getBytes());

        formData.add("document", documentFile.getResource());
        System.out.println("Form Data in multipart is set");

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);

        HttpEntity<MultiValueMap<String, Object>> entity = new HttpEntity<>(formData, headers);

        ResponseEntity<String> responseEntity = new TestRestTemplate().postForEntity(
                url, entity, String.class);

        String response = responseEntity.getBody();
        System.out.println("RESPONSE - " + response);
        JSONObject responseJson = new JSONObject(response);
        System.out.println(responseJson);

        //JSONObject data = responseJson.getJSONObject("data");
        //Assertions.assertEquals(Constants.RESPONSE_SUCCESS, responseJson.getString("status"));

    }
}
*/
