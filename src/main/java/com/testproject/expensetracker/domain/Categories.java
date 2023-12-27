package com.testproject.expensetracker.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Categories {

    private Integer category_id;
    private Integer uder_id;
    private String titte;
    private String descripction;
    private Double totalExpense;

}
