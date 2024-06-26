package org.onyx.showcasebackend.entities;

import com.fasterxml.jackson.annotation.JsonValue;
import io.swagger.v3.oas.annotations.media.Schema;


public enum Category {
    CHEMISES(0),
    JEANS(1),
    ENSEMBLES(2),
    VESTES(3),
    BAS(4),
    TOPS(5),
    ACCESSOIRES(6);
    @JsonValue
    private final int code;

    Category(int code){
        this.code = code;
    }

    public int getCode() {
        return this.code;
    }
}
