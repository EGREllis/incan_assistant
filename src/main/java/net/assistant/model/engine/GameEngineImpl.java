package net.assistant.model.engine;

import net.assistant.model.*;

import java.util.*;

public class GameEngineImpl implements GameEngine {
    public static final int SCORE_FIRST_THREE_ARTIFACTS = 5;
    public static final int SCORE_REMAINING_ARTIFACTS = 10;
    public static final int NUMBER_OF_ROUNDS = 5;
    private final RoundEngine roundEngine;

    public GameEngineImpl(RoundEngine roundEngine) {
        this.roundEngine = roundEngine;
    }

    @Override
    public Map<String, Integer> processGame(Map<String, Agent> agents) {
        RoundState roundState = newRoundState(agents);
        for (int round = 1; round <= NUMBER_OF_ROUNDS; round++) {
            roundState.newRound(round);
            roundState = roundEngine.processRound(roundState);
        }
        return calculateScores(roundState);
    }

    private RoundState newRoundState(Map<String, Agent> agents) {
        Map<String, PlayerState> players = newPlayerStates(agents);
        List<Integer> cardsToRemove = new ArrayList<>();
        List<Integer> artifactOrder = new ArrayList<>();
        return new RoundState(new DeckImpl(1, cardsToRemove),
                players, agents, cardsToRemove, artifactOrder);
    }

    private Map<String, PlayerState> newPlayerStates(Map<String, Agent> agents) {
        Map<String, PlayerState> playerStates = new TreeMap<>();
        for (String playerName : agents.keySet()) {
            playerStates.put(playerName, new PlayerState(playerName));
        }
        return playerStates;
    }

    Map<String,Integer> calculateScores(RoundState roundState) {
        Map<String,Integer> scores = new TreeMap<>();
        for (String player : roundState.getPlayers().keySet()) {
            PlayerState playerState = roundState.getPlayers().get(player);
            int score = playerState.getSavedGems();
            List<Integer> artifactOrder = roundState.getArtifactOrder();
            for (int i = 0; i < artifactOrder.size(); i++) {
                int artifact = artifactOrder.get(i);
                if (playerState.getSavedArtifacts().contains(artifact)) {
                    if (i < 2) {
                        score += SCORE_FIRST_THREE_ARTIFACTS;
                    } else {
                        score += SCORE_REMAINING_ARTIFACTS;
                    }
                }
            }
            scores.put(player, score);
        }
        return scores;
    }
}
