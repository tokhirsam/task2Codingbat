package uz.pdp.task2.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.pdp.task2.entity.Task;
import uz.pdp.task2.payload.ApiResponse;
import uz.pdp.task2.payload.TaskDto;
import uz.pdp.task2.service.TaskService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/task")
public class TaskController {

    @Autowired
    TaskService taskService;


    @GetMapping
    public HttpEntity<?> getAll(){
        List<Task> tasks = taskService.getAll();
        return ResponseEntity.status(tasks.size() != 0 ? HttpStatus.OK : HttpStatus.NOT_FOUND).body(tasks);
    }

    @GetMapping("/{id}")
    public HttpEntity<?> getById(@PathVariable Integer id){
        ApiResponse byId = taskService.getById(id);
        return ResponseEntity.status(byId.isSuccess() ? HttpStatus.OK : HttpStatus.NOT_FOUND).body(byId);
    }


    @PostMapping
    public HttpEntity<?> add(@Valid @RequestBody TaskDto taskDto){
        ApiResponse apiResponse = taskService.add(taskDto);
        return ResponseEntity.status(apiResponse.isSuccess() ? HttpStatus.OK : HttpStatus.CONFLICT).body(apiResponse);
    }


    @PutMapping("/{id}")
    public HttpEntity<?> edit(@Valid @RequestBody TaskDto taskDto, @PathVariable Integer id){
        ApiResponse apiResponse = taskService.edit(taskDto, id);
        return ResponseEntity.status(apiResponse.isSuccess() ? HttpStatus.OK : HttpStatus.CONFLICT).body(apiResponse);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Integer id){
        ApiResponse response = taskService.delete(id);
        return ResponseEntity.status(response.isSuccess()?202:409).body(response);
    }
}
