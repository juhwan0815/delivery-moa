package inu.deliverymoa.category.dto;

import inu.deliverymoa.category.domain.Category;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CategoryResponse {

    private Long categoryId;

    private String name;

    public static CategoryResponse from(Category category) {
        CategoryResponse response = new CategoryResponse();
        response.categoryId = category.getId();
        response.name = category.getName();
        return response;
    }
}
