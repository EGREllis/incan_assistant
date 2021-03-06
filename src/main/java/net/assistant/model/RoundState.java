package net.assistant.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

//TODO: Consider making this class immutable - or at least convertable to/from an immutable representation
public class RoundState {
    private Deck deck;
    private final Map<String, PlayerState> players;
    private final Map<String, Agent> agents;
    private final List<Integer> cardsToRemove;
    private final List<Integer> artifactOrder;
    private final List<Integer> visibleCards;
    private final List<Integer> remainingGems;

    public RoundState(Deck deck, Map<String, PlayerState> players, Map<String, Agent> agents, List<Integer> cardsToRemove, List<Integer> artifactOrder) {
        this.deck = deck;
        this.agents = agents;
        this.players = players;
        this.cardsToRemove = cardsToRemove;
        this.artifactOrder = artifactOrder;
        this.visibleCards = new ArrayList<>(deck.getSize());
        this.remainingGems = new ArrayList<>(deck.getSize());
    }

    public void newRound(int round) {
        this.deck = new DeckImpl(round, cardsToRemove);
    }

    public Deck getDeck() {
        return deck;
    }

    public Map<String,Agent> getAgent() {
        return agents;
    }

    public Map<String, PlayerState> getPlayers() {
        return players;
    }

    public List<Integer> getCardsToRemove() {
        return cardsToRemove;
    }

    public List<Integer> getArtifactOrder() {
        return artifactOrder;
    }

    public List<Integer> getVisibleCards() {return visibleCards;}

    public List<Integer> getRemainingGems() {return remainingGems;}

    public void appendCard(int card, int remainingGems) {
        this.visibleCards.add(card);
        this.remainingGems.add(remainingGems);
    }

    public void removeCard(int index) {
        this.visibleCards.remove(index);
        this.remainingGems.remove(index);
    }
}
