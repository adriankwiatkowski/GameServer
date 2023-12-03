package com.example.gameserver.service;

import com.example.gameserver.GameServerApplication;
import com.example.gameserver.domain.PublisherEntity;
import com.example.gameserver.dto.PublisherDto;
import com.example.gameserver.mapper.PublisherMapper;
import com.example.gameserver.repository.PublisherRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest(classes = GameServerApplication.class)
class PublisherServiceTest {

    @MockBean
    private PublisherRepository repository;

    @Autowired
    private PublisherService service;

    @Autowired
    private PublisherMapper mapper;

    @Test
    void givenPublishers_whenGetAllPublishers_thenOk() {
        //given
        List<PublisherEntity> expectedList = getPublishers();
        when(repository.findAll()).thenReturn(expectedList);

        //when
        List<PublisherDto> actualList = service.getAllPublishers();

        //then
        assertEquals(expectedList.size(), actualList.size());
        for (int i = 0; i < expectedList.size(); i++) {
            PublisherEntity expected = expectedList.get(1);
            PublisherDto actual = actualList.get(1);

            assertNotNull(actual);
            assertEquals(expected.getName(), actual.getName());
        }
        Mockito.verify(repository).findAll();
    }

    @Test
    void givenDtoAndMockSave_whenInsert_thenIdIsNull() {
        //given
        PublisherDto original = getPublisherDto("Publisher 1");
        original.setId(1L);
        when(repository.save(any(PublisherEntity.class))).thenReturn(mapper.toEntity(original));

        //when
        PublisherDto actual = service.insert(original);

        //then
        assertNull(actual.getId());
    }

    @Test
    void givenDtoAndMockSave_whenInsert_thenNameIsSame() {
        //given
        PublisherDto original = getPublisherDto("Publisher 1");
        original.setId(1L);
        when(repository.save(any(PublisherEntity.class))).thenReturn(mapper.toEntity(original));

        //when
        PublisherDto actual = service.insert(original);

        //then
        assertEquals("Publisher 1", actual.getName());
    }

    @Test
    void givenDtoAndMockSaveAndMockExistsById_whenUpdate_thenDataIsSame() {
        //given
        PublisherDto original = getPublisherDto("Publisher 1");
        original.setId(1L);
        when(repository.save(any(PublisherEntity.class))).thenReturn(mapper.toEntity(original));
        when(repository.existsById(1L)).thenReturn(true);

        //when
        PublisherDto actual = service.update(original);

        //then
        assertEquals(1L, actual.getId());
        assertEquals("Publisher 1", actual.getName());
    }

    @Test
    void givenNonExistentId_whenUpdate_thenThrowsEntityNotFoundException() {
        //given
        PublisherDto original = getPublisherDto("Publisher 2");
        original.setId(1L);
        when(repository.existsById(any())).thenReturn(false);

        //when
        //then
        assertThrows(EntityNotFoundException.class, () -> service.update(original));
    }

    @Test
    void givenExistentId_whenDelete_thenOk() {
        //given
        Long id = 1L;
        when(repository.findById(any())).thenReturn(Optional.of(getPublisher("Publisher 3")));
        when(repository.existsById(any())).thenReturn(true);

        //when
        //then
        assertDoesNotThrow(() -> service.deletePublisher(id));
    }

    @Test
    void givenNonExistentId_whenDelete_thenThrowsEntityNotFoundException() {
        //given
        PublisherDto original = getPublisherDto("Publisher 2");
        original.setId(1L);
        when(repository.findById(any())).thenReturn(Optional.empty());

        //when
        //then
        assertThrows(EntityNotFoundException.class, () -> service.update(original));
    }

    private List<PublisherEntity> getPublishers() {
        return List.of(
                getPublisher("Publisher 1"),
                getPublisher("Publisher 2"),
                getPublisher("Publisher 3")
        );
    }

    private PublisherEntity getPublisher(String name) {
        return PublisherEntity.builder()
                .name(name)
                .build();
    }

    private PublisherDto getPublisherDto(String name) {
        return PublisherDto.builder()
                .name(name)
                .build();
    }
}