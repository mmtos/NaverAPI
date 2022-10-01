package com.naverapi.naverapi.interfaces.contorller;

import com.naverapi.naverapi.component.api.NaverSearchApi;
import lombok.RequiredArgsConstructor;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/naver")
public class NaverSearchApiController {

    @Autowired
    NaverSearchApi naverSearchApi = new NaverSearchApi();

    @GetMapping("/test")
    public String test()
    {
        return "hello";
    }

    @ResponseBody
    @GetMapping("/test/blog")
    public ResponseEntity<JSONObject> getPlace(@RequestParam(value = "query") String query ) throws Exception {
        String url = "https://openapi.naver.com/v1/search/blog.json?query=";
        String option = "&display=100&start=1&sort=sim";
        JSONParser parser = new JSONParser();
        Object obj = parser.parse( naverSearchApi.search( url , query,option) );
        JSONObject jsonObj = (JSONObject) obj;
        return ResponseEntity.ok(jsonObj);
    }

    @ResponseBody
    @GetMapping("/test/news")
    public ResponseEntity<JSONObject> getNewsPlace(@RequestParam(value = "query") String query) throws  Exception {
        String url = "https://openapi.naver.com/v1/search/news.json?query=";
        String option = "&display=100&start=1&sort=sim";
        JSONParser parser = new JSONParser();
        Object obj = parser.parse( naverSearchApi.search( url , query,option) );
        JSONObject jsonObj = (JSONObject) obj;
        return ResponseEntity.ok(jsonObj);
    }

    @ResponseBody
    @GetMapping("/test/cafe")
    public ResponseEntity<JSONObject> getCafePlace(@RequestParam(value = "query") String query) throws  Exception {
        String url = "https://openapi.naver.com/v1/search/cafearticle.json?query=";
        String option = "&display=100&start=1&sort=sim";
        JSONParser parser = new JSONParser();
        Object obj = parser.parse( naverSearchApi.search( url , query,option) );
        JSONObject jsonObj = (JSONObject) obj;
        return ResponseEntity.ok(jsonObj);
    }

    @ResponseBody
    @GetMapping("/test/webdoc")
    public ResponseEntity<JSONObject> getWebDocPlace(@RequestParam(value = "query") String query) throws  Exception {
        String url = "https://openapi.naver.com/v1/search/webkr.json?query=";
        String option = "&display=100&start=1";
        JSONParser parser = new JSONParser();
        Object obj = parser.parse( naverSearchApi.search( url , query,option) );
        JSONObject jsonObj = (JSONObject) obj;
        return ResponseEntity.ok(jsonObj);
    }
}
