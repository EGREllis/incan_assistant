package net.assistant.model.agent;

import net.assistant.model.*;

import java.util.*;

public class RationalFailureAgent implements ConditionalAgent {
    private final double threshold;

    public RationalFailureAgent(double threshold) {
        this.threshold = threshold;
    }

    @Override
    public PlayerDecision decide(RoundState roundState) {
        Set<Integer> visibleHazards = filterVisibleHazards(roundState);
        double probFail = calculateProbabilityOfFailure(visibleHazards, roundState);
        PlayerDecision decision;
        if (probFail < threshold) {
            decision = PlayerDecision.EXCAVATE;
        } else {
            decision = PlayerDecision.WITHDRAW;
        }
        return decision;
    }

    @Override
    public boolean isApplicable(RoundState state) {
        return true;
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
