package inu.deliverymoa.category.domain;

import inu.deliverymoa.common.domain.YN;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, Long> {

    List<Category> findByDelYn(YN delYn);
    Optional<Category> findByNameAndDelYn(String name, YN delYn);
}
