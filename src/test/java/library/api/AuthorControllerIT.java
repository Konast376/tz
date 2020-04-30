package library.api;

import com.github.database.rider.core.api.dataset.DataSet;
import com.github.database.rider.core.api.dataset.ExpectedDataSet;
import com.jupiter.tools.spring.test.postgres.annotation.meta.EnablePostgresIntegrationTest;
import com.whitesoft.api.dto.CollectionDTO;
import library.api.author.dto.AuthorDto;
import library.api.author.dto.CreateAuthorDto;
import library.api.author.dto.UpdateAuthorDto;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.sql.Timestamp;

import static org.assertj.core.api.Assertions.assertThat;

@AutoConfigureWebTestClient(timeout = "15s")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@EnablePostgresIntegrationTest
public class AuthorControllerIT {

    @Autowired
    private WebTestClient client;

    long id = 1L;
    final AuthorDto expectedDto = AuthorDto.builder()
                                           .id(1L)
                                           .fullName("full name")
                                           .dateOfBirth(Timestamp.valueOf("2019-05-20 21:15:30.0"))
                                           .nationality("nationality")
                                           .build();

    @Test
    @DataSet(value = "/datasets/author/api/empty.json", cleanAfter = true, cleanBefore = true)
    @ExpectedDataSet("/datasets/author/api/create__expected.json")
    void create() throws Exception {
        // Arrange
        CreateAuthorDto authorDto = CreateAuthorDto.builder()
                                                   .fullName("full name")
                                                   .nationality("nationality")
                                                   .dateOfBirth(Timestamp.valueOf("2019-05-20 21:15:30.0"))
                                                   .build();
        // Act
        AuthorDto result = client.post()
                                 .uri("/authors/create")
                                 .bodyValue(authorDto)
                                 .exchange()

                                 // Assert
                                 .expectStatus()
                                 .isCreated()
                                 .expectBody(AuthorDto.class)
                                 .returnResult()
                                 .getResponseBody();

        assertThat(result).isEqualTo(expectedDto);
        assertThat(result.getId()).isNotNull();
    }

    @Test
    @DataSet(value = "/datasets/author/api/update.json", cleanAfter = true, cleanBefore = true)
    @ExpectedDataSet("/datasets/author/api/update__expected.json")
    void update() throws Exception {
        // Arrange
        UpdateAuthorDto updateDto = UpdateAuthorDto.builder()
                                                   .fullName("full name")
                                                   .nationality("nationality")
                                                   .dateOfBirth(Timestamp.valueOf("2019-05-20 21:15:30.0"))
                                                   .build();

        // Act
        AuthorDto result = client.post()
                                 .uri("/authors/{id}/update", id)
                                 .bodyValue(updateDto)
                                 .exchange()

                                 // Assert
                                 .expectStatus()
                                 .isOk()
                                 .expectBody(AuthorDto.class)
                                 .returnResult()
                                 .getResponseBody();

        assertThat(result).isEqualTo(expectedDto);
    }

    @Test
    @DataSet(value = "/datasets/author/api/empty.json", cleanAfter = true, cleanBefore = true)
    @ExpectedDataSet("/datasets/author/api/empty.json")
    void getWhenNotExists() throws Exception {
        // Act
        client.get()
              .uri("/authors/{id}", id)
              .exchange()

              // Assert
              .expectStatus().isNotFound()
              .expectBody().json("{\"message\":\"Автор не найден\"}");
    }

    @Test
    @DataSet(value = "/datasets/author/api/get.json", cleanAfter = true, cleanBefore = true)
    @ExpectedDataSet("/datasets/author/api/get.json")
    void get() throws Exception {
        // Act
        AuthorDto result = client.get()
                                 .uri("/authors/{id}", id)
                                 .exchange()

                                 // Assert
                                 .expectStatus()
                                 .isOk()
                                 .expectBody(AuthorDto.class)
                                 .returnResult()
                                 .getResponseBody();
        assertThat(result).isEqualTo(expectedDto);
    }

    @Test
    @DataSet(value = "/datasets/author/api/list.json", cleanBefore = true, cleanAfter = true)
    void list() throws Exception {
        // Act
        CollectionDTO<AuthorDto> result = client.get().uri(uriBuilder -> uriBuilder.path("/authors/list")
                                                                                   .queryParam("pageNo", 0)
                                                                                   .queryParam("pageSize", 1)
                                                                                   .queryParam("sortField", "id")
                                                                                   .queryParam("sortDirection", "DESC")
                                                                                   .build())

                                                .exchange()

                                                // Assert
                                                .expectStatus()
                                                .isOk()
                                                .expectBody(new ParameterizedTypeReference<CollectionDTO<AuthorDto>>() {
                                                })
                                                .returnResult().getResponseBody();
        Assertions.assertThat(result.getTotalCount()).isEqualTo(1);
        assertThat(result.getItems().get(0)).isEqualTo(expectedDto);
    }

    @Test
    @DataSet(value = "/datasets/author/api/delete.json", cleanBefore = true, cleanAfter = true)
    @ExpectedDataSet("/datasets/author/api/delete__expected.json")
    void delete() throws Exception {
        // Act
        client.post()
              .uri("/authors/{id}/delete", id)
              .exchange()

              // Assert
              .expectStatus()
              .isOk();
    }
}

