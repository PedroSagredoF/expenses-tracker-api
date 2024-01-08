package com.testproject.expensetracker.repositories;

import com.testproject.expensetracker.domain.Category;
import com.testproject.expensetracker.exceptions.EtBadRequestException;
import com.testproject.expensetracker.exceptions.EtResourceNotFoundException;

import java.util.List;

public interface CategoryRepository {
    List<Category> findAll(Integer userId) throws EtResourceNotFoundException;
    Category findById(Integer userId, Integer categoryId) throws EtResourceNotFoundException;
    Integer create(Integer userId, String title, String description) throws EtBadRequestException;
    void update(Integer userId, Integer categoryId, Category category) throws EtBadRequestException;
    void removeById(Integer userId, Integer categoryId);
}
