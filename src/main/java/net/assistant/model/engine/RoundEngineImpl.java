package net.assistant.model.engine;

import net.assistant.model.*;

import java.util.*;

public class RoundEngineImpl implements RoundEngine {
    @Override
    public RoundState processRound(RoundState round) {
        Set<String> playersInRound = new TreeSet<>();
        playersInRound.addAll(round.getPlayers().keySet());

        List<Integer> visibleCards = new ArrayList<>(round.getDeck().getSize());
        List<Integer> remainingGems = new ArrayList<>(round.getDeck().getSize());
        Deck deck = round.getDeck();
        boolean isPlayable = true;
        Decisions decisions = new Decisions(playersInRound, Collections.emptySet());

        // First card is not optional
        int firstCard = deck.drawCard();
        if (CardType.GEM.equals(deck.getCardType(firstCard))) {
            processGems(firstCard, deck, visibleCards, remainingGems, playersInRound, round);
        }

        //TODO: Consider adding visibleCard and remainingGems in one statement at the bottom (happens in all cases)
        while (isPlayable) {
            // Consult agents for decisions
            decisions = collectDecisions(playersInRound, round);

            // Process withdrawing players (they are safe before the consequences of the next card)
            processWithdrawl(visibleCards, remainingGems, playersInRound, deck, decisions, round);

            // If everyone left, the round is over.
            if (playersInRound.size() == 0) {
                break;
            }

            // Draw card
            int card = round.getDeck().drawCard();

            // Process card effects
            switch (deck.getCardType(card)) {
                case GEM:
                    processGems(card, deck, visibleCards, remainingGems, playersInRound, round);
                    break;
                case HAZARD:
                    isPlayable = processHazard(card, visibleCards, remainingGems, decisions, round);
                    break;
                case ARTIFACT:
                    processArtifact(card, visibleCards, remainingGems);
                    break;
            }
        }
        return round;
    }

    private void processGems(int card, Deck deck, List<Integer> visibleCards, List<Integer> remainingGems, Set<String> playersInRound, RoundState round) {
        int gemValue = deck.getGemValue(card);
        int split = gemValue / playersInRound.size();
        for (String player : playersInRound) {
            PlayerState state = round.getPlayers().get(player);
            state.collectGems(split);
        }
        int remainder = gemValue - (split * playersInRound.size());
        remainingGems.add(remainder);
        visibleCards.add(card);
    }

    private boolean processHazard(int card, List<Integer> visibleCards, List<Integer> remainingGems, Decisions decisions, RoundState round) {
        boolean isPlayable = !visibleCards.contains(card);
        if (!isPlayable) {
            round.getCardsToRemove().add(card);
            for (String player : decisions.excavate) {
                PlayerState state = round.getPlayers().get(player);
                state.failedExcavate();
            }
        }
        visibleCards.add(card);
        remainingGems.add(0);

        return isPlayable;
    }

    private void processArtifact(int card, List<Integer> visibleCards, List<Integer> remainingGems) {
        visibleCards.add(card);
        remainingGems.add(0);
    }

    private Decisions collectDecisions(Set<String> playersInRound, RoundState round) {
        Set<String> excavating = new TreeSet<>();
        Set<String> withdrawing = new TreeSet<>();
        for (String activePlayer : playersInRound) {
            Agent agent = round.getAgent().get(activePlayer);
            PlayerDecision decision = agent.decide(round);
            switch (decision) {
                case EXCAVATE:
                    excavating.add(activePlayer);
                    break;
                case WITHDRAW:
                    withdrawing.add(activePlayer);
                    break;
                default:
                    throw new IllegalStateException(String.format("This should never be reached... but was! (%1$s)", decision));
            }
        }
        return new Decisions(excavating, withdrawing);
    }

    private void processWithdrawl(List<Integer> visibleCards, List<Integer> remainingGems, Set<String> playersInRound, Deck deck, Decisions decisions, RoundState round) {
        switch (decisions.withdraw.size()) {
            case 0:
                // Nothing to process
                break;
            case 1:
                // One person
                for (String withdrawing : decisions.withdraw) {
                    int sumRemaining = 0;
                    PlayerState player = round.getPlayers().get(withdrawing);

                    for (int i = visibleCards.size()-1; i >= 0; i--) {
                        int visible = visibleCards.get(i);
                        if (CardType.ARTIFACT.equals(deck.getCardType(visible))) {
                            player.collectArtifact(visible);
                            visibleCards.remove(i);
                            remainingGems.remove(i);
                        }
                        sumRemaining += remainingGems.get(i);
                        remainingGems.set(i, 0);
                    }
                    player.collectGems(sumRemaining);
                    player.successfulWithdraw();
                }
                break;
            default:
                // Multiple people leave
                int perPlayerTake = 0;
                for (int i = visibleCards.size()-1; i >= 0; i--) {
                    // Gems are only on gem cards
                    if (CardType.GEM.equals(deck.getCardType(visibleCards.get(i)))) {
                        int gemsOnCard = remainingGems.get(i);
                        int perPlayerCardTake = gemsOnCard / decisions.withdraw.size();
                        if (perPlayerCardTake > 0) {
                            perPlayerTake += perPlayerCardTake;
                            remainingGems.set(i, gemsOnCard - perPlayerCardTake * decisions.withdraw.size());
                        }
                    }
                }
                for (String player : decisions.withdraw) {
                    PlayerState state = round.getPlayers().get(player);
                    state.collectGems(perPlayerTake);
                    state.successfulWithdraw();
                }
                break;
        }
        // Once you have left, you can't come back
        for (String withdrawn : decisions.withdraw) {
            playersInRound.remove(withdrawn);
        }
    }

    private static class Decisions {
        private final Set<String> excavate;
        private final Set<String> withdraw;

        private Decisions(Set<String> excavate, Set<String> withdraw) {
            this.excavate = excavate;
            this.withdraw = withdraw;
        }
    }
}
