package edu.handong.week12.book;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Min;

public record BookRequest(
        @NotBlank String title,
        @NotBlank String author,
        @Min(0) int price
) {
}