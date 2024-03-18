package org.vibelite.Server.utils;

import java.nio.file.*;

public class Constants
{
    public static final int PORT = 6000;

    public static final String PATH_SEPARATOR = "/";

    public static final String AUDIO_DIRECTORY = (Paths.get("").toAbsolutePath()+ PATH_SEPARATOR +"src" + PATH_SEPARATOR + "main" + PATH_SEPARATOR + "resources"+ PATH_SEPARATOR + "Audio" + PATH_SEPARATOR);

    public static final int BUFFER_SIZE = 4096; // Buffer size for reading and writing

    public static final String REQUEST_LIBRARY = "library";

    public static final String REQUEST_AUDIO_STREAMING = "audioFile";

    public static final String REQUEST_CREATE_PLAYLIST = "createPlaylist";

    public static final String REQUEST_PLAYLIST_NAMES = "getPlaylistName";

    public static final String REQUEST_ADDTO_PLAYLIST = "addToPlaylist";

    public static final String REQUEST_REMOVE_FROM_PLAYLIST = "removeFromPlaylist";

    public static final String REQUEST_REMOVE_PLAYLIST = "removePlaylist";

    public static final String REQUEST_PLAYLIST = "getPlaylist";

    public static final String REQUEST_UPLOAD = "uploadAudio";

    public static final String REQUEST_DOWNLOAD = "downloadAudio";

    public static final String REQUEST_EXIT = "exit";


}
