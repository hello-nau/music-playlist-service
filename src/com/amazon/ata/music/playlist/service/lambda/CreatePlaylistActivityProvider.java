package com.amazon.ata.music.playlist.service.lambda;

//import com.amazon.ata.music.playlist.service.dependency.App;
import com.amazon.ata.music.playlist.service.activity.CreatePlaylistActivity;
import com.amazon.ata.music.playlist.service.dependency.DaggerServiceComponent;
import com.amazon.ata.music.playlist.service.dependency.ServiceComponent;
import com.amazon.ata.music.playlist.service.models.requests.CreatePlaylistRequest;
import com.amazon.ata.music.playlist.service.models.results.CreatePlaylistResult;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

public class CreatePlaylistActivityProvider implements RequestHandler<CreatePlaylistRequest, CreatePlaylistResult> {

//    private static App app;

    public CreatePlaylistActivityProvider() {

    }

    @Override
    public CreatePlaylistResult handleRequest(final CreatePlaylistRequest createPlaylistRequest, Context context) {
        CreatePlaylistActivity createPlaylistActivity = new CreatePlaylistActivity();
//        return getApp().provideCreatePlaylistActivity().handleRequest(createPlaylistRequest, context);
        return createPlaylistActivity.handleRequest(createPlaylistRequest,context);
    }

    private ServiceComponent getDagger() {
        ServiceComponent dagger = DaggerServiceComponent.create();
        return dagger;
    }
}
