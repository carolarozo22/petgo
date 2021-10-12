package com.peigo.wallet.ms.boilerplate.service;

import com.peigo.wallet.dto.users.UserDTO;
import com.peigo.wallet.ms.boilerplate.constants.LogsConstant;
import com.peigo.wallet.ms.boilerplate.model.User;
import com.peigo.wallet.ms.boilerplate.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class UserServiceImpl implements  IUserService{

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public UserDTO saveUser(UserDTO userDTO) {
        log.info(LogsConstant.MESSAGE_INPUT_DATA, 1, userDTO);
        User user = modelMapper.map(userDTO, User.class);
        userRepository.saveUser(user);
        return userDTO;
    }

    @Override
    public List<UserDTO> getUsers() {
        List<User> userList = userRepository.getUsers();
        List<UserDTO> userDTOList = userList
                .stream()
                .map(user -> modelMapper.map(user, UserDTO.class))
                .peek(user -> user.setName(user.getName().toUpperCase()))
                .collect(Collectors.toList());
        return userDTOList;
    }
}
