package com.peigo.wallet.ms.boilerplate.config;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.auth.InstanceProfileCredentialsProvider;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapperConfig;
import com.peigo.wallet.ms.boilerplate.constants.DynamoDBConstant;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DynamoDBConfig {

    @Value("${aws.dynamodb.secret-key:}")
    private String secretKey;

    @Value("${aws.dynamodb.access-key:}")
    private String accessKey;

    @Value("${aws.dynamodb.end-point:}")
    private String endpoint;

    @Value("${Spring.profiles.active:}")
    private String profile;

    @Bean
    public DynamoDBMapper mapper(){
        return new DynamoDBMapper(amazonDynamoDB(), dynamoDBMapperConfig());
    }

    private DynamoDBMapperConfig dynamoDBMapperConfig(){
        return DynamoDBMapperConfig
                .builder()
                .withTableNameOverride(DynamoDBMapperConfig.TableNameOverride.withTableNameReplacement(DynamoDBConstant.USER_TABLE_NAME))
                .build();
    }

    private AmazonDynamoDB amazonDynamoDB(){
        if (!profile.isEmpty()){
            return AmazonDynamoDBClientBuilder
                    .standard()
                    .withEndpointConfiguration(new AwsClientBuilder.EndpointConfiguration(endpoint, Regions.AP_EAST_1.toString()))
                    .withCredentials(new AWSStaticCredentialsProvider(new BasicAWSCredentials(accessKey, secretKey)))
                    .build();
        }else{
            return AmazonDynamoDBClientBuilder
                    .standard()
                    .withRegion(Regions.AP_EAST_1)
                    .withCredentials(new InstanceProfileCredentialsProvider(false))
                    .build();
        }
    }
}
