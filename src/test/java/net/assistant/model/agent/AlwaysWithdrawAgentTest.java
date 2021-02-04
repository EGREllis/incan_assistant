package net.assistant.model.agent;

import net.assistant.model.*;
import org.junit.Test;

import java.util.*;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class AlwaysWithdrawAgentTest {
    private static final int SAMPLE_SIZE = 1000;

    @Test
    public void when_askedToDecide_then_alwaysServesAndAlwaysWithdraws() {
        List<Integer> cardsToRemove = new ArrayList<>();
        ConditionalAgent conditionalAgent = new AlwaysWithdrawAgent();
        Deck deck = new DeckImpl(1, cardsToRemove);
        Map<String, PlayerState> players = new TreeMap<>();
        Map<String, Agent> agents = new TreeMap<>();
        List<Integer> artifactOrder = new ArrayList<>();

        RoundState round = new RoundState(deck, players, agents, cardsToRemove, artifactOrder);

        for (int i = 0; i < 1000; i++) {
            assertThat(conditionalAgent.isApplicable(round), equalTo(true));
            assertThat(conditionalAgent.decide(round), equalTo(PlayerDecision.WITHDRAW));
        }
    }
}
