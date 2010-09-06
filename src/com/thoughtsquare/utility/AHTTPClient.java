package com.thoughtsquare.utility;

import org.apache.http.HttpEntityEnclosingRequest;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.HttpResponseException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class AHTTPClient {

    public AHTTPClient() {
    }

    public AHTTPResponse post(String url, Map<String, String> params) {
        HttpClient httpClient = new DefaultHttpClient();
        HttpPost post = new HttpPost(url);
        setHeaders(post, params);
        return getResponse(httpClient, post);
    }

    public AHTTPResponse put(String url, Map<String, String> params) {
        HttpClient httpClient = new DefaultHttpClient();
        HttpPut put = new HttpPut(url);
        setHeaders(put, params);
        return getResponse(httpClient, put);
    }

    public AHTTPResponse get(String url) {
        HttpClient httpClient = new DefaultHttpClient();
        HttpGet get = new HttpGet(url);
        return getResponse(httpClient, get);
    }

    private void setHeaders(HttpEntityEnclosingRequest method, Map<String, String> params) {
        try {
            method.setEntity(new UrlEncodedFormEntity(getParams(params)));
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }

    private AHTTPResponse getResponse(HttpClient httpClient, HttpRequestBase request) {
        try {
            return httpClient.execute(request, new AHTTPResponseHandler());
        } catch (IOException e) {
            throw new RuntimeException("Error during http request", e);
        } finally {
            httpClient.getConnectionManager().shutdown();
        }
    }

    private List<NameValuePair> getParams(Map<String, String> params) {
        List<NameValuePair> list = new ArrayList<NameValuePair>();

        for (Map.Entry<String, String> entry : params.entrySet()) {
            list.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
        }

        return list;
    }

    class AHTTPResponseHandler implements ResponseHandler<AHTTPResponse> {
        public AHTTPResponse handleResponse(HttpResponse response) throws IOException {
            int responseStatus = response.getStatusLine().getStatusCode();

            String responseBody = null;
            try {
                responseBody = new BasicResponseHandler().handleResponse(response);
            } catch (HttpResponseException e) {
                //basic response handler throws this exception if resposne >= 300
                //but we want to capture the status
            }

            return new AHTTPResponse(responseStatus, responseBody);
        }
    }
}
