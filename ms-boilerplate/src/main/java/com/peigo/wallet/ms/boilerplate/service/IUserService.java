package com.peigo.wallet.ms.boilerplate.service;


import com.peigo.wallet.ms.boilerplate.dto.request.User;

import java.util.List;

public interface IUserService {

    User saveUser(User user);

    List<User> getUsers();
}
