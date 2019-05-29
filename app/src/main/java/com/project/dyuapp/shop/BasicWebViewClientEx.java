package com.project.dyuapp.shop;

import android.annotation.TargetApi;
import android.content.Context;
import android.net.http.SslError;
import android.os.Build;
import android.webkit.ClientCertRequest;
import android.webkit.SslErrorHandler;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import java.io.InputStream;
import java.security.KeyStore;
import java.security.PrivateKey;
import java.security.cert.Certificate;
import java.security.cert.X509Certificate;
import java.util.Enumeration;

/**
 * Describe:
 * Created by jingang on 2019/5/22
 */
public class BasicWebViewClientEx extends WebViewClient {
    private X509Certificate[] certificatesChain;
    private PrivateKey clientCertPrivateKey;

//    public BasicWebViewClientEx() {
//        initPrivateKeyAndX509Certificate(MyApplacation.contextApplication);
//    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onReceivedClientCertRequest(WebView view, ClientCertRequest request) {
        if ((null != clientCertPrivateKey) && ((null != certificatesChain) && (certificatesChain.length != 0))) {
            request.proceed(clientCertPrivateKey, certificatesChain);
        } else {
            request.cancel();
        }
    }

    private static final String KEY_STORE_CLIENT_PATH = "client.p12";//客户端要给服务器端认证的证书
    private static final String KEY_STORE_PASSWORD = "btydbg2018";// 客户端证书密码

    private void initPrivateKeyAndX509Certificate(Context context) {
        try {
            KeyStore keyStore = KeyStore.getInstance("PKCS12");
            InputStream ksIn = context.getResources().getAssets().open(KEY_STORE_CLIENT_PATH);
            keyStore.load(ksIn, KEY_STORE_PASSWORD.toCharArray());
            Enumeration<?> localEnumeration;
            localEnumeration = keyStore.aliases();
            while (localEnumeration.hasMoreElements()) {
                String str3 = (String) localEnumeration.nextElement();
                clientCertPrivateKey = (PrivateKey) keyStore.getKey(str3, KEY_STORE_PASSWORD.toCharArray());
                if (clientCertPrivateKey == null) {
                    continue;
                } else {
                    Certificate[] arrayOfCertificate = keyStore.getCertificateChain(str3);
                    certificatesChain = new X509Certificate[arrayOfCertificate.length];
                    for (int j = 0; j < certificatesChain.length; j++) {
                        certificatesChain[j] = ((X509Certificate) arrayOfCertificate[j]);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onReceivedSslError(final WebView view, SslErrorHandler handler,
                                   SslError error) {
        handler.proceed();
    }

}