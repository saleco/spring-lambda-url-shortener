package com.github.saleco.dao;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.dynamodbv2.document.UpdateItemOutcome;
import com.amazonaws.services.dynamodbv2.document.spec.UpdateItemSpec;
import com.amazonaws.services.dynamodbv2.document.utils.ValueMap;
import com.amazonaws.services.dynamodbv2.model.*;
import com.amazonaws.services.kms.model.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

@Repository
public class DynamoDBUrlDAO implements UrlDAO {

  private static final String ID_TABLE_NAME = "maxid";
  private static final String URL_TABLE_NAME = "URL";

  @Autowired
  private AmazonDynamoDB amazonDynamoDB;

  @Override
  public String generateShortCode() {
    DynamoDB dynamoDB = new DynamoDB( amazonDynamoDB);
    Table table = dynamoDB.getTable(ID_TABLE_NAME);

    UpdateItemSpec updateItemSpec = new UpdateItemSpec().withPrimaryKey("id", 1)
        .withUpdateExpression("set shortCode = shortCode + :val")
        .withValueMap(new ValueMap().withNumber(":val", 1)).withReturnValues(ReturnValue.UPDATED_OLD);

    UpdateItemOutcome outcome = table.updateItem(updateItemSpec);

    return Long.toString(Long.parseLong(outcome.getItem().get("shortCode").toString()), 36);
  }

  @Override
  public void storeUrl(String shortcode, String url) {
    Map<String, AttributeValue> item  = new HashMap<>();
    item.put("shortCode", new AttributeValue(shortcode));
    item.put("url", new AttributeValue(url));
    PutItemRequest request = new PutItemRequest(URL_TABLE_NAME, item);
    amazonDynamoDB.putItem(request);
  }

  @Override
  public String getUrl(String shortcode) {

    Map<String, AttributeValue> keyToGet = new HashMap<>();
    keyToGet.put("shortCode", new AttributeValue(shortcode));

    GetItemRequest request = new GetItemRequest()
        .withKey(keyToGet)
        .withTableName(URL_TABLE_NAME);

    GetItemResult result = amazonDynamoDB.getItem(request);

    if(result.getItem() == null) {
      throw new NotFoundException("Couldn't find the shortcode mapping");
    }

    return result.getItem().get("url").getS();

  }
}
