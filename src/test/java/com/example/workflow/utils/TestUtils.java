package com.example.workflow.utils;

import org.jetbrains.annotations.NotNull;
import org.json.JSONObject;
import org.mockserver.client.server.MockServerClient;
import org.mockserver.matchers.Times;
import org.mockserver.model.Header;
import org.mockserver.model.HttpRequest;
import org.mockserver.model.HttpResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.concurrent.TimeUnit;

import static org.mockserver.matchers.Times.exactly;
import static org.mockserver.model.HttpRequest.request;
import static org.mockserver.model.HttpResponse.response;

/**
 * @implNote Class to add mock server implementations
 */
public class TestUtils {

    public static void createExpectationForOcrApi(Integer port) {
        new MockServerClient("127.0.0.1", port)
                .when(request().withMethod("POST")
                        .withPath("/document"), Times.once())
                .respond(response().withStatusCode(HttpStatus.OK.value())
                        .withHeaders(new Header("Content-Type", "application/json; charset=utf-8"),
                                new Header("Cache-Control", "public, max-age=86400"))
                        .withBody(getOcrResponse())
                        .withDelay(TimeUnit.SECONDS, 1));
    }

    @NotNull
    public static String getOcrResponse() {
        return "{\n" +
                "  \"IsErroredOnProcessing\": false,\n" +
                "  \"ParsedResults\": [\n" +
                "    {\n" +
                "      \"TextOrientation\": \"0\",\n" +
                "      \"ParsedText\": \"DRIVING LICENSE\\r\\n2— JANE SMITH\\r\\n—-3301 niL1990 KINGDOM\\r\\n-01.01.2012cc:ae\\r\\n0101.2020\\r\\nsÆ--SAMPLE12345ABCOEFGHIJKV\\r\\n8303 SAMPLE STREET, LONDONJXYZIAB\\r\\n\",\n" +
                "      \"FileParseExitCode\": 1,\n" +
                "      \"ErrorDetails\": \"\",\n" +
                "      \"ErrorMessage\": \"\",\n" +
                "      \"TextOverlay\": {\n" +
                "        \"Message\": \"Total lines: 7\",\n" +
                "        \"Lines\": [\n" +
                "          {\n" +
                "            \"LineText\": \"DRIVING LICENSE\",\n" +
                "            \"MinTop\": 44,\n" +
                "            \"Words\": [\n" +
                "              {\n" +
                "                \"Left\": 153,\n" +
                "                \"Top\": 44,\n" +
                "                \"Height\": 18,\n" +
                "                \"WordText\": \"DRIVING\",\n" +
                "                \"Width\": 99\n" +
                "              },\n" +
                "              {\n" +
                "                \"Left\": 260,\n" +
                "                \"Top\": 44,\n" +
                "                \"Height\": 18,\n" +
                "                \"WordText\": \"LICENSE\",\n" +
                "                \"Width\": 96\n" +
                "              }\n" +
                "            ],\n" +
                "            \"MaxHeight\": 18\n" +
                "          },\n" +
                "          {\n" +
                "            \"LineText\": \"2— JANE SMITH\",\n" +
                "            \"MinTop\": 97,\n" +
                "            \"Words\": [\n" +
                "              {\n" +
                "                \"Left\": 207,\n" +
                "                \"Top\": 97,\n" +
                "                \"Height\": 13,\n" +
                "                \"WordText\": \"2—\",\n" +
                "                \"Width\": 27\n" +
                "              },\n" +
                "              {\n" +
                "                \"Left\": 236,\n" +
                "                \"Top\": 97,\n" +
                "                \"Height\": 12,\n" +
                "                \"WordText\": \"JANE\",\n" +
                "                \"Width\": 37\n" +
                "              },\n" +
                "              {\n" +
                "                \"Left\": 277,\n" +
                "                \"Top\": 97,\n" +
                "                \"Height\": 12,\n" +
                "                \"WordText\": \"SMITH\",\n" +
                "                \"Width\": 44\n" +
                "              }\n" +
                "            ],\n" +
                "            \"MaxHeight\": 13\n" +
                "          },\n" +
                "          {\n" +
                "            \"LineText\": \"—-3301 niL1990 KINGDOM\",\n" +
                "            \"MinTop\": 134,\n" +
                "            \"Words\": [\n" +
                "              {\n" +
                "                \"Left\": 181,\n" +
                "                \"Top\": 134,\n" +
                "                \"Height\": 17,\n" +
                "                \"WordText\": \"—-3301\",\n" +
                "                \"Width\": 69\n" +
                "              },\n" +
                "              {\n" +
                "                \"Left\": 253,\n" +
                "                \"Top\": 135,\n" +
                "                \"Height\": 16,\n" +
                "                \"WordText\": \"niL1990\",\n" +
                "                \"Width\": 55\n" +
                "              },\n" +
                "              {\n" +
                "                \"Left\": 389,\n" +
                "                \"Top\": 139,\n" +
                "                \"Height\": 12,\n" +
                "                \"WordText\": \"KINGDOM\",\n" +
                "                \"Width\": 69\n" +
                "              }\n" +
                "            ],\n" +
                "            \"MaxHeight\": 17\n" +
                "          },\n" +
                "          {\n" +
                "            \"LineText\": \"-01.01.2012cc:ae\",\n" +
                "            \"MinTop\": 159,\n" +
                "            \"Words\": [\n" +
                "              {\n" +
                "                \"Left\": 229,\n" +
                "                \"Top\": 159,\n" +
                "                \"Height\": 16,\n" +
                "                \"WordText\": \"-01.01.2012cc:ae\",\n" +
                "                \"Width\": 125\n" +
                "              }\n" +
                "            ],\n" +
                "            \"MaxHeight\": 16\n" +
                "          },\n" +
                "          {\n" +
                "            \"LineText\": \"0101.2020\",\n" +
                "            \"MinTop\": 181,\n" +
                "            \"Words\": [\n" +
                "              {\n" +
                "                \"Left\": 236,\n" +
                "                \"Top\": 181,\n" +
                "                \"Height\": 12,\n" +
                "                \"WordText\": \"0101.2020\",\n" +
                "                \"Width\": 72\n" +
                "              }\n" +
                "            ],\n" +
                "            \"MaxHeight\": 12\n" +
                "          },\n" +
                "          {\n" +
                "            \"LineText\": \"sÆ--SAMPLE12345ABCOEFGHIJKV\",\n" +
                "            \"MinTop\": 201,\n" +
                "            \"Words\": [\n" +
                "              {\n" +
                "                \"Left\": 181,\n" +
                "                \"Top\": 201,\n" +
                "                \"Height\": 17,\n" +
                "                \"WordText\": \"sÆ--SAMPLE12345ABCOEFGHIJKV\",\n" +
                "                \"Width\": 265\n" +
                "              }\n" +
                "            ],\n" +
                "            \"MaxHeight\": 17\n" +
                "          },\n" +
                "          {\n" +
                "            \"LineText\": \"8303 SAMPLE STREET, LONDONJXYZIAB\",\n" +
                "            \"MinTop\": 277,\n" +
                "            \"Words\": [\n" +
                "              {\n" +
                "                \"Left\": 207,\n" +
                "                \"Top\": 277,\n" +
                "                \"Height\": 19,\n" +
                "                \"WordText\": \"8303\",\n" +
                "                \"Width\": 54\n" +
                "              },\n" +
                "              {\n" +
                "                \"Left\": 264,\n" +
                "                \"Top\": 284,\n" +
                "                \"Height\": 12,\n" +
                "                \"WordText\": \"SAMPLE\",\n" +
                "                \"Width\": 58\n" +
                "              },\n" +
                "              {\n" +
                "                \"Left\": 325,\n" +
                "                \"Top\": 284,\n" +
                "                \"Height\": 15,\n" +
                "                \"WordText\": \"STREET,\",\n" +
                "                \"Width\": 57\n" +
                "              },\n" +
                "              {\n" +
                "                \"Left\": 386,\n" +
                "                \"Top\": 284,\n" +
                "                \"Height\": 14,\n" +
                "                \"WordText\": \"LONDONJXYZIAB\",\n" +
                "                \"Width\": 126\n" +
                "              }\n" +
                "            ],\n" +
                "            \"MaxHeight\": 19\n" +
                "          }\n" +
                "        ],\n" +
                "        \"HasOverlay\": true\n" +
                "      }\n" +
                "    }\n" +
                "  ],\n" +
                "  \"ProcessingTimeInMilliseconds\": \"1031\",\n" +
                "  \"SearchablePDFURL\": \"Searchable PDF not generated as it was not requested.\",\n" +
                "  \"OCRExitCode\": 1\n" +
                "}";
    }

}
