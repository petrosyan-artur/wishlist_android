package com.tlab.wish.wishes;

import lombok.Data;

public @Data class Wish {

    private String username;
    private String userId;
    private String createdDate;
    private String content;
    private String Id;
    private boolean isActive;
}