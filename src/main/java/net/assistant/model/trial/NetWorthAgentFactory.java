package net.assistant.model.trial;

import net.assistant.model.Agent;
import net.assistant.model.agent.DoubleMetricAgent;
import net.assistant.model.calc.PlayerNetWorthMetric;
import net.assistant.model.calc.WithdrawValueMetric;

import java.util.Map;
import java.util.TreeMap;

public class NetWorthAgentFactory implements AgentFactory {

    @Override
    public Map<String, Agent> newAgents() {
        Map<String, Agent> agents = new TreeMap<>();
        String playerName = "NetWorth/Withdraw 0.5";
        agents.put(playerName, new DoubleMetricAgent(0.5,
                new PlayerNetWorthMetric(playerName),
                new WithdrawValueMetric()));
        playerName = "NetWorth/Withdraw 2.0";
        agents.put(playerName, new DoubleMetricAgent(2.0,
                new PlayerNetWorthMetric(playerName),
                new WithdrawValueMetric()));
        playerName = "NetWorth/Withdraw 1.0";
        agents.put(playerName, new DoubleMetricAgent(1.0,
                new PlayerNetWorthMetric(playerName),
                new WithdrawValueMetric()));
        return agents;
    }
}
