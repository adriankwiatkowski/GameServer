package com.example.gameserver.service;

import com.example.gameserver.GameServerApplication;
import com.example.gameserver.domain.DeveloperEntity;
import com.example.gameserver.dto.DeveloperDto;
import com.example.gameserver.mapper.DeveloperMapper;
import com.example.gameserver.repository.DeveloperRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = GameServerApplication.class)
@Transactional
class DeveloperServiceTest {

    @Autowired
    private DeveloperService service;

    @Autowired
    private DeveloperRepository repository;

    @Autowired
    private DeveloperMapper mapper;

    @Test
    void givenDevelopers_whenGetAllDevelopers_thenOk() {
        //given
        List<DeveloperDto> expectedList = List.of(
                givenDeveloperDto(getDeveloperDto("Developer 1")),
                givenDeveloperDto(getDeveloperDto("Developer 2"))
        );

        //when
        List<DeveloperDto> actualList = service.getAllDevelopers();

        //then
        assertEquals(expectedList.size(), actualList.size());
        for (int i = 0; i < expectedList.size(); i++) {
            DeveloperDto expected = expectedList.get(i);
            DeveloperDto actual = actualList.get(i);

            assertNotNull(actual);
            assertEquals(expected.getName(), actual.getName());
        }
    }

    @Test
    void givenDtoWithUsedId_whenInsert_thenIdIsNew() {
        //given
        DeveloperDto previousDeveloper = givenDeveloperDto(getDeveloperDto("Developer 1"));
        DeveloperDto original = getDeveloperDto("Developer 2");
        original.setId(previousDeveloper.getId());

        //when
        DeveloperDto actual = service.insert(original);

        //then
        assertNotEquals(original.getId(), actual.getId());
    }

    @Test
    void givenDto_whenInsert_thenNameIsSame() {
        //given
        DeveloperDto expected = getDeveloperDto("Developer 1");

        //when
        DeveloperDto actual = service.insert(expected);

        //then
        assertEquals(expected.getName(), actual.getName());
    }

    @Test
    void givenDto_whenInsert_thenIncreaseCount() {
        //given
        long expected = repository.count() + 1L;
        DeveloperDto dto = getDeveloperDto("Developer 1");

        //when
        service.insert(dto);
        long actual = repository.count();

        //then
        assertEquals(expected, actual);
    }

    @Test
    void givenDto_whenUpdate_thenDataIsSame() {
        //given
        DeveloperDto expected = givenDeveloperDto(getDeveloperDto("Developer 1"));

        //when
        DeveloperDto actual = service.update(expected);

        //then
        assertEquals(expected.getId(), actual.getId());
        assertEquals(expected.getName(), actual.getName());
    }

    @Test
    void givenNonExistentId_whenUpdate_thenThrowsEntityNotFoundException() {
        //given
        DeveloperDto original = getDeveloperDto("Developer 1");
        original.setId(1L);

        //when
        //then
        assertThrows(EntityNotFoundException.class, () -> service.update(original));
    }

    @Test
    void givenExistentId_whenDelete_thenHappyPath() {
        //given
        DeveloperDto original = givenDeveloperDto(getDeveloperDto("Developer 1"));

        //when
        //then
        assertDoesNotThrow(() -> service.deleteDeveloper(original.getId()));
    }

    @Test
    void givenNonExistentId_whenDelete_thenThrowsEntityNotFoundException() {
        //given
        Long id = 1L;

        //when
        //then
        assertThrows(EntityNotFoundException.class, () -> service.deleteDeveloper(id));
    }

    private DeveloperDto givenDeveloperDto(DeveloperDto developerDto) {
        DeveloperEntity developerEntity = mapper.toEntity(developerDto);
        developerEntity = repository.save(developerEntity);
        return mapper.toDto(developerEntity);
    }

    private DeveloperDto getDeveloperDto(String name) {
        return DeveloperDto.builder()
                .name(name)
                .build();
    }
}