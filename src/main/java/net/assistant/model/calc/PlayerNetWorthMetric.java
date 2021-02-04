package net.assistant.model.calc;

import net.assistant.model.PlayerState;
import net.assistant.model.RoundState;

import java.util.List;

public class PlayerNetWorthMetric implements Metric {
    private final String playerName;

    public PlayerNetWorthMetric(String playerName) {
        this.playerName = playerName;
    }

    @Override
    public double calculate(RoundState round) {
        PlayerState playerState = round.getPlayers().get(playerName);
        int netWorth = playerState.getSavedGems() + playerState.getTemporaryGems();
        List<Integer> artifactOrder = round.getArtifactOrder();
        for (int i = 0; i < artifactOrder.size(); i++) {
            int artifact = artifactOrder.get(i);
            if (    playerState.getSavedArtifacts().contains(artifact) ||
                    playerState.getTemporaryArtifacts().contains(artifact)) {
                if (i <= 2) {
                    netWorth += 5;
                } else {
                    netWorth += 10;
                }
            }
        }
        return netWorth;
    }

    @Override
    public String toString() {
        return String.format("Net-Worth of player %1$s", playerName);
    }
}
