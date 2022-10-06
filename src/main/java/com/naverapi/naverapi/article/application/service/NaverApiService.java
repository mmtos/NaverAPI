package com.naverapi.naverapi.article.application.service;

import com.naverapi.naverapi.article.component.api.NaverSearchApi;
import com.naverapi.naverapi.article.domain.apirequest.ApiReponse;
import com.naverapi.naverapi.article.domain.apirequest.ApiReponseRepository;
import com.naverapi.naverapi.article.domain.blogarticle.NaverBlogResultRepository;
import com.naverapi.naverapi.article.ui.dto.ApiResponseSaveDto;
import com.naverapi.naverapi.article.ui.dto.NaverBlogResultSaveDto;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.*;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


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

    private ApiReponseRepository apiReponseRepository;
    private NaverSearchApi naverSearchApi;


    @Transactional
    public String getBlogContentsSortByExac( String keyword ) {
        // TODO - 정확도 순으로 데이터를 조회하여 HashSet 자료구조에서 중복을 제외합니다.
        return "";
    }


    @Transactional
    public String getBlogContentsSortByDate( String keyword ) {
        // api 요청 로직 ( api 요청은 비동기입니다. )
        List<ApiReponse> responseList = getTotalResponseResult(keyword);
        List<NaverBlogResultSaveDto> saveDtoList = getSaveBlogArticleList(responseList);;
        // 저장 로직
        for ( NaverBlogResultSaveDto dto : saveDtoList) {
            naverBlogResultRepository.save(dto.toEntity());
        }

        for( ApiReponse apiReponse : responseList ) {
            ApiResponseSaveDto dto = ApiResponseSaveDto.builder()
                                                       .lastBuildDate(apiReponse.getLastBuildDate())
                                                       .total(apiReponse.getTotal())
                                                       .url(apiReponse.getRequestUrl())
                                                       .build();
            apiReponseRepository.save(dto.toEntity());
        }

        return "test";
    }

    public NaverBlogResultSaveDto parseBlogArticleItem( JSONObject item ) {
        String link = (String) item.get("link");
        String postdate = (String) item.get("postdate");
        String description = (String) item.get("description");
        String title = (String) item.get("title");
        String bloggerlink = (String) item.get("bloggerlink");
        String bloggername = (String) item.get("bloggername");

        return new NaverBlogResultSaveDto(
                title, postdate, description, link, bloggerlink, bloggername );
    }

    private List<NaverBlogResultSaveDto> getSaveBlogArticleList( List<ApiReponse> apiReponseList ) {

        boolean stop = false;
        List<NaverBlogResultSaveDto> saveList = new ArrayList<>();

        for ( ApiReponse apiResponse : apiReponseList ) {
            JSONArray item = apiResponse.getItemList();
            Iterator<JSONObject> iter = item.iterator();

            while(iter.hasNext()) {
                JSONObject itemEach = (JSONObject) iter.next();
                String link = (String) itemEach.get("link");
                String postdate = (String) itemEach.get("postdate");
                String description = (String) itemEach.get("description");
                String title = (String) itemEach.get("title");
                String bloggerlink = (String) itemEach.get("bloggerlink");
                String bloggername = (String) itemEach.get("bloggername");

                saveList.add( 0, new NaverBlogResultSaveDto( title, postdate, description, link, bloggerlink, bloggername ));
            }
        }
        return saveList;
    }

    private List<ApiReponse> getTotalResponseResult( String keyword ){
        List<ApiReponse> responseList = new ArrayList<>();
        for (int i = 0; i < MAX_CNT; i++) {
            ApiReponse result = getBlogArticleAndParse( keyword, (1 + (100*i)) );
            // 요청이 null이 아닌 것들만 데이터베이스에 기록한다.
            if(result != null) {
                responseList.add(result);
            }
        }
        return responseList;
    }

    private ApiReponse getBlogArticleAndParse( String keyword, int startPage ){
        String url = makeUrl( TYPE_BLOG, keyword, startPage ,TYPE_DATE );
        String apiResponse = naverSearchApi.search( url );
        return apiResponseParser(url, apiResponse);
    }

    // TODO - 카페에서 데이터를 가져와서 저장합니다.
    public String getCafeArticleAndParse( String keyword, int startPage ) {
        String url = makeUrl( TYPE_CAFE, keyword, startPage, TYPE_DATE );
        String apiResponse = naverSearchApi.search(url);
        return "";
    }

    // TODO - 뉴스에서 정보를 조회하여 DB에 저장합니다.
    public String getNewArticleAndParse( String keyword, int startPage ) {
        String url = makeUrl( TYPE_NEWS, keyword, startPage, TYPE_DATE );
        String apiResponse = naverSearchApi.search(url);
        return "";
    }

    private ApiReponse apiResponseParser( String url, String apiResponse ) {
        JSONParser parser = new JSONParser();
        try {
            JSONObject object = (JSONObject) parser.parse( apiResponse );
            String lastBuildDateChanel = (String) object.get( "lastBuildDate" );
            Long totalChanel = (Long) object.get( "total" );
            JSONArray item = (JSONArray) object.get( "items" );
            return new ApiReponse(lastBuildDateChanel, totalChanel, url, item);
        } catch ( ParseException e ) {
            log.debug( e.getMessage() );
            return null;
        }
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
