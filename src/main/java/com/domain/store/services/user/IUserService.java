package com.domain.store.services.user;

import com.domain.store.model.User;
import com.domain.store.request.UserRequest;

public interface IUserService {
    public User addUser(UserRequest user);
    public User getUser(Long id);
    public User updateUser(Long id, UserRequest user);
    public void deleteUser(Long id);
}
