package library.model.author;

import library.model.book.Book;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "author")
public class Author {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, unique = true)
    private Long id;

    @Column(nullable = false)
    private String fullName;

    @Column(nullable = false)
    private Date dateOfBirth;

    @Column
    private String nationality;

    @Builder.Default
    @OneToMany(cascade = CascadeType.PERSIST, mappedBy = "author")
    private Set<Book> books = new HashSet<>();
}
