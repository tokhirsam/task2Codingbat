package uz.pdp.task2.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.pdp.task2.entity.Category;
import uz.pdp.task2.entity.ProgLang;
import uz.pdp.task2.payload.ApiResponse;
import uz.pdp.task2.payload.CategoryDto;
import uz.pdp.task2.repository.CategoryRepository;
import uz.pdp.task2.repository.ProgLangRepository;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    ProgLangRepository progLangRepository;


    public ApiResponse add(CategoryDto categoryDto){
        Optional<ProgLang> progLangRepositoryById = progLangRepository.findById(categoryDto.getProglangId());

        if (!progLangRepositoryById.isPresent())
            return new ApiResponse("Bunday til mavjud emas", false,null);

        boolean existsByName = categoryRepository.existsByNameAndProgLang(categoryDto.getName(), progLangRepositoryById.get());
        if (existsByName) {
            return new ApiResponse("Bunday categoriya nomi mavjud!",false,null);
        }



        Category category = new Category();
        category.setName(categoryDto.getName());
        category.setDescription(categoryDto.getDescription());
        category.setProgLang(progLangRepositoryById.get());
        Category savedCategory = categoryRepository.save(category);
        return new ApiResponse("Categoriya saqlandi!", true, savedCategory);
    }



    public ApiResponse edit(CategoryDto categoryDto, Integer id){
        Optional<Category> optionalCategory = categoryRepository.findById(id);
        if (!optionalCategory.isPresent())
            return new ApiResponse("Bunday categoriya mavjud emas!", false, null);

        Optional<ProgLang> progLangRepositoryById = progLangRepository.findById(categoryDto.getProglangId());

        if (!progLangRepositoryById.isPresent())
            return new ApiResponse("Bunday til mavjud emas", false,null);

        boolean existsByNameAndIdNot = categoryRepository.existsByNameAndProgLangAndIdNot(categoryDto.getName(), progLangRepositoryById.get(), id);
        if (existsByNameAndIdNot)
            return new ApiResponse("Bunday categoriya nomi mavjud!",false,null);

        Category category = optionalCategory.get();
        category.setName(categoryDto.getName());
        category.setDescription(categoryDto.getDescription());
        category.setProgLang(progLangRepositoryById.get());
        Category editedCategory = categoryRepository.save(category);
        return new ApiResponse("Categoriya tahrirlandi!", true, editedCategory);
    }

    public List<Category> getAll(){
        return categoryRepository.findAll();
    }


    public ApiResponse getById(Integer id){
        Optional<Category> categoryRepositoryById = categoryRepository.findById(id);
        return categoryRepositoryById.map(category -> new ApiResponse("Categoriya topildi", true, category)).orElseGet(() -> new ApiResponse("Categoriya topilmadi", false, null));
    }

    public ApiResponse delete(Integer id){
        try {
            categoryRepository.deleteById(id);
            return new ApiResponse("Category deleted", true);
        }catch (Exception e){
            return new ApiResponse("Category mavjud emas", false);
        }
    }
}
