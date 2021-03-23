package uz.pdp.task2.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.pdp.task2.entity.Answer;
import uz.pdp.task2.payload.AnswerDto;
import uz.pdp.task2.payload.ApiResponse;
import uz.pdp.task2.service.AnswerService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/answer")
public class AnswerController {

    @Autowired
    AnswerService answerService;

    @GetMapping
    public HttpEntity<?> getAll(){
        List<Answer> answerList = answerService.getAll();
        return ResponseEntity.status(answerList.size() != 0 ? HttpStatus.OK : HttpStatus.NO_CONTENT).body(answerList);
    }

    @GetMapping("/{id}")
    public HttpEntity<?> getById(@PathVariable Integer id){
        Answer answerServiceById = answerService.getOne(id);
        return ResponseEntity.ok(answerServiceById);
    }

    @PostMapping
    public HttpEntity<?> add(@Valid @RequestBody AnswerDto answerDto){
        ApiResponse apiResponse = answerService.add(answerDto);
        return ResponseEntity.status(apiResponse.isSuccess() ? HttpStatus.OK : HttpStatus.BAD_REQUEST).body(apiResponse);
    }

    @PutMapping("/{id}")
    public HttpEntity<?> edit(@Valid @RequestBody AnswerDto answerDto, @PathVariable Integer id){
        ApiResponse apiResponse = answerService.edit(answerDto, id);
        return ResponseEntity.status(apiResponse.isSuccess() ? HttpStatus.OK : HttpStatus.BAD_REQUEST).body(apiResponse);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Integer id){
        ApiResponse response = answerService.delete(id);
        return ResponseEntity.status(response.isSuccess()?202:409).body(response);
    }
}
