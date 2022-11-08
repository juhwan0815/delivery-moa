package inu.deliverymoa.category.service;

import inu.deliverymoa.category.domain.Category;
import inu.deliverymoa.category.domain.CategoryRepository;
import inu.deliverymoa.category.dto.CategoryCreateRequest;
import inu.deliverymoa.category.dto.CategoryResponse;
import inu.deliverymoa.category.dto.CategoryUpdateRequest;
import inu.deliverymoa.common.domain.YN;
import inu.deliverymoa.common.exception.DuplicateException;
import inu.deliverymoa.common.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;

    @Transactional
    public void createCategory(CategoryCreateRequest request) {

        if (categoryRepository.findByNameAndDelYn(request.getName(), YN.N).isPresent()) {
            throw new DuplicateException("이미 존재하는 카테고리입니다.");
        }

        Category category = Category.createCategory(request.getName());
        categoryRepository.save(category);
    }

    @Transactional
    public void updateCategory(Long categoryId, CategoryUpdateRequest request) {

        if (categoryRepository.findByNameAndDelYn(request.getName(), YN.N).isPresent()) {
            throw new DuplicateException("이미 존재하는 카테고리입니다.");
        }

        Category findCategory = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new NotFoundException("존재하지 않는 카테고리입니다."));


        findCategory.update(request.getName());
    }

    public List<CategoryResponse> findCategories() {
        List<Category> findCategories = categoryRepository.findByDelYn(YN.N);
        return findCategories.stream()
                .map(CategoryResponse::from)
                .collect(Collectors.toList());
    }

    @Transactional
    public void deleteCategory(Long categoryId) {
        Category findCategory = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new NotFoundException("존재하지 않는 카테고리입니다."));

        findCategory.delete();
    }
}
