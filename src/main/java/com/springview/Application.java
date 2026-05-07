package com.springview;

import com.springview.services.StreamService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        ApplicationContext context = SpringApplication.run(Application.class, args);
        StreamService streamService = context.getBean(StreamService.class);
        try {
            streamService.startStream();
        } catch (Exception ex) {
            System.out.printf("Could not start stream, ex: %s%n", ex.getMessage());
        }
    }

}
