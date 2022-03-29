package com.amazon.ata.graphs.handler;

import com.amazon.ata.graphs.cli.DiscussionCliOperation;
import com.amazon.ata.graphs.cli.DiscussionCliState;
import com.amazon.ata.graphs.dynamodb.*;
import com.amazon.ata.input.console.ATAUserHandler;

import javax.inject.Inject;

/**
 * Handler for the CREATE_TOPIC operation
 */
public class CreateFollowHandler implements DiscussionCliOperationHandler {
    private final FollowEdgeDao followEdgeDao;
    private final MemberDao memberDao;
    private final ATAUserHandler userHandler;

    /**
     * Constructs handler with its dependencies.
     * @param topicDao FollowEdgeDao
     * @param userHandler the ATAUserHandler, for user input
     */
    @Inject
    public CreateFollowHandler(FollowEdgeDao topicDao, MemberDao memberDao, ATAUserHandler userHandler) {
        this.followEdgeDao = topicDao;
        this.memberDao = memberDao;
        this.userHandler = userHandler;
    }

    @Override
    public String handleRequest(DiscussionCliState state) {
        String username = userHandler.getString("", "Username you want to follow: ");

        Member member = memberDao.getMember(username);
        if (member == null) {
            return String.format("Member %s does not exist.", username);
        }

        followEdgeDao.createFollowEdge(state.getCurrentMember().getUsername(), username);

        return String.format("You are now following %s!", username);
    }
}
