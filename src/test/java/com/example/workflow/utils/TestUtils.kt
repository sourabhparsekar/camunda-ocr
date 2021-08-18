package com.example.workflow.utils

/**
 * @implNote Class to add mock server implementations
 */
object TestUtils {
    //    public static void createExpectationForOcrApi(Integer port) {
    //        new MockServerClient("127.0.0.1", port)
    //                .when(request().withMethod("POST")
    //                        .withPath("/document"), Times.once())
    //                .respond(response().withStatusCode(HttpStatus.OK.value())
    //                        .withHeaders(new Header("Content-Type", "application/json; charset=utf-8"),
    //                                new Header("Cache-Control", "public, max-age=86400"))
    //                        .withBody(getOcrResponse())
    //                        .withDelay(TimeUnit.SECONDS, 1));
    //    }



    val ocrResponse: String
        get() = """{
  "IsErroredOnProcessing": false,
  "ParsedResults": [
    {
      "TextOrientation": "0",
      "ParsedText": "DRIVING LICENSE\r\n2— JANE SMITH\r\n—-3301 niL1990 KINGDOM\r\n-01.01.2012cc:ae\r\n0101.2020\r\nsÆ--SAMPLE12345ABCOEFGHIJKV\r\n8303 SAMPLE STREET, LONDONJXYZIAB\r\n",
      "FileParseExitCode": 1,
      "ErrorDetails": "",
      "ErrorMessage": "",
      "TextOverlay": {
        "Message": "Total lines: 7",
        "Lines": [
          {
            "LineText": "DRIVING LICENSE",
            "MinTop": 44,
            "Words": [
              {
                "Left": 153,
                "Top": 44,
                "Height": 18,
                "WordText": "DRIVING",
                "Width": 99
              },
              {
                "Left": 260,
                "Top": 44,
                "Height": 18,
                "WordText": "LICENSE",
                "Width": 96
              }
            ],
            "MaxHeight": 18
          },
          {
            "LineText": "2— JANE SMITH",
            "MinTop": 97,
            "Words": [
              {
                "Left": 207,
                "Top": 97,
                "Height": 13,
                "WordText": "2—",
                "Width": 27
              },
              {
                "Left": 236,
                "Top": 97,
                "Height": 12,
                "WordText": "JANE",
                "Width": 37
              },
              {
                "Left": 277,
                "Top": 97,
                "Height": 12,
                "WordText": "SMITH",
                "Width": 44
              }
            ],
            "MaxHeight": 13
          },
          {
            "LineText": "—-3301 niL1990 KINGDOM",
            "MinTop": 134,
            "Words": [
              {
                "Left": 181,
                "Top": 134,
                "Height": 17,
                "WordText": "—-3301",
                "Width": 69
              },
              {
                "Left": 253,
                "Top": 135,
                "Height": 16,
                "WordText": "niL1990",
                "Width": 55
              },
              {
                "Left": 389,
                "Top": 139,
                "Height": 12,
                "WordText": "KINGDOM",
                "Width": 69
              }
            ],
            "MaxHeight": 17
          },
          {
            "LineText": "-01.01.2012cc:ae",
            "MinTop": 159,
            "Words": [
              {
                "Left": 229,
                "Top": 159,
                "Height": 16,
                "WordText": "-01.01.2012cc:ae",
                "Width": 125
              }
            ],
            "MaxHeight": 16
          },
          {
            "LineText": "0101.2020",
            "MinTop": 181,
            "Words": [
              {
                "Left": 236,
                "Top": 181,
                "Height": 12,
                "WordText": "0101.2020",
                "Width": 72
              }
            ],
            "MaxHeight": 12
          },
          {
            "LineText": "sÆ--SAMPLE12345ABCOEFGHIJKV",
            "MinTop": 201,
            "Words": [
              {
                "Left": 181,
                "Top": 201,
                "Height": 17,
                "WordText": "sÆ--SAMPLE12345ABCOEFGHIJKV",
                "Width": 265
              }
            ],
            "MaxHeight": 17
          },
          {
            "LineText": "8303 SAMPLE STREET, LONDONJXYZIAB",
            "MinTop": 277,
            "Words": [
              {
                "Left": 207,
                "Top": 277,
                "Height": 19,
                "WordText": "8303",
                "Width": 54
              },
              {
                "Left": 264,
                "Top": 284,
                "Height": 12,
                "WordText": "SAMPLE",
                "Width": 58
              },
              {
                "Left": 325,
                "Top": 284,
                "Height": 15,
                "WordText": "STREET,",
                "Width": 57
              },
              {
                "Left": 386,
                "Top": 284,
                "Height": 14,
                "WordText": "LONDONJXYZIAB",
                "Width": 126
              }
            ],
            "MaxHeight": 19
          }
        ],
        "HasOverlay": true
      }
    }
  ],
  "ProcessingTimeInMilliseconds": "1031",
  "SearchablePDFURL": "Searchable PDF not generated as it was not requested.",
  "OCRExitCode": 1
}"""

    val ocrErrorResponse: String
        get() = "{" +
                "\"IsErroredOnProcessing\": false," +
                "\"ParsedResults\": [{" +
                "\"TextOrientation\": \"0\"," +
                "\"ParsedText\": \"CALIFORNIAA DRIVER LIC\"," +
                "\"FileParseExitCode\": 0," +
                "\"ErrorDetails\": \"Test Error Details\"," +
                "\"ErrorMessage\": \"Test Error Message\"," +
                "\"TextOverlay\": \"\"" +
                "}]," +
                "\"ProcessingTimeInMilliseconds\": \"24428\"," +
                "\"SearchablePDFURL\": \"Searchable PDF not generated as it was not requested.\"," +
                "\"OCRExitCode\": 1" +
                "}"

}