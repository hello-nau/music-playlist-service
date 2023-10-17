package com.amazon.ata.music.playlist.service.lambda;

//import com.amazon.ata.music.playlist.service.dependency.App;
import com.amazon.ata.music.playlist.service.activity.CreatePlaylistActivity;
import com.amazon.ata.music.playlist.service.converters.ModelConverter;
import com.amazon.ata.music.playlist.service.dependency.DaggerServiceComponent;
import com.amazon.ata.music.playlist.service.dependency.ServiceComponent;
import com.amazon.ata.music.playlist.service.dynamodb.PlaylistDao;
import com.amazon.ata.music.playlist.service.dynamodb.models.Playlist;
import com.amazon.ata.music.playlist.service.exceptions.InvalidAttributeValueException;
import com.amazon.ata.music.playlist.service.models.PlaylistModel;
import com.amazon.ata.music.playlist.service.models.requests.CreatePlaylistRequest;
import com.amazon.ata.music.playlist.service.models.results.CreatePlaylistResult;
import com.amazon.ata.music.playlist.service.util.MusicPlaylistServiceUtils;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class CreatePlaylistActivityProvider implements RequestHandler<CreatePlaylistRequest, CreatePlaylistResult> {

//    private static App app;

    public CreatePlaylistActivityProvider() {

    }

    @Override
    public CreatePlaylistResult handleRequest(final CreatePlaylistRequest createPlaylistRequest, Context context) {
//        CreatePlaylistActivity createPlaylistActivity = new CreatePlaylistActivity();
////        return getApp().provideCreatePlaylistActivity().handleRequest(createPlaylistRequest, context);
//        return createPlaylistActivity.handleRequest(createPlaylistRequest,context);
        PlaylistDao playlistDao = getDagger().provideDaoModule().providePlaylistDao();
        if(!MusicPlaylistServiceUtils.isValidString(createPlaylistRequest.getName()) ||
                createPlaylistRequest.getCustomerId().contains("\"")
                || createPlaylistRequest.getCustomerId().contains("'")
                || createPlaylistRequest.getCustomerId().contains("\\")){
            throw new InvalidAttributeValueException();
        }
        String newPlaylistId = MusicPlaylistServiceUtils.generatePlaylistId();
        Playlist playlist = new Playlist();
        playlist.setId(newPlaylistId);
        playlist.setName(createPlaylistRequest.getName());
        playlist.setCustomerId(createPlaylistRequest.getCustomerId());
        if(createPlaylistRequest.getTags()!=null) {
            playlist.setTags(new HashSet<>(createPlaylistRequest.getTags()));
        }else{
            Set<String> sortOfEmptySet= new HashSet<>();
            sortOfEmptySet.add("");
            playlist.setTags(sortOfEmptySet);
        }
        playlist.setSongList(new ArrayList<>());
        playlist.setSongCount(0);
        playlistDao.savePlaylist(playlist);
        PlaylistModel playlistModel = new ModelConverter().toPlaylistModel(playlist);

        return CreatePlaylistResult.builder()
                .withPlaylist(playlistModel)
                .build();
    }

    private ServiceComponent getDagger() {
        ServiceComponent dagger = DaggerServiceComponent.create();
        return dagger;
    }
}
