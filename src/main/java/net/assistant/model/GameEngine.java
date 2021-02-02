package net.assistant.model;

import java.util.Map;

public interface GameEngine {
    Map<String,Integer> processGame(Map<String,Agent> agents);
}
