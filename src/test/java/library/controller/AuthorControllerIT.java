package library.controller;

import com.github.database.rider.core.api.dataset.DataSet;
import com.github.database.rider.core.api.dataset.ExpectedDataSet;
import com.jupiter.tools.spring.test.postgres.annotation.meta.EnablePostgresIntegrationTest;
import library.api.author.dto.AuthorDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.util.Date;

/**
 * @author Maxim Seredkin
 */
@AutoConfigureWebTestClient
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@EnablePostgresIntegrationTest
class AuthorControllerIT {
    @Autowired
    private WebTestClient client;

    @Test
    @DataSet(value = "/datasets/author/api/empty.json", cleanAfter = true, cleanBefore = true)
    @ExpectedDataSet("/datasets/author/api/create__expected.json")
    void createWhenIdNotNull() throws Exception {
        // Arrange
        AuthorDto authorDto = AuthorDto.builder()
                                       .id(1L)
                                       .fullName("full name")
                                       .nationality("nationality")
                                       .dateOfBirth(new Date(1))
                                       .build();

        // Act
        client.post()
              .uri("/authors/create")
              .bodyValue(authorDto)
              .exchange()

              // Assert
              .expectStatus().isOk()
              .expectBody().json("{\"id\":1," +
                                 "\"fullName\":\"full name\"," +
                                 "\"dateOfBirth\":\"1970-01-01T00:00:00.001+0000\"," +
                                 "\"nationality\":\"nationality\"," +
                                 "\"books\":null}");
    }

    @Test
    @DataSet(value = "/datasets/author/api/create--when-id-null.json", cleanAfter = true, cleanBefore = true)
    @ExpectedDataSet("/datasets/author/api/create--when-id-null__expected.json")
    void createWhenIdNull() throws Exception {
        // Arrange
        AuthorDto authorDto = AuthorDto.builder()
                                       .id(null)
                                       .fullName("full name")
                                       .nationality("nationality")
                                       .dateOfBirth(new Date(1))
                                       .build();

        // Act
        client.post()
              .uri("/authors/create")
              .bodyValue(authorDto)
              .exchange()

              // Assert
              .expectStatus().isOk()
              .expectBody().json("{\"id\":2," +
                                 "\"fullName\":\"full name\"," +
                                 "\"dateOfBirth\":\"1970-01-01T00:00:00.001+0000\"," +
                                 "\"nationality\":\"nationality\"," +
                                 "\"books\":null}");
    }

    @Test
    @DataSet(value = "/datasets/author/api/update.json", cleanAfter = true, cleanBefore = true)
    @ExpectedDataSet("/datasets/author/api/update__expected.json")
    void update() throws Exception {
        // Arrange
        long id = 1L;
        AuthorDto authorDto = AuthorDto.builder()
                                       .id(id)
                                       .fullName("full name")
                                       .nationality("nationality")
                                       .dateOfBirth(new Date(1))
                                       .build();

        // Act
        client.post()
              .uri("/authors/{id}/update", id)
              .bodyValue(authorDto)
              .exchange()

              // Assert
              .expectStatus().isOk()
              .expectBody().json("{\"id\":1," +
                                 "\"fullName\":\"full name\"," +
                                 "\"dateOfBirth\":\"1970-01-01T00:00:00.001+0000\"," +
                                 "\"nationality\":\"nationality\"," +
                                 "\"books\":null}");
    }

    @Test
    @DataSet(value = "/datasets/author/api/empty.json", cleanAfter = true, cleanBefore = true)
    @ExpectedDataSet("/datasets/author/api/empty.json")
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
}