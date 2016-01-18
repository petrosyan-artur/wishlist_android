package com.tlab.wish.wishes;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

public @Data class Wishes {

    private boolean success;
    private List<Wish> wishes = new ArrayList<Wish>();


}