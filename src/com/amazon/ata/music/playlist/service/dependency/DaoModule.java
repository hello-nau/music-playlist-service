package com.amazon.ata.music.playlist.service.dependency;
import com.amazon.ata.aws.dynamodb.DynamoDbClientProvider;
import com.amazon.ata.music.playlist.service.dynamodb.PlaylistDao;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import dagger.Module;
import dagger.Provides;

import javax.inject.Inject;
import javax.inject.Singleton;

@Module
public class DaoModule {
    @Inject
    public DaoModule(){}
    @Provides
    @Singleton
    public DynamoDBMapper provideDynamoDBMapper() {
        return new DynamoDBMapper((DynamoDbClientProvider.getDynamoDBClient()));
    }
    @Provides
    @Singleton
    public PlaylistDao providePlaylistDao() {
        return new PlaylistDao(new DynamoDBMapper(DynamoDbClientProvider.getDynamoDBClient()));
    }
}
