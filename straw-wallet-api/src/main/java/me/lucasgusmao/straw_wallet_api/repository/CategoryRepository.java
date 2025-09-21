package me.lucasgusmao.straw_wallet_api.repository;

import me.lucasgusmao.straw_wallet_api.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface CategoryRepository extends JpaRepository<Category, UUID> {

    List<Category> findByUserID(UUID userId);

    Optional<Category> findByIdAndUserId(UUID categoryId, UUID userId);

    List<Category> findByTypeAndUserId(String type, UUID userId);

    Boolean existsByNameAndUserId(String name, UUID userId);
}
