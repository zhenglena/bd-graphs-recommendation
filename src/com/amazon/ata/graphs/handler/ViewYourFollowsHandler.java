package com.amazon.ata.graphs.handler;


import com.amazon.ata.graphs.cli.DiscussionCliState;
import com.amazon.ata.graphs.dynamodb.FollowEdge;
import com.amazon.ata.graphs.dynamodb.FollowEdgeDao;
import com.amazon.ata.graphs.dynamodb.Topic;
import com.amazon.ata.input.console.ATAUserHandler;
import com.amazon.ata.string.TextTable;
import com.google.common.collect.ImmutableList;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

/**
 * Handler for the VIEW_TOPICS operation.
 */
public class ViewYourFollowsHandler implements DiscussionCliOperationHandler {

    private final FollowEdgeDao followEdgeDao;
    private final ATAUserHandler userHandler;

    /**
     * Constructs handler with its dependencies.
     * @param followEdgeDao The FollowEdgeDao
     * @param userHandler the ATAUserHandler, for user input
     */
    @Inject
    public ViewYourFollowsHandler(FollowEdgeDao followEdgeDao, ATAUserHandler userHandler) {
        this.followEdgeDao = followEdgeDao;
        this.userHandler = userHandler;
    }

    @Override
    public String handleRequest(DiscussionCliState state) {
        List<FollowEdge> follows = followEdgeDao.getAllFollows(state.getCurrentMember().getUsername());
        return renderFollows(follows);
    }

    private String renderFollows(List<FollowEdge> follows) {
        List<String> headers = ImmutableList.of("#", "Here's who you follow:");
        List<List<String>> rows = new ArrayList<>();
        int topicNum = 0;
        for (FollowEdge follow : follows) {
            List<String> row = new ArrayList<>();
            row.add(Integer.toString(topicNum++));
            row.add(follow.getToUsername());
            rows.add(row);
        }

        return new TextTable(headers, rows).toString();
    }
}
