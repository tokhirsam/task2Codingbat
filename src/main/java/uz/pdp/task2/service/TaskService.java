package uz.pdp.task2.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.pdp.task2.entity.Category;
import uz.pdp.task2.entity.Task;
import uz.pdp.task2.payload.ApiResponse;
import uz.pdp.task2.payload.TaskDto;
import uz.pdp.task2.repository.CategoryRepository;
import uz.pdp.task2.repository.TaskRepository;

import java.util.List;
import java.util.Optional;

@Service
public class TaskService {

    @Autowired
    TaskRepository taskRepository;

    @Autowired
    CategoryRepository categoryRepository;


    public List<Task> getAll(){
        return taskRepository.findAll();
    }


    public ApiResponse getById(Integer id){
        Optional<Task> byId = taskRepository.findById(id);
        return byId.map(task -> new ApiResponse("Topildi", true, task)).orElseGet(() -> new ApiResponse("Bunday Idli Task topilmadi", false, null));
    }


    public ApiResponse add(TaskDto taskDto){
        Optional<Category> categoryOptional = categoryRepository.findById(taskDto.getCategoryId());
        if (!categoryOptional.isPresent())
            return new ApiResponse("Kategoriya topilmadi", false, null);

        if (taskRepository.existsByNameAndCategory(taskDto.getName(), categoryOptional.get())){
            return new ApiResponse("Bunday nomli masala mavjud",false,null);
        }

        Task task = new Task();
        task.setCategory(categoryOptional.get());
        task.setExamples(taskDto.getExamples());
        task.setHasStar(taskDto.isHasStar());
        task.setName(taskDto.getName());
        task.setSolution(taskDto.getSolution());
        task.setText(taskDto.getText());
        Task savedTask = taskRepository.save(task);
        return new ApiResponse("Task saqlandi!",true,savedTask);
    }


    public ApiResponse edit(TaskDto taskDto, Integer id){
        Optional<Category> categoryOptional = categoryRepository.findById(taskDto.getCategoryId());
        if (!categoryOptional.isPresent())
            return new ApiResponse("Kategoriya topilmadi", false, null);

        Optional<Task> taskOptional = taskRepository.findById(id);
        if (!taskOptional.isPresent())
            return new ApiResponse("Task topilmadi!", false, null);

        Task task = taskOptional.get();
        task.setExamples(taskDto.getExamples());
        task.setCategory(categoryOptional.get());
        task.setName(taskDto.getName());
        task.setHasStar(taskDto.isHasStar());
        task.setSolution(taskDto.getSolution());
        task.setText(taskDto.getText());
        Task savedTask = taskRepository.save(task);
        return new ApiResponse("Task o'zgartirildi!",true,savedTask);
    }

    public ApiResponse delete(Integer id){
        try {
            taskRepository.deleteById(id);
            return new ApiResponse("Task deleted", true);
        }catch (Exception e){
            return new ApiResponse("Task mavjud emas", false);
        }
    }
}

