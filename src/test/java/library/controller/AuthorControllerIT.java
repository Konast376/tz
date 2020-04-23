package library.controller;

import com.github.database.rider.core.api.dataset.DataSet;
import com.github.database.rider.core.api.dataset.ExpectedDataSet;
import com.jupiter.tools.spring.test.postgres.annotation.meta.EnablePostgresIntegrationTest;
import com.whitesoft.cloud.api.dto.mailbox.AttachmentDTO;
import library.AuthorService;
import library.controller.dto.AuthorDto;
import library.controller.dto.CreateAuthorDto;
import library.service.CreateAuthorArgument;
import lombok.var;
import org.assertj.core.api.Assertions;
import org.assertj.core.api.BDDSoftAssertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.util.Date;

@AutoConfigureWebTestClient
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@EnablePostgresIntegrationTest
public class AuthorControllerIT {
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
    @DataSet(value = "datasets/author/api/empty.json", cleanAfter = true, cleanBefore = true)
    @ExpectedDataSet("datasets/author/api/create__expected.json")
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

        Assertions.assertThat(result).isEqualToIgnoringGivenFields(expectedDto, "id");

        Assertions.assertThat(service.create(CreateAuthorArgument.builder().build()));

    }



    @Test
    @DataSet(value = "/api/update.json", cleanAfter = true, cleanBefore = true)
    @ExpectedDataSet("/api/update__expected.json")
    void update(BDDSoftAssertions softly) throws Exception {
        // Arrange
        long id = 1L;
        AttachmentDTO updateDto = null; /*AttachmentDTO.builder()
                                     .id(id)
                                     .fullName("full name")
                                     .nationality("nationality")
                                     .dateOfBirth(new Date(1))
                                     .build()*/
        ;

        // Act
        var result = client.post()
                           .uri("/authors/{id}/update", id)
                           .bodyValue(updateDto)
                           .exchange()

                           // Assert
                           .expectStatus()
                           .isOk()
                           .expectBody(AttachmentDTO.class)
                           .returnResult()
                           .getResponseBody();

        softly.then(result).isEqualToIgnoringGivenFields(expectedDto, "id", "createDate", "description");
//        softly.then(result.get)
    }

    @Test
    @DataSet(value = "/api/empty.json", cleanAfter = true, cleanBefore = true)
    @ExpectedDataSet("/api/empty.json")
    void getWhenNotExists() throws Exception {
        // Act
        client.get()
              .uri("/authors/{id}", 1)
              .exchange()

              // Assert
              .expectStatus().isNotFound()
              .expectBody().json("{\"status\":404," +
                                 "\"error\":\"Not Found\"," +
                                 "\"message\":\"Record not found with id :\"," +
                                 "\"path\":\"/authors/1\"}}");
    }

    @Test
    @DataSet(value = "/api/exist.json", cleanAfter = true, cleanBefore = true)
    @ExpectedDataSet("/api/exist.json")
    void getExist() throws Exception {
        // Act
        client.get()
              .uri("/authors/{id}", 1)
              .exchange()

              // Assert
              .expectStatus().isNotFound();
//              .expectBody().json("
    }
}
