package com.example.gameserver.service;

import com.example.gameserver.GameServerApplication;
import com.example.gameserver.domain.PlatformEntity;
import com.example.gameserver.dto.PlatformDto;
import com.example.gameserver.mapper.PlatformMapper;
import com.example.gameserver.repository.PlatformRepository;
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
class PlatformServiceTest {

    @MockBean
    private PlatformRepository repository;

    @Autowired
    private PlatformService service;

    @Autowired
    private PlatformMapper mapper;

    @Test
    void givenPlatforms_whenGetAllPlatforms_thenOk() {
        //given
        List<PlatformEntity> expectedList = getPlatforms();
        when(repository.findAll()).thenReturn(expectedList);

        //when
        List<PlatformDto> actualList = service.getAllPlatforms();

        //then
        assertEquals(expectedList.size(), actualList.size());
        for (int i = 0; i < expectedList.size(); i++) {
            PlatformEntity expected = expectedList.get(i);
            PlatformDto actual = actualList.get(i);

            assertNotNull(actual);
            assertEquals(expected.getName(), actual.getName());
        }
        Mockito.verify(repository).findAll();
    }

    @Test
    void givenDtoAndMockSave_whenInsert_thenIdIsNull() {
        //given
        PlatformDto original = getPlatformDto("Platform 1");
        original.setId(1L);
        when(repository.save(any(PlatformEntity.class))).thenReturn(mapper.toEntity(original));

        //when
        PlatformDto actual = service.insert(original);

        //then
        assertNull(actual.getId());
    }

    @Test
    void givenDtoAndMockSave_whenInsert_thenNameIsSame() {
        //given
        PlatformDto original = getPlatformDto("Platform 1");
        original.setId(1L);
        when(repository.save(any(PlatformEntity.class))).thenReturn(mapper.toEntity(original));

        //when
        PlatformDto actual = service.insert(original);

        //then
        assertEquals("Platform 1", actual.getName());
    }

    @Test
    void givenDtoAndMockSaveAndMockExistsById_whenUpdate_thenDataIsSame() {
        //given
        PlatformDto original = getPlatformDto("Platform 1");
        original.setId(1L);
        when(repository.save(any(PlatformEntity.class))).thenReturn(mapper.toEntity(original));
        when(repository.existsById(1L)).thenReturn(true);

        //when
        PlatformDto actual = service.update(original);

        //then
        assertEquals(1L, actual.getId());
        assertEquals("Platform 1", actual.getName());
    }

    @Test
    void givenNonExistentId_whenUpdate_thenThrowsEntityNotFoundException() {
        //given
        PlatformDto original = getPlatformDto("Platform 2");
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
        when(repository.findById(any())).thenReturn(Optional.of(getPlatform("Platform 3")));
        when(repository.existsById(any())).thenReturn(true);

        //when
        //then
        assertDoesNotThrow(() -> service.deletePlatform(id));
    }

    @Test
    void givenNonExistentId_whenDelete_thenThrowsEntityNotFoundException() {
        //given
        PlatformDto original = getPlatformDto("Platform 2");
        original.setId(1L);
        when(repository.findById(any())).thenReturn(Optional.empty());

        //when
        //then
        assertThrows(EntityNotFoundException.class, () -> service.update(original));
    }

    private List<PlatformEntity> getPlatforms() {
        return List.of(
                getPlatform("Platform 1"),
                getPlatform("Platform 2"),
                getPlatform("Platform 3")
        );
    }

    private PlatformEntity getPlatform(String name) {
        return PlatformEntity.builder()
                .name(name)
                .build();
    }

    private PlatformDto getPlatformDto(String name) {
        return PlatformDto.builder()
                .name(name)
                .build();
    }
}