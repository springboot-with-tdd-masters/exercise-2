package com.example.exercise2.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class BookEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	Long id;
	
	String title;
	
	String author;

	public BookEntity() { }
	

	public BookEntity(String title, String autor) {
		this.title = title;
		this.author = autor;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}
	
}
