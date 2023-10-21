package com.example.gameserver.model.dto;

import com.example.gameserver.model.domain.Publisher;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * DTO for {@link com.example.gameserver.model.domain.Publisher}
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true, fluent = true)
public class PublisherDto implements Serializable {

    private Integer id;
    private String name;

    public static PublisherDto from(Publisher publisher) {
        var publisherDto = new PublisherDto();

        publisherDto.id(publisher.getId())
                .name(publisher.getName());

        return publisherDto;
    }
}