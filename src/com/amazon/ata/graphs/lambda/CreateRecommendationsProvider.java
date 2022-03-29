package com.amazon.ata.graphs.lambda;

import com.amazon.ata.graphs.dependency.DaggerDiscussionCliComponent;
import com.amazon.ata.graphs.dynamodb.Recommendation;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

import java.util.List;

public class CreateRecommendationsProvider implements RequestHandler<CreateRecommendationsRequest, List<Recommendation>> {
    @Override
    public List<Recommendation> handleRequest(CreateRecommendationsRequest input, Context context) {
        return new CreateRecommendationsActivity(DaggerDiscussionCliComponent.create().provideRecommendationDao(), DaggerDiscussionCliComponent.create().provideFollowEdgeDao()).handleRequest(input, context);
    }
}
