# SpringView

A simple app that uses Java Spring Boot and FFmpeg to capture and stream desktop video & audio. This stream is then
accessible from a secondary device on the local network.

## Requirements
* Java
* FFmpeg
* Windows
* An audio device capable of loopback audio

Please note that the configuration values in `application.properties` need to be updated before running the app on a new machine. These values set the output dir of the stream files and provide the names of the audio devices.

## Run
The app is started by executing the maven wrapper file `\.mvnw.cmd spring-boot:run`
