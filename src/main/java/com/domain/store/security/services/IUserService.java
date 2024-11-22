package com.domain.store.security.services;

import com.domain.store.model.User;
import com.domain.store.request.SignUpRequest;

public interface IUserService {
    public User getUser(Long id);
    public User updateUser(Long id, SignUpRequest user);
    public void deleteUser(Long id);
}
