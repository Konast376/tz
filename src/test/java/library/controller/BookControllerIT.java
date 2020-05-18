package library.controller;

import com.github.database.rider.core.api.dataset.DataSet;
import com.github.database.rider.core.api.dataset.ExpectedDataSet;
import com.jupiter.tools.spring.test.postgres.annotation.meta.EnablePostgresIntegrationTest;
import library.api.book.dto.BookDto;
import library.repository.BookRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Maxim Seredkin
 */
@AutoConfigureWebTestClient
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@EnablePostgresIntegrationTest
class BookControllerIT {
    @Autowired
    private WebTestClient client;

    @Autowired
    private BookRepository bookRepository;

    /** TODO: Починить работу метода. Чтобы начал задаваться автор. */
    @Test
    @DataSet(value = "/datasets/book/api/create.json", cleanAfter = true, cleanBefore = true)
    @ExpectedDataSet("/datasets/book/api/create__expected.json")
    void create() throws Exception {
        // Arrange
        BookDto bookDto = BookDto.builder()
                                   .id(1L)
                                   .bookName("book name")
                                   .numberOfPages(1)
                                   .publicationYear(1970)
                           //        .authorId(1L)
                                   .build();

        // Act
        client.post()
              .uri("/api/book/create")
              .bodyValue(bookDto)
              .exchange()

              // Assert
              .expectStatus().isOk()
              .expectBody().json("{\"id\":1," +
                                 "\"bookName\":\"book name\"," +
                                 "\"numberOfPages\":1," +
                                 "\"publicationYear\":\"1970-01-01T00:00:00.000+0000\"," +
                                 "\"authorId\":null}");

        assertThat(bookRepository.count()).isEqualTo(1L);
    }
}