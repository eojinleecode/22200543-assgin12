package edu.handong.week12.book;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/books")
public class BookController {
    private final BookService service;

    public BookController(BookService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<BookResponse>> list() {
        List<BookResponse> books = service.findAll()
                .stream()
                .map(this::toResponse)
                .toList();

        return ResponseEntity.ok(books);
    } // TODO: Pageable

    @GetMapping("/{id}")
    public ResponseEntity<BookResponse> get(@PathVariable Long id) {
        Book book = service.findById(id);
        return ResponseEntity.ok(toResponse(book));
    }

    @PostMapping
    public ResponseEntity<BookResponse> create(@Valid @RequestBody BookRequest input) {
            Book book = new Book(input.title(), input.author(), input.price());
             Book saved =service.create(book);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(toResponse(saved));
    }
    // TODO: @Valid, ResponseEntity 201

    @PutMapping("/{id}")
    public ResponseEntity<BookResponse> update(
            @PathVariable Long id,@Valid @RequestBody BookRequest input) {

        Book book = new Book(input.title(), input.author(), input.price());
        Book updated = service.update(id, book);

        return ResponseEntity.ok(toResponse(updated));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
    // TODO: 204


    private BookResponse toResponse(Book book) {
        return new BookResponse(
                book.getId(),
                book.getTitle(),
                book.getAuthor(),
                book.getPrice()
        );
    }


}
