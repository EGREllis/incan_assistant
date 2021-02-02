package net.assistant.model.trial;

import net.assistant.model.Agent;
import net.assistant.model.agent.CompositeAgent;
import net.assistant.model.agent.NoOpportunityCostAgent;
import net.assistant.model.agent.RandomAgent;

import java.util.Arrays;
import java.util.Map;
import java.util.TreeMap;

public class UtilityAgentFactory implements AgentFactory {
    @Override
    public Map<String, Agent> newAgents() {
        Map<String, Agent> agents = new TreeMap<>();
        agents.put("80% excavate", new RandomAgent(0.8));
        agents.put("no Opporunity (%80 excavate)", new CompositeAgent(Arrays.asList(
                new NoOpportunityCostAgent(),
                new RandomAgent(0.8)
        )));
        return agents;
    }
}
