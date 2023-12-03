package com.example.gameserver.service;

import com.example.gameserver.GameServerApplication;
import com.example.gameserver.domain.GenreEntity;
import com.example.gameserver.dto.GenreDto;
import com.example.gameserver.mapper.GenreMapper;
import com.example.gameserver.repository.GenreRepository;
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
class GenreServiceTest {

    @MockBean
    private GenreRepository repository;

    @Autowired
    private GenreService service;

    @Autowired
    private GenreMapper mapper;

    @Test
    void givenGenres_whenGetAllGenres_thenOk() {
        //given
        List<GenreEntity> expectedList = getGenres();
        when(repository.findAll()).thenReturn(expectedList);

        //when
        List<GenreDto> actualList = service.getAllGenres();

        //then
        assertEquals(expectedList.size(), actualList.size());
        for (int i = 0; i < expectedList.size(); i++) {
            GenreEntity expected = expectedList.get(i);
            GenreDto actual = actualList.get(i);

            assertNotNull(actual);
            assertEquals(expected.getName(), actual.getName());
        }
        Mockito.verify(repository).findAll();
    }

    @Test
    void givenDtoAndMockSave_whenInsert_thenIdIsNull() {
        //given
        GenreDto original = getGenreDto("Genre 1");
        original.setId(1L);
        when(repository.save(any(GenreEntity.class))).thenReturn(mapper.toEntity(original));

        //when
        GenreDto actual = service.insert(original);

        //then
        assertNull(actual.getId());
    }

    @Test
    void givenDtoAndMockSave_whenInsert_thenNameIsSame() {
        //given
        GenreDto original = getGenreDto("Genre 1");
        original.setId(1L);
        when(repository.save(any(GenreEntity.class))).thenReturn(mapper.toEntity(original));

        //when
        GenreDto actual = service.insert(original);

        //then
        assertEquals("Genre 1", actual.getName());
    }

    @Test
    void givenDtoAndMockSaveAndMockExistsById_whenUpdate_thenDataIsSame() {
        //given
        GenreDto original = getGenreDto("Genre 1");
        original.setId(1L);
        when(repository.save(any(GenreEntity.class))).thenReturn(mapper.toEntity(original));
        when(repository.existsById(1L)).thenReturn(true);

        //when
        GenreDto actual = service.update(original);

        //then
        assertEquals(1L, actual.getId());
        assertEquals("Genre 1", actual.getName());
    }

    @Test
    void givenNonExistentId_whenUpdate_thenThrowsEntityNotFoundException() {
        //given
        GenreDto original = getGenreDto("Genre 2");
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
        when(repository.findById(any())).thenReturn(Optional.of(getGenre("Genre 3")));
        when(repository.existsById(any())).thenReturn(true);

        //when
        //then
        assertDoesNotThrow(() -> service.deleteGenre(id));
    }

    @Test
    void givenNonExistentId_whenDelete_thenThrowsEntityNotFoundException() {
        //given
        GenreDto original = getGenreDto("Genre 2");
        original.setId(1L);
        when(repository.findById(any())).thenReturn(Optional.empty());

        //when
        //then
        assertThrows(EntityNotFoundException.class, () -> service.update(original));
    }

    private List<GenreEntity> getGenres() {
        return List.of(
                getGenre("Genre 1"),
                getGenre("Genre 2"),
                getGenre("Genre 3")
        );
    }

    private GenreEntity getGenre(String name) {
        return GenreEntity.builder()
                .name(name)
                .build();
    }

    private GenreDto getGenreDto(String name) {
        return GenreDto.builder()
                .name(name)
                .build();
    }
}