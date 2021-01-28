package net.assistant.model;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.MatcherAssert.assertThat;

import org.junit.Test;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class DeckTest {
    @Test
    public void when_deckCreated_then_deckIsExpectedSize() {
        Deck deckRound1 = new DeckImpl(1, Collections.emptySet());
        Deck deckRound2 = new DeckImpl(2, Collections.emptySet());
        Deck deckRound3 = new DeckImpl(3, Collections.emptySet());
        Deck deckRound4 = new DeckImpl(4, Collections.emptySet());
        Deck deckRound5 = new DeckImpl(5, Collections.emptySet());

        assertThat(deckRound1.getSize(), equalTo(31));
        assertThat(deckRound2.getSize(), equalTo(32));
        assertThat(deckRound3.getSize(), equalTo(33));
        assertThat(deckRound4.getSize(), equalTo(34));
        assertThat(deckRound5.getSize(), equalTo(35));
    }

    @Test
    public void when_deckCreated_then_frequencyCountIsExpected() {
        Map<Integer, Integer> expected = new HashMap<>();
        expected.put(1, 1);
        expected.put(2, 1);
        expected.put(3, 1);
        expected.put(4, 1);
        expected.put(5, 2);
        expected.put(7, 2);
        expected.put(9, 1);
        expected.put(11, 2);
        expected.put(13, 1);
        expected.put(14, 1);
        expected.put(15, 1);
        expected.put(17, 1);
        expected.put(-1, 3);
        expected.put(-2, 3);
        expected.put(-3, 3);
        expected.put(-4, 3);
        expected.put(-5, 3);
        expected.put(21, 1);

        Map<Integer, Integer> freq = new HashMap<>();

        Deck deck = new DeckImpl(1, Collections.emptySet());
        for (int i = 0; i < deck.getSize(); i++) {
            int card = deck.drawCard();
            int cFrequency = 1;
            if (freq.containsKey(card)) {
                cFrequency = freq.get(card) + 1;
            }
            freq.put(card, cFrequency);
        }

        StringBuilder diff = new StringBuilder();
        diff.append(String.format("Expected size: %1$d Actual size: %2$d\n", expected.size(), freq.size()));

        int matches = 0;
        for (Map.Entry<Integer,Integer> expectedEntry : expected.entrySet()) {
            if (freq.containsKey(expectedEntry.getKey()) && freq.get(expectedEntry.getKey()).equals(expectedEntry.getValue())) {
                matches++;
            } else if (freq.containsKey(expectedEntry.getKey())) {
                diff.append(String.format("Expected pair: (%1$d) %2$d Actual: %3$d\n", expectedEntry.getKey(), expectedEntry.getValue(), freq.get(expectedEntry.getKey())));
            } else {
                diff.append(String.format("Expected contains pair (%1$d) %2$d ; Actual does not have this key\n", expectedEntry.getKey(), expectedEntry.getValue()));
            }
        }

        for (Map.Entry<Integer, Integer> actualEntry : freq.entrySet()) {
            if (expected.containsKey(actualEntry.getKey()) && actualEntry.getValue().equals(freq.get(actualEntry.getKey()))) {
                matches++;
            } else if (expected.containsKey(actualEntry.getKey())) {
                diff.append(String.format("Actual pair: (%1$d) %2$d Expected: %3$d\n", actualEntry.getKey(), actualEntry.getValue(), expected.get(actualEntry.getKey())));
            } else {
                diff.append(String.format("Actual contains pair (%1$d) %2$d ; Expected does not have this key\n", actualEntry.getKey(), actualEntry.getValue()));
            }
        }
        diff.append(String.format("Matches: %1$d", matches));

        assert matches == expected.size() * 2 : diff.toString();
        assert matches == freq.size() * 2 : diff.toString();
    }

    @Test
    public void when_deckInspected_givenRoundZeroNoArtifactPicked_then_classificationMatchesExpectations() {
        Deck deck = new DeckImpl(1, Collections.emptySet());

        int nGemCard = 0;
        int nHazardCard = 0;
        int nArtifact = 0;

        int size = deck.getSize();
        for (int i = 0; i < size; i++) {
            int card = deck.drawCard();
            switch (deck.getCardType(card)) {
                case GEM:
                    nGemCard++;
                    break;
                case HAZARD:
                    nHazardCard++;
                    break;
                case ARTIFACT:
                    nArtifact++;
                    break;
                default:
                    throw new IllegalArgumentException("We should never reach this.");
            }
        }

        assertThat(size, equalTo(31));
        assertThat(nHazardCard, equalTo(15));
        assertThat(nGemCard, equalTo(15));
        assertThat(nArtifact, equalTo(1));
    }

    @Test
    public void when_deckSummed_given_roundOne_then_sumMatchesExpectations() {
        Deck deck = new DeckImpl(1, Collections.emptySet());

        int size = deck.getSize();
        int gemSum = 0;
        for (int i = 0; i < size; i++) {
            int card = deck.drawCard();
            if (CardType.GEM.equals(deck.getCardType(card))) {
                gemSum += deck.getGemValue(card);
            }
        }

        assertThat(gemSum, equalTo(124));
    }

    @Test
    public void when_deckCreated_given_round2_then_twoArtifactsPresent() {
        Deck deck = new DeckImpl(2, Collections.emptySet());

        int nArtifact = 0;
        for (int i = 0; i < deck.getSize(); i++) {
            int card = deck.drawCard();
            if (CardType.ARTIFACT.equals(deck.getCardType(card))) {
                nArtifact++;
            }
        }

        assertThat(nArtifact, equalTo(2));
    }

    @Test
    public void when_deckCreated_given_round2And1ArtifactTaken_then_oneDistinctArtifactPresent() {
        DeckImpl deck = new DeckImpl(2, Collections.singleton(21));

        int nArtifact = 0;
        for (int i = 0; i < deck.getSize(); i++) {
            int card = deck.drawCard();
            if (CardType.ARTIFACT.equals(deck.getCardType(card))) {
                nArtifact++;
                assertThat(card, not(equalTo(21)));
            }
        }

        assertThat(nArtifact, equalTo(1));
    }
}
