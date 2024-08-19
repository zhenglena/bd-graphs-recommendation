package com.amazon.ata.graphs.lambda;

import com.amazon.ata.graphs.dynamodb.FollowEdge;
import com.amazon.ata.graphs.dynamodb.FollowEdgeDao;
import com.amazon.ata.graphs.dynamodb.Recommendation;
import com.amazon.ata.graphs.dynamodb.RecommendationDao;
import com.amazonaws.services.dynamodbv2.datamodeling.PaginatedQueryList;
import com.amazonaws.services.lambda.runtime.Context;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.stream.Collectors;

public class CreateRecommendationsActivity {

    private FollowEdgeDao followEdgeDao;
    private RecommendationDao recommendationDao;

    public CreateRecommendationsActivity(RecommendationDao recommendationDao, FollowEdgeDao followEdgeDao) {
        this.followEdgeDao = followEdgeDao;
        this.recommendationDao = recommendationDao;
    }

    public List<Recommendation> handleRequest(CreateRecommendationsRequest input, Context context) {
        List<Recommendation> recommendations = new ArrayList<>();
        //Recommendations should not include anyone the member already follows or themselves.
            //Recommendations should be unique in a given call
        if (input == null || input.getUsername() == null) {
            throw new InvalidParameterException("invalid username");
        }

        List<String> follows = this.followEdgeDao.getAllFollows(input.getUsername()).stream()
                .map(FollowEdge::getToUsername)
                .collect(Collectors.toList());

        for (String user : follows) {
            List<String> followsFollows = followEdgeDao.getAllFollows(user).stream()
                    .map(FollowEdge::getToUsername)
                    .collect(Collectors.toList());
            for (String candidate : followsFollows) {
                if (!candidate.equals(input.getUsername()) && !follows.contains(candidate)
                        && !recommendations.contains(candidate)) {
                    recommendations.add(new Recommendation(input.getUsername(), candidate, "active");
                    if (recommendations.size() >= input.getLimit()) {
                        return recommendations;
                    }
                }
            }
        }
        return recommendations;
    }
}
