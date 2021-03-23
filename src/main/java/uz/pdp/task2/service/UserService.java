package uz.pdp.task2.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.pdp.task2.entity.StarBadge;
import uz.pdp.task2.entity.User;
import uz.pdp.task2.payload.ApiResponse;
import uz.pdp.task2.payload.UserDto;
import uz.pdp.task2.repository.StarBadgeRepository;
import uz.pdp.task2.repository.UserRepository;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    StarBadgeRepository starBadgeRepository;

    public ApiResponse add(UserDto userDto){
        User user = new User();
        if (userDto.getStarBadge() != null){
            Optional<StarBadge> starBadgeRepositoryById = starBadgeRepository.findById(userDto.getStarBadge());
            if (!starBadgeRepositoryById.isPresent())
                return new ApiResponse("StarBadge topilmadi!",false, null);
            user.setStarBadge(starBadgeRepositoryById.get());
        }


        if (userRepository.existsByEmail(userDto.getEmail()))
            return new ApiResponse("Email allaqachon ro'yxatdan o'tgan!",false,null);

        user.setEmail(userDto.getEmail());
        user.setPassword(userDto.getPassword());
        User savedUser = userRepository.save(user);
        return new ApiResponse("User qo'shildi!",true,savedUser);
    }

    public ApiResponse edit(UserDto userDto, Integer id){
        Optional<User> userOptional = userRepository.findById(id);
        if (!userOptional.isPresent())
            return new ApiResponse("User topilmadi", false, null);

        if (userRepository.existsByEmailAndIdNot(userDto.getEmail(),id))
            return new ApiResponse("Email allaqachon ro'yxatdan o'tgan!",false,null);
        User user = userOptional.get();
        if (userDto.getStarBadge() != null){
            Optional<StarBadge> starBadgeRepositoryById = starBadgeRepository.findById(userDto.getStarBadge());
            if (!starBadgeRepositoryById.isPresent())
                return new ApiResponse("StarBadge topilmadi!",false, null);
            user.setStarBadge(starBadgeRepositoryById.get());
        }
        user.setEmail(userDto.getEmail());
        user.setPassword(userDto.getPassword());
        User savedUser = userRepository.save(user);
        return new ApiResponse("User qo'shildi!",true,savedUser);
    }

    public List<User> getAll(){
        return userRepository.findAll();
    }

    public ApiResponse getById(Integer id){
        Optional<User> byId = userRepository.findById(id);
        return byId.map(user -> new ApiResponse("Ma'lumot topildi", true, user)).orElseGet(() -> new ApiResponse("Ma'lumot topilmadi", false, null));
    }
    public ApiResponse delete(Integer id){
        try {
            userRepository.deleteById(id);
            return new ApiResponse("User deleted", true);
        }catch (Exception e){
            return new ApiResponse("User mavjud emas", false);
        }
    }
}
