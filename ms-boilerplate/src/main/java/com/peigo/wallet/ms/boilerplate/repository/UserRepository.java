package com.peigo.wallet.ms.boilerplate.repository;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import com.peigo.wallet.ms.boilerplate.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UserRepository {

    @Autowired
    private DynamoDBMapper dynamoDBMapper;

    public User saveUser(User user){
        dynamoDBMapper.save(user);
        return user;
    }

    public List<User> getUsers(){
        return dynamoDBMapper.scan(User.class, new DynamoDBScanExpression());
    }
}
