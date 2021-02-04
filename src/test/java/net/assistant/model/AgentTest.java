package net.assistant.model;

import net.assistant.model.agent.AlwaysExcavateAgent;
import net.assistant.model.agent.AlwaysWithdrawAgent;
import net.assistant.model.agent.RandomAgent;
import org.junit.Test;

import java.util.*;

public class AgentTest {
    private static final double TOLERANCE = 0.1;
    private static final int N_TRIALS = 1000;

    @Test
    public void when_makingDecision_given_alwaysExcavate_then_onlyReturnsExcavate() {
        Agent agent = new AlwaysExcavateAgent();
        RoundState roundState = emptyRoundState();

        int count = 0;
        for (int i = 0; i < N_TRIALS; i++) {
            PlayerDecision decision = agent.decide(roundState);
            if (PlayerDecision.EXCAVATE.equals(decision)) {
                count++;
            }
        }

        assert count == N_TRIALS;
    }

    @Test
    public void when_makingDecision_given_alwaysWithdraw_then_onlyReturnsWithdraw() {
        Agent agent = new AlwaysWithdrawAgent();
        RoundState roundState = emptyRoundState();

        int count = 0;
        for (int i = 0; i < N_TRIALS; i++) {
            PlayerDecision decision = agent.decide(roundState);
            if (PlayerDecision.WITHDRAW.equals(decision)) {
                count++;
            }
        }

        assert count == N_TRIALS;
    }

    @Test
    public void when_makingDecision_given_randomThreshold_then_roughlyEqual() {
        Agent agent = new RandomAgent(0.5);
        RoundState roundState = emptyRoundState();

        int excavateCount = 0;
        int withdrawCount = 0;
        for (int i = 0; i < N_TRIALS; i++) {
            PlayerDecision decision = agent.decide(roundState);
            switch (decision) {
                case EXCAVATE:
                    excavateCount++;
                    break;
                case WITHDRAW:
                    withdrawCount++;
                    break;
                default:
                    throw new IllegalArgumentException(String.format("This branch should never be reached... %1$s", decision));
            }
        }

        int diff;
        if (excavateCount <  withdrawCount) {
            diff = withdrawCount - excavateCount;
        } else if (withdrawCount < excavateCount) {
            diff = excavateCount - withdrawCount;
        } else {
            diff = 0;
        }

        double errorRatio = diff / (N_TRIALS * 1.0);

        assert errorRatio < TOLERANCE : String.format("Error ratio above tolerance (excavated: %1$d withdrawn: %2$d errorRate: %3$f tolerance: %4$f)", excavateCount, withdrawCount, errorRatio, TOLERANCE);
    }

    private RoundState emptyRoundState() {
        Deck deck = new DeckImpl(1, Collections.emptyList());
        Map<String, PlayerState> players = new TreeMap<>();
        Map<String, Agent> agents = new TreeMap<>();
        List<Integer> artifactOrder = new ArrayList<>();
        return new RoundState(deck, players, agents, Collections.emptyList(), artifactOrder);
    }
}
