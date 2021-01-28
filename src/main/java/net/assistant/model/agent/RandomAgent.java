package net.assistant.model.agent;

import net.assistant.model.Agent;
import net.assistant.model.PlayerDecision;
import net.assistant.model.RoundState;

public class RandomAgent implements Agent {
    private final double threshold;

    public RandomAgent(double threshold) {
        this.threshold = threshold;
    }

    @Override
    public PlayerDecision decide(RoundState roundState) {
        PlayerDecision decision;
        if (Math.random() <= threshold) {
            decision = PlayerDecision.EXCAVATE;
        } else {
            decision = PlayerDecision.WITHDRAW;
        }
        return decision;
    }

    @Override
    public String toString() {
        return String.format("Random agent that excavates with %1$f probability.", threshold);
    }
}
