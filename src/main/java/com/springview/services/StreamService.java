package com.springview.services;

import com.springview.components.AudioSwitcher;
import com.springview.config.AppConfig;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

@Service
public class StreamService {

    private Process ffmpegProcess;
    private final String playlistDir;
    private final String streamAudioDevice;

    public StreamService(AppConfig appConfig)
    {
        this.playlistDir = appConfig.getPlaylistDir();
        this.streamAudioDevice = appConfig.getStreamAudioDevice();
    }

    public void startStream() throws Exception {

        if (ffmpegProcess != null && ffmpegProcess.isAlive()) {
            System.out.println("Stream already running");
            return;
        }

        AudioSwitcher.switchToNew();

        ProcessBuilder pb = new ProcessBuilder(
                "ffmpeg",

                // Video capture
                "-f", "gdigrab",
                "-framerate", "30",
                "-thread_queue_size", "512",
                "-i", "desktop",

                // Audio capture
                "-f", "dshow",
                "-thread_queue_size", "512",
                "-i", "audio=" + streamAudioDevice,

                // Video settings
                "-vf", "scale=1280:-1:flags=lanczos",
                "-r", "30",
                "-vsync", "cfr",
                "-pix_fmt", "yuv420p",
                "-c:v", "h264_nvenc",
                "-preset", "p4",
                "-rc", "cbr",
                "-b:v", "2500k",
                "-maxrate", "2500k",
                "-bufsize", "500k",

                // Audio settings
                "-c:a", "aac",
                "-b:a", "128k",
                "-ar", "44100",
                "-af", "aresample=async=1:first_pts=0",

                // Sync video and audio
                "-map", "0:v:0",
                "-map", "1:a:0",

                // Keyframe settings
                "-g", "30",
                "-sc_threshold", "0",

                // HLS settings
                "-f", "hls",
                "-hls_time", "1",
                "-hls_list_size", "2",
                "-hls_flags", "delete_segments+append_list+independent_segments",
                "-hls_playlist_type", "event",
                playlistDir + "\\stream.m3u8"
        );

        pb.inheritIO();

        ffmpegProcess = pb.start();

        System.out.println("Stream started");
    }

    public void stopStream() {
        if (ffmpegProcess != null && ffmpegProcess.isAlive()) {
            ffmpegProcess.destroy();
            System.out.println("Stream stopped");

            try (Stream<Path> files = Files.list(Paths.get(playlistDir))) {
                files.forEach(path -> {
                    try {
                        Files.deleteIfExists(path);
                    } catch (IOException e) {
                        System.out.println("Failed to delete: " + path);
                    }
                });
            } catch (IOException e) {
                System.out.println("Cleanup failed");
            }
        }
    }
}
