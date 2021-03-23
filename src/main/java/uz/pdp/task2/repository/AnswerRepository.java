package uz.pdp.task2.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.task2.entity.Answer;
import uz.pdp.task2.entity.Task;
import uz.pdp.task2.entity.User;

public interface AnswerRepository extends JpaRepository<Answer, Integer> {
    boolean existsByTaskAndUser(Task task, User user);

}
