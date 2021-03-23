package uz.pdp.task2.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.pdp.task2.payload.ApiResponse;
import uz.pdp.task2.payload.ProgLangDto;
import uz.pdp.task2.service.ProgLangService;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/proglang")
public class ProgLangController {

    @Autowired
    ProgLangService progLangService;

    @GetMapping()
    public HttpEntity<?> getAll() {
        return progLangService.getAll();
    }

    @GetMapping("/{id}")
    public HttpEntity<?> getById(@PathVariable Integer id) {
        ApiResponse byId = progLangService.getById(id);
        return ResponseEntity.status(byId.isSuccess() ? HttpStatus.OK : HttpStatus.NOT_FOUND).body(byId);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Integer id){
        ApiResponse response = progLangService.delete(id);
        return ResponseEntity.status(response.isSuccess()?202:409).body(response);
    }

    @PostMapping
    public HttpEntity<?> add(@Valid @RequestBody ProgLangDto progLangDto) {
        return progLangService.add(progLangDto);
    }

    @PutMapping("/{id}")
    public HttpEntity<?> edit(@Valid @RequestBody ProgLangDto progLangDto, @PathVariable Integer id) {
        return progLangService.edit(progLangDto, id);
    }
}
