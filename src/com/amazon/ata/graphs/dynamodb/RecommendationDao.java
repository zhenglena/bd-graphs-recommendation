package com.amazon.ata.graphs.dynamodb;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBQueryExpression;
import com.amazonaws.services.dynamodbv2.datamodeling.PaginatedQueryList;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.ComparisonOperator;
import com.amazonaws.services.dynamodbv2.model.Condition;

import javax.inject.Inject;
import java.util.HashMap;
import java.util.Map;

/**
 * Provides access to Member items.
 */
public class RecommendationDao {
    private DynamoDBMapper mapper;

    /**
     * Creates a MemberDao with the given DynamoDBMapper
     * @param mapper The DynamoDBMapper
     */
    @Inject
    public RecommendationDao(DynamoDBMapper mapper) {
        this.mapper = mapper;
    }

    /**
     * Retrieves a list of recommendations for the given username, if one exists.
     * @param username The username to look for
     * @return The list of recomendations for the given username, if one exists. {@code null} otherwise.
     */
    public PaginatedQueryList<Recommendation> getRecommendations(String username) {
        if (username.equals("")) {
            throw new IllegalArgumentException("username cannot be empty");
        }

        Map<String, AttributeValue> query = new HashMap<>();
        query.put(":v1", new AttributeValue().withS("active"));

        DynamoDBQueryExpression queryExpression = new DynamoDBQueryExpression()
                .withHashKeyValues(username)
                .withKeyConditionExpression("status = :v1")
                .withExpressionAttributeValues(query)
                .withLimit(3);

        return mapper.query(Recommendation.class, queryExpression);
    }

    public void createRecommendation(Recommendation recommendation) {
        mapper.save(recommendation);
    }

    public void deleteRecommendation(Recommendation recommendation) {
        recommendation.setActive("dismissed");
        mapper.save(recommendation);
    }

}
