package com.amazon.ata.music.playlist.service.activity;


import com.amazon.ata.music.playlist.service.dependency.DaggerServiceComponent;
import com.amazon.ata.music.playlist.service.dependency.ServiceComponent;
import com.amazon.ata.music.playlist.service.dynamodb.models.Playlist;
import com.amazon.ata.music.playlist.service.exceptions.InvalidAttributeChangeException;
import com.amazon.ata.music.playlist.service.exceptions.InvalidAttributeValueException;
import com.amazon.ata.music.playlist.service.exceptions.PlaylistNotFoundException;
import com.amazon.ata.music.playlist.service.models.PlaylistModel;
import com.amazon.ata.music.playlist.service.models.requests.UpdatePlaylistRequest;
import com.amazon.ata.music.playlist.service.models.results.UpdatePlaylistResult;
import com.amazon.ata.music.playlist.service.dynamodb.PlaylistDao;

import com.amazon.ata.music.playlist.service.util.MusicPlaylistServiceUtils;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.inject.Inject;

/**
 * Implementation of the UpdatePlaylistActivity for the MusicPlaylistService's UpdatePlaylist API.
 *
 * This API allows the customer to update their saved playlist's information.
 */
public class UpdatePlaylistActivity implements RequestHandler<UpdatePlaylistRequest, UpdatePlaylistResult> {
    private final Logger log = LogManager.getLogger();
    private final PlaylistDao playlistDao;




    /**
     * Instantiates a new UpdatePlaylistActivity object.
     *
     * @param - PlaylistDao to access the playlist table.
     */
@Inject
    public UpdatePlaylistActivity() {
        ServiceComponent dagger = DaggerServiceComponent.create();
        playlistDao = dagger.provideDaoModule().providePlaylistDao();
    }

    /**
     * This method handles the incoming request by retrieving the playlist, updating it,
     * and persisting the playlist.
     * <p>
     * It then returns the updated playlist.
     * <p>
     * If the playlist does not exist, this should throw a PlaylistNotFoundException.
     * <p>
     * If the provided playlist name or customer ID has invalid characters, throws an
     * InvalidAttributeValueException
     * <p>
     * If the request tries to update the customer ID,
     * this should throw an InvalidAttributeChangeException
     *
     * @param updatePlaylistRequest request object containing the playlist ID, playlist name, and customer ID
     *                              associated with it
     * @return updatePlaylistResult result object containing the API defined {@link PlaylistModel}
     */
    @Override
    public UpdatePlaylistResult handleRequest(final UpdatePlaylistRequest updatePlaylistRequest, Context context) {
        log.info("Received UpdatePlaylistRequest {}", updatePlaylistRequest);

        if(!MusicPlaylistServiceUtils.isValidString(updatePlaylistRequest.getName())) {
            throw new InvalidAttributeValueException();
        }

        String playListId;
            Playlist playlist;
            try {
               playListId  = updatePlaylistRequest.getId();
               playlist = playlistDao.getPlaylist(playListId);
            } catch (PlaylistNotFoundException e) {
                throw new PlaylistNotFoundException("The playlist with " + updatePlaylistRequest.getId() + " id was not found.");
            }

            String playlistCustomerId = playlist.getCustomerId();
            String customerIdToUpdate = updatePlaylistRequest.getCustomerId();

            if (playlistCustomerId.equals(customerIdToUpdate)) {
                playlistDao.savePlaylist(playlist);
            } else {
                throw new InvalidAttributeChangeException("Customer id's don't match.");
            }

        PlaylistModel playlistModel = new PlaylistModel.Builder()
                .withCustomerId(updatePlaylistRequest.getCustomerId())
                .withName(updatePlaylistRequest.getName())
                .withId(updatePlaylistRequest.getId())
                .build();

        return UpdatePlaylistResult.builder()
                .withPlaylist(playlistModel)
                .build();
    }
}
