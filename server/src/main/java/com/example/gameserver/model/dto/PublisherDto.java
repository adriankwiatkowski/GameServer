package com.example.gameserver.model.dto;

import com.example.gameserver.model.domain.Publisher;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * DTO for {@link com.example.gameserver.model.domain.Publisher}
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PublisherDto implements Serializable {

    private Integer id;
    private String name;

    public static PublisherDto from(Publisher publisher) {
        var publisherDto = new PublisherDto();

        publisherDto.setId(publisher.getId());
        publisherDto.setName(publisher.getName());

        return publisherDto;
    }
}