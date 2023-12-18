package com.testproject.expensetracker.resources;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/categories")
public class Categories {

    @GetMapping("")
    public String getAllCategories(HttpServletRequest request){
        int userId = (Integer) request.getAttribute("userId");
        return "Autenticated! UserId: "+ userId;
    }
}
