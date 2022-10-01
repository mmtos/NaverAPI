package com.naverapi.naverapi.component.api;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

@Component
public class NaverSearchApi {

    @Value("${NAVER-CLINET}")
    private String clientId;

    @Value("${NAVER-KEY}")
    private String ClientKey;

    public String search( String url, String qeury, String option ) {
        StringBuilder responseBody = new StringBuilder();
        // API에서 qeury는 UTF-8 인코딩을 명시하고 있습니다.
        try {
            qeury = URLEncoder.encode(qeury, "UTF-8");
        } catch ( UnsupportedEncodingException e ) {
            throw new RuntimeException("Query incoding fail", e);
        }
        // URL 세팅
        String apiUrl = url + qeury + option;
        // Key Setting
        Map<String, String> requestHeaders = new HashMap<>();
        requestHeaders.put("X-Naver-Client-Id", clientId);
        requestHeaders.put("X-Naver-Client-Secret", ClientKey);
        // 요청
        responseBody.append(get(apiUrl,requestHeaders));
        return responseBody.toString();
    }

    private String get(String apiUrl, Map<String, String> reqHeaders){

        try {
            // connection setting
            HttpURLConnection urlConnection = connect(apiUrl);
            urlConnection.setReadTimeout(50000);
            urlConnection.setConnectTimeout(30000);
            // setting method
            urlConnection.setRequestMethod("GET");
            for(Map.Entry<String, String> header : reqHeaders.entrySet()) {
                urlConnection.setRequestProperty(header.getKey(), header.getValue());
            }
            // response
            int responsCode = urlConnection.getResponseCode();
            if( responsCode == HttpURLConnection.HTTP_OK ) {
                return readBody(urlConnection.getInputStream());
            }
            return readBody( urlConnection.getErrorStream() );

        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    private static HttpURLConnection connect(String apiUrl){
        try {
            URL url = new URL(apiUrl);
            return (HttpURLConnection)url.openConnection();
        } catch (MalformedURLException e) {
            throw new RuntimeException("API URL이 잘못되었습니다. : " + apiUrl, e);
        } catch (IOException e) {
            throw new RuntimeException("연결이 실패했습니다. : " + apiUrl, e);
        }
    }


    private static String readBody(InputStream body){
        InputStreamReader streamReader = new InputStreamReader(body);


        try (BufferedReader lineReader = new BufferedReader(streamReader)) {
            StringBuilder responseBody = new StringBuilder();


            String line;
            while ((line = lineReader.readLine()) != null) {
                responseBody.append(line);
            }


            return responseBody.toString();
        } catch (IOException e) {
            throw new RuntimeException("API 응답을 읽는 데 실패했습니다.", e);
        }
    }

}
