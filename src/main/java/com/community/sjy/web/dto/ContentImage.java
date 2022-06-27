package com.community.sjy.web.dto;

import lombok.extern.slf4j.Slf4j;

public class ContentImage {
    private String image_file;
    private String preview_URL;

    public String getImage_file() {
        return image_file;
    }

    public void setImage_file(String image_file) {
        this.image_file = image_file;
    }

    public String getPreview_URL() {
        return preview_URL;
    }

    public void setPreview_URL(String preview_URL) {
        this.preview_URL = preview_URL;
    }

}
