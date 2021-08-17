# OCR using Camunda Workflow
***
![Camunda + Springboot](./documentation/images/camunda%20springboot.png)

**_Powered by:_** ![OCR Space Logo](./documentation/images/ocrspacelogo.png) ![Kotlin Logo](./documentation/images/kotlinlogo.png)

## Introduction:
This standalone process application is a kotlin example using [Camunda Workflow](https://camunda.com/) 
& [Springboot](https://spring.io/projects/spring-boot) with JUnit5 Test cases. 
As for the use case, we will try to _ocr a document using open [OCR Space API](https://ocr.space/OCRAPI)_.

## OCR Space API

The OCR Space API provides a simple way of parsing images and multi-page PDF documents (PDF OCR).
The extracted text results will be returned in a JSON format.

To get a free version of API Key, you can register with your email at [OCR Space site](https://ocr.space/ocrapi#free)

The free OCR API plan we are using to demo the application, has 
* a rate limit of 500 requests within one day per IP address to prevent accidental spamming
* a limit of 25000 request per month
* a max file size of 1 MB
* a max of 3 pages can be OCRed per document 

You can check the API performance and uptime at the [API status page](https://status.ocr.space/)

## Camunda Workflow:
As per [Wikipedia](https://en.wikipedia.org/wiki/Camunda), Camunda Platform is an open-source workflow
and decision automation platform. Camunda Platform ships with tools for creating workflow and decision 
models, operating deployed models in production, and allowing users to execute workflow tasks assigned 
to them.

It is developed in Java and released as open-source software under the terms of Apache License. It 
provides 
* **Business Process Model and Notation** (BPMN) standard compliant workflow engine  
* **Decision Model and Notation** (DMN) standard compliant decision engine 
  
These can be embedded in Java applications and with other languages via REST. For more details on Camunda 
Workflow usage, you can refer to below blogs-
* [BPMN Engines: A Brief Introduction](https://medium.com/nerd-for-tech/bpmn-engines-a-brief-introduction-2123b5e15435)
* [Hands-on - Camunda Workflow Spring-Boot Application](https://medium.com/nerd-for-tech/bpmn2-0-camunda-workflow-spring-boot-application-2381f3d42e5f)  

Below is the workflow that we would use to understand use of Camunda Workflow to perform Document OCR using OCR Space API.
![Camunda Workflow](./src/main/resources/document_ocr.png)

***
## Code Configuration

### Application Configuration
> Spring-Boot:  (v2.4.3)  
Camunda Platform: (v7.15.0)  
Camunda Platform Spring Boot Starter: (v7.15.0)

### Default Ports

On systems running the `API`, it is recommended to use the below port for starting an instance of the API.

> `10101` – default api listening port

### API Build & deploy from GIT repo
Below section would cover high level tasks required to configure and deploy api jar using Apache Maven

1. Clone repository on local system. By default, jars would be taken from Maven Central Repository.
2. Update properties in logback-spring.xml, application.yaml if applicable
3. Build with maven task `mvnw clean install`
4. Copy .jar file from /target/ to your `deployment-directory`
5. Environment specific `application.yaml` & `logback-spring.xml` are to be modified and placed in deployment-directory along with `.jar` if applicable
6. Start execution with `java -jar camunda-ocr-<version>.jar`
7. Logs are generated in deployment-directory/logs folder with file 
   name `camunda-ocr-logger.log` or as mentioned in `logback-spring.xml`



## Testing it out

### Run the Springboot application with maven

- You can follow above steps in order to compile, build, package and install jar using maven.

- Use command `mvnw clean install` to build and install the jar

- Use command `mvnw spring-boot:run` to run the springboot application.

- Alternatively you can navigate to file [Application.java](./src/main/java/com/example/workflow/Application.java) and start the application.

- If every thing works then you should see below log in your console
```
2021-08-17 22:17:28,936 INFO  [main] org.springframework.boot.web.embedded.tomcat.TomcatWebServer: Tomcat started on port(s): 10101 (http) with context path '/camunda-ocr'
2021-08-17 22:17:28,960 INFO  [main] com.example.workflow.Application: Started Application in 21.931 seconds (JVM running for 23.514)
2021-08-17 22:17:28,966 INFO  [main] org.camunda.bpm.engine.jobexecutor: ENGINE-14014 Starting up the JobExecutor[org.camunda.bpm.engine.spring.components.jobexecutor.SpringJobExecutor].
2021-08-17 22:17:28,969 INFO  [JobExecutor[org.camunda.bpm.engine.spring.components.jobexecutor.SpringJobExecutor]] org.camunda.bpm.engine.jobexecutor: ENGINE-14018 JobExecutor[org.camunda.bpm.engine.spring.components.jobexecutor.SpringJobExecutor] starting to acquire jobs
```
- Refer to [running-your-application](https://docs.spring.io/spring-boot/docs/1.5.16.RELEASE/reference/html/using-boot-running-your-application.html) for more help with running boot application.

### Instantiate new process to perform OCR

#### 1. Open & Login Camunda in Browser

- On your local machine, you can access Camunda using [Camunda Home Page](http://localhost:10101/camunda-ocr) url
- Credentials are configured in [application.yaml](./src/main/resources/application.yaml) file in key `camunda.bpm.admin-user`. Default credentials are
  > `username:` demo   
  `password:` demo

![camunda homepage](./documentation/images/camunda-homepage.png)

#### 2. Navigate to Camunda Cockpit to monitor the flow

- `Cockpit` helps us to visualise which step our process is. It would also give us the process instance id.

![camunda cockpit](./documentation/images/camunda-cockpit.png)

- You can now click on `Running Process Instance` > `Ocr Document` Process Definition to view the running processes.

![camunda activity instance](./documentation/images/camunda-activity-instance.png)

_**Note**: You may not see the running process as it completes very quick._

#### 3. To perform document ocr, Open Swagger API Browser

- URL to access [Swagger OpenAPI Apecification](http://localhost:10101/camunda-ocr/swagger-ui.html)

#### 4. Use Try it out to test the API

- Click on `Try it out` in right corner to enable the API.

- Click `Browse` to upload a file

- Click `Execute` to send the request. You can check the response which is processed via triggering a camunda workflow.

- If you want to read more about swagger, then you can go through blog 
  - [OAS Swagger UI Setup](https://medium.com/nerd-for-tech/open-api-specification-swagger3-fc9ad3bbacdd)
  - [OAS Swagger Authentication](https://medium.com/nerd-for-tech/openapi-specification-swagger-authentication-c150f86748ea)

### Check Logs

- In the end, all the steps we did will be tracked via the logs in the console. If you filter the logs using keyword `workflow-service-info` then you would see below


Logs for **Process instantiation**
```

-- add logs for process instantiation

```

_Thus we have implemented Document OCR using Kotlin and Camunda Workflow!!_

***
### Appendix - Deployment as Docker container

To deploy API as Docker Container refer [Docker-Image-Deployment](./documentation/deployment/Readme.md)
