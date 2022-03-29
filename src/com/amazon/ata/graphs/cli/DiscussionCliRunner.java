package com.amazon.ata.graphs.cli;

import com.amazon.ata.graphs.dependency.DaggerDiscussionCliComponent;
import com.amazon.ata.graphs.dependency.DiscussionCliComponent;
import com.amazon.ata.input.console.ATAUserHandler;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;

/**
 * Provides a main method to instantiate and run the DiscussionCli we will be
 * working with in this lesson.
 */
public class DiscussionCliRunner {
    private static DynamoDBMapper mapper;
    private static ATAUserHandler userHandler;

    /**
     * Starts the CLI application.
     * @param args no args expected
     */
    public static void main(String[] args) {
        DiscussionCliComponent component = DaggerDiscussionCliComponent.create();
        DiscussionCli cli = component.provideDiscussionCli();
        cli.handleRequests();
    }

}
