package com.naverapi.naverapi.interfaces.contorller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/keyword")
@Slf4j
public class KeyWordController {

    @GetMapping("/add")
    public void addKeyWord() {
        return;
    }

    @GetMapping("/update")
    public void updateKeyWord() {
        return;
    }
}
