package net.assistant.model;

import java.util.Set;

public class Deck {
    private static final int[] cards = new int [] { 1, 2, 3, 4, 5,
                                                    5, 7, 7, 9, 11,
                                                    11, 13, 14, 15, 17,
                                                    -1, -1, -1, -2, -2,
                                                    -2, -3, -3, -3, -4,
                                                    -4, -4, -5, -5, -5,
                                                    21, 22, 23, 24, 25};
    private int[] deck;
    private int drawn = 0;

    public Deck(int round, Set<Integer> seen) {
        int size = 30 + round - seen.size();
        deck = new int[size];
        System.arraycopy(cards, 0, deck, 0, size);

        //TODO: Unit test this
        if (seen.size() > 0) {
            int deckIndex = 30;
            for (int cardIndex = 0; cardIndex < cards.length && deckIndex < deck.length; cardIndex++) {
                if (!seen.contains(cards[30 + cardIndex])) {
                    deck[deckIndex++] = cards[30 + cardIndex];
                }
            }
        }
        shuffle();
    }

    public boolean isGem(int value) {
        return value > 0 && value < 20;
    }

    public boolean isArtifact(int value) {
        return value > 20;
    }

    public boolean isHazard(int value) {
        return value < 0;
    }

    public int getGemValue(int value) {
        if (!isGem(value)) {
            throw new RuntimeException(
                    String.format("This card is not a gem! %1$s", (isArtifact(value)) ? "It is an artifact!" : ( (isHazard(value) ? "It is a hazard!" : "It is not a card!"))));
        }
        return value;
    }

    private void shuffle() {
        for (int i = 0; i < deck.length; i++) {
            int deckIndex1 = getRandomDeckIndex();
            int deckIndex2 = getDistinctRandomDeckIndex(deckIndex1);

            int temp = deck[deckIndex1];
            deck[deckIndex1] = deck[deckIndex2];
            deck[deckIndex2] = temp;
        }
    }

    private int getRandomDeckIndex() {
        return (int)(Math.random() * deck.length);
    }

    private int getDistinctRandomDeckIndex(int other) {
        int index = getRandomDeckIndex();
        while (index == other) {
            index = getRandomDeckIndex();
        }
        return index;
    }

    public int getSize() {
        return deck.length;
    }

    public int drawCard() {
        return deck[deck.length - 1 - drawn++];
    }

    @Override
    public String toString() {
        StringBuilder message = new StringBuilder();
        message.append("drawn: ").append(drawn).append(" deck: ");
        for (int i = deck.length-1-drawn; i >= 0; i--) {
            if (i < deck.length-1-drawn) {
                message.append(',');
            }
            message.append(deck[i]);
        }
        return message.toString();
    }
}
