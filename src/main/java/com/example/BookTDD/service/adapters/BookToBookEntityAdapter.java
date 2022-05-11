package com.example.BookTDD.service.adapters;

import com.example.BookTDD.repository.entity.BookEntity;
import com.example.BookTDD.service.models.Book;

public interface BookToBookEntityAdapter {

    BookEntity convert(Book book);
}
