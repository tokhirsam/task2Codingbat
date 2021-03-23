package uz.pdp.task2.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.task2.entity.Category;
import uz.pdp.task2.entity.Task;

public interface TaskRepository extends JpaRepository<Task, Integer> {
    boolean existsByNameAndCategory(String name, Category category);
}
