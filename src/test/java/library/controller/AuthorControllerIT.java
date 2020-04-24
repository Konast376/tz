package library.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.github.database.rider.core.api.dataset.DataSet;
import com.github.database.rider.core.api.dataset.ExpectedDataSet;
import com.google.common.collect.Sets;
import com.jupiter.tools.spring.test.postgres.annotation.meta.EnablePostgresIntegrationTest;
import com.whitesoft.api.dto.CollectionDTO;
import com.whitesoft.api.dto.SimpleValueDTO;
import com.whitesoft.cloud.api.dto.mailbox.AttachmentDTO;
import com.whitesoft.util.Tuples;
import library.AuthorService;
import library.argument.UpdateAuthorArgument;
import library.controller.dto.AuthorDto;
import library.controller.dto.CreateAuthorDto;
import library.controller.dto.UpdateAuthorDto;
import library.service.CreateAuthorArgument;
import lombok.var;
import org.assertj.core.api.Assertions;
import org.assertj.core.api.BDDSoftAssertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.http.HttpStatus.OK;

@AutoConfigureWebTestClient
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@EnablePostgresIntegrationTest
public class AuthorControllerIT {
    long id = 1L;
    @Autowired
    private WebTestClient client;
    @Autowired
    AuthorService service;

    final AuthorDto expectedDto = AuthorDto.builder()
            .fullName("full name")
            .dateOfBirth(new Date(1))
            .nationality("nationality")
            .build();

    @Test
    @DataSet(value = "/datasets/author/api/empty.json", cleanAfter = true, cleanBefore = true)
    @ExpectedDataSet("/datasets/author/api/create__expected.json")
    void createWhenIdNotNull() throws Exception {
        // Arrange
        CreateAuthorDto authorDto = CreateAuthorDto.builder()
                .fullName("full name")
                .nationality("nationality")
                .dateOfBirth(new Date(1))
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

        assertThat(result).isEqualToIgnoringGivenFields(expectedDto, "id");

        assertThat(service.create(CreateAuthorArgument.builder().build()));

    }

    @Test
    @DataSet(value = "/datasets/author/api/update.json", cleanAfter = true, cleanBefore = true)
    @ExpectedDataSet("/datasets/author/api/update__expected.json")
    void update() throws Exception {
        // Arrange
        UpdateAuthorDto updateDto = UpdateAuthorDto.builder()
                .fullName("full name")
                .nationality("nationality")
                .dateOfBirth(new Date(1))
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

        assertThat(result).isEqualToIgnoringGivenFields(expectedDto, "id");
        assertThat(service.update(id, UpdateAuthorArgument.builder().build()));
    }

    @Test
    @DataSet(value = "/datasets/author/api/update.json", cleanAfter = true, cleanBefore = true)
    @ExpectedDataSet("/datasets/author/api/empty.json")
    void updateNotFound() throws Exception {

        // Act
        client.post()
                .uri("/authors/{id}/update", id)
                .exchange()

                // Assert
                .expectStatus().isNotFound()
                .expectBody().json("{\"status\":404," +
                "\"error\":\"Not Found\"," +
                "\"message\":\"Record not found with id :\"," +
                "\"path\":\"/authors/1\"}}");


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
                .expectBody().json("{\"status\":404," +
                "\"error\":\"Not Found\"," +
                "\"message\":\"Record not found with id :\"," +
                "\"path\":\"/authors/1\"}}");
    }

    @Test
    @DataSet(value = "/datasets/author/api/exist.json", cleanAfter = true, cleanBefore = true)
    @ExpectedDataSet("/datasets/author/api/exist.json")
    void getExist() throws Exception {
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
        assertThat(result).isEqualToIgnoringGivenFields(expectedDto, "id");
        assertThat(service.getExisting(id));
    }

    @Test
    @DataSet(value = "/datasets/author/api/list.json", cleanBefore = true, cleanAfter = true)
    void list() throws Exception {
        // Act
        CollectionDTO<AuthorDto> result = client.get().uri(uriBuilder -> uriBuilder.path("/authors/list")
                .queryParam("pageNo", 0)
                .queryParam("pageSize", 10)
                .queryParam("sortField", "id")
                .queryParam("sortDirection", "DESC")
                .build())

                .exchange()

                // Assert
                .expectStatus()
                .isOk()
                .expectBody(new ParameterizedTypeReference<CollectionDTO<AuthorDto>>() {})
                .returnResult().getResponseBody();


    }

    @Test
    @DataSet(value = "/datasets/author/api/delete.json", cleanBefore = true, cleanAfter = true)
    @ExpectedDataSet("/datasets/author/api/empty.json")
    void delete() throws Exception {
        // Act
        client.post()
                .uri("/authors/{id}/delete", id)
                .exchange()

                // Assert
                .expectStatus()
                .isOk();
    }

    @Test
    @DataSet(value = "/datasets/author/api/empty.json", cleanAfter = true, cleanBefore = true)
    @ExpectedDataSet("/datasets/author/api/empty.json")
    void deleteNotFound() throws Exception {
        // Act
        client.post()
                .uri("/authors/{id}/delete", id)
                .exchange()

                // Assert
                .expectStatus().isNotFound()
                .expectBody().json("{\"status\":404," +
                "\"error\":\"Not Found\"," +
                "\"message\":\"Record not found with id :\"," +
                "\"path\":\"/authors/1\"}}");
    }
}

