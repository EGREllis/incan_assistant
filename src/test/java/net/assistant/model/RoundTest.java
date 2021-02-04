package net.assistant.model;

import net.assistant.model.engine.RoundEngineImpl;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.*;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.mockito.Mockito.*;
import static net.assistant.model.DeckImpl.ARTIFACT_ONE;
import static net.assistant.model.DeckImpl.HAZARD_ONE;

public class RoundTest {
    private Deck deck;
    private PlayerState player1;
    private PlayerState player2;
    private Map<String,PlayerState> players;
    private Agent agent1;
    private Agent agent2;
    private Map<String,Agent> agents;
    private List<Integer> cardsToRemove;
    private List<Integer> artifactOrder;
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
        cardsToRemove = new ArrayList<>();
        artifactOrder = new ArrayList<>();
        firstRound = new RoundState(deck, players, agents, cardsToRemove, artifactOrder);
    }

    @Test
    public void when_roundExecuted_given_twoHazards_then_hazardAddedToCardsToRemove() {
        when(deck.drawCard()).thenReturn(HAZARD_ONE).thenReturn(HAZARD_ONE);
        when(deck.getCardType(HAZARD_ONE)).thenReturn(CardType.HAZARD);
        when(agent1.decide(firstRound)).thenReturn(PlayerDecision.WITHDRAW);
        when(agent2.decide(firstRound)).thenReturn(PlayerDecision.EXCAVATE);

        RoundEngine roundEngine = new RoundEngineImpl();
        RoundState finalState = roundEngine.processRound(firstRound);

        assertThat(finalState.getCardsToRemove().size(), equalTo(1));
        assertThat(finalState.getCardsToRemove().contains(HAZARD_ONE), equalTo(true));
        assertThat(player1.getTemporaryGems(), equalTo(0));
        assertThat(player2.getTemporaryGems(), equalTo(0));
        assertThat(player1.getSavedGems(), equalTo(0));
        assertThat(player2.getSavedGems(), equalTo(0));
        assertThat(player1.getTemporaryArtifacts().size(), equalTo(0));
        assertThat(player2.getTemporaryArtifacts().size(), equalTo(0));
        assertThat(player1.getSavedArtifacts().size(), equalTo(0));
        assertThat(player2.getSavedArtifacts().size(), equalTo(0));
    }

    @Test
    public void when_roundExecuted_given_oneGemTwoHazards_then_withdrawnRetainsGemsAndExcavateDoesNot() {
        when(deck.drawCard()).thenReturn(2).thenReturn(HAZARD_ONE).thenReturn(HAZARD_ONE);
        when(deck.getCardType(2)).thenReturn(CardType.GEM);
        when(deck.getCardType(HAZARD_ONE)).thenReturn(CardType.HAZARD);
        when(deck.getGemValue(2)).thenReturn(2);
        when(agent1.decide(firstRound)).thenReturn(PlayerDecision.EXCAVATE);
        when(agent2.decide(firstRound)).thenReturn(PlayerDecision.WITHDRAW);

        RoundEngine roundEngine = new RoundEngineImpl();
        RoundState finalState = roundEngine.processRound(firstRound);

        assertThat(finalState.getCardsToRemove().size(), equalTo(1));
        assertThat(finalState.getCardsToRemove().contains(HAZARD_ONE), equalTo(true));
        // Player that excavated
        assertThat(player1.getTemporaryGems(), equalTo(0));
        assertThat(player1.getSavedGems(), equalTo(0));
        assertThat(player1.getTemporaryArtifacts().size(), equalTo(0));
        assertThat(player1.getSavedArtifacts().size(), equalTo(0));
        // Player that withdrew
        assertThat(player2.getTemporaryGems(), equalTo(0));
        assertThat(player2.getSavedGems(), equalTo(1));
        assertThat(player2.getTemporaryArtifacts().size(), equalTo(0));
        assertThat(player2.getSavedArtifacts().size(), equalTo(0));
    }

    @Test
    public void when_oneArtifactTwoHazards_given_bothWithdraw_then_neitherGetsArtifact() {
        when(deck.drawCard()).thenReturn(ARTIFACT_ONE).thenReturn(HAZARD_ONE).thenReturn(HAZARD_ONE);
        when(deck.getCardType(ARTIFACT_ONE)).thenReturn(CardType.ARTIFACT);
        when(deck.getCardType(HAZARD_ONE)).thenReturn(CardType.HAZARD);
        when(agent1.decide(firstRound)).thenReturn(PlayerDecision.WITHDRAW);
        when(agent2.decide(firstRound)).thenReturn(PlayerDecision.WITHDRAW);

        RoundEngine roundEngine = new RoundEngineImpl();
        RoundState finalState = roundEngine.processRound(firstRound);

        // Both players left - no cards need to be removed.
        assertThat(finalState.getCardsToRemove().size(), equalTo(0));
        // Both players should get nothing
        assertThat(player1.getTemporaryGems(), equalTo(0));
        assertThat(player1.getSavedGems(), equalTo(0));
        assertThat(player1.getTemporaryArtifacts().size(), equalTo(0));
        assertThat(player1.getSavedArtifacts().size(), equalTo(0));
        assertThat(player2.getTemporaryGems(), equalTo(0));
        assertThat(player2.getSavedGems(), equalTo(0));
        assertThat(player2.getTemporaryArtifacts().size(), equalTo(0));
        assertThat(player2.getSavedArtifacts().size(), equalTo(0));
    }

    @Test
    public void when_oneGemTwoHazards_given_oneWithdrawsThenTheyGetTheRemainder() {
        when(deck.drawCard()).thenReturn(5).thenReturn(HAZARD_ONE).thenReturn(HAZARD_ONE);
        when(deck.getCardType(5)).thenReturn(CardType.GEM);
        when(deck.getGemValue(5)).thenReturn(5);
        when(deck.getCardType(-1)).thenReturn(CardType.HAZARD);
        when(agent1.decide(firstRound)).thenReturn(PlayerDecision.WITHDRAW);
        when(agent2.decide(firstRound)).thenReturn(PlayerDecision.EXCAVATE).thenReturn(PlayerDecision.WITHDRAW);

        RoundEngine roundEngine = new RoundEngineImpl();
        RoundState finalState = roundEngine.processRound(firstRound);

        // Both players withdrew.
        assertThat(finalState.getCardsToRemove().size(), equalTo(0));
        // First player should get the remainder from
        assertThat(player1.getTemporaryGems(), equalTo(0));
        assertThat(player1.getSavedGems(), equalTo(3));
        assertThat(player1.getTemporaryArtifacts().size(), equalTo(0));
        assertThat(player1.getSavedArtifacts().size(), equalTo(0));
        assertThat(player2.getTemporaryGems(), equalTo(0));
        assertThat(player2.getSavedGems(), equalTo(2));
        assertThat(player2.getTemporaryArtifacts().size(), equalTo(0));
        assertThat(player2.getSavedArtifacts().size(), equalTo(0));
    }

    @Test
    public void when_oneArtifactTwoHazards_given_playersWithdrawAtDifferentTimes_then_firstGetsArtifact() {
        when(deck.drawCard()).thenReturn(ARTIFACT_ONE).thenReturn(HAZARD_ONE).thenReturn(HAZARD_ONE);
        when(deck.getCardType(ARTIFACT_ONE)).thenReturn(CardType.ARTIFACT);
        when(deck.getCardType(HAZARD_ONE)).thenReturn(CardType.HAZARD);
        when(agent1.decide(firstRound)).thenReturn(PlayerDecision.WITHDRAW);
        when(agent2.decide(firstRound)).thenReturn(PlayerDecision.EXCAVATE).thenReturn(PlayerDecision.WITHDRAW);

        RoundEngine roundEngine = new RoundEngineImpl();
        RoundState finalState = roundEngine.processRound(firstRound);

        // Both players withdrew, however and artifact was obtained, which needs to be removed from the next round
        assertThat(finalState.getCardsToRemove().size(), equalTo(1));
        assertThat(finalState.getCardsToRemove().contains(ARTIFACT_ONE), equalTo(true));
        // First player withdrew first and keeps the artifact
        assertThat(player1.getTemporaryGems(), equalTo(0));
        assertThat(player1.getSavedGems(), equalTo(0));
        assertThat(player1.getTemporaryArtifacts().size(), equalTo(0));
        assertThat(player1.getSavedArtifacts().size(), equalTo(1));
        assertThat(player1.getSavedArtifacts().contains(ARTIFACT_ONE), equalTo(true));
        // Second player withdrew second and gets nothing
        assertThat(player2.getTemporaryGems(), equalTo(0));
        assertThat(player2.getSavedGems(), equalTo(0));
        assertThat(player2.getSavedArtifacts().size(), equalTo(0));
        assertThat(player2.getTemporaryArtifacts().size(), equalTo(0));
    }

    @Test
    public void when_threePlayers_given_twoWithdrawSameTime_then_theySplitRemainingGems() {
        // Add the third player
        PlayerState player3 = new PlayerState("Player3");
        players.put("Player3", player3);
        Agent agent3 = Mockito.mock(Agent.class);
        agents.put("Player3", agent3);

        //Test setup
        when(deck.drawCard()).thenReturn(5).thenReturn(10).thenReturn(HAZARD_ONE).thenReturn(HAZARD_ONE);
        when(deck.getGemValue(5)).thenReturn(5);
        when(deck.getGemValue(10)).thenReturn(10);
        when(deck.getCardType(5)).thenReturn(CardType.GEM);
        when(deck.getCardType(10)).thenReturn(CardType.GEM);
        when(deck.getCardType(HAZARD_ONE)).thenReturn(CardType.HAZARD);
        when(agent1.decide(firstRound)).thenReturn(PlayerDecision.EXCAVATE).thenReturn(PlayerDecision.WITHDRAW);
        when(agent2.decide(firstRound)).thenReturn(PlayerDecision.EXCAVATE).thenReturn(PlayerDecision.WITHDRAW);
        when(agent3.decide(firstRound)).thenReturn(PlayerDecision.EXCAVATE).thenReturn(PlayerDecision.EXCAVATE).thenReturn(PlayerDecision.WITHDRAW);

        RoundEngine roundEngine = new RoundEngineImpl();
        RoundState finalState = roundEngine.processRound(firstRound);

        // All players withdrew, no hazard to remove
        assertThat(finalState.getCardsToRemove().size(), equalTo(0));
        // Player 1 and Player 2 should get their share, and split the remainders when they left
        assertThat(player1.getTemporaryGems(), equalTo(0));
        assertThat(player1.getSavedGems(), equalTo(5)); // Gems: 1, 3, Withdraw: 1, 0
        assertThat(player1.getTemporaryArtifacts().size(), equalTo(0));
        assertThat(player1.getSavedArtifacts().size(), equalTo(0));
        assertThat(player2.getTemporaryGems(), equalTo(0));
        assertThat(player2.getSavedGems(), equalTo(5)); // Gems: 1, 3, Withdraw: 1, 0
        assertThat(player2.getTemporaryArtifacts().size(), equalTo(0));
        assertThat(player2.getSavedArtifacts().size(), equalTo(0));
        // Player 3 retreats last, gets 1 remaining gem from card 2 (which the others blocked each other)
        assertThat(player3.getTemporaryGems(), equalTo(0));
        assertThat(player3.getSavedGems(), equalTo(5));
        assertThat(player3.getTemporaryArtifacts().size(), equalTo(0));
        assertThat(player3.getSavedArtifacts().size(), equalTo(0));
    }
}
