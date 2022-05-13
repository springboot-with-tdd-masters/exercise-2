package com.masters.masters.exercise.services;

import com.masters.masters.exercise.model.BookDtoRequest;
import com.masters.masters.exercise.model.BookDtoResponse;
import com.masters.masters.exercise.model.BookEntity;
import com.masters.masters.exercise.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class BookServiceImpl implements BookService{

    @Autowired
    private BookRepository repo;

    public BookDtoResponse save(BookDtoRequest bookDto) {
        BookEntity entity = new BookEntity();
        entity.setAuthor(bookDto.getAuthor());
        entity.setTitle(bookDto.getTitle());
        BookEntity savedEntity = repo.save(entity);
        BookDtoResponse response = new BookDtoResponse();
        response.setAuthor(savedEntity.getAuthor());
        response.setTitle(savedEntity.getTitle());
        return response;

    }

    public List<BookEntity> findAllBooks() {
        List<BookEntity> bookList = repo.findAll();
        if(bookList.isEmpty()){
            return new ArrayList<BookEntity>();
        }else{
            return bookList;
        }
    }
}
