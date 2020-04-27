package library.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;
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
    @Column
    private String fullName;
    @Column
    private Date dateOfBirth;
    @Column
    private String nationality;
    @OneToMany(cascade = CascadeType.PERSIST, mappedBy = "author")
    private Set<Book> books;
}
