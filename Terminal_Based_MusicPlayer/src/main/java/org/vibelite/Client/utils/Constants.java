package org.vibelite.Client.utils;

import java.util.Scanner;

public class Constants
{

    public static final int PORT = 6000;

    public static final String  IP_ADDRESS = "localhost";

    public static final String NEWLINE = "\n";

    public static final String TAB = "\t";

    public static final String PATH_SEPARATOR = "/";

    public static final String FILE_IDENTIFIER = ".";

    public static final Scanner INPUT = new Scanner(System.in);

    public static final String LIBRARY_PLAYER_ID = "LIBRARY";

    public static final String PLAYLIST_PLAYER_ID = "PLAYLIST";

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
