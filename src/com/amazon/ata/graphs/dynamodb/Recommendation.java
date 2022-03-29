package com.amazon.ata.graphs.dynamodb;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBRangeKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;

import java.util.Objects;

@DynamoDBTable(tableName = "Graphs-Recommendations")
public class Recommendation {

    /**
     * The username that the recommendation should go to
     */
    private String username;

    /**
     * The recommended username
     */
    private String recommendation;

    private String status;

    public Recommendation() {}

    public Recommendation(String username, String recommendation, String status) {
        this.username = username;
        this.recommendation = recommendation;
        this.status = status;
    }

    @DynamoDBHashKey(attributeName = "username")
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @DynamoDBAttribute(attributeName = "recommendation")
    public String getRecommendation() {
        return recommendation;
    }

    public void setRecommendation(String recommendation) {
        this.recommendation = recommendation;
    }

    @DynamoDBRangeKey(attributeName = "status")
    public String getStatus() {
        return status;
    }

    public void setActive(String status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Recommendation that = (Recommendation) o;
        return Objects.equals(username, that.username) && Objects.equals(recommendation, that.recommendation) && Objects.equals(status, that.status);
    }

    @Override
    public int hashCode() {
        return Objects.hash(username, recommendation, status);
    }
}
