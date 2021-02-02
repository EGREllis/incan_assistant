package net.assistant.model.agent;

import net.assistant.model.ConditionalAgent;
import net.assistant.model.PlayerDecision;
import net.assistant.model.RoundState;

public class AlwaysExcavateAgent implements ConditionalAgent {
    @Override
    public PlayerDecision decide(RoundState roundState) {
        return PlayerDecision.EXCAVATE;
    }

    @Override
    public boolean isApplicable(RoundState state) {
        return true;
    }

    @Override
    public String toString() {
        return "AlwaysExcavate";
    }
}
