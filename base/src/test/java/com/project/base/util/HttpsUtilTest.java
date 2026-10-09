package com.project.base.util;

import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import okhttp3.mockwebserver.RecordedRequest;
import org.junit.*;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeUnit;
import static org.junit.Assert.*;

public class HttpsUtilTest {
    private MockWebServer server;
    private String url;

    @Before public void startServer() throws Exception {
        server = new MockWebServer();
        server.start();
        server.enqueue(new MockResponse().setBody("响应成功"));
        url = server.url("/").toString();
    }

    @After public void stopServer() throws Exception { server.shutdown(); }

    @Test public void getKeepsItsMethodAndDoesNotWriteABody() throws Exception {
        assertEquals("响应成功", HttpsUtil.getJson("", url));
        RecordedRequest request = server.takeRequest(2, TimeUnit.SECONDS);
        assertNotNull(request);
        assertEquals("GET", request.getMethod());
        assertEquals(0, request.getBodySize());
    }

    @Test public void unicodeFormUsesByteLengthAndUtf8() throws Exception {
        String input = "name=中文";
        assertEquals("响应成功", HttpsUtil.postForm(input, url));
        RecordedRequest request = server.takeRequest(2, TimeUnit.SECONDS);
        assertNotNull(request);
        assertEquals("POST", request.getMethod());
        assertEquals(input, request.getBody().readUtf8());
        assertEquals(String.valueOf(input.getBytes(StandardCharsets.UTF_8).length), request.getHeader("Content-Length"));
    }

    @Test public void nullPostBodyIsEmpty() throws Exception {
        assertEquals("响应成功", HttpsUtil.postJson(null, url));
        RecordedRequest request = server.takeRequest(2, TimeUnit.SECONDS);
        assertNotNull(request);
        assertEquals(0, request.getBodySize());
        assertEquals("0", request.getHeader("Content-Length"));
    }
}
