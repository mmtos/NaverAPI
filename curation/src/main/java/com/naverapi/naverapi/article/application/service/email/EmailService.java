package com.naverapi.naverapi.article.application.service.email;

import com.naverapi.naverapi.article.domain.email.Email;
import com.naverapi.naverapi.article.domain.email.EmailRepository;
import com.naverapi.naverapi.article.ui.dto.EmailSaveDto;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@AllArgsConstructor
@Service
@Slf4j
public class EmailService {
    private final EmailRepository emailRepository;

    @Transactional
    public Email saveEmailEntity( EmailSaveDto email ) {
        return emailRepository.save(email.toEntity());
    }
}
