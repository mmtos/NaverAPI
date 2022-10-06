package com.naverapi.naverapi.article.application.service;

import com.naverapi.naverapi.article.component.api.NaverSearchApi;
import com.naverapi.naverapi.article.domain.apireponse.ApiReponse;
import com.naverapi.naverapi.article.domain.apireponse.ApiReponseRepository;
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
    public int getBlogContentsSortByDate( String keyword ) {
        // api 요청 로직 ( api 요청은 비동기입니다. )
        List<ApiResponseSaveDto> responseList = getTotalResponseResult(keyword, TYPE_BLOG);
        // 블로그 콘텐츠 관련 목록
        List<NaverBlogResultSaveDto> saveDtoList = getSaveBlogArticleList(responseList);
        // 저장 로직
        for ( NaverBlogResultSaveDto dto : saveDtoList) {
            naverBlogResultRepository.save(dto.toEntity());
        }
        for( ApiResponseSaveDto apiReponse : responseList ) {
            apiReponseRepository.save(apiReponse.toEntity());
        }
        return saveDtoList.size();
    }

    private List<ApiResponseSaveDto> getTotalResponseResult( String keyword, int articleType ){
        List<ApiResponseSaveDto> responseList = new ArrayList<>();
        for (int i = 0; i < MAX_CNT; i++) {
            String url = makeUrl(articleType, keyword, (1 + (100*i)) , TYPE_DATE );
            ApiResponseSaveDto result = apiResponseParser(url);
            if(result != null) {
                responseList.add(result);
            }
        }
        return responseList;
    }

    private List<NaverBlogResultSaveDto> getSaveBlogArticleList( List<ApiResponseSaveDto> apiReponseList ) {
        List<NaverBlogResultSaveDto> saveList = new ArrayList<>();
        for ( ApiResponseSaveDto apiResponse : apiReponseList ) {
            JSONArray item = apiResponse.getItem();
            Iterator<JSONObject> iter = item.iterator();
            while(iter.hasNext()) {
                JSONObject itemEach = (JSONObject) iter.next();
                saveList.add( 0, parseBlogArticleItem(itemEach));
            }
        }
        return saveList;
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

    private ApiResponseSaveDto apiResponseParser( String url ) {
        JSONParser parser = new JSONParser();
        try {
            String apiResponse = naverSearchApi.search(url);
            JSONObject object = (JSONObject) parser.parse( apiResponse );
            String lastBuildDateChanel = (String) object.get( "lastBuildDate" );
            Long totalChanel = (Long) object.get( "total" );
            JSONArray item = (JSONArray) object.get( "items" );

            return ApiResponseSaveDto.builder()
                    .lastBuildDate(lastBuildDateChanel)
                    .total(totalChanel)
                    .url(url)
                    .item(item)
                    .build();

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
