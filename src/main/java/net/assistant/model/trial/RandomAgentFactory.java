package net.assistant.model.trial;

import net.assistant.model.Agent;
import net.assistant.model.agent.RandomAgent;

import java.util.Map;
import java.util.TreeMap;

public class RandomAgentFactory implements AgentFactory {
    private final double[] thresholds;

    public RandomAgentFactory(double ... thresholds) {
        this.thresholds = thresholds;
    }

    @Override
    public Map<String, Agent> newAgents() {
        Map<String, Agent> agents = new TreeMap<>();
        for (int i = 0; i < thresholds.length; i++) {
            double threshold = thresholds[i];
            agents.put(String.format("%1$f%%", 100 * threshold), new RandomAgent(threshold));
        }
        return agents;
    }
}
