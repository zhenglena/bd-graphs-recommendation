package com.amazon.ata.graphs.handler;

import com.amazon.ata.graphs.cli.DiscussionCliOperation;
import com.amazon.ata.graphs.cli.DiscussionCliState;
import com.amazon.ata.graphs.dynamodb.Topic;
import com.amazon.ata.graphs.dynamodb.TopicDao;
import com.amazon.ata.input.console.ATAUserHandler;

import javax.inject.Inject;

/**
 * Handler for the CREATE_TOPIC operation
 */
public class CreateTopicHandler implements DiscussionCliOperationHandler {
    private final TopicDao topicDao;
    private final ATAUserHandler userHandler;

    /**
     * Constructs handler with its dependencies.
     * @param topicDao TopicDao
     * @param userHandler the ATAUserHandler, for user input
     */
    @Inject
    public CreateTopicHandler(TopicDao topicDao, ATAUserHandler userHandler) {
        this.topicDao = topicDao;
        this.userHandler = userHandler;
    }

    @Override
    public String handleRequest(DiscussionCliState state) {
        String topicName = userHandler.getString("", "New topic name: ");
        String description = userHandler.getString("", "New topic description: ");

        Topic newTopic = new Topic(topicName, description);
        newTopic = topicDao.createTopic(newTopic);
        state.setCurrentTopic(newTopic);
        state.setNextOperation(DiscussionCliOperation.VIEW_TOPIC_MESSAGES);

        return String.format("New topic '%s' was created!", newTopic.getName());
    }
}
