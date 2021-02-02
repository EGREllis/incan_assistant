package net.assistant.model.trial;

import net.assistant.model.Agent;
import net.assistant.model.agent.RandomAgent;

import java.util.Map;
import java.util.TreeMap;

public class RandomAgentFactory implements AgentFactory {
    @Override
    public Map<String, Agent> newAgents() {
        Map<String, Agent> agents = new TreeMap<>();
        for (int i = 1; i < 5; i++) {
            agents.put(String.format("%1$d0%%", i*2), new RandomAgent(i/5.0));
        }
        return agents;
    }
}
