package me.lucasgusmao.straw_wallet_api.repository;

import me.lucasgusmao.straw_wallet_api.model.Expense;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Repository
public interface ExpenseRepository extends JpaRepository<Expense, UUID> {

    List<Expense> findByUserIdOrderByDateDesc(UUID userId);

    List<Expense> findTop5ByUserIdOrderByDateDesc(UUID userId);

    @Query("SELECT SUM(e.amount) FROM Expense e WHERE e.user.id = :userId")
    BigDecimal findTotalExpensesByUserId(@Param("userId") UUID userId);

    List<Expense> findByUserIdAndDateBetweenAndNameContainingIgnoreCase(UUID userId, LocalDate startDate, LocalDate endDate, String name);

    List<Expense> findByUserIdAndDateBetween(UUID userId, LocalDate startDate, LocalDate endDate);
}
