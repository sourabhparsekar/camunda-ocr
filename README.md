# OCR using Camunda Workflow
***
![Camunda + Springboot](./documentation/images/camunda%20springboot.png)

**_Powered by:_** ![OCR Space Logo](./documentation/images/ocrspacelogo.png) ![Kotlin Logo](./documentation/images/kotlinlogo.png)

## Introduction:
This standalone process application is a kotlin example using [Camunda Workflow](https://camunda.com/) 
& [Springboot](https://spring.io/projects/spring-boot) with JUnit5 Test cases. 
As for the use case, we will try to _ocr a document using open [OCR Space API](https://ocr.space/OCRAPI)_.



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

***
## Code Configuration

### Camunda Springboot Initializer

Camunda Springboot project can be created by [Camunda Platform Initializer](https://start.camunda.com/) which contains Spring Boot framework. This makes Spring ready to work inside your camunda workflow application.

These starters pre-configure the Camunda process engine, REST API and Web applications, so they can easily be used in the standalone process application.

For detailed notes and guides on setup, refer [Camunda Docs-Springboot](https://docs.camunda.org/get-started/spring-boot/)

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

add startup logs here

```
- Refer to [running-your-application](https://docs.spring.io/spring-boot/docs/1.5.16.RELEASE/reference/html/using-boot-running-your-application.html) for more help with running boot application.

### Instantiate new process to perform OCR

#### 1. Open & Login Camunda in Browser

- On your local machine, you can access Camunda using [Camunda Home Page](http://localhost:10101/camunda-ocr) url
- Credentials are configured in [application.yaml](./src/main/resources/application.yaml) file in key `camunda.bpm.admin-user`. Default credentials are
  > `username:` demo   
  `password:` demo

![camunda homepage](./documentation/images/camunda-homepage.png)

#### 2. Navigate to Camunda Cockpit to view the flow

- `Cockpit` helps us to visualise which step our process is. It would also give us the process instance id.

![camunda cockpit](./documentation/images/camunda-cockpit.png)

- You can now click on `Document OCR` Process Definition to view the process.

![camunda activity instance](./documentation/images/camunda-activity-instance.png)


#### 3. To perform document ocr, Open Swagger API Browser

- URL to access [Swagger API](http://localhost:10101/camunda-ocr/swagger-ui.html)

#### 4. Use Try it out to test the API

- Click on `Try it out` to enable the API. 

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
