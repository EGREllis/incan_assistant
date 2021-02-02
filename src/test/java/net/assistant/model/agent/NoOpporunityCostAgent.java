package net.assistant.model.agent;

import net.assistant.model.*;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.*;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.CoreMatchers.equalTo;

public class NoOpporunityCostAgent {
    private static final int SAMPLE_SIZE = 1000;
    private RoundState noHazardRound;
    private RoundState oneHazardRound;
    private ConditionalAgent agent;

    @Before
    public void setup() {
        Set<Integer> cardsToRemove = new TreeSet<>();
        Deck deck = new DeckImpl(1, cardsToRemove);
        Map<String, PlayerState> players = new TreeMap<>();
        Map<String, Agent> agents = new TreeMap<>();
        List<Integer> artifactOrder = new ArrayList<>();

        this.noHazardRound = new RoundState(deck, players, agents, cardsToRemove, artifactOrder);
        this.oneHazardRound = new RoundState(deck, players, agents, cardsToRemove, artifactOrder);
        oneHazardRound.appendCard(DeckImpl.HAZARD_ONE, 0);
        this.agent = new NoOpportunityCostAgent();
    }


    @Test
    public void when_askedToDecide_given_noHazards_then_excavates() {
        for (int i = 0; i < SAMPLE_SIZE; i++) {
            assertThat(agent.isApplicable(noHazardRound), equalTo(true));
            assertThat(agent.decide(noHazardRound), equalTo(PlayerDecision.EXCAVATE));
        }
    }

    @Test
    public void when_askedToDecide_given_atLeastOneHazard_then_delegates() {
        for (int i = 0; i < SAMPLE_SIZE; i++) {
            assertThat(agent.isApplicable(oneHazardRound), equalTo(false));
        }
    }
}
