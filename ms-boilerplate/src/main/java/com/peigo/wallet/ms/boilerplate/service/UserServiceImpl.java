package com.peigo.wallet.ms.boilerplate.service;

import com.peigo.wallet.ms.boilerplate.dto.request.User;
import com.peigo.wallet.ms.boilerplate.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements  IUserService{

    @Autowired
    private UserRepository userRepository;

    @Override
    public User saveUser(User user) {
        return null;
    }

    @Override
    public List<User> getUsers() {
        return null;
    }
}
