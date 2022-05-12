package com.exercise2.adaptors;

import com.exercise2.model.Book;
import com.exercise2.model.BookEntity;

public interface BookEntityToBookAdaptor {
    public Book convert(BookEntity mockedBookEntityFromBookDomain);
}
