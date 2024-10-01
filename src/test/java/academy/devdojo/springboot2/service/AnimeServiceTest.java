package academy.devdojo.springboot2.service;

import academy.devdojo.springboot2.domain.Anime;
import academy.devdojo.springboot2.exception.BadRequestException;
import academy.devdojo.springboot2.repository.AnimeRepository;
import academy.devdojo.springboot2.util.AnimeCreator;
import academy.devdojo.springboot2.util.AnimePostRequestBodyCreator;
import academy.devdojo.springboot2.util.AnimePutRequestBodyCreator;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@ExtendWith(SpringExtension.class)
class AnimeServiceTest {

    @InjectMocks
    private AnimeService animeService;

    @Mock
    private AnimeRepository animeRepositoryMock;

    @BeforeEach
    void setUp() {
        PageImpl<Anime> animePage = new PageImpl<>(List.of(AnimeCreator.createValidAnime()));
        BDDMockito.when(animeRepositoryMock.findAll(ArgumentMatchers.any(PageRequest.class)))
                .thenReturn(animePage);

        BDDMockito.when(animeRepositoryMock.findAll())
                .thenReturn(List.of(AnimeCreator.createValidAnime()));

        BDDMockito.when(animeRepositoryMock.findById(ArgumentMatchers.anyLong()))
                .thenReturn(Optional.of(AnimeCreator.createValidAnime()));

        BDDMockito.when(animeRepositoryMock.findByName(ArgumentMatchers.anyString()))
                .thenReturn(List.of(AnimeCreator.createValidAnime()));

        BDDMockito.when(animeRepositoryMock.save(ArgumentMatchers.any(Anime.class)))
                .thenReturn(AnimeCreator.createValidAnime());

        BDDMockito.doNothing().when(animeRepositoryMock).delete(ArgumentMatchers.any(Anime.class));
    }

    @Test
    @DisplayName("findAll returns list of animes inside page object when sucessful ")
    void findAll_ReturnsListOfAnimesInsidePageObject_WhenSucessful() {
        String exceptedName = AnimeCreator.createValidAnime().getName();

        Page<Anime> animePage = animeService.findAll(PageRequest.of(1,1));

        Assertions.assertThat(animePage).isNotNull();

        Assertions.assertThat(animePage.toList())
                .isNotEmpty()
                .hasSize(1);

        Assertions.assertThat(animePage.toList().get(0).getName()).isEqualTo(exceptedName);
    }

    @Test
    @DisplayName("findAllNonPageable returns list of animes when sucessful ")
    void findAllNonPageable_ReturnsListOfAnimes_WhenSucessful() {
        String exceptedName = AnimeCreator.createValidAnime().getName();

        List<Anime> animes = animeService.findAllNonPageable();

        Assertions.assertThat(animes)
                .isNotNull()
                .isNotEmpty()
                .hasSize(1);

        Assertions.assertThat(animes.get(0).getName()).isEqualTo(exceptedName);
    }

    @Test
    @DisplayName("findByIdOrThrowsBadRequestException returns anime when sucessful")
    void findByIdOrThrowsBadRequestException_ReturnsAnime_WhenSucessful() {
        Long exceptedId = AnimeCreator.createValidAnime().getId();

        Anime anime = animeService.findByIdOrThrowsBadRequestException(1);

        Assertions.assertThat(anime).isNotNull();

        Assertions.assertThat(anime.getId()).isNotNull().isEqualTo(exceptedId);
    }

    @Test
    @DisplayName("findByIdOrThrowsBadRequestException throws BadRequestException when anime is not found")
    void findByIdOrThrowsBadRequestException_ThrowsBadRequestException_WhenAnimeIsNotFound() {
        BDDMockito.when(animeRepositoryMock.findById(ArgumentMatchers.anyLong()))
                .thenReturn(Optional.empty());

        Assertions.assertThatExceptionOfType(BadRequestException.class)
                .isThrownBy(() -> animeService.findByIdOrThrowsBadRequestException(1));
    }

    @Test
    @DisplayName("findByName returns a list of anime when sucessful")
    void findByName_ReturnsListOfAnime_WhenSucessful() {
        String exceptedName = AnimeCreator.createValidAnime().getName();

        List<Anime> animes = animeService.findByName("anime");

        Assertions.assertThat(animes)
                .isNotNull()
                .isNotEmpty()
                .hasSize(1);

        Assertions.assertThat(animes.get(0).getName()).isEqualTo(exceptedName);
    }

    @Test
    @DisplayName("findByName returns a empty list when anime is not found")
    void findByName_ReturnsEmptyList_WhenAnimeIsNotFound() {
        BDDMockito.when(animeRepositoryMock.findByName(ArgumentMatchers.anyString()))
                .thenReturn(Collections.emptyList());

        List<Anime> animes = animeService.findByName("anime");

        Assertions.assertThat(animes)
                .isNotNull()
                .isEmpty();
    }

    @Test
    @DisplayName("save returns anime when sucessful")
    void save_ReturnsAnime_WhenSucessful() {
        Anime anime = animeService.save(AnimePostRequestBodyCreator.createAnimePostRequestBody());

        Assertions.assertThat(anime).isNotNull().isEqualTo(AnimeCreator.createValidAnime());
    }

    @Test
    @DisplayName("replace updates anime when sucessful")
    void replace_UpdatesAnime_WhenSucessful() {
        Assertions.assertThatCode(() -> animeService.replace(AnimePutRequestBodyCreator.createAnimePutRequestBody()))
                .doesNotThrowAnyException();
    }

    @Test
    @DisplayName("delete removes anime when sucessful")
    void delete_RemovesAnime_WhenSucessful() {
        Assertions.assertThatCode(() -> animeService.delete(1))
                .doesNotThrowAnyException();
    }
}