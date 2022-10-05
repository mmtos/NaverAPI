package com.naverapi.naverapi.application.service;

import com.naverapi.naverapi.component.api.NaverSearchApi;
import com.naverapi.naverapi.domain.NaverBlogResultRepository;
import com.naverapi.naverapi.interfaces.dtos.NaverBlogResultSaveDto;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Iterator;


@AllArgsConstructor
@Service
@Slf4j
public class NaverApiService {
    private final static int MAX_CNT = 10;
    private final static int TYPE_BLOG = 1;
    private final static int TYPE_NEWS = 2;
    private final static int TYPE_CAFE = 3;
    private final static int TYPE_DOC = 4;

    private final static boolean TYPE_SIM = true;
    private final static boolean TYPE_DATE = false;
    private NaverBlogResultRepository naverBlogResultRepository;
    private NaverSearchApi naverSearchApi;

    private ApiRequestService apiRequestService;

    @Transactional
    public String getBlogContentsSortByExac( String query ) {
        return "test";
    }

    @Transactional
    public String getBlogContentsSortByDate(String query ) {

        JSONParser parser = new JSONParser();

        for (int i = 0; i < MAX_CNT; i++) {
            String url = makeUrl( TYPE_BLOG, query, (1 + (100*i)) ,TYPE_DATE );
            String apiResponse = naverSearchApi.search( url );

            if(apiResponse.length() == 0) continue;

            try {
                JSONObject object = (JSONObject) parser.parse( apiResponse );
                // 블로그 콘텐츠 목록만 추출하는 형태임
                String lastBuildDateChanel = (String) object.get("lastBuildDate");
                Long totalChanel = (Long) object.get("total");
                // 블로그 콘텐츠 목록 추출
                JSONArray item = (JSONArray) object.get("items");
                Iterator<JSONObject> iter = item.iterator();

                while(iter.hasNext()) {
                    JSONObject itemEach = (JSONObject) iter.next();
                    String link = (String) itemEach.get("link");
                    String postdate = (String) itemEach.get("postdate");
                    String description = (String) itemEach.get("description");
                    String title = (String) itemEach.get("title");
                    String bloggerlink = (String) itemEach.get("bloggerlink");
                    String bloggername = (String) itemEach.get("bloggername");

                    NaverBlogResultSaveDto dto = new NaverBlogResultSaveDto(
                            title, postdate, description, link, bloggerlink, bloggername
                    );
                    naverBlogResultRepository.save( dto.toEntity() );
                }

                // 요청 정보에 대한 저장 - 성공이든 실패든 저장한다.
                apiRequestService.saveApiRequestResult( lastBuildDateChanel, totalChanel, url);
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }

        }

        return "test";
    }

    private String makeUrl( int type, String query, int start, boolean sort ){
        StringBuilder queryBuilder = new StringBuilder();

        // url 세팅
        switch ( type ) {
            case TYPE_BLOG : {
                queryBuilder.append("https://openapi.naver.com/v1/search/blog.json?query=");
                break;
            }
            case TYPE_NEWS : {
                queryBuilder.append("https://openapi.naver.com/v1/search/news.json?query=");
                break;
            }
            case TYPE_CAFE: {
                queryBuilder.append("https://openapi.naver.com/v1/search/cafearticle.json?query=");
                break;
            }
            case TYPE_DOC: {
                queryBuilder.append("https://openapi.naver.com/v1/search/webkr.json?query=");
                break;
            }
            default:
                break;
        } // end switch

        // 쿼리 조합
        try {
            query = URLEncoder.encode(query, "UTF-8");
        } catch ( UnsupportedEncodingException e ) {
            throw new RuntimeException("Query incoding fail", e);
        }

        queryBuilder.append(query);

        // 한번에 가져올 페이지
        queryBuilder.append("&display=100");
        // 시작 페이지
        queryBuilder.append("&start=" + String.valueOf(start));
        // 정확도순 최신순
        if( sort ) {
            queryBuilder.append("&sort=sim");
        } else {
            queryBuilder.append("&sort=date");
        }

        return queryBuilder.toString();
    }
}
