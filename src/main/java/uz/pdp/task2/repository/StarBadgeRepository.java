package uz.pdp.task2.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.task2.entity.ProgLang;
import uz.pdp.task2.entity.StarBadge;

public interface StarBadgeRepository extends JpaRepository<StarBadge, Integer> {
    boolean existsByScoreAndProgLang(Integer score, ProgLang progLang);
    boolean existsByScoreAndProgLangAndIdNot(Integer score, ProgLang progLang, Integer id);
}
