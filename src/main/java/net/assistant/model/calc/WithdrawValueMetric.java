package net.assistant.model.calc;

import net.assistant.model.CardType;
import net.assistant.model.Deck;
import net.assistant.model.RoundState;

public class WithdrawValueMetric implements Metric {
    @Override
    public double calculate(RoundState round) {
        Deck deck = round.getDeck();

        int gemsRemaining = 0;
        int visibleArtifacts = 0;
        for (int i = 0; i < round.getVisibleCards().size(); i++) {
            gemsRemaining += round.getRemainingGems().get(i);
            int card = round.getVisibleCards().get(i);
            if (CardType.ARTIFACT.equals(deck.getCardType(card))) {
                visibleArtifacts++;
            }
        }

        // The first three artifacts are worth 5VP, the last 2 10VP
        int totalKnownArtifacts = visibleArtifacts + round.getArtifactOrder().size();
        if (totalKnownArtifacts > 3) {
            // There is at least one 10VP artifact
            int tenVpArtifacts = totalKnownArtifacts - 3;
            int fiveVpArtifacts = visibleArtifacts - tenVpArtifacts;
            gemsRemaining += tenVpArtifacts * 10 + fiveVpArtifacts * 5;
        } else {
            gemsRemaining += visibleArtifacts * 5;
        }

        return gemsRemaining;
    }

    @Override
    public String toString() {
        return "Withdraw value";
    }
}
