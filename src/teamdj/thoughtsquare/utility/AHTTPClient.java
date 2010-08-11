package teamdj.thoughtsquare.utility;

import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class AHTTPClient {
    public int post(String url, Map<String, String> postParams) {
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

        return response.getStatusLine().getStatusCode(); //To change body of created methods use File | Settings | File Templates.
    }

    private List<NameValuePair> getParams(Map<String, String> postParams) {
        List<NameValuePair> list = new ArrayList<NameValuePair>();

        for (Map.Entry<String, String> entry : postParams.entrySet()) {
            list.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
        }

        return list;
    }
}
