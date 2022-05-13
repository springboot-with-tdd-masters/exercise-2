package com.masters.masters.exercise.services;

import com.masters.masters.exercise.model.BookDtoRequest;
import com.masters.masters.exercise.model.BookDtoResponse;
import com.masters.masters.exercise.model.BookEntity;

import java.util.List;

public interface BookService {

    BookDtoResponse save(BookDtoRequest bookDto);
    public List<BookEntity> findAllBooks();
}
