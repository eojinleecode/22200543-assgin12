package edu.handong.week12.book;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.stereotype.Service;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


@Service
public class BookService {
  private final BookRepository repository;

  public BookService(BookRepository repository) {
    this.repository = repository;
  }


  public Page<Book> findAll(Pageable pageable) {
    return repository.findAll(pageable);
  }// TODO STEP 6: Pagination/Sorting

  public Book findById(Long id) {
    return repository.findById(id)
            .orElseThrow(() -> new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "Book not found: " + id
            ));
  }
  // TODO STEP 5: 404


  public Book create(Book book) {
    return repository.save(book);
  } // TODO STEP 3: DTO

  public Book update(Long id, Book input) {
    Book book = findById(id);
    book.update(input.getTitle(), input.getAuthor(), input.getPrice());
    return repository.save(book);
  }


  public void delete(Long id) {
    findById(id);
    repository.deleteById(id);
  }
  // TODO: 404 if absent
}
