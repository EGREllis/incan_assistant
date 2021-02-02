package net.assistant.model.trial;

import net.assistant.model.Agent;

import java.util.Map;

public interface AgentFactory {
    Map<String, Agent> newAgents();
}
