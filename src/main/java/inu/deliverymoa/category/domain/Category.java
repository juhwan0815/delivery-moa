package inu.deliverymoa.category.domain;

import inu.deliverymoa.common.domain.BaseEntity;
import inu.deliverymoa.common.domain.YN;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Category extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "category_id")
    private Long id;

    private String name;

    @Enumerated(EnumType.STRING)
    private YN delYn;

    public static Category createCategory(String name) {
        Category category = new Category();
        category.name = name;
        category.delYn = YN.N;
        return category;
    }

    public void update(String name) {
        this.name = name;
    }

    public void delete() {
        this.delYn = YN.Y;
    }
}
