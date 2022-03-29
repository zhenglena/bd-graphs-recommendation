package com.amazon.ata.graphs.lambda;

public class CreateRecommendationsRequest {

    private String username;

    private int limit;

    public CreateRecommendationsRequest(String fromUsername, int limit) {
        this.username = fromUsername;
        this.limit = limit;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

}
