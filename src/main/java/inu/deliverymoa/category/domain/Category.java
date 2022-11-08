package inu.deliverymoa.category.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "category_id")
    private Long id;

    private String name;

    public static Category createCategory(String name) {
        Category category = new Category();
        category.name = name;
        return category;
    }

    public void update(String name) {
        this.name = name;
    }
}
