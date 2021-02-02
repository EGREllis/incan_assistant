package net.assistant.model.agent;

import net.assistant.model.Agent;
import net.assistant.model.ConditionalAgent;
import net.assistant.model.PlayerDecision;
import net.assistant.model.RoundState;

import java.util.List;

public class CompositeAgent implements Agent {
    private final List<ConditionalAgent> agents;

    public CompositeAgent(List<ConditionalAgent> conditionalAgents) {
        this.agents = conditionalAgents;
    }

    @Override
    public PlayerDecision decide(RoundState roundState) {
        PlayerDecision decision = null;
        for (ConditionalAgent agent : agents) {
            if (agent.isApplicable(roundState)) {
                decision = agent.decide(roundState);
            }
        }
        if (decision == null) {
            throw new IllegalStateException("This composite agent could not make a decision!");
        }
        return decision;
    }

    @Override
    public String toString() {
        StringBuilder message = new StringBuilder();
        boolean isFirst = true;
        for (Agent agent : agents) {
            if (isFirst) {
                isFirst = false;
            } else {
                message.append(", ");
            }
            message.append(agent.toString());
        }
        return message.toString();
    }
}
