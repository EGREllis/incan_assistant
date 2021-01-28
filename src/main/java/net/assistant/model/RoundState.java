package net.assistant.model;

import java.util.Map;
import java.util.Set;

public class RoundState {
    private final Deck deck;
    private final Map<String, PlayerState> players;
    private final Set<Integer> cardsToRemove;

    public RoundState(Deck deck, Map<String, PlayerState> players, Set<Integer> cardsToRemove) {
        this.deck = deck;
        this.players = players;
        this.cardsToRemove = cardsToRemove;
    }

    public Deck getDeck() {
        return deck;
    }

    public Map<String, PlayerState> getPlayers() {
        return players;
    }

    public Set<Integer> getCardsToRemove() {
        return cardsToRemove;
    }
}
