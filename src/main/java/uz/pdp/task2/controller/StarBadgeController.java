package uz.pdp.task2.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.pdp.task2.entity.StarBadge;
import uz.pdp.task2.payload.ApiResponse;
import uz.pdp.task2.payload.StarBadgeDto;
import uz.pdp.task2.service.StarBadgeService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/starbadge")
public class StarBadgeController {

    @Autowired
    StarBadgeService starBadgeService;

    @GetMapping
    public HttpEntity<?> getAll(){
        List<StarBadge> starBadges = starBadgeService.getAll();
        return ResponseEntity.status(starBadges.size() != 0 ? HttpStatus.OK : HttpStatus.NO_CONTENT).body(starBadges);
    }


    @GetMapping("/{id}")
    public HttpEntity<?> getById(@PathVariable Integer id){
        ApiResponse byId = starBadgeService.getById(id);
        return ResponseEntity.status(byId.isSuccess() ? HttpStatus.OK : HttpStatus.NO_CONTENT).body(byId);
    }


    @PostMapping
    public HttpEntity<?> add(@Valid @RequestBody StarBadgeDto starBadgeDto){
        ApiResponse apiResponse = starBadgeService.add(starBadgeDto);
        return ResponseEntity.status(apiResponse.isSuccess() ? HttpStatus.OK : HttpStatus.CONFLICT).body(apiResponse);
    }


    @PutMapping("/{id}")
    public HttpEntity<?> edit(@Valid @RequestBody StarBadgeDto starBadgeDto, @PathVariable Integer id){
        ApiResponse apiResponse = starBadgeService.edit(starBadgeDto, id);
        return ResponseEntity.status(apiResponse.isSuccess() ? HttpStatus.OK : HttpStatus.CONFLICT).body(apiResponse);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Integer id){
        ApiResponse response = starBadgeService.delete(id);
        return ResponseEntity.status(response.isSuccess()?202:409).body(response);
    }
}
