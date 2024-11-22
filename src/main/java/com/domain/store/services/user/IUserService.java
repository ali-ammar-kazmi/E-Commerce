package com.domain.store.services.user;

import com.domain.store.model.User;
import com.domain.store.request.SignUpRequest;

public interface IUserService {
    User addUser(SignUpRequest signUpRequest);
    User getUser(Long id);
    User updateUser(Long id, SignUpRequest user);
    void deleteUser(Long id);
}
