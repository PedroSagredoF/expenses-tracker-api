package com.testproject.expensetracker.Service;

import com.testproject.expensetracker.domain.Category;
import com.testproject.expensetracker.exceptions.EtBadRequestException;
import com.testproject.expensetracker.exceptions.EtResourceNotFoundException;

import java.util.List;

public interface CategoryServices {

    List<Category> fetchAllCategories(Integer userId);

    Category FetchCategoryById(Integer userID, Integer categoryId) throws EtResourceNotFoundException;

    Category addCategory(Integer userId, String tittle, String description) throws EtBadRequestException;

    void updateCategory(Integer uderId, Integer categoryId, Category category) throws EtBadRequestException;

    void removeCategoryWithAllTransactions(Integer userId, Integer categoryId) throws EtBadRequestException;





}
