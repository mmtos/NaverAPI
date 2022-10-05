package com.naverapi.naverapi.article.ui.contorller;

import com.naverapi.naverapi.article.application.service.NaverApiService;
import com.naverapi.naverapi.article.component.api.NaverSearchApi;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/naver")
@Slf4j
public class NaverSearchApiController {

    @Autowired
    NaverSearchApi naverSearchApi = new NaverSearchApi();
    @Autowired
    NaverApiService naverApiService;

    @GetMapping("/test")
    public String test()
    {
        return "hello";
    }

    @ResponseBody
    @GetMapping("/test/blog")
    public ResponseEntity<String> getPlace(@RequestParam(value = "query") String query ) throws Exception {
        return ResponseEntity.ok( naverApiService.getBlogContentsSortByDate(query) );
    }
//
//    @ResponseBody
//    @GetMapping("/test/blog/dag")
//    public ResponseEntity<String> getPlaceTst(@RequestParam(value = "query") String query ) throws  Exception {
//        String result = " ";// = naverApiService.parseAndSave(query);
//        return ResponseEntity.ok(result);
//    }
//
//    @ResponseBody
//    @GetMapping("/test/news")
//    public ResponseEntity<JSONObject> getNewsPlace(@RequestParam(value = "query") String query) throws  Exception {
//        String url = "https://openapi.naver.com/v1/search/news.json?query=";
//        String option = "&display=100&start=1&sort=sim";
//        JSONParser parser = new JSONParser();
//        Object obj = parser.parse( naverSearchApi.search( url + query + option) );
//        JSONObject jsonObj = (JSONObject) obj;
//        return ResponseEntity.ok(jsonObj);
//    }
//
//    @ResponseBody
//    @GetMapping("/test/cafe")
//    public ResponseEntity<JSONObject> getCafePlace(@RequestParam(value = "query") String query) throws  Exception {
//        String url = "https://openapi.naver.com/v1/search/cafearticle.json?query=";
//        String option = "&display=100&start=1&sort=sim";
//        JSONParser parser = new JSONParser();
//        Object obj = parser.parse( naverSearchApi.search( url + query + option) );
//        JSONObject jsonObj = (JSONObject) obj;
//        return ResponseEntity.ok(jsonObj);
//    }
//
//    @ResponseBody
//    @GetMapping("/test/webdoc")
//    public ResponseEntity<JSONObject> getWebDocPlace(@RequestParam(value = "query") String query) throws  Exception {
//        String url = "https://openapi.naver.com/v1/search/webkr.json?query=";
//        String option = "&display=100&start=1";
//        JSONParser parser = new JSONParser();
//        Object obj = parser.parse( naverSearchApi.search( url + query + option) );
//        JSONObject jsonObj = (JSONObject) obj;
//        return ResponseEntity.ok(jsonObj);
//    }
}
