package net.assistant.model;

public interface Agent {
    PlayerDecision decide(RoundState roundState);
}
