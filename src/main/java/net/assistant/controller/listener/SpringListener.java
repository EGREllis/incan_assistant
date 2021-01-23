package net.assistant.controller.listener;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.SpringApplicationRunListener;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;

import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

public class SpringListener implements SpringApplicationRunListener {
    public SpringListener(SpringApplication app, String args[]) {
    }

    @Override
    public void starting() {
        log("Spring starting");
    }

    @Override
    public void environmentPrepared(ConfigurableEnvironment environment) {
        log("Spring environment prepared");
    }

    @Override
    public void contextPrepared(ConfigurableApplicationContext context) {
        log("Spring context prepared");
    }

    @Override
    public void contextLoaded(ConfigurableApplicationContext context) {
        log("Spring loaded");
    }

    @Override
    public void started(ConfigurableApplicationContext context) {
        log("Spring started");
    }

    @Override
    public void running(ConfigurableApplicationContext context) {
        log("Spring running");
        openBrowser();
        log("Spring openned browser");
    }

    @Override
    public void failed(ConfigurableApplicationContext context, Throwable exception) {
        log("Spring failed");
    }

    private void log(String message) {
        System.err.println(message);
        System.err.flush();
    }

    private void openBrowser() {
        if (Desktop.isDesktopSupported() && Desktop.getDesktop().isSupported(Desktop.Action.BROWSE)) {
            System.err.println("Desktop and Browse are supported");
            System.err.flush();
            try {
                Desktop.getDesktop().browse(new URI("http://localhost:8080/"));
            } catch (URISyntaxException | IOException e) {
                System.err.println(e.getMessage());
                e.printStackTrace(System.err);
                System.err.flush();
            }
        } else {
            System.err.println("Desktop and Browse are not supported.  Please visit http://localhost:8080");
            System.err.flush();
        }
    }
}
