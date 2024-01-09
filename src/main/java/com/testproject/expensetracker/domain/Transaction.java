package com.testproject.expensetracker.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Transaction {
    private Integer transaction_id;
    private Integer category_id;
    private Integer userId;
    private Double amount;
    private String note;
    private Long transactionDate;
}
