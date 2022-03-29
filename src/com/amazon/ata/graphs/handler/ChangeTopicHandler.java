package com.amazon.ata.graphs.handler;

import com.amazon.ata.graphs.cli.DiscussionCliOperation;
import com.amazon.ata.graphs.cli.DiscussionCliState;
import com.amazon.ata.graphs.dynamodb.Topic;
import com.amazon.ata.input.console.ATAUserHandler;

import javax.inject.Inject;

/**
 * Handles the CHANGE_TOPIC operation.
 */
public class ChangeTopicHandler implements DiscussionCliOperationHandler {
    private ATAUserHandler userHandler;

    /**
     * Constructs handler with its dependencies.
     * @param userHandler the ATAUserHandler, for user input
     */
    @Inject
    public ChangeTopicHandler(ATAUserHandler userHandler) {
        this.userHandler = userHandler;
    }

    @Override
    public String handleRequest(DiscussionCliState state) {
        int topicNumber = userHandler.getInteger(0, state.getListedTopics().size() - 1, "Select topic number:");
        Topic nextTopic = state.getListedTopics().get(topicNumber);
        state.setCurrentTopic(nextTopic);
        state.setNextOperation(DiscussionCliOperation.VIEW_TOPIC_MESSAGES);

        return String.format("Topic changed to '%s'", nextTopic.getName());
    }
}
