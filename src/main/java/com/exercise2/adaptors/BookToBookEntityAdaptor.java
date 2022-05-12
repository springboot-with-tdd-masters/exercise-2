package com.exercise2.adaptors;

import com.exercise2.model.Book;
import com.exercise2.model.BookEntity;

public interface BookToBookEntityAdaptor {
    BookEntity convert(Book book);
}
