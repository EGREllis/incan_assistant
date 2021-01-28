package net.assistant.model.agent;

import net.assistant.model.Agent;
import net.assistant.model.PlayerDecision;
import net.assistant.model.RoundState;

public class AlwaysWithdrawAgent implements Agent {
    @Override
    public PlayerDecision decide(RoundState roundState) {
        return PlayerDecision.WITHDRAW;
    }
}
