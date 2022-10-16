package com.naverapi.naverapi.apirequest.application.service;

import com.naverapi.naverapi.apirequest.application.NaverSearchApi;
import com.naverapi.naverapi.apirequest.domain.meta.ApiRequestMetaRepository;
import com.naverapi.naverapi.apirequest.domain.article.BlogArticle;
import com.naverapi.naverapi.apirequest.domain.article.BlogArticleRepository;
import com.naverapi.naverapi.apirequest.domain.article.CafeArticle;
import com.naverapi.naverapi.apirequest.domain.article.CafeArticleRepository;
import com.naverapi.naverapi.apirequest.domain.article.NewsArticle;
import com.naverapi.naverapi.apirequest.domain.article.NewsArticleRepository;
import com.naverapi.naverapi.apirequest.schedule.dto.ApiResponseSaveDto;
import com.naverapi.naverapi.apirequest.schedule.dto.NaverBlogArticleResponseDto;
import com.naverapi.naverapi.apirequest.schedule.dto.NaverBlogResultSaveDto;
import com.naverapi.naverapi.apirequest.schedule.dto.NaverCafeResultResponseDto;
import com.naverapi.naverapi.apirequest.schedule.dto.NaverNewsResultResponseDto;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.*;

import com.naverapi.naverapi.apirequest.schedule.dto.NaverCafeResultSaveDto;
import com.naverapi.naverapi.apirequest.schedule.dto.NaverNewsResultSaveDto;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@AllArgsConstructor
@Service
@Slf4j
public class NaverApiRequestService {
    private final static int TYPE_BLOG = 1;
    private final static int TYPE_NEWS = 2;
    private final static int TYPE_CAFE = 3;
    private final static int TYPE_DOC = 4;

    private final static boolean TYPE_SIM = true;
    private final static boolean TYPE_DATE = false;

    private BlogArticleRepository blogArticleRepository;
    private CafeArticleRepository cafeArticleRepository;
    private NewsArticleRepository newsArticleRepository;
    private ApiRequestMetaRepository apiRequestMetaRepository;

    private NaverSearchApi naverSearchApi;

    @Transactional
    public int getBlogContentsSortByDate( String keyword, int cnt ) {
        // api 요청 로직 ( api 요청은 비동기입니다. )
        List<ApiResponseSaveDto> responseList = getTotalResponseResult(keyword, TYPE_BLOG, TYPE_DATE, cnt);
        // 블로그 콘텐츠 관련 목록
        List<NaverBlogResultSaveDto> saveDtoList = deleteDuplicationBlogArticle(keyword, getSaveBlogArticleList(responseList));

        // 저장 로직
        for ( NaverBlogResultSaveDto dto : saveDtoList) {
            blogArticleRepository.save(dto.toEntity());
        }
        for( ApiResponseSaveDto apiResponse : responseList ) {
            apiRequestMetaRepository.save(apiResponse.toEntity());
        }
        return saveDtoList.size();
    }

    @Transactional
    public int getCafeContentsSortByDate( String keyword, int cnt ) {
        List<ApiResponseSaveDto> apiResponseSaveDtoList = getTotalResponseResult(keyword, TYPE_CAFE, TYPE_DATE, cnt);

        List<NaverCafeResultSaveDto> saveDtoList = deleteDuplicationCafeArticle(keyword, getSaveCafeArticleList(apiResponseSaveDtoList));

        for( NaverCafeResultSaveDto dto : saveDtoList ) {
            cafeArticleRepository.save(dto.toEntity());
        }
        for( ApiResponseSaveDto apiReponse : apiResponseSaveDtoList ) {
            apiRequestMetaRepository.save(apiReponse.toEntity());
        }
        return saveDtoList.size();
    }

    @Transactional
    public int getNewsContentsSortByDate(String keyword, int cnt ) {
        List<ApiResponseSaveDto> apiResponseSaveDtoList = getTotalResponseResult(keyword, TYPE_NEWS, TYPE_DATE, cnt);

        List<NaverNewsResultSaveDto> saveDtoList = deleteDuplicationNewsArticle(keyword,getSaveNewsArticleList(apiResponseSaveDtoList));

        for( NaverNewsResultSaveDto dto : saveDtoList ) {
            newsArticleRepository.save(dto.toEntity());
        }
        for( ApiResponseSaveDto apiReponse : apiResponseSaveDtoList ) {
            apiRequestMetaRepository.save(apiReponse.toEntity());
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



    private List<NaverBlogResultSaveDto> deleteDuplicationBlogArticle( String keyword , List<NaverBlogResultSaveDto> newList ){

        List<NaverBlogResultSaveDto> list = new ArrayList<>();
        List<NaverBlogArticleResponseDto> oldList = getBlogArticleBeforeSaving100(keyword);

        List<BlogArticle> newEntityList = new ArrayList<>();
        List<BlogArticle> oldEntityList = new ArrayList<>();

        for ( NaverBlogResultSaveDto newDto : newList ) {
            newEntityList.add(newDto.toEntity());
        }

        for ( NaverBlogArticleResponseDto oldDto : oldList ) {
            oldEntityList.add((oldDto.toEntity()));
        }

        Set<BlogArticle> newEntitySet = new HashSet<>(newEntityList);
        Set<BlogArticle> oldEntitySet = new HashSet<>(oldEntityList);

        newEntitySet.removeAll(oldEntitySet);

        List<BlogArticle> resultList = new ArrayList<>(newEntitySet);

        for (BlogArticle nbr : resultList) {
            list.add( NaverBlogResultSaveDto.builder()
                    .title(nbr.getTitle())
                    .postdate(nbr.getPostdate())
                    .description(nbr.getDescription())
                    .link(nbr.getLink())
                    .bloggerlink(nbr.getBloggerlink())
                    .bloggername(nbr.getBloggername())
                    .keyword(nbr.getKeyword())
                    .build() );
        }

        return list;
    }

    private List<NaverCafeResultSaveDto> deleteDuplicationCafeArticle(String keyword , List<NaverCafeResultSaveDto> newList ){

        List<NaverCafeResultSaveDto> list = new ArrayList<>();
        List<NaverCafeResultResponseDto> oldList = getCafeArticleBeforeSaving100(keyword);

        List<CafeArticle> newEntityList = new ArrayList<>();
        List<CafeArticle> oldEntityList = new ArrayList<>();

        for ( NaverCafeResultSaveDto newDto : newList ) {
            newEntityList.add(newDto.toEntity());
        }

        for ( NaverCafeResultResponseDto oldDto : oldList ) {
            oldEntityList.add((oldDto.toEntity()));
        }

        Set<CafeArticle> newEntitySet = new HashSet<>(newEntityList);
        Set<CafeArticle> oldEntitySet = new HashSet<>(oldEntityList);

        newEntitySet.removeAll(oldEntitySet);

        List<CafeArticle> resultList = new ArrayList<>(newEntitySet);

        for (CafeArticle ncr : resultList) {
            list.add( NaverCafeResultSaveDto.builder()
                    .title(ncr.getTitle())
                    .link(ncr.getLink())
                    .description(ncr.getDescription())
                    .cafeName(ncr.getCafeName())
                    .cafeUrl(ncr.getCafeUrl())
                    .keyword(ncr.getKeyword())
                    .build() );
        }

        return list;
    }

    private List<NaverNewsResultSaveDto> deleteDuplicationNewsArticle( String keyword , List<NaverNewsResultSaveDto> newList ){

        List<NaverNewsResultSaveDto> list = new ArrayList<>();
        List<NaverNewsResultResponseDto> oldList = getNewsArticleBeforeSaving100(keyword);

        List<NewsArticle> newEntityList = new ArrayList<>();
        List<NewsArticle> oldEntityList = new ArrayList<>();

        for ( NaverNewsResultSaveDto newDto : newList ) {
            newEntityList.add(newDto.toEntity());
        }

        for ( NaverNewsResultResponseDto oldDto : oldList ) {
            oldEntityList.add((oldDto.toEntity()));
        }

        Set<NewsArticle> newEntitySet = new HashSet<>(newEntityList);
        Set<NewsArticle> oldEntitySet = new HashSet<>(oldEntityList);

        newEntitySet.removeAll(oldEntitySet);

        List<NewsArticle> resultList = new ArrayList<>(newEntitySet);

        for (NewsArticle nnr : resultList) {
            list.add( NaverNewsResultSaveDto.builder()
                    .title(nnr.getTitle())
                    .originallink(nnr.getOriginallink())
                    .link(nnr.getLink())
                    .description(nnr.getDescription())
                    .pubDate(nnr.getPubDate())
                    .keyword(nnr.getKeyword())
                    .build() );
        }

        return list;
    }

    @Cacheable(cacheNames = "blogArticleList")
    @Transactional(readOnly = true)
    private List<NaverBlogArticleResponseDto> getBlogArticleBeforeSaving100( String keyword ) {
        return blogArticleRepository.findTop100ByKeywordOrderByIdDesc(keyword)
                .stream().map(NaverBlogArticleResponseDto::new).collect(Collectors.toList());
    }

    @Cacheable(cacheNames = "cafeArticleList")
    @Transactional(readOnly = true)
    private List<NaverCafeResultResponseDto> getCafeArticleBeforeSaving100( String keyword ) {
        return cafeArticleRepository.findTop100ByKeywordOrderByIdDesc(keyword)
                .stream().map(NaverCafeResultResponseDto::new).collect(Collectors.toList());
    }

    @Cacheable(cacheNames = "newsArticleList")
    @Transactional(readOnly = true)
    private List<NaverNewsResultResponseDto> getNewsArticleBeforeSaving100( String keyword ) {
        return newsArticleRepository.findTop100ByKeywordOrderByIdDesc(keyword)
                .stream().map(NaverNewsResultResponseDto::new).collect(Collectors.toList());
    }

}
