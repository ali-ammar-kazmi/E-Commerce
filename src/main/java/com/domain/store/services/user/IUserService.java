package com.domain.store.services.user;

import com.domain.store.model.User;

public interface IUserService {
    public User addUser(User user);
    public User getUser(Long id);
    public User updateUser(Long id, User user);
    public void deleteUser(Long id);
}
