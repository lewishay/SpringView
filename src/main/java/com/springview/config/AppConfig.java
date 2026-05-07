package com.springview.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class AppConfig {

    @Value("${stream.playlistDir}")
    private String playlistDir;

    @Value("${stream.audioDevice}")
    private String streamAudioDevice;

    @Value("${soundVolumeView.audioDevice.old}")
    private String audioDeviceOld;

    @Value("${soundVolumeView.audioDevice.new}")
    private String audioDeviceNew;

    public String getPlaylistDir() { return playlistDir; }
    public String getStreamAudioDevice() { return streamAudioDevice; }
    public String getAudioDeviceOld() { return audioDeviceOld; }
    public String getAudioDeviceNew() { return audioDeviceNew; }
}
