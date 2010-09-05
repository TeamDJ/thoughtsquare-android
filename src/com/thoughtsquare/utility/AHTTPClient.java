package com.thoughtsquare.utility;

import org.apache.http.HttpEntity;
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

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class AHTTPClient {

    public AHTTPClient() {
    }

    public AHTTPResponse post(String url, Map<String, String> postParams) {
        HttpClient httpClient = new DefaultHttpClient();
        HttpPost post = new HttpPost(url);

        List<NameValuePair> params = getParams(postParams);
        try {
            post.setEntity(new UrlEncodedFormEntity(params));
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }

        return getResponse(httpClient, post);
    }

    public AHTTPResponse get(String url) {
        HttpClient httpClient = new DefaultHttpClient();
        HttpGet get = new HttpGet(url);

         return getResponse(httpClient, get);
    }

    public AHTTPResponse put(String url, Map<String, String> putParams) {
        HttpClient httpClient = new DefaultHttpClient();
        HttpPut put = new HttpPut(url);

        List<NameValuePair> params = getParams(putParams);
        try {
            put.setEntity(new UrlEncodedFormEntity(params));
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }


        return getResponse(httpClient, put);
    }

    private AHTTPResponse getResponse(HttpClient httpClient, HttpRequestBase request){

        try {
            return httpClient.execute(request, new AHTTPResponseHandler());
        } catch (IOException e) {
            throw new RuntimeException("Error during http request", e);
        }
        finally{
            httpClient.getConnectionManager().shutdown();
        }
    }

    private List<NameValuePair> getParams(Map<String, String> postParams) {
        List<NameValuePair> list = new ArrayList<NameValuePair>();

        for (Map.Entry<String, String> entry : postParams.entrySet()) {
            list.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
        }

        return list;
    }

    class AHTTPResponseHandler implements ResponseHandler<AHTTPResponse> {


        public AHTTPResponse handleResponse(HttpResponse response) throws HttpResponseException, IOException {
            int responseStatus = response.getStatusLine().getStatusCode();

            String responseBody = null;
            try {
                responseBody =  new BasicResponseHandler().handleResponse(response);
            } catch (HttpResponseException e) {
                //basic response handler throws this exception if resposne >= 300
                //but we want to capture the status
            }

            return new AHTTPResponse(responseStatus, responseBody);
        }

    }
}
