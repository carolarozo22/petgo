package com.peigo.wallet.ms.boilerplate.repository;

import com.peigo.wallet.ms.boilerplate.model.entity.UserEntity;
import org.socialsignin.spring.data.dynamodb.repository.EnableScan;
import org.springframework.data.repository.CrudRepository;

@EnableScan
public interface UserRepository extends CrudRepository<UserEntity, String> { }
