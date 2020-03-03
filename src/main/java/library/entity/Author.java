package library.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

@Data
@NoArgsConstructor
@Entity
@Table(name = "authors")
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

    @OneToMany(cascade = CascadeType.PERSIST, mappedBy = "author")
    private Set<Book> books;
}
