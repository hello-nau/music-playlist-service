package com.amazon.ata.music.playlist.service.lambda;

import com.amazon.ata.music.playlist.service.activity.GetPlaylistActivity;
//import com.amazon.ata.music.playlist.service.dependency.App;
import com.amazon.ata.music.playlist.service.dependency.DaggerServiceComponent;
import com.amazon.ata.music.playlist.service.dependency.ServiceComponent;
import com.amazon.ata.music.playlist.service.dynamodb.PlaylistDao;
import com.amazon.ata.music.playlist.service.models.requests.GetPlaylistRequest;
import com.amazon.ata.music.playlist.service.models.results.GetPlaylistResult;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

import javax.inject.Inject;

public class GetPlaylistActivityProvider implements RequestHandler<GetPlaylistRequest, GetPlaylistResult> {

@Inject
    public GetPlaylistActivityProvider() {

    }

    @Override
    public GetPlaylistResult handleRequest(final GetPlaylistRequest getPlaylistRequest, Context context) {
        PlaylistDao playlistDao = getDagger().provideDaoModule().providePlaylistDao();
        GetPlaylistActivity getPlaylistActivity = new GetPlaylistActivity();
        return getPlaylistActivity.handleRequest(getPlaylistRequest, context);


    }

    private ServiceComponent getDagger() {
       ServiceComponent dagger = DaggerServiceComponent.create();
        return dagger;
    }
}
