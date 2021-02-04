package net.assistant.model.agent;

import net.assistant.model.ConditionalAgent;
import net.assistant.model.PlayerDecision;
import net.assistant.model.RoundState;

public class AlwaysAgent implements ConditionalAgent {
    private final PlayerDecision decision;

    public AlwaysAgent(PlayerDecision decision) {
        this.decision = decision;
    }

    @Override
    public PlayerDecision decide(RoundState roundState) {
        return decision;
    }

    @Override
    public boolean isApplicable(RoundState state) {
        return true;
    }

    @Override
    public String toString() {
        return String.format("Always %1$s", decision);
    }
}
