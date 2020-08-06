package com.llt.rainbow.fart;

import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.Player;
import java.io.*;
import java.util.*;

/**
 * @author llt
 */
public class VoicePlayer {

    private static Random random = new Random();

    public static void randomPlay(List<byte[]> sounds) {
        try {
            new Player(new ByteArrayInputStream(sounds.get(random.nextInt(sounds.size())))).play();
        } catch (JavaLayerException e) {
            e.printStackTrace();
        }
    }
}
