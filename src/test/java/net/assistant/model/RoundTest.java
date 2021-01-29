package net.assistant.model;

import net.assistant.model.engine.RoundEngineImpl;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.mockito.Mockito.*;

public class RoundTest {
    private Deck deck;
    private PlayerState player1;
    private PlayerState player2;
    private Map<String,PlayerState> players;
    private Agent agent1;
    private Agent agent2;
    private Map<String,Agent> agents;
    private Set<Integer> cardsToRemove;
    private RoundState firstRound;

    @Before
    public void setup() {
        deck = Mockito.mock(Deck.class);
        player1 = new PlayerState("Player1");
        player2 = new PlayerState("Player2");
        players = new TreeMap<>();
        players.put(player1.getName(), player1);
        players.put(player2.getName(), player2);
        agent1 = Mockito.mock(Agent.class);
        agent2 = Mockito.mock(Agent.class);
        agents = new TreeMap<>();
        agents.put("Player1", agent1);
        agents.put("Player2", agent2);
        cardsToRemove = new TreeSet<>();
        firstRound = new RoundState(deck, players, agents, cardsToRemove);
    }

    @Test
    public void when_roundExecuted_given_twoHazards_then_hazardAddedToCardsToRemove() {
        when(deck.drawCard()).thenReturn(-1).thenReturn(-1);
        when(deck.getCardType(-1)).thenReturn(CardType.HAZARD);
        when(agent1.decide(firstRound)).thenReturn(PlayerDecision.WITHDRAW);
        when(agent2.decide(firstRound)).thenReturn(PlayerDecision.EXCAVATE);

        RoundEngine roundEngine = new RoundEngineImpl();
        RoundState finalState = roundEngine.processRound(firstRound);

        assertThat(finalState.getCardsToRemove().size(), equalTo(1));
        assertThat(finalState.getCardsToRemove().contains(-1), equalTo(true));
        assertThat(player1.getTemporaryGems(), equalTo(0));
        assertThat(player2.getTemporaryGems(), equalTo(0));
        assertThat(player1.getSavedGems(), equalTo(0));
        assertThat(player2.getSavedGems(), equalTo(0));
        assertThat(player1.getTemporaryArtifacts().size(), equalTo(0));
        assertThat(player2.getTemporaryArtifacts().size(), equalTo(0));
        assertThat(player1.getSavedArtifacts().size(), equalTo(0));
        assertThat(player2.getSavedArtifacts().size(), equalTo(0));
    }


}
