package com.cg.service;

import com.cg.model.User;

import java.util.List;

public interface IUserService {
    void init();
    List<User> getAll();
    void setCurrentId();
    List<User> searchProduct(String kw);
    User findBy(long id);
    void updateUser(long id, User userEdit);
    boolean removeUser(long id);
    void addUser(User user);
    List<User> searchUser(String kw);
    User checkUserNamePasswordCorrect(String username, String password);
}
