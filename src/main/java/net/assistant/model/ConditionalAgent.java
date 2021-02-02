package net.assistant.model;

public interface ConditionalAgent extends Agent {
    boolean isApplicable(RoundState state);
}
