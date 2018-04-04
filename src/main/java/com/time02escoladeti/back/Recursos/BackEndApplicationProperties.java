package com.time02escoladeti.back.Recursos;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class BackEndApplicationProperties {

    @Value("${back-end.application.path-photo}")
    private String pathPhoto;

    @Value("${back-end.application.files-url}")
    private String filesUrl;

    public String getPathPhoto() {
        return pathPhoto;
    }

    public String getFilesUrl() {
        return filesUrl;
    }
}
