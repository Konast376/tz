package entity;
import javax.persistence.*;
import java.util.Date;




    @Entity
    @Table(name = "Authors")
    public class Authors {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        public Long id;

        @Column
        public String Fullname;

        @Column
        public Date Date_of_birth;

        @Column
        public String Nationality;

        public Authors() {
        }

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public String getFullname() {
            return Fullname;
        }

        public void setFullname(String fullname) {
            Fullname = fullname;
        }

        public Date getDate_of_birth() {
            return Date_of_birth;
        }

        public void setDate_of_birth(Date date_of_birth) {
            Date_of_birth = date_of_birth;
        }

        public String getNationality() {
            return Nationality;
        }

        public void setNationality(String nationality) {
            Nationality = nationality;
        }
    }

