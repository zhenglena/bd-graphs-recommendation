package com.amazon.ata.graphs.handler;

import com.amazon.ata.graphs.cli.DiscussionCliState;

/**
 * General interface for the Handler classes.
 */
public interface DiscussionCliOperationHandler {
    /**
     * Handles the relevant operation requested and returns response to be displayed to
     * user (if any).
     * @param state The current CLI app state
     * @return String of content to be rendered to user, if any
     */
    String handleRequest(DiscussionCliState state);
}
