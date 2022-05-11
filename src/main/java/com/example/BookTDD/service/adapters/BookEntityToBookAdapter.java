package com.example.BookTDD.service.adapters;

import com.example.BookTDD.repository.entity.BookEntity;
import com.example.BookTDD.service.models.Book;

public interface BookEntityToBookAdapter {
    Book convert(BookEntity savedBookFromRepository);
}
