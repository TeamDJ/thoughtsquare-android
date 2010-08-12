package teamdj.thoughtsquare.utility;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class AHTTPClient {
    public AHTTPResponse post(String url, Map<String, String> postParams) {
        HttpClient httpClient = new DefaultHttpClient();
        HttpPost post = new HttpPost(url);

        List<NameValuePair> params = getParams(postParams);
        try {
            post.setEntity(new UrlEncodedFormEntity(params));
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }

        HttpResponse response;
        try {
            response = httpClient.execute(post);
        } catch (IOException e) {
            throw new RuntimeException("Error POSTing", e);
        }

        String responseBody = null;
        int responseStatus = response.getStatusLine().getStatusCode();

        HttpEntity entity = response.getEntity();

        if (entity != null) {
            InputStream instream;
            try {
                instream = entity.getContent();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            try {
                BufferedReader reader = new BufferedReader(new InputStreamReader(instream));
                try {
                    responseBody = reader.readLine();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            } catch (RuntimeException ex) {
                post.abort();
                throw ex;
            } finally {
                try {
                    instream.close();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
            httpClient.getConnectionManager().shutdown();
        }

        return new AHTTPResponse(responseStatus, responseBody);
    }

    private List<NameValuePair> getParams(Map<String, String> postParams) {
        List<NameValuePair> list = new ArrayList<NameValuePair>();

        for (Map.Entry<String, String> entry : postParams.entrySet()) {
            list.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
        }

        return list;
    }
}
