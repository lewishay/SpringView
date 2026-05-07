package com.springview.components;

import com.springview.config.AppConfig;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

@Component
public class AudioSwitcher {

    private static String audioDeviceOld;
    private static String audioDeviceNew;

    public AudioSwitcher(AppConfig appConfig) {
        audioDeviceOld = appConfig.getAudioDeviceOld();
        audioDeviceNew = appConfig.getAudioDeviceNew();
    }

    private static void switchTo(String audioDevice) throws IOException {

        Path exe = Paths.get("soundvolumeview", "SoundVolumeView.exe");

        new ProcessBuilder(
            exe.toAbsolutePath().toString(),
            "/SetDefault",
            audioDevice,
            "all"
        ).start();
    }

    public static void switchToOld() throws IOException {
        switchTo(audioDeviceOld);
    }

    public static void switchToNew() throws IOException {
        switchTo(audioDeviceNew);
    }
}
