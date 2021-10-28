package com.peigo.wallet.ms.boilerplate.service;

import com.peigo.wallet.ms.boilerplate.model.dto.UserDTO;
import com.peigo.wallet.ms.boilerplate.model.entity.UserEntity;
import com.peigo.wallet.ms.boilerplate.model.mapper.UserMapper;
import com.peigo.wallet.ms.boilerplate.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@AllArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    @Qualifier("snake_template")
    private final RestTemplate restTemplateSnake;

    private final UserMapper userMapper;

    @Cacheable(value = "new_user")
    public UserDTO saveUser(UserDTO userDTO) {
        log.info("New user to register: {}", userDTO);
        UserEntity userEntity = userMapper.toEntity(userDTO);
        userRepository.save(userEntity);
        userDTO = userMapper.toDto(userEntity);
        log.info("save user data: {}", userDTO);
        return userDTO;
    }

    @Cacheable(value = "all_users")
    public List<UserDTO> getUsers() {
        log.info("consult information of all users");
        List<UserEntity> userList = (List<UserEntity>) userRepository.findAll();
        List<UserDTO> userDTOList = userList
                .stream()
                .map(user -> userMapper.toDto(user))
                .peek(user -> user.setName(user.getName().toUpperCase()))
                .collect(Collectors.toList());
        log.info("List users: {}", userDTOList);
        return userDTOList;
    }

    @CacheEvict(value = "all_users", allEntries = true)
    public void deleteUser(){
        log.info("Delete user dto:::");
    }

    public Object getHeaders(){
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.getForObject("https://httpbin.org/get", Object.class);
    }

    public Object getObjectHeadersSnake(){
        return restTemplateSnake.getForObject("https://httpbin.org/get", Object.class);
    }
}
