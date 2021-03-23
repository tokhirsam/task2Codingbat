package uz.pdp.task2.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import uz.pdp.task2.entity.ProgLang;
import uz.pdp.task2.payload.ApiResponse;
import uz.pdp.task2.payload.ProgLangDto;
import uz.pdp.task2.repository.ProgLangRepository;

import java.util.Optional;

@Service
public class ProgLangService {

    @Autowired
    ProgLangRepository progLangRepository;


    public HttpEntity<ApiResponse> add(ProgLangDto progLangDto){
        boolean existsByName = progLangRepository.existsByName(progLangDto.getName());
        if (existsByName)
            return ResponseEntity.status(409).body(new ApiResponse("Bunday til mavjud!", false, null));

        ProgLang progLang = new ProgLang();
        progLang.setName(progLangDto.getName());
        progLang.setDescription(progLangDto.getDescription());
        ProgLang savedProglang = progLangRepository.save(progLang);
        return ResponseEntity.status(HttpStatus.CREATED).body(new ApiResponse("Til muvaffaqqiyatli qo'shildi!",true,savedProglang));
    }


    public HttpEntity<?> edit(ProgLangDto progLangDto, Integer id){
        boolean existsByNameAndIdNot = progLangRepository.existsByNameAndIdNot(progLangDto.getName(), id);
        if (existsByNameAndIdNot)
            return ResponseEntity.status(409).body(new ApiResponse("Bunday til mavjud!", false, null));

        Optional<ProgLang> byId = progLangRepository.findById(id);
        if (byId.isPresent()) {
            ProgLang progLang = byId.get();
            progLang.setName(progLangDto.getName());
            progLang.setDescription(progLangDto.getDescription());
            ProgLang savedProglang = progLangRepository.save(progLang);
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(new ApiResponse("Til o'zgartirildi", true, savedProglang));
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse("Bunday idli til topilmadi", false, null));
    }


    public ApiResponse delete(Integer id){
        try {
            progLangRepository.deleteById(id);
            return new ApiResponse("Dasturlash tili deleted", true);
        }catch (Exception e){
            return new ApiResponse("Dasturlash tili mavjud emas", false);
        }
    }


    public HttpEntity<?> getAll(){
        return ResponseEntity.ok(progLangRepository.findAll());
    }


    public ApiResponse getById(Integer id){
        Optional<ProgLang> byId = progLangRepository.findById(id);
        return byId.map(progLang -> new ApiResponse("Dasturlash tili topildi!", true, progLang)).orElseGet(() -> new ApiResponse("Dasturlash tili topilmadi!", false, null));
    }
}
