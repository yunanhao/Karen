package com.project.base.util.http;

import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import okhttp3.tls.HandshakeCertificates;
import okhttp3.tls.HeldCertificate;
import org.junit.*;
import javax.net.ssl.*;
import java.io.InputStream;
import static org.junit.Assert.*;

public class SSLConfigTest {
    private MockWebServer server;
    private SSLSocketFactory trustTestCertificate;

    @Before public void createLocalTlsServer() throws Exception {
        HeldCertificate certificate = new HeldCertificate.Builder()
                .commonName("localhost").addSubjectAlternativeName("localhost").build();
        HandshakeCertificates serverCertificates = new HandshakeCertificates.Builder()
                .heldCertificate(certificate).build();
        trustTestCertificate = new HandshakeCertificates.Builder()
                .addTrustedCertificate(certificate.certificate()).build().sslSocketFactory();
        server = new MockWebServer();
        server.useHttps(serverCertificates.sslSocketFactory(), false);
        server.start();
        server.enqueue(new MockResponse().setBody("accepted"));
    }

    @After public void cleanup() throws Exception { server.shutdown(); }

    @Test public void defaultFactoryRejectsUntrustedCertificate() throws Exception {
        HttpsURLConnection connection = connection("localhost", SSLConfig.DEFAULT_SSL_SOCKET_FACTORY);
        try { assertThrows(SSLException.class, connection::getInputStream); }
        finally { connection.disconnect(); }
    }

    @Test public void defaultVerifierRejectsHostnameMismatch() throws Exception {
        HttpsURLConnection connection = connection("127.0.0.1", trustTestCertificate);
        try { assertThrows(SSLException.class, connection::getInputStream); }
        finally { connection.disconnect(); }
    }

    @Test public void matchingHostnameWorksWithExplicitTestTrust() throws Exception {
        HttpsURLConnection connection = connection("localhost", trustTestCertificate);
        try (InputStream input = connection.getInputStream()) { assertEquals('a', input.read()); }
        finally { connection.disconnect(); }
    }

    private HttpsURLConnection connection(String host, SSLSocketFactory factory) throws Exception {
        HttpsURLConnection connection = (HttpsURLConnection) server.url("/").newBuilder().host(host).build().url().openConnection();
        connection.setConnectTimeout(5000);
        connection.setReadTimeout(5000);
        connection.setSSLSocketFactory(factory);
        connection.setHostnameVerifier(SSLConfig.DEFAULT_VERIFIER);
        return connection;
    }
}
