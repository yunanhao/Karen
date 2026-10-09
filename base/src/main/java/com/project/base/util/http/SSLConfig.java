package com.project.base.util.http;

import androidx.annotation.NonNull;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLSocketFactory;

/**
 * HTTPS configuration. Defaults use the platform trust store and hostname checks.
 * Original utility author: Blankj (http://blankj.com), 2019/02/20.
 */
public final class SSLConfig {
    SSLSocketFactory mSSLSocketFactory;
    HostnameVerifier mHostnameVerifier;

    public SSLConfig(@NonNull SSLSocketFactory factory, @NonNull HostnameVerifier verifier) {
        mSSLSocketFactory = factory;
        mHostnameVerifier = verifier;
    }

    public static final HostnameVerifier DEFAULT_VERIFIER = HttpsURLConnection.getDefaultHostnameVerifier();
    public static final SSLSocketFactory DEFAULT_SSL_SOCKET_FACTORY = HttpsURLConnection.getDefaultSSLSocketFactory();
    public static final SSLConfig DEFAULT_SSL_CONFIG = new SSLConfig(DEFAULT_SSL_SOCKET_FACTORY, DEFAULT_VERIFIER);
}
