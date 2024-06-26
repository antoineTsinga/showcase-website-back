package org.onyx.showcasebackend.entities;

import com.fasterxml.jackson.annotation.JsonValue;
import io.swagger.v3.oas.annotations.media.Schema;


public enum Genre {
    HOMME(0),FEMME(1),ENFANT(2);
    @JsonValue
    private final int code;

    Genre(int code){
        this.code = code;
    }

    public int getCode(){
        return this.code;
    }
}
