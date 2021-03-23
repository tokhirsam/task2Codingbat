package uz.pdp.task2.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.pdp.task2.entity.User;
import uz.pdp.task2.payload.ApiResponse;
import uz.pdp.task2.payload.UserDto;
import uz.pdp.task2.service.UserService;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserService userService;

    @GetMapping
    public HttpEntity<?> getAll(){
        List<User> userList = userService.getAll();
        return ResponseEntity.status(userList.size() != 0 ? HttpStatus.OK : HttpStatus.NOT_FOUND).body(userList);
    }

    @GetMapping("/{id}")
    public HttpEntity<?> getById(@PathVariable Integer id){
        ApiResponse userServiceById = userService.getById(id);
        return ResponseEntity.status(userServiceById.isSuccess() ? HttpStatus.OK : HttpStatus.NOT_FOUND).body(userServiceById);
    }

    @PostMapping
    public HttpEntity<?> add(@RequestBody UserDto userDto){
        ApiResponse apiResponse = userService.add(userDto);
        return ResponseEntity.status(apiResponse.isSuccess() ? HttpStatus.OK : HttpStatus.BAD_REQUEST).body(apiResponse);
    }

    @PutMapping("/{id}")
    public HttpEntity<?> edit(@RequestBody UserDto userDto, @PathVariable Integer id){
        ApiResponse apiResponse = userService.edit(userDto, id);
        return ResponseEntity.status(apiResponse.isSuccess() ? HttpStatus.OK : HttpStatus.BAD_REQUEST).body(apiResponse);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Integer id){
        ApiResponse response = userService.delete(id);
        return ResponseEntity.status(response.isSuccess()?202:409).body(response);
    }
}
