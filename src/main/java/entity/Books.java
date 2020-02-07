package entity;
import javax.persistence.*;

@Entity
@Table(name = "Books")
public class Books {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String Book_name;

    @Column
    private String Author;

    @Column
    private int Number_of_pages;

    public Books() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBook_name() {
        return Book_name;
    }

    public void setBook_name(String book_name) {
        Book_name = book_name;
    }

    public String getAuthor() {
        return Author;
    }

    public void setAuthor(String author) {
        Author = author;
    }

    public int getNumber_of_pages() {
        return Number_of_pages;
    }

    public void setNumber_of_pages(int number_of_pages) {
        Number_of_pages = number_of_pages;
    }
}




