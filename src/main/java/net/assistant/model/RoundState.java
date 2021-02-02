package net.assistant.model;

import java.util.List;
import java.util.Map;
import java.util.Set;

//TODO: Consider making this class immutable - or at least convertable to/from an immutable representation
public class RoundState {
    private Deck deck;
    private final Map<String, PlayerState> players;
    private final Map<String, Agent> agents;
    private final Set<Integer> cardsToRemove;
    private final List<Integer> artifactOrder;

    public RoundState(Deck deck, Map<String, PlayerState> players, Map<String, Agent> agents, Set<Integer> cardsToRemove, List<Integer> artifactOrder) {
        this.deck = deck;
        this.agents = agents;
        this.players = players;
        this.cardsToRemove = cardsToRemove;
        this.artifactOrder = artifactOrder;
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

    public Set<Integer> getCardsToRemove() {
        return cardsToRemove;
    }

    public List<Integer> getArtifactOrder() {
        return artifactOrder;
    }
}
