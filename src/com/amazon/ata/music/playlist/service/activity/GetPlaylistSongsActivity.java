package com.amazon.ata.music.playlist.service.activity;

import com.amazon.ata.music.playlist.service.converters.ModelConverter;
import com.amazon.ata.music.playlist.service.dependency.DaggerServiceComponent;
import com.amazon.ata.music.playlist.service.dependency.ServiceComponent;
import com.amazon.ata.music.playlist.service.dynamodb.models.AlbumTrack;
import com.amazon.ata.music.playlist.service.dynamodb.models.Playlist;
import com.amazon.ata.music.playlist.service.exceptions.PlaylistNotFoundException;
import com.amazon.ata.music.playlist.service.models.SongOrder;
import com.amazon.ata.music.playlist.service.models.requests.GetPlaylistSongsRequest;
import com.amazon.ata.music.playlist.service.models.results.GetPlaylistSongsResult;
import com.amazon.ata.music.playlist.service.models.SongModel;
import com.amazon.ata.music.playlist.service.dynamodb.PlaylistDao;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.inject.Inject;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * Implementation of the GetPlaylistSongsActivity for the MusicPlaylistService's GetPlaylistSongs API.
 *
 * This API allows the customer to get the list of songs of a saved playlist.
 */
public class GetPlaylistSongsActivity implements RequestHandler<GetPlaylistSongsRequest, GetPlaylistSongsResult> {
    private final Logger log = LogManager.getLogger();
    private final PlaylistDao playlistDao;


    /**
     * Instantiates a new GetPlaylistSongsActivity object.
     *
     * @param - PlaylistDao to access the playlist table.
     */
@Inject
//    public GetPlaylistSongsActivity() {
//    ServiceComponent dagger = DaggerServiceComponent.create();
//    playlistDao = dagger.provideDaoModule().providePlaylistDao();
//    }
    public GetPlaylistSongsActivity(PlaylistDao playlistDao) {
    this.playlistDao = playlistDao;
    }

    /**
     * This method handles the incoming request by retrieving the playlist from the database.
     * <p>
     * It then returns the playlist's song list.
     * <p>
     * If the playlist does not exist, this should throw a PlaylistNotFoundException.
     *
     * @param getPlaylistSongsRequest request object containing the playlist ID
     * @return getPlaylistSongsResult result object containing the playlist's list of API defined {@link SongModel}s
     */
    @Override
    public GetPlaylistSongsResult handleRequest(final GetPlaylistSongsRequest getPlaylistSongsRequest, Context context) {
        log.info("Received GetPlaylistSongsRequest {}", getPlaylistSongsRequest);

        Playlist playlist = playlistDao.getPlaylist(getPlaylistSongsRequest.getId());
        if (playlist.getSongCount() == 0) {
            return GetPlaylistSongsResult.builder()
                    .withSongList(new LinkedList<SongModel>())
                    .build();
        }

        List<AlbumTrack> albumTracks = playlist.getSongList();
        List<SongModel> songModelList = new LinkedList<>();

        ModelConverter modelConverter = new ModelConverter();

        SongOrder order = getPlaylistSongsRequest.getOrder();
        if (order == null) {
            order = SongOrder.DEFAULT;
        }
        switch (order) {
            case DEFAULT:
                break;
            case REVERSED:
                Collections.reverse(albumTracks);
                break;
            case SHUFFLED:
                Collections.shuffle(albumTracks);
                break;
            default:
                throw new IllegalArgumentException("playlist order is not specified");
        }


        for (AlbumTrack albumTrack : albumTracks) {
            songModelList.add(modelConverter.toSongModel(albumTrack));
        }



        return GetPlaylistSongsResult.builder()
                .withSongList(songModelList)
                .build();
    }
}
