package com.domain.store.services.user;

import com.domain.store.exception.StoreException;
import com.domain.store.model.ERole;
import com.domain.store.model.Role;
import com.domain.store.model.User;
import com.domain.store.repository.RoleRepository;
import com.domain.store.repository.UserRepository;
import com.domain.store.request.SignUpRequest;
import com.domain.store.services.cart.ICartService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Data
@Service
@RequiredArgsConstructor
public class UserService implements IUserService{

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final ICartService cartService;
    private final PasswordEncoder passwordEncoder;

    @Override
    public User getUser(Long id) {
        return userRepository.findById(id).orElseThrow(()-> new StoreException("User not found with Id: "+id));
    }

    @Override
    public User updateUser(Long id, SignUpRequest user) {
        User oldUser = getUser(id);
        oldUser.setUsername(user.getUsername());
        oldUser.setEmail(user.getEmail());

        Set<String> strRoles = user.getRoles();
        Set<Role> roles = new HashSet<>();

        if (strRoles == null) {
            Role userRole = roleRepository.findByName(ERole.USER)
                    .orElseThrow(() -> new StoreException("Error: Role is not found."));
            roles.add(userRole);
        } else {
            strRoles.forEach(role -> {
                switch (role) {
                    case "admin":
                        Role adminRole = roleRepository.findByName(ERole.ADMIN)
                                .orElseThrow(() -> new StoreException("Error: Role is not found."));
                        roles.add(adminRole);

                        break;
                    case "mod":
                        Role modRole = roleRepository.findByName(ERole.MODERATOR)
                                .orElseThrow(() -> new StoreException("Error: Role is not found."));
                        roles.add(modRole);

                        break;
                    default:
                        Role userRole = roleRepository.findByName(ERole.USER)
                                .orElseThrow(() -> new StoreException("Error: Role is not found."));
                        roles.add(userRole);
                }
            });
        }
        oldUser.setRoles(roles);
        return userRepository.save(oldUser);
    }

    @Override
    public void deleteUser(Long id) {
        roleRepository.findById(id).ifPresentOrElse(roleRepository::delete, ()-> { throw new StoreException("Role not found with Id: "+id);});
        userRepository.findById(id).ifPresentOrElse(userRepository::delete, ()-> { throw new StoreException("User not found with Id: "+id);});
    }
}

