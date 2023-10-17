package com.amazon.ata.music.playlist.service.lambda;

//import com.amazon.ata.music.playlist.service.dependency.App;
import com.amazon.ata.music.playlist.service.activity.GetPlaylistActivity;
import com.amazon.ata.music.playlist.service.activity.UpdatePlaylistActivity;
import com.amazon.ata.music.playlist.service.dependency.DaggerServiceComponent;
import com.amazon.ata.music.playlist.service.dependency.ServiceComponent;
import com.amazon.ata.music.playlist.service.dynamodb.PlaylistDao;
import com.amazon.ata.music.playlist.service.models.requests.UpdatePlaylistRequest;
import com.amazon.ata.music.playlist.service.models.results.UpdatePlaylistResult;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

import javax.inject.Inject;

public class UpdatePlaylistActivityProvider implements RequestHandler<UpdatePlaylistRequest, UpdatePlaylistResult> {

@Inject
    public UpdatePlaylistActivityProvider() {

    }

    @Override
    public UpdatePlaylistResult handleRequest(final UpdatePlaylistRequest updatePlaylistRequest, Context context) {
        PlaylistDao playlistDao = getDagger().provideDaoModule().providePlaylistDao();
        UpdatePlaylistActivity updatePlaylistActivity = new UpdatePlaylistActivity();
        return updatePlaylistActivity.handleRequest(updatePlaylistRequest, context);
    }

    private ServiceComponent getDagger() {
        ServiceComponent dagger = DaggerServiceComponent.create();
        return dagger;
    }
}
