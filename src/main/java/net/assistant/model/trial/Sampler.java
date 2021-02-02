package net.assistant.model.trial;

import net.assistant.model.Agent;
import net.assistant.model.GameEngine;
import net.assistant.model.RoundEngine;
import net.assistant.model.engine.GameEngineImpl;
import net.assistant.model.engine.RoundEngineImpl;

import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.Callable;

public class Sampler implements Callable<Map<String,Double>> {
    private final AgentFactory agentFactory;
    private final int sampleSize;

    public Sampler(AgentFactory agentFactory, int sampleSize) {
        this.agentFactory = agentFactory;
        this.sampleSize = sampleSize;
    }

    @Override
    public Map<String, Double> call() throws Exception {
        RoundEngine roundEngine = new RoundEngineImpl();
        GameEngine gameEngine = new GameEngineImpl(roundEngine);

        Map<String, Agent> agents = agentFactory.newAgents();

        Map<String,Integer> tally = new TreeMap<>();
        for (int game = 0; game < sampleSize; game++) {
            Map<String,Integer> scores = gameEngine.processGame(agents);
            for (Map.Entry<String, Integer> entry : scores.entrySet()) {
                Integer currentTally = tally.get(entry.getKey());
                if (currentTally == null) {
                    currentTally = 0;
                }
                currentTally += entry.getValue();
                tally.put(entry.getKey(), currentTally);
            }
            if (game % 100000 == 0) {
                System.out.println(String.format("Completed game %1$7d/%2$7d", game, sampleSize));
            }
        }

        Map<String, Double> averageScore = new TreeMap<>();
        for (Map.Entry<String, Integer> entry : tally.entrySet()) {
            averageScore.put(entry.getKey(), entry.getValue() * 1.0 / sampleSize);
        }
        return averageScore;
    }
}
