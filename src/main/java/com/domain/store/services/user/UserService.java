package com.domain.store.services.user;

import com.domain.store.exception.FoundException;
import com.domain.store.model.ERole;
import com.domain.store.model.Role;
import com.domain.store.model.User;
import com.domain.store.repository.RoleRepository;
import com.domain.store.repository.UserRepository;
import com.domain.store.request.UserRequest;
import com.domain.store.services.cart.ICartService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Data
@Service
@RequiredArgsConstructor
public class UserService implements IUserService{

    private final UserRepository userRepository;
    private final RoleRepository authorityRepository;
    private final ICartService cartService;
    private final PasswordEncoder passwordEncoder;

    @Override
    public User addUser(UserRequest newUser) {
        User user = new User();
        user.setUsername(newUser.getUsername());
        user.setEmail(newUser.getEmail());
        user.setPassword(passwordEncoder.encode(newUser.getPassword()));
        User savedUser = userRepository.save(user);
        cartService.addOrderCart(savedUser);
        Role role = new Role();
        role.setName(ERole.ROLE_USER);
        authorityRepository.save(role);
        return userRepository.save(savedUser);
    }

    @Override
    public User getUser(Long id) {
        return userRepository.findById(id).orElseThrow(()-> new FoundException("User not found with Id: "+id));
    }

    @Override
    public User updateUser(Long id, UserRequest user) {
        User oldUser = getUser(id);
        oldUser.setUsername(user.getUsername());
        oldUser.setEmail(user.getEmail());
        oldUser.setPassword(passwordEncoder.encode(user.getPassword()));
        Role role = authorityRepository.findById(id).orElseThrow(()-> new FoundException("Role not found with Id: "+id));
        role.setName(ERole.ROLE_USER);
        authorityRepository.save(role);
        return userRepository.save(oldUser);
    }

    @Override
    public void deleteUser(Long id) {
        authorityRepository.findById(id).ifPresentOrElse(authorityRepository::delete, ()-> { throw new FoundException("Role not found with Id: "+id);});
        userRepository.findById(id).ifPresentOrElse(userRepository::delete, ()-> { throw new FoundException("User not found with Id: "+id);});
    }
}

