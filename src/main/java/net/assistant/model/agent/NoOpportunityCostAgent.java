package net.assistant.model.agent;

import net.assistant.model.*;

import java.util.List;

/**
 * This agent always excavates when there is no risk of failure
 */
public class NoOpportunityCostAgent implements ConditionalAgent {
    @Override
    public PlayerDecision decide(RoundState roundState) {
        return PlayerDecision.EXCAVATE;
    }

    @Override
    public boolean isApplicable(RoundState state) {
        boolean isApplicable = true;
        Deck deck = state.getDeck();
        List<Integer> visibleCards = state.getVisibleCards();
        for (int i = visibleCards.size()-1; i >= 0; i--) {
            int card = visibleCards.get(i);
            if (CardType.HAZARD.equals(deck.getCardType(card))) {
                isApplicable = false;
            }
        }
        return isApplicable;
    }
}
