package com.testproject.expensetracker.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Category {

    private Integer category_id;
    private Integer user_id;
    private String title;
    private String description;
    private Double totalExpense;

}
