package com.amazon.ata.graphs.handler;


import com.amazon.ata.graphs.cli.DiscussionCliState;
import com.amazon.ata.graphs.dynamodb.Recommendation;
import com.amazon.ata.graphs.dynamodb.Topic;
import com.amazon.ata.graphs.dynamodb.RecommendationDao;
import com.amazon.ata.input.console.ATAUserHandler;
import com.amazon.ata.string.TextTable;
import com.google.common.collect.ImmutableList;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

/**
 * Handler for the VIEW_TOPICS operation.
 */
public class ViewRecommendationsHandler implements DiscussionCliOperationHandler {

    private final RecommendationDao recommendationDao;
    private final ATAUserHandler userHandler;

    /**
     * Constructs handler with its dependencies.
     * @param topicDao The RecommendationDao
     * @param userHandler the ATAUserHandler, for user input
     */
    @Inject
    public ViewRecommendationsHandler(RecommendationDao topicDao, ATAUserHandler userHandler) {
        this.recommendationDao = topicDao;
        this.userHandler = userHandler;
    }

    @Override
    public String handleRequest(DiscussionCliState state) {
        List<Recommendation> recommendations = recommendationDao.getRecommendations(state.getCurrentMember().getUsername());
        return renderRecommendations(recommendations);
    }

    private String renderRecommendations(List<Recommendation> recommendations) {
        List<String> headers = ImmutableList.of("#", "Recommended usernames to follow:");
        List<List<String>> rows = new ArrayList<>();
        int topicNum = 0;
        for (Recommendation recommendation : recommendations) {
            List<String> row = new ArrayList<>();
            row.add(Integer.toString(topicNum++));
            row.add(recommendation.getUsername());
            rows.add(row);
        }

        return new TextTable(headers, rows).toString();
    }
}
