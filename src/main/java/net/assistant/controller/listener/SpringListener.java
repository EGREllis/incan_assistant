package net.assistant.controller.listener;

import net.assistant.model.Agent;
import net.assistant.model.GameEngine;
import net.assistant.model.RoundEngine;
import net.assistant.model.engine.GameEngineImpl;
import net.assistant.model.engine.RoundEngineImpl;
import net.assistant.model.trial.AgentFactory;
import net.assistant.model.trial.RandomAgentFactory;
import net.assistant.model.trial.Sampler;
import net.assistant.model.trial.UtilityAgentFactory;
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
import java.util.concurrent.Callable;

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

        AgentFactory pairFactory = new RandomAgentFactory(0.6, 0.8);
        AgentFactory randomFactory = new RandomAgentFactory(0.0, 0.2, 0.4, 0.6, 0.8, 1.0);
        AgentFactory opporunityFactory = new UtilityAgentFactory();
        Callable<Map<String, Double>> pairSampler = new Sampler(pairFactory, 1000000);
        Callable<Map<String, Double>> sampler = new Sampler(randomFactory, 1000000);
        Callable<Map<String, Double>> opportunitySampler = new Sampler(opporunityFactory, 1000000);

        Map<String,Double> pairScores;
        Map<String,Double> averageScores;
        Map<String,Double> opportunityScores;
        try {
            pairScores = pairSampler.call();
            averageScores = sampler.call();
            opportunityScores = opportunitySampler.call();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        logAverageScores(pairScores);
        logAverageScores(averageScores);
        logAverageScores(opportunityScores);
    }


    private void logAverageScores(Map<String, Double> averageScore) {
        for (Map.Entry<String,Double> entry : averageScore.entrySet()) {
            System.out.println(String.format("%1$s (average score) :- %2$s", entry.getKey(), entry.getValue()));
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
