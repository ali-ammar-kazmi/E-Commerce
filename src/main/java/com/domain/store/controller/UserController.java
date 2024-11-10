package com.domain.store.controller;

import com.domain.store.exception.FoundException;
import com.domain.store.model.User;
import com.domain.store.request.UserRequest;
import com.domain.store.response.ApiResponse;
import com.domain.store.services.user.IUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.*;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

@RequiredArgsConstructor
@RestController
@RequestMapping("${api.prefix}/user")
public class UserController {

    private final IUserService userService;

    @PostMapping("/save")
    public ResponseEntity<ApiResponse> addUser(@RequestBody UserRequest user){
        try{
            User newUser = userService.addUser(user);
            return ResponseEntity.ok(new ApiResponse("User Added!", newUser));
        } catch ( FoundException e){
            return ResponseEntity.status(FOUND).body(new ApiResponse(e.getMessage(), FOUND));
        } catch ( Exception e){
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(), INTERNAL_SERVER_ERROR));
        }
    }

    @GetMapping("/retrieveById/{userId}")
    public ResponseEntity<ApiResponse> getUserById(@PathVariable Long userId){
        try{
            User user = userService.getUser(userId);
            return ResponseEntity.ok(new ApiResponse("User Found!", user));
        } catch ( FoundException e){
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), NOT_FOUND));
        } catch ( Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(), INTERNAL_SERVER_ERROR));
        }
    }

    @PutMapping("/update/{userId}")
    public ResponseEntity<ApiResponse> updateCategory(@RequestBody UserRequest user, @PathVariable Long userId){
        try{
            User updatedUser = userService.updateUser(userId, user);
            return ResponseEntity.ok(new ApiResponse("User Updated!", updatedUser));
        } catch ( FoundException e){
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), NOT_FOUND));
        } catch ( Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(), INTERNAL_SERVER_ERROR));
        }
    }

    @DeleteMapping("/delete/{userId}")
    public ResponseEntity<ApiResponse> deleteUser(@PathVariable Long userId){
        try{
            userService.deleteUser(userId);
            return ResponseEntity.ok(new ApiResponse("User Deleted!", null));
        } catch ( FoundException e){
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), NOT_FOUND));
        } catch ( Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(), INTERNAL_SERVER_ERROR));
        }
    }
}
