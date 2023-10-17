package com.amazon.ata.music.playlist.service.lambda;

import com.amazon.ata.music.playlist.service.activity.AddSongToPlaylistActivity;
import com.amazon.ata.music.playlist.service.activity.UpdatePlaylistActivity;
//import com.amazon.ata.music.playlist.service.dependency.App;
import com.amazon.ata.music.playlist.service.dependency.DaggerServiceComponent;
import com.amazon.ata.music.playlist.service.dependency.ServiceComponent;
import com.amazon.ata.music.playlist.service.dynamodb.AlbumTrackDao;
import com.amazon.ata.music.playlist.service.dynamodb.PlaylistDao;
import com.amazon.ata.music.playlist.service.models.requests.AddSongToPlaylistRequest;
import com.amazon.ata.music.playlist.service.models.results.AddSongToPlaylistResult;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

import javax.inject.Inject;

public class AddSongToPlaylistActivityProvider implements RequestHandler<AddSongToPlaylistRequest, AddSongToPlaylistResult> {

//    private static App app;
    @Inject
    public AddSongToPlaylistActivityProvider() {

    }

    @Override
    public AddSongToPlaylistResult handleRequest(final AddSongToPlaylistRequest addSongToPlaylistRequest, Context context) {
//        PlaylistDao playlistDao = getDagger().provideDaoModule().providePlaylistDao();
//
        AddSongToPlaylistActivity addSongToPlaylistActivity = getDagger().provideAddSongToPlaylistActivity();
//                new AddSongToPlaylistActivity(playlistDao, new AlbumTrackDao(getDagger().provideDaoModule().provideDynamoDBMapper()));
        return addSongToPlaylistActivity.handleRequest(addSongToPlaylistRequest, context);
    }

    private ServiceComponent getDagger() {
        ServiceComponent dagger = DaggerServiceComponent.create();
        return dagger;
    }
}
