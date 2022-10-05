package com.naverapi.naverapi.article.application.service;

import com.naverapi.naverapi.article.domain.apirequest.ApiRequestRepository;
import com.naverapi.naverapi.article.ui.dto.ApiRequestSaveDto;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@AllArgsConstructor
@Service
@Slf4j
public class ApiRequestService {
    private ApiRequestRepository apiRequestRepository;

    @Transactional
    public boolean saveApiRequestResult(String lastBuildDate, Long total, String url ){

        ApiRequestSaveDto apiRequestSaveDto = new ApiRequestSaveDto(  lastBuildDate, total, url );
        apiRequestRepository.save( apiRequestSaveDto.toEntity() );

        return true;
    }
}
