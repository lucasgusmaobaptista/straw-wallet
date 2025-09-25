package me.lucasgusmao.straw_wallet_api.mappers;

import me.lucasgusmao.straw_wallet_api.dto.expense.ExpenseRequest;
import me.lucasgusmao.straw_wallet_api.dto.expense.ExpenseResponse;
import me.lucasgusmao.straw_wallet_api.model.Expense;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface ExpenseMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "user", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    Expense toEntity(ExpenseRequest dto);

    @Mapping(source = "category.id", target = "categoryId")
    @Mapping(source = "category.name", target = "categoryName")
    ExpenseResponse toResponse(Expense entity);
}
