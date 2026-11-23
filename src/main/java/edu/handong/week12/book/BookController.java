package edu.handong.week12.book;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@RestController
@Tag(name = "Book API", description = "Book REST CRUD API")
@RequestMapping("/api/books")
public class BookController {
    private final BookService service;

    public BookController(BookService service) {
        this.service = service;
    }
    @Operation(summary ="책 목록 조회",
                                     description="Pagination과 Sorting을 적용하여 도서 목록을 조회.")
    @GetMapping
    public ResponseEntity<Page<BookResponse>> list(Pageable pageable) {
        Page<BookResponse> books = service.findAll(pageable)
                .map(this::toResponse);

        return ResponseEntity.ok(books);
    }    // TODO: Pageable

    @GetMapping("/{id}")
    public ResponseEntity<BookResponse> get(@PathVariable Long id) {
        Book book = service.findById(id);
        return ResponseEntity.ok(toResponse(book));
    }


    @Operation(summary="도서 등록",
                                   description="신규 도서를 등록 후 201 상태코드 반환.")
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
