# Amazon Music Playlist Service

## Overview

The Amazon Music Playlist Service is an AWS service designed to enhance the Amazon Music Unlimited experience by allowing customers to create and manage their own custom playlists. This service interacts with the Amazon Music client to provide song metadata for each playlist, which the client can then use to fetch and play the song files.

## Features

- Create a new, empty playlist with a custom name and optional tags.
- Retrieve a playlist using its unique ID.
- Update the name of a playlist.
- Add a song to the beginning or end of a playlist.
- Retrieve all songs in a playlist, in a specified order (in order, reverse order, shuffled).

## Implementation

The service package includes a basic implementation of the Get Playlists endpoint. The Amazon Music Client, responsible for user login and passing the customer ID, will call this service. The `MusicPlaylistServiceUtils` class assists with validation and playlist ID generation.

## Exceptions

The service can throw three custom exceptions:

- `PlaylistNotFoundException`
- `InvalidAttributeValueExcpetion`
- `AlbumTrackNotFoundException`

## Endpoints

- **Get Playlist Endpoint**: Accepts GET requests to `/playlists/:id`. If the given playlist ID is not found, it throws a `PlaylistNotFoundException`.
- **Create Playlist Endpoint**: Accepts POST requests to `/playlists`. It creates a new playlist with a provided name, a given customer ID, and an optional list of tags.
- **Get Playlist Songs Endpoint**: Accepts GET requests to `/playlists/:id/songs`. Retrieves all songs of a playlist with the given playlist ID. Returns the song list in default playlist order. If the optional order parameter is provided, this API will return the song list in order, reverse order, or shuffled order, based on the value of order. If the playlist ID is found, but contains no songs, the songs list will be empty. If the playlist ID is not found, will throw a `PlaylistNotFoundException`.

## Data

In order to initiate development, a number of albums have been imported into a local table named `album_tracks`. Two Java models, `Playlist.java` and `AlbumTrack.java`, have been created to represent an item in the `playlists` and `album_tracks` tables respectively.

For the sake of simpler song list retrieval, the list of songs will be stored directly in the `playlists` table, represented in the Java model as a `List<AlbumTrack>`. This approach, known as denormalizing data, has its limitations. Based on the maximum size of an item in DynamoDB, it is estimated that about 2500 songs can be stored in a single playlist, which is above the projected playlist size that customers will need in this phase. However, this comes at the cost of having to store large items as the scale increases.

A more forward-thinking approach would be to create a separate table in which each song in a playlist is represented by a distinct item in the table (which contains the playlist ID and song identifiers). This removes the limitation of songs in a playlist, but consumes more data (and thus cost) and includes more complexity to retrieve the playlist’s songs. A migration to this data model may be considered in the future when customer needs are more certain.
