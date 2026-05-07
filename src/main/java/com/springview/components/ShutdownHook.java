package com.springview.components;

import com.springview.services.StreamService;
import jakarta.annotation.PreDestroy;
import org.springframework.stereotype.Component;

@Component
public class ShutdownHook {

    private final StreamService streamService;

    public ShutdownHook(StreamService streamService) {

        this.streamService = streamService;
    }

    @PreDestroy
    public void onShutdown() throws Exception {
        streamService.stopStream();
        AudioSwitcher.switchToOld();
        System.out.println("App shutting down, FFmpeg stopped.");
    }
}
