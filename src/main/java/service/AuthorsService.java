package service;

import entity.Authors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import repository.AuthorsRepository;
import java.util.List;

@Service
public class AuthorsService {
    @Autowired
    private final AuthorsRepository authorsRepository;


    public AuthorsService(AuthorsRepository AuthorsRepository, AuthorsRepository usersRepository, AuthorsRepository authorsRepository){
        this.authorsRepository = authorsRepository;
    }

    public void createAuthors(Authors authors) {
        authorsRepository.save(authors);
    }

    public List<Authors> findAll(){

        return authorsRepository.findAll();
    }

    public Authors findById(Long userId){
        return authorsRepository.findById(userId).orElse(null);
    }

    public List<Authors> findAllByName(String name){
        return authorsRepository.findAllByFullname(name);
    }

}
