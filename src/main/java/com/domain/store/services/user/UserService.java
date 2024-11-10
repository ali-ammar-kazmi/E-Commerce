package com.domain.store.services.user;

import com.domain.store.exception.FoundException;
import com.domain.store.model.Cart;
import com.domain.store.model.User;
import com.domain.store.repository.UserRepository;
import com.domain.store.request.UserRequest;
import com.domain.store.services.cart.ICartService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Data
@Service
@RequiredArgsConstructor
public class UserService implements IUserService{

    private final UserRepository userRepository;
    private final ICartService cartService;

    @Override
    public User addUser(UserRequest newUser) {
        User user = new User();
        user.setFirstName(newUser.getFirstName());
        user.setLastName(newUser.getLastName());
        user.setEmail(newUser.getEmail());
        user.setPassword(newUser.getPassword());
        User savedUser = userRepository.save(user);
        Cart cart = cartService.addOrderCart(savedUser);
        savedUser.setCart(cart);
        return userRepository.save(savedUser);
    }

    @Override
    public User getUser(Long id) {
        return userRepository.findById(id).orElseThrow(()-> new FoundException("User not found with Id: "+id));
    }

    @Override
    public User updateUser(Long id, UserRequest user) {
        User oldUser = getUser(id);
        oldUser.setFirstName(user.getFirstName());
        oldUser.setLastName(user.getLastName());
        oldUser.setEmail(user.getEmail());
        oldUser.setPassword(user.getPassword());
        return userRepository.save(oldUser);
    }

    @Override
    public void deleteUser(Long id) {
        userRepository.findById(id).ifPresentOrElse(userRepository::delete, ()-> { throw new FoundException("User not found with Id: "+id);});
    }
}

