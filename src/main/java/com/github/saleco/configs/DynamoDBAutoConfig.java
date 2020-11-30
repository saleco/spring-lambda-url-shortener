package com.github.saleco.configs;

import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DynamoDBAutoConfig {

  @Bean(name = "amazonDynamoDB")
  public AmazonDynamoDB amazonDynamoDB(AWSCredentialsProvider credentialsProvider) {
    return AmazonDynamoDBClientBuilder.standard()
        .withCredentials(credentialsProvider)
        .withRegion(Regions.US_EAST_2)
        .build();
  }
}
