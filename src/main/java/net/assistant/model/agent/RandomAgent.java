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
        double random = Math.random();
        if (random <= threshold) {
            decision = PlayerDecision.EXCAVATE;
        } else {
            decision = PlayerDecision.WITHDRAW;
        }
        System.out.println(String.format("Agent (threshold:%1$s) rolled: %2$f decision: %3$s", threshold, random, decision));
        System.out.flush();
        return decision;
    }

    @Override
    public String toString() {
        return String.format("Random agent that excavates with %1$f probability.", threshold);
    }
}
