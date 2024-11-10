package com.domain.store.services.user;

import com.domain.store.exception.FoundException;
import com.domain.store.model.User;
import com.domain.store.repository.UserRepository;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Data
@Service
@RequiredArgsConstructor
public class UserService implements IUserService{

    private final UserRepository userRepository;

    @Override
    public User addUser(User newUser) {
        User user = new User();
        user.setFirstName(newUser.getFirstName());
        user.setLastNAme(newUser.getLastNAme());
        user.setEmail(newUser.getEmail());
        user.setPassword(newUser.getPassword());
        return userRepository.save(user);
    }

    @Override
    public User getUser(Long id) {
        return userRepository.findById(id).orElseThrow(()-> new FoundException("User not found with Id: "+id));
    }

    @Override
    public User updateUser(Long id, User user) {
        User oldUser = getUser(id);
        oldUser.setFirstName(user.getFirstName());
        oldUser.setLastNAme(user.getLastNAme());
        oldUser.setEmail(user.getEmail());
        oldUser.setPassword(user.getPassword());
        return userRepository.save(oldUser);
    }

    @Override
    public void deleteUser(Long id) {
        userRepository.findById(id).ifPresentOrElse(userRepository::delete, ()-> { throw new FoundException("User not founf with Id: "+id);});
    }
}

