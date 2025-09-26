package me.lucasgusmao.straw_wallet_api.dto.other;

import me.lucasgusmao.straw_wallet_api.model.enums.CategoryType;

import java.time.LocalDate;

public record FIlterDTO(

        CategoryType type,
        LocalDate startDate,
        LocalDate finalDate,
        String keyword,
        String sortField,
        String sortOrder
) {
}
