package com.game.biz.task;

import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicHeader;
import org.apache.http.util.EntityUtils;
import org.springframework.boot.configurationprocessor.json.JSONArray;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.boot.configurationprocessor.json.JSONTokener;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class SalesForceRetrieve {

    private static final String USERNAME = "lionel.tuttle@softeam.fr";
    private static final String PASSWORD = "Salesforce2019";
    private static final String LOGINURL = "https://login.salesforce.com";
    private static final String GRANTSERVICE = "/services/oauth2/token?grant_type=password";
    private static final String CLIENTID = "3MVG95G8WxiwV5Pt6EdgAxiu4mfR_bfGNpMjndpYf80UduPHFWrtFYtnJzCawn05TJYTFa7dI4z2gO81Kue.U";
    private static final String CLIENTSECRET = "A447E622428EA6C871F4CCB2049C9E8AA96E71BA61ED8FA594051B7AE56074CB";

    private static String baseUri;
    private static Header oauthHeader;
    private static Header prettyPrintHeader = new BasicHeader("X-PrettyPrint", "1");


    public static void main(String[] args) {
        String REST_ENDPOINT = "/services/data";
        String API_VERSION = "/v35.0";

        HttpClient httpclient = HttpClientBuilder.create().build();

        // Assemble the login request URL
        String loginURL = LOGINURL +
            GRANTSERVICE +
            "&client_id=" + CLIENTID +
            "&client_secret=" + CLIENTSECRET +
            "&username=" + USERNAME +
            "&password=" + PASSWORD;

        // Login requests must be POSTs
        HttpPost httpPost = new HttpPost(loginURL);
        HttpResponse response = null;

        try {
            // Execute the login POST request
            response = httpclient.execute(httpPost);
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }

        // verify response is HTTP OK
        assert response != null;
        final int statusCode = response.getStatusLine().getStatusCode();
        if (statusCode != org.apache.http.HttpStatus.SC_OK) {
            System.out.println("Error authenticating to Force.com: " + statusCode);
            // Error is in EntityUtils.toString(response.getEntity())
            return;
        }

        String getResult = null;
        try {
            getResult = EntityUtils.toString(response.getEntity());
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }

        JSONObject jsonObject;
        String loginAccessToken = null;
        String loginInstanceUrl = null;

        try {
            jsonObject = (JSONObject) new JSONTokener(getResult).nextValue();
            loginAccessToken = jsonObject.getString("access_token");
            loginInstanceUrl = jsonObject.getString("instance_url");
        } catch (JSONException jsonException) {
            jsonException.printStackTrace();
        }

        baseUri = loginInstanceUrl + REST_ENDPOINT + API_VERSION;
        oauthHeader = new BasicHeader("Authorization", "OAuth " + loginAccessToken);
        System.out.println("oauthHeader1: " + oauthHeader);
        System.out.println("\n" + response.getStatusLine());
        System.out.println("Successful login");
        System.out.println("instance URL: " + loginInstanceUrl);
        System.out.println("access token/session ID: " + loginAccessToken);
        System.out.println("baseUri: " + baseUri);

        // Run codes to query, isnert, update and delete records in Salesforce using REST API
        getReport();

        // release connection
        httpPost.releaseConnection();
    }


    // Create Leads using REST HttpPost
    private static void getReport() {
        System.out.println("\n_______________ Get Report _______________");

        String uri = baseUri + "/analytics/reports/00O2o000007NfkQEAS?includeDetails=true";
        try {

            System.err.println(uri);

            //Construct the objects needed for the request
            HttpClient httpClient = HttpClientBuilder.create().build();

            HttpGet httpGet = new HttpGet(uri);
            httpGet.addHeader(oauthHeader);
            httpGet.addHeader(prettyPrintHeader);

            //Make the request
            HttpResponse response = httpClient.execute(httpGet);

            //Process the results
            int statusCode = response.getStatusLine().getStatusCode();
            if (statusCode == 200) {
                String response_string = EntityUtils.toString(response.getEntity());
                try {
                    JSONObject json = new JSONObject(response_string);
                    System.out.println("JSON result of Query:\n" + json.toString(1));
                    JSONArray rows = json.getJSONObject("factMap").getJSONObject("T!T").getJSONArray("rows");
                    for (int i = 0; i < rows.length(); i++) {
                        JSONArray dataCells = rows.getJSONObject(i).getJSONArray("dataCells");
                        StringBuilder sb = new StringBuilder();
                        for (int j = 0; j < dataCells.length(); j++) {
                            sb.append(dataCells.getJSONObject(j).get("label")).append("\t");
                        }
                        System.out.println(sb);
                    }
                } catch (JSONException je) {
                    je.printStackTrace();
                }
            } else {
                System.out.println("Query was unsuccessful. Status code returned is " + statusCode);
                System.out.println("An error has occured. Http status: " + response.getStatusLine().getStatusCode());
                System.out.println(getBody(response.getEntity().getContent()));
                System.exit(-1);
            }
        } catch (IOException | NullPointerException ioe) {
            ioe.printStackTrace();
        }
    }

    private static String getBody(InputStream inputStream) {
        StringBuilder result = new StringBuilder();
        try {
            BufferedReader in = new BufferedReader(
                new InputStreamReader(inputStream)
            );
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                result.append(inputLine);
                result.append("\n");
            }
            in.close();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
        return result.toString();
    }
}

