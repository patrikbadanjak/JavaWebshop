package hr.algebra.pbadanjak.webshop.listeners;

import hr.algebra.pbadanjak.webshop.di.FirebaseFactory;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;

import javax.net.ssl.*;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Properties;

public class ConfigContextListener implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        ServletContextListener.super.contextInitialized(sce);
        configSystemProperties();
        setTrustManager();
        FirebaseFactory.initialize();
    }

    private void configSystemProperties() {
        Properties properties = System.getProperties();
//        properties.setProperty("com.sun.net.ssl.checkRevocation", "false");
//        properties.setProperty("com.sun.security.enableAIAcaIssuers", "true");
//        properties.setProperty("javax.net.ssl.trustStore","clientTrustStore");
//        properties.setProperty("javax.net.ssl.trustStorePassword","changeit");
        System.setProperty("javax.net.ssl.trustStore", "NUL");
        System.setProperty("javax.net.ssl.trustStoreType", "Windows-ROOT");
//        System.setProperty("jdk.tls.client.protocols", "TLSv1");

        System.setProperties(properties);
    }

    private void setTrustManager() {
        TrustManager[] trustAllCerts = new TrustManager[] {
            new X509TrustManager() {
                public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                    return null;
                }
                @Override
                public void checkClientTrusted(X509Certificate[] arg0, String arg1)
                        throws CertificateException {}

                @Override
                public void checkServerTrusted(X509Certificate[] arg0, String arg1)
                        throws CertificateException {}
            }
        };

        SSLContext sc=null;

        try {
            sc = SSLContext.getInstance("SSL");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        try {
            sc.init(null, trustAllCerts, new java.security.SecureRandom());
        } catch (KeyManagementException e) {
            e.printStackTrace();
        }

        HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
        // Create all-trusting host name verifier
        HostnameVerifier validHosts = new HostnameVerifier() {
            @Override
            public boolean verify(String arg0, SSLSession arg1) {
                return true;
            }
        };
        // All hosts will be valid
        HttpsURLConnection.setDefaultHostnameVerifier(validHosts);
    }
}
