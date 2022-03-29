package com.amazon.ata.graphs.dependency;

import com.amazon.ata.aws.dynamodb.DynamoDbClientProvider;
import com.amazon.ata.input.console.ATAUserHandler;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import dagger.Module;
import dagger.Provides;

import javax.inject.Singleton;

@Module
public class DiscussionCliModule {
    // PARTICIPANTS: declare your provider methods here

    @Provides
    public ATAUserHandler provideATAUserHandler() {
        return new ATAUserHandler();
    }

    @Provides
    @Singleton
    public DynamoDBMapper provideDynamoDBMapper() {
        return new DynamoDBMapper(DynamoDbClientProvider.getDynamoDBClient());
    }

}
