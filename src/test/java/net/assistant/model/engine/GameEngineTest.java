package net.assistant.model.engine;

import net.assistant.model.*;
import org.junit.Test;

import java.util.*;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.CoreMatchers.equalTo;

public class GameEngineTest {
    @Test
    public void when_gameEngineCalculatesScores_given_aWideDistributionOfPlayers_then_calculatesAsExpected() {
        Map<String, PlayerState> players = new TreeMap<>();
        RoundEngine roundEngine = new RoundEngineImpl();
        GameEngineImpl gameEngine = new GameEngineImpl(roundEngine);

        Set<Integer> cardsToRemove = new TreeSet<>();
        Deck deck = new DeckImpl(1, cardsToRemove);

        PlayerState noGemsNoArtifact = new PlayerState("noGemsNoArtifact");
        PlayerState oneGemOnly = new PlayerState("oneGemOnly");
        oneGemOnly.collectGems(1);
        oneGemOnly.successfulWithdraw();
        PlayerState noGemOneArtifact = new PlayerState("noGemOneArtifact");
        noGemOneArtifact.collectArtifact(DeckImpl.ARTIFACT_ONE);
        noGemOneArtifact.successfulWithdraw();
        PlayerState noGemsFourthArtifact = new PlayerState("noGemsFourthArtifact");
        noGemsFourthArtifact.collectArtifact(DeckImpl.ARTIFACT_TWO);
        noGemsFourthArtifact.successfulWithdraw();
        for (PlayerState player : Arrays.asList(noGemsNoArtifact, oneGemOnly, noGemOneArtifact, noGemsFourthArtifact)) {
            players.put(player.getName(), player);
        }

        Map<String, Agent> agents = new TreeMap<>();
        List<Integer> artifactOrder = new ArrayList<>();
        artifactOrder.add(DeckImpl.ARTIFACT_ONE);
        artifactOrder.add(DeckImpl.ARTIFACT_FIVE);
        artifactOrder.add(DeckImpl.ARTIFACT_FOUR);
        artifactOrder.add(DeckImpl.ARTIFACT_THREE);
        artifactOrder.add(DeckImpl.ARTIFACT_TWO);

        RoundState roundState = new RoundState(deck, players, agents, cardsToRemove, artifactOrder);

        Map<String, Integer> scores = gameEngine.calculateScores(roundState);
        assertThat(scores.size(), equalTo(4));
        assertThat(scores.get("noGemsNoArtifact"), equalTo(0));
        assertThat(scores.get("oneGemOnly"), equalTo(1));
        assertThat(scores.get("noGemOneArtifact"), equalTo(5));
        assertThat(scores.get("noGemsFourthArtifact"), equalTo(10));
    }
}
