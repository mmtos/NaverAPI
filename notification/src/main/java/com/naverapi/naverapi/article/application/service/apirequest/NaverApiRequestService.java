package com.naverapi.naverapi.article.application.service.apirequest;

import com.naverapi.naverapi.article.application.service.article.ArticleService;
import com.naverapi.naverapi.article.component.api.NaverSearchApi;
import com.naverapi.naverapi.article.domain.apireponse.ApiReponseRepository;
import com.naverapi.naverapi.article.domain.blogarticle.NaverBlogResultRepository;
import com.naverapi.naverapi.article.domain.cafearticle.NaverCafeResultRepository;
import com.naverapi.naverapi.article.domain.newsarticle.NaverNewsResultRepository;
import com.naverapi.naverapi.article.ui.dto.ApiResponseSaveDto;
import com.naverapi.naverapi.article.ui.dto.NaverBlogResultSaveDto;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.*;

import com.naverapi.naverapi.article.ui.dto.NaverCafeResultSaveDto;
import com.naverapi.naverapi.article.ui.dto.NaverNewsResultSaveDto;
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
public class NaverApiRequestService {
    private final static int MAX_CNT = 10;
    private final static int TYPE_BLOG = 1;
    private final static int TYPE_NEWS = 2;
    private final static int TYPE_CAFE = 3;
    private final static int TYPE_DOC = 4;

    private final static boolean TYPE_SIM = true;
    private final static boolean TYPE_DATE = false;
    private NaverBlogResultRepository naverBlogResultRepository;

    private NaverCafeResultRepository naverCafeResultRepository;

    private NaverNewsResultRepository naverNewsResultRepository;
    private ApiReponseRepository apiReponseRepository;
    private NaverSearchApi naverSearchApi;

    private ArticleService articleService;

    @Transactional
    public int getBlogContentsSortByDate( String keyword, int cnt ) {
        // api 요청 로직 ( api 요청은 비동기입니다. )
        List<ApiResponseSaveDto> responseList = getTotalResponseResult(keyword, TYPE_BLOG, TYPE_DATE, cnt);
        // 블로그 콘텐츠 관련 목록
        List<NaverBlogResultSaveDto> saveDtoList = articleService.deleteDuplicationBlogArticle(keyword, getSaveBlogArticleList(responseList));

        // 저장 로직
        for ( NaverBlogResultSaveDto dto : saveDtoList) {
            naverBlogResultRepository.save(dto.toEntity());
        }
        for( ApiResponseSaveDto apiReponse : responseList ) {
            apiReponseRepository.save(apiReponse.toEntity());
        }
        return saveDtoList.size();
    }

    @Transactional
    public int getCafeContentsSortByDate( String keyword, int cnt ) {
        List<ApiResponseSaveDto> apiResponseSaveDtoList = getTotalResponseResult(keyword, TYPE_CAFE, TYPE_DATE, cnt);

        List<NaverCafeResultSaveDto> saveDtoList = articleService.deleteDuplicationCafeArticle(keyword, getSaveCafeArticleList(apiResponseSaveDtoList));

        for( NaverCafeResultSaveDto dto : saveDtoList ) {
            naverCafeResultRepository.save(dto.toEntity());
        }
        for( ApiResponseSaveDto apiReponse : apiResponseSaveDtoList ) {
            apiReponseRepository.save(apiReponse.toEntity());
        }
        return saveDtoList.size();
    }

    @Transactional
    public int getNewsContentsSortByDate(String keyword, int cnt ) {
        List<ApiResponseSaveDto> apiResponseSaveDtoList = getTotalResponseResult(keyword, TYPE_NEWS, TYPE_DATE, cnt);

        List<NaverNewsResultSaveDto> saveDtoList = articleService.deleteDuplicationNewsArticle(keyword,getSaveNewsArticleList(apiResponseSaveDtoList));

        for( NaverNewsResultSaveDto dto : saveDtoList ) {
            naverNewsResultRepository.save(dto.toEntity());
        }
        for( ApiResponseSaveDto apiReponse : apiResponseSaveDtoList ) {
            apiReponseRepository.save(apiReponse.toEntity());
        }
        return saveDtoList.size();
    }

    private List<ApiResponseSaveDto> getTotalResponseResult( String keyword, int articleType, boolean sortType, int cnt ){
        List<ApiResponseSaveDto> responseList = new ArrayList<>();
        for (int i = 0; i < cnt; i++) {
            String url = makeUrl(articleType, keyword, (1 + (100*i)) , sortType );
            ApiResponseSaveDto result = apiResponseParser(url, keyword);
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
                saveList.add( 0, parseBlogArticleItem(itemEach, apiResponse.getKeyword()));
            }
        }
        return saveList;
    }

    private NaverBlogResultSaveDto parseBlogArticleItem( JSONObject item, String keyword ) {
        String link = (String) item.get("link");
        String postdate = (String) item.get("postdate");
        String description = (String) item.get("description");
        String title = (String) item.get("title");
        String bloggerlink = (String) item.get("bloggerlink");
        String bloggername = (String) item.get("bloggername");
        return new NaverBlogResultSaveDto(
                title, postdate, description, link, bloggerlink, bloggername, keyword );
    }

    private List<NaverCafeResultSaveDto> getSaveCafeArticleList( List<ApiResponseSaveDto> apiResponseSaveDtoList ) {
        List<NaverCafeResultSaveDto> saveDtoList = new ArrayList<>();
        for ( ApiResponseSaveDto apiResponse : apiResponseSaveDtoList ) {
            JSONArray item = apiResponse.getItem();
            Iterator<JSONObject> iter = item.iterator();
            while(iter.hasNext()) {
                JSONObject itemEach = (JSONObject) iter.next();
                saveDtoList.add(0, parseCafeArticleItem(itemEach, apiResponse.getKeyword()) );
            }
        }
        return saveDtoList;
    }

    private  List<NaverNewsResultSaveDto> getSaveNewsArticleList( List<ApiResponseSaveDto> apiResponseSaveDtoList) {
        List<NaverNewsResultSaveDto> saveDtoList = new ArrayList<>();
        for( ApiResponseSaveDto apiResponse : apiResponseSaveDtoList ) {
            JSONArray item = apiResponse.getItem();
            Iterator<JSONObject> iter = item.iterator();
            while(iter.hasNext()) {
                JSONObject itemEach = (JSONObject) iter.next();
                saveDtoList.add(0, parseNewsArticleItem(itemEach, apiResponse.getKeyword()));
            }
        }
        return saveDtoList;
    }

    private NaverCafeResultSaveDto parseCafeArticleItem( JSONObject item, String keyword ) {
        String title = (String) item.get("title");
        String link = (String) item.get("link");
        String descriptions = (String) item.get("description");
        String cafeName = (String) item.get("cafename");
        String cafeUrl = (String) item.get("cafeurl");
        return new NaverCafeResultSaveDto( title, link, descriptions, cafeName, cafeUrl, keyword );
    }

    private NaverNewsResultSaveDto parseNewsArticleItem( JSONObject item, String keyword ) {
        String title = (String) item.get("title");
        String originallink = (String) item.get("originallink");
        String link = (String) item.get("link");
        String description = (String) item.get("description");
        String pubDate = (String) item.get("pubDate");
        return new NaverNewsResultSaveDto( title, originallink, link, description, pubDate, keyword );
    }

    private ApiResponseSaveDto apiResponseParser( String url, String keyword ) {
        JSONParser parser = new JSONParser();
        try {
            String apiResponse = naverSearchApi.search(url);
            JSONObject object = (JSONObject) parser.parse( apiResponse );
            String lastBuildDateChanel = (String) object.get( "lastBuildDate" );
            Long totalChanel = (Long) object.get( "total" );
            JSONArray item = (JSONArray) object.get( "items" );

            return ApiResponseSaveDto.builder()
                    .keyword(keyword)
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
