package com.naverapi.naverapi.interfaces.contorller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.util.JSONPObject;
import com.naverapi.naverapi.component.api.NaverBlogSearchApi;
import lombok.RequiredArgsConstructor;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/open")
public class NaverBlogApiController {

    @Autowired
    NaverBlogSearchApi naverBlogSearchApi = new NaverBlogSearchApi();

    @GetMapping("/test")
    public String test()
    {
        return "hello";
    }

    @ResponseBody
    @GetMapping("/naver/blog")
    public ResponseEntity<JSONObject> getPlace(@RequestParam(value = "query") String query ) throws Exception {
        JSONParser parser = new JSONParser();
        Object obj = parser.parse( naverBlogSearchApi.search(query));
        JSONObject jsonObj = (JSONObject) obj;

        return ResponseEntity.ok(jsonObj);
    }
}
