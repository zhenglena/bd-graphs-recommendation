package com.amazon.ata.graphs.handler;

import com.amazon.ata.graphs.cli.DiscussionCliOperation;
import com.amazon.ata.graphs.cli.DiscussionCliState;

import javax.inject.Inject;

/**
 * Handler for the EXIT operation.
 */
public class ExitHandler implements DiscussionCliOperationHandler {

    @Inject
    public ExitHandler() {

    }

    @Override
    public String handleRequest(DiscussionCliState state) {
        state.setNextOperation(DiscussionCliOperation.EXIT);
        return "Hope you enjoyed, good-bye!";
    }
}
