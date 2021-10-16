package com.peigo.wallet.ms.boilerplate.service;

import com.peigo.wallet.ms.boilerplate.model.dto.UserDTO;
import com.peigo.wallet.ms.boilerplate.model.entity.UserEntity;
import com.peigo.wallet.ms.boilerplate.model.mapper.UserMapper;
import com.peigo.wallet.ms.boilerplate.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@AllArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public UserDTO saveUser(UserDTO userDTO) {
        log.info("New user to register: {}", userDTO);
        UserEntity userEntity = UserMapper.INSTANCE.toEntity(userDTO);
        userRepository.save(userEntity);
        userDTO = UserMapper.INSTANCE.toDto(userEntity);
        log.info("save user data: {}", userDTO);
        return userDTO;
    }

    public List<UserDTO> getUsers() {
        log.info("consult information of all users");
        List<UserEntity> userList = (List<UserEntity>) userRepository.findAll();
        List<UserDTO> userDTOList = userList
                .stream()
                .map(user -> UserMapper.INSTANCE.toDto(user))
                .peek(user -> user.setName(user.getName().toUpperCase()))
                .collect(Collectors.toList());
        log.info("List users: {}", userDTOList);
        return userDTOList;
    }
}
