package com.example.chuyen_de_1.controller;

import com.example.chuyen_de_1.model.Category;
import com.example.chuyen_de_1.model.ResponseObject;
import com.example.chuyen_de_1.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/category")
public class CategoryController {

    @Autowired
    private CategoryRepository categoryRepository;

    @GetMapping("/all")
    public ResponseEntity<ResponseObject> getAllCategory() {
        return ResponseEntity.ok(new ResponseObject("Ok", "success get all category", categoryRepository.findAll()));
    }

    @PostMapping("/create")
    public ResponseEntity<ResponseObject> createCategory(@RequestBody Category category) {
        if (categoryRepository.findByName(category.getName()) == null) {
            return ResponseEntity.ok(new ResponseObject("Ok", "success create category", categoryRepository.save(category)));
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseObject("Error", "error to create category", ""));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ResponseObject> updateCategory(@PathVariable Long id, @RequestBody Category category) {
        Category checkCategory = categoryRepository.findById(id).orElse(null);
        if (checkCategory != null) {
            checkCategory.setName(category.getName());
            return ResponseEntity.ok(new ResponseObject("Ok", "success update category", categoryRepository.save(checkCategory)));
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseObject("Error", "error to update category", ""));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ResponseObject> deleteCategory(@PathVariable Long id) {
        Category checkCategory = categoryRepository.findById(id).orElse(null);
        if (checkCategory != null) {
            categoryRepository.delete(checkCategory);
            return ResponseEntity.ok(new ResponseObject("Ok", "success delete category", ""));
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseObject("Error", "error to delete category", ""));
    }
}
