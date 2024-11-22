package com.domain.store.controller;

import com.domain.store.exception.StoreException;
import com.domain.store.model.ERole;
import com.domain.store.model.Role;
import com.domain.store.model.User;
import com.domain.store.repository.RoleRepository;
import com.domain.store.repository.UserRepository;
import com.domain.store.request.LoginRequest;
import com.domain.store.request.SignUpRequest;
import com.domain.store.response.StoreResponse;
import com.domain.store.response.JwtResponse;
import com.domain.store.services.jwt.JwtUtils;
import com.domain.store.services.user.UserDetailsImpl;
import com.domain.store.services.user.IUserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("${api.prefix}/user")
@RequiredArgsConstructor
public class AuthController {

    private final IUserService userService;
    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder encoder;
    private final JwtUtils jwtUtils;

    @PostMapping("/sign-in")
    public ResponseEntity<StoreResponse> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());

        return ResponseEntity.ok(new StoreResponse("SignIn Successfully!",new JwtResponse(jwt,
                userDetails.getEmail(),
                roles)));
    }

    @PostMapping("/sign-up")
    public ResponseEntity<StoreResponse> registerUser(@Valid @RequestBody SignUpRequest signUpRequest) {
        if (userRepository.findByUsername(signUpRequest.getUsername()).isPresent()) {
            return ResponseEntity
                    .badRequest()
                    .body(new StoreResponse("Error: Username is already taken!", null));
        }

        // Create new user's account
        User user = new User();
        user.setUsername(signUpRequest.getUsername());
        user.setEmail(signUpRequest.getEmail());
        user.setPassword(encoder.encode(signUpRequest.getPassword()));
        Set<String> strRoles = signUpRequest.getRoles();
        Set<Role> roles = new HashSet<>();

        if (strRoles == null) {
            Role userRole = roleRepository.findByName(ERole.USER)
                    .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
            roles.add(userRole);
        } else {
            strRoles.forEach(role -> {
                switch (role) {
                    case "admin":
                        Role adminRole = roleRepository.findByName(ERole.ADMIN)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(adminRole);

                        break;
                    case "mod":
                        Role modRole = roleRepository.findByName(ERole.MODERATOR)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(modRole);

                        break;
                    default:
                        Role userRole = roleRepository.findByName(ERole.USER)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(userRole);
                }
            });
        }
        user.setRoles(roles);
        userRepository.save(user);
        return ResponseEntity.ok(new StoreResponse("User registered successfully!", null));
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping("/update/{userId}")
    public ResponseEntity<StoreResponse> updateUser(@PathVariable Long userId, @RequestBody SignUpRequest userRequest){
        try{
            User updatedUser = userService.updateUser(userId, userRequest);
            return ResponseEntity.ok(new StoreResponse("User Updated!", updatedUser));
        } catch ( StoreException e){
            return ResponseEntity.status(NOT_FOUND).body(new StoreResponse("User Not Updated!", NOT_FOUND));
        } catch ( Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new StoreResponse("User Not Updated!", INTERNAL_SERVER_ERROR));
        }
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/delete/{userId}")
    public ResponseEntity<StoreResponse> deleteUser(@PathVariable Long userId){
        try{
            userService.deleteUser(userId);
            return ResponseEntity.ok(new StoreResponse("User Deleted!", null));
        } catch ( StoreException e){
            return ResponseEntity.status(NOT_FOUND).body(new StoreResponse("User Not Deleted!", NOT_FOUND));
        } catch ( Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new StoreResponse("User Not Deleted!", INTERNAL_SERVER_ERROR));
        }
    }
}
