package com.masters.masters.exercise.repository;

import com.masters.masters.exercise.model.BookEntity;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

@DataJpaTest
public class BookRepositoryTest {

    @Autowired
    BookRepository repository;

    @Test
    public void saveHappyPath(){
        BookEntity entity = new BookEntity();
        entity.setAuthor("author");
        entity.setTitle("title");
        BookEntity savedEntity = repository.save(entity);
        Assertions.assertThat(savedEntity).extracting("title","author").containsExactly("title","author");
    }

    @Test
    public void findAllHappyPath(){
        BookEntity entity1 = new BookEntity();
        BookEntity entity2 = new BookEntity();
        entity1.setTitle("title1");
        entity1.setAuthor("author1");
        entity2.setTitle("title2");
        entity2.setAuthor("author2");
        List<BookEntity> entityList = repository.saveAll(List.of(entity1,entity2));
        Assertions.assertThat(repository.findAll()).containsAll(entityList);
    }

    @Test
    public void findAllNoRecords(){
        Assertions.assertThat(repository.findAll()).isEmpty();
    }
}
