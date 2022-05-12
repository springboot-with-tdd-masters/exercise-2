package com.magenic.mobog.exercise2app.repositories;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
public class BookRepositoryTest {

	
	@Test
	@DisplayName("findAll should retrieve all books")
	void findAllShouldRetrieveAllBooks() {
		
	}
}
