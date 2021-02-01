package net.assistant.model;

import java.util.Set;

public class DeckImpl implements Deck {
    public static final int HAZARD_ONE = -1;
    public static final int HAZARD_TWO = -2;
    public static final int HAZARD_THREE = -3;
    public static final int HAZARD_FOUR = -4;
    public static final int HAZARD_FIVE = -5;
    public static final int ARTIFACT_ONE = 21;
    public static final int ARTIFACT_TWO = 22;
    public static final int ARTIFACT_THREE = 23;
    public static final int ARTIFACT_FOUR = 24;
    public static final int ARTIFACT_FIVE = 25;
    private static final int POST_GEM_AND_HAZARD_DECK_INDEX = 30;
    private static final int[] cards = new int [] { 1, 2, 3, 4, 5,
                                                    5, 7, 7, 9, 11,
                                                    11, 13, 14, 15, 17,
                                                    HAZARD_ONE, HAZARD_ONE, HAZARD_ONE,
                                                    HAZARD_TWO, HAZARD_TWO, HAZARD_TWO,
                                                    HAZARD_THREE, HAZARD_THREE, HAZARD_THREE,
                                                    HAZARD_FOUR, HAZARD_FOUR, HAZARD_FOUR,
                                                    HAZARD_FIVE, HAZARD_FIVE, HAZARD_FIVE,
                                                    ARTIFACT_ONE, ARTIFACT_TWO, ARTIFACT_THREE,
                                                    ARTIFACT_FOUR, ARTIFACT_FIVE};
    private int[] deck;
    private int drawn = 0;

    public DeckImpl(int round, Set<Integer> seen) {
        int size = POST_GEM_AND_HAZARD_DECK_INDEX + round - seen.size();
        deck = new int[size];
        System.arraycopy(cards, 0, deck, 0, size);

        //TODO: Unit test this
        if (seen.size() > 0) {
            int deckIndex = POST_GEM_AND_HAZARD_DECK_INDEX;
            for (int cardIndex = 0; cardIndex < cards.length && deckIndex < deck.length; cardIndex++) {
                if (!seen.contains(cards[30 + cardIndex])) {
                    deck[deckIndex++] = cards[30 + cardIndex];
                }
            }
        }
        shuffle();
    }

    public int getDrawn() {
        return drawn;
    }

    public CardType getCardType(int value) {
        CardType result;
        if (isGem(value)) {
            result = CardType.GEM;
        } else if (isArtifact(value)) {
            result = CardType.ARTIFACT;
        } else if (isHazard(value)) {
            result = CardType.HAZARD;
        } else {
            throw new IllegalStateException("This branch should never be reached.");
        }
        return result;
    }

    private boolean isGem(int value) {
        return value > 0 && value < 20;
    }

    private boolean isArtifact(int value) {
        return value > 20;
    }

    private boolean isHazard(int value) {
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
