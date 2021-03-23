package uz.pdp.task2.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.pdp.task2.entity.Category;
import uz.pdp.task2.payload.ApiResponse;
import uz.pdp.task2.payload.CategoryDto;
import uz.pdp.task2.service.CategoryService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/category")
public class CategoryController {

    @Autowired
    CategoryService categoryService;


    @GetMapping
    public HttpEntity<?> getAll(){
        List<Category> categories = categoryService.getAll();
        return ResponseEntity.status(categories.size() != 0 ? HttpStatus.OK : HttpStatus.NOT_FOUND).body(categories);
    }


    @GetMapping("/{id}")
    public HttpEntity<?> getById(@PathVariable Integer id){
        ApiResponse byId = categoryService.getById(id);
        return ResponseEntity.status(byId.isSuccess() ? HttpStatus.OK : HttpStatus.NOT_FOUND).body(byId);
    }


    @PostMapping
    public HttpEntity<?> add(@Valid @RequestBody CategoryDto categoryDto){
        ApiResponse apiResponse = categoryService.add(categoryDto);
        return ResponseEntity.status(apiResponse.isSuccess() ? HttpStatus.OK : HttpStatus.CONFLICT).body(apiResponse);
    }

    @PutMapping("/{id}")
    public HttpEntity<?> edit(@Valid @RequestBody CategoryDto categoryDto, @PathVariable Integer id){
        ApiResponse apiResponse = categoryService.edit(categoryDto, id);
        return ResponseEntity.status(apiResponse.isSuccess() ? HttpStatus.OK : HttpStatus.CONFLICT).body(apiResponse);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Integer id){
        ApiResponse response = categoryService.delete(id);
        return ResponseEntity.status(response.isSuccess()?202:409).body(response);
    }
}
