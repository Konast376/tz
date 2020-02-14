package library.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "books")
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String bookName;
    private int numberOfPages;
    private Date publicationYear;
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "author_id")
    private Set<Author> author;
}




