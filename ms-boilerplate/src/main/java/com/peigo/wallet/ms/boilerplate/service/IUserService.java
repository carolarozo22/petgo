package com.peigo.wallet.ms.boilerplate.service;


import com.peigo.wallet.ms.boilerplate.dto.UserDTO;

import java.util.List;

public interface IUserService {

    UserDTO saveUser(UserDTO userDTO);

    List<UserDTO> getUsers();
}
