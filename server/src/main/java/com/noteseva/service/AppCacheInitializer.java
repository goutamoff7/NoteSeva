package com.noteseva.service;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AppCacheInitializer {

    @Autowired
    AppCacheService appCacheService;

    @PostConstruct
    public void init(){
        appCacheService.reloadSubjectStructure();
    }
}
