package me.lucasgusmao.straw_wallet_api.repository;

import me.lucasgusmao.straw_wallet_api.model.Income;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Repository
public interface IncomeRepository extends JpaRepository<Income, UUID> {

    List<Income> findByUserIdOrderByDateDesc(UUID userId);

    List<Income> findTop5ByUserIdOrderByDateDesc(UUID userId);

    @Query("SELECT SUM(i.amount) FROM Income i WHERE i.user.id = :userId")
    BigDecimal findTotalIncomesByUserId(@Param("userId") UUID userId);

    List<Income> findByUserIdAndDateBetweenAndNameContainingIgnoreCase(UUID userId, LocalDate startDate, LocalDate endDate, String name);

    List<Income> findByUserIdAndDateBetween(UUID userId, LocalDate startDate, LocalDate endDate);
}
