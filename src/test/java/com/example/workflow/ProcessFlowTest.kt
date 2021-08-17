package com.example.workflow

import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.TestPropertySource

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(locations = ["classpath:/application-test.properties"])
abstract class ProcessFlowTest { // abstract class to test process workflow
}