package com.amazon.ata.graphs.lambda;

import com.amazon.ata.graphs.dynamodb.FollowEdge;
import com.amazon.ata.graphs.dynamodb.FollowEdgeDao;
import com.amazon.ata.graphs.dynamodb.Recommendation;
import com.amazon.ata.graphs.dynamodb.RecommendationDao;
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
        return null;
    }
}
