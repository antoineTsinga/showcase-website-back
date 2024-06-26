package org.onyx.showcasebackend.entities;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.stream.Stream;

@Converter(autoApply = true)
public class GenreConverter implements AttributeConverter<Genre, Integer> {

    @Override
    public Integer convertToDatabaseColumn(Genre genre) {
        if (genre == null) {
            return null;
        }
        return genre.getCode();
    }


    @Override
    public Genre convertToEntityAttribute(Integer code) {
        if (code == null) {
            return null;
        }

        return Stream.of(Genre.values())
                .filter(c -> c.getCode() == code)
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }
}