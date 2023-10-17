package com.amazon.ata.music.playlist.service.dependency;

import com.amazon.ata.music.playlist.service.activity.*;
import dagger.Component;
import javax.inject.Singleton;

@Component(modules = {DaoModule.class})
@Singleton
public interface ServiceComponent {
     DaoModule provideDaoModule();
     GetPlaylistActivity provideGetPlaylistActivity();
     CreatePlaylistActivity provideCreatePlaylistActivity();
     UpdatePlaylistActivity provideUpdatePlaylistActivity();
     AddSongToPlaylistActivity provideAddSongToPlaylistActivity();
     GetPlaylistSongsActivity provideGetPlaylistSongsActivity();



}
