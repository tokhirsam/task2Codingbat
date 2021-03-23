package uz.pdp.task2.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.pdp.task2.entity.Answer;
import uz.pdp.task2.entity.Task;
import uz.pdp.task2.entity.User;
import uz.pdp.task2.payload.AnswerDto;
import uz.pdp.task2.payload.ApiResponse;
import uz.pdp.task2.repository.AnswerRepository;

import java.util.List;
import java.util.Optional;

@Service
public class AnswerService {

    @Autowired
    AnswerRepository answerRepository;

    @Autowired
    TaskService taskService;

    @Autowired
    UserService userService;

    public ApiResponse add(AnswerDto answerDto){

        ApiResponse byId = userService.getById(answerDto.getUser_id());
        if (!byId.isSuccess())
            return new ApiResponse("User topilmadi", false, null);

        ApiResponse byId1 = taskService.getById(answerDto.getTask_id());
        if (!byId1.isSuccess())
            return new ApiResponse("Task topilmadi", false, null);

        if (answerRepository.existsByTaskAndUser((Task) byId1.getObject(),(User) byId.getObject())){
            return new ApiResponse("Bunday answer mavjud!", false, null);
        }

        Answer answer = new Answer();
        answer.setBody(answerDto.getBody());
        answer.setTask((Task) byId1.getObject());
        answer.setUser((User) byId.getObject());
        answer.setTrue(answerDto.isTrue());
        Answer savedAnswer = answerRepository.save(answer);
        return new ApiResponse("Answer qo'shildi", true, savedAnswer);
    }

    public ApiResponse edit(AnswerDto answerDto, Integer id){
        Optional<Answer> byId2 = answerRepository.findById(id);
        if (!byId2.isPresent())
            return new ApiResponse("Answer topilmadi", false, null);

        ApiResponse byId1 = taskService.getById(answerDto.getTask_id());
        if (!byId1.isSuccess())
            return new ApiResponse("Task topilmadi", false, null);

        ApiResponse byId = userService.getById(answerDto.getUser_id());
        if (!byId.isSuccess())
            return new ApiResponse("User topilmadi", false, null);

        Answer answer = byId2.get();
        answer.setTask((Task) byId1.getObject());
        answer.setBody(answerDto.getBody());
        answer.setTrue(answerDto.isTrue());
        answer.setUser((User) byId.getObject());
        Answer savedAnswer = answerRepository.save(answer);
        return new ApiResponse("Answer o'zgartirildi", true, savedAnswer);
    }

    public List<Answer> getAll(){
    return answerRepository.findAll();
    }

    public Answer getOne(Integer id){
        Optional<Answer> optionalAddress = answerRepository.findById(id);
        return optionalAddress.orElse(null);
    }
    public ApiResponse delete(Integer id){
        try {
            answerRepository.deleteById(id);
            return new ApiResponse("Answer deleted", true);
        }catch (Exception e){
            return new ApiResponse("Answer mavjud emas", false);
        }
    }
}
