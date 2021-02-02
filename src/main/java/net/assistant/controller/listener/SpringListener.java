package net.assistant.controller.listener;

import net.assistant.model.Agent;
import net.assistant.model.GameEngine;
import net.assistant.model.RoundEngine;
import net.assistant.model.agent.RandomAgent;
import net.assistant.model.engine.GameEngineImpl;
import net.assistant.model.engine.RoundEngineImpl;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.SpringApplicationRunListener;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;

import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Map;
import java.util.TreeMap;

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

        logAFourPlayerRandomGame();
    }

    private void logAFourPlayerRandomGame() {
        RoundEngine roundEngine = new RoundEngineImpl();
        GameEngine gameEngine = new GameEngineImpl(roundEngine);

        Map<String, Agent> agents = new TreeMap<>();
        for (int i = 1; i < 5; i++) {
            agents.put(String.format("%1$d0%%", i*2), new RandomAgent(i / 5.0));
        }

        Map<String,Integer> tally = new TreeMap<>();
        final int SAMPLE_SIZE = 1000000;
        for (int game = 0; game < SAMPLE_SIZE; game++) {
            Map<String,Integer> scores = gameEngine.processGame(agents);
            for (Map.Entry<String, Integer> entry : scores.entrySet()) {
                Integer currentTally = tally.get(entry.getKey());
                if (currentTally == null) {
                    currentTally = 0;
                }
                currentTally += entry.getValue();
                tally.put(entry.getKey(), currentTally);
            }
            if (game % 10000 == 0) {
                System.out.println(String.format("Completed game %1$7d/%2$7d", game, SAMPLE_SIZE));
            }
        }

        for (Map.Entry<String,Integer> entry : tally.entrySet()) {
            System.out.println(String.format("%1$s (average score) :- %2$s", entry.getKey(), (1.0*entry.getValue())/SAMPLE_SIZE));
        }
        System.out.flush();
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
