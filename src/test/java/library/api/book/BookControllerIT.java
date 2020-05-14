package library.api.book;

import com.github.database.rider.core.api.dataset.DataSet;
import com.github.database.rider.core.api.dataset.ExpectedDataSet;
import com.jupiter.tools.spring.test.postgres.annotation.meta.EnablePostgresIntegrationTest;
import com.whitesoft.api.dto.CollectionDTO;
import library.api.book.dto.BookDto;
import library.api.book.dto.CreateBookDto;
import library.api.book.dto.UpdateBookDto;
import library.model.author.Author;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.test.web.reactive.server.WebTestClient;

import static org.assertj.core.api.Assertions.assertThat;

@AutoConfigureWebTestClient
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@EnablePostgresIntegrationTest
public class BookControllerIT {

    @Autowired
    private WebTestClient client;

    private final long id = 1L;
    final BookDto expectedDto = BookDto.builder()
                                       .id(id)
                                       .bookName("bookName")
                                       .numberOfPages(120)
                                       .publicationYear(1970)
                                       .build();

    @Test
    @DataSet(value = "/datasets/book/api/create.json", cleanAfter = true, cleanBefore = true)
    @ExpectedDataSet("datasets/book/api/create__expected.json")
    void create() throws Exception {
        //Arrange
        CreateBookDto bookDto = CreateBookDto.builder()
                                             .bookName("bookName")
                                             .numberOfPages(120)
                                             .publicationYear(1970)
                                             .authorId(1L)
                                             .build();

        //Act
        BookDto result = client.post()
                               .uri("/books/create")
                               .bodyValue(bookDto)
                               .exchange()

                               //Assert
                               .expectStatus()
                               .isCreated()
                               .expectBody(BookDto.class)
                               .returnResult()
                               .getResponseBody();

        assertThat(result).isEqualToComparingFieldByField(expectedDto);
    }

    @Test
    @DataSet(value = "/datasets/book/api/update.json", cleanAfter = true, cleanBefore = true)
    @ExpectedDataSet("datasets/book/api/update__expected.json")
    void update() throws Exception {
        //Arrange
        UpdateBookDto updateDto = UpdateBookDto.builder()
                                               .bookName("bookName")
                                               .numberOfPages(120)
                                               .publicationYear(1970)
                                               .authorId(1L)
                                               .build();
        //Act
        BookDto result = client.post()
                               .uri("/books/{id}/update", id)
                               .bodyValue(updateDto)
                               .exchange()

                               //Assert
                               .expectStatus()
                               .isOk()
                               .expectBody(BookDto.class)
                               .returnResult()
                               .getResponseBody();

        assertThat(result).isEqualToComparingFieldByField(expectedDto);
    }

    @Test
    @DataSet(value = "/datasets/book/api/empty.json", cleanAfter = true, cleanBefore = true)
    @ExpectedDataSet("/datasets/book/api/empty.json")
    void getWhenNotExists() throws Exception {
        // Act
        client.get()
              .uri("/books/{id}", id)
              .exchange()

              // Assert
              .expectStatus().isNotFound()
              .expectBody().json("{\"message\":\"Книга не найдена\"}");
    }

    @Test
    @DataSet(value = "/datasets/book/api/get.json", cleanAfter = true, cleanBefore = true)
    @ExpectedDataSet("/datasets/book/api/get.json")
    void get() throws Exception {
        // Act
        BookDto result = client.get()
                               .uri("/books/{id}", id)
                               .exchange()

                               // Assert
                               .expectStatus()
                               .isOk()
                               .expectBody(BookDto.class)
                               .returnResult()
                               .getResponseBody();
        assertThat(result).isEqualTo(expectedDto);
    }

    @Test
    @DataSet(value = "/datasets/book/api/list.json", cleanBefore = true, cleanAfter = true)
    void list() throws Exception {
        // Act
        CollectionDTO<BookDto> result = client.get().uri(uriBuilder -> uriBuilder.path("/books/list")
                                                                                 .queryParam("pageNo", 1)
                                                                                 .queryParam("pageSize", 1)
                                                                                 .queryParam("sortField", "id")
                                                                                 .queryParam("sortDirection", "DESC")
                                                                                 .build())

                                              .exchange()

                                              // Assert
                                              .expectStatus()
                                              .isOk()
                                              .expectBody(new ParameterizedTypeReference<CollectionDTO<BookDto>>() {})
                                              .returnResult().getResponseBody();
        Assertions.assertThat(result.getTotalCount()).isEqualTo(2);
        assertThat(result.getItems().get(0)).isEqualToIgnoringGivenFields(expectedDto, "id");
    }

    @Test
    @DataSet(value = "/datasets/book/api/delete.json", cleanBefore = true, cleanAfter = true)
    @ExpectedDataSet("/datasets/book/api/delete__expected.json")
    void delete() throws Exception {
        // Act
        client.post()
              .uri("/books/{id}/delete", id)
              .exchange()

              // Assert
              .expectStatus()
              .isOk();
    }
}