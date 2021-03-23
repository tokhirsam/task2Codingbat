package uz.pdp.task2.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.task2.entity.Category;
import uz.pdp.task2.entity.ProgLang;

public interface CategoryRepository extends JpaRepository<Category, Integer> {
    boolean existsByNameAndProgLang(String name, ProgLang progLang);
    boolean existsByNameAndProgLangAndIdNot(String name, ProgLang progLang, Integer id);
}
