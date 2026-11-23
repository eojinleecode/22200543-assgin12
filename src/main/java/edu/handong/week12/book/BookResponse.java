package edu.handong.week12.book;

public record BookResponse(
        Long id,
        String title,
        String author,
        int price
) {
}
