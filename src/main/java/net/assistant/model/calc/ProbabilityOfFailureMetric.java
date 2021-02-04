package net.assistant.model.calc;

import net.assistant.model.CardType;
import net.assistant.model.Deck;
import net.assistant.model.DeckImpl;
import net.assistant.model.RoundState;

import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

public class ProbabilityOfFailureMetric implements Metric {
    @Override
    public double calculate(RoundState round) {
        Set<Integer> visibleHazards = filterVisibleHazards(round);
        return calculateProbabilityOfFailure(visibleHazards, round);
    }

    private Set<Integer> filterVisibleHazards(RoundState round) {
        Set<Integer> hazards = new TreeSet<>();
        Deck deck = round.getDeck();
        for (int visible : round.getVisibleCards()) {
            if (CardType.HAZARD.equals(deck.getCardType(visible))) {
                hazards.add(visible);
            }
        }
        return hazards;
    }

    private double calculateProbabilityOfFailure(Set<Integer> visibleHazards, RoundState round) {
        double pFail = 0.0;
        if (visibleHazards.size() == 0) {
            pFail = 0.0;
        } else {
            int failCards = 0;
            Map<Integer,Integer> removedCardsTally = tallyCardsToRemove(round);
            for (int visibleHazard : DeckImpl.HAZARDS) {
                failCards += (2 - removedCardsTally.get(visibleHazard));
            }
            Deck deck = round.getDeck();
            int drawableCards = deck.getSize() - deck.getDrawn();
            pFail = (failCards * 1.0) / drawableCards;
        }
        return pFail;
    }

    private Map<Integer, Integer> tallyCardsToRemove(RoundState roundState) {
        Map<Integer, Integer> tally = new TreeMap<>();
        for (int key : DeckImpl.NON_GEMS) {
            tally.put(key, 0);
        }

        for (int removed : roundState.getCardsToRemove()) {
            int thisTally = tally.get(removed);
            tally.put(removed, ++thisTally);
        }

        return tally;
    }
}
