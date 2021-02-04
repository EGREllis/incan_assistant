package net.assistant.model.agent;

import net.assistant.model.ConditionalAgent;
import net.assistant.model.PlayerDecision;
import net.assistant.model.RoundState;
import net.assistant.model.calc.Metric;

public class DoubleMetricAgent implements ConditionalAgent {
    private final double threshold;
    private final Metric firstMetric;
    private final Metric secondMetric;

    public DoubleMetricAgent(double threshold, Metric firstMetric, Metric secondMetric) {
        this.threshold = threshold;
        this.firstMetric = firstMetric;
        this.secondMetric = secondMetric;
    }

    @Override
    public PlayerDecision decide(RoundState round) {
        double valueFirstMetric = firstMetric.calculate(round);
        double valueSecondMetric = secondMetric.calculate(round);
        double ratio = valueFirstMetric / valueSecondMetric;
        PlayerDecision decision;
        if (ratio < threshold) {
            decision = PlayerDecision.EXCAVATE;
        } else {
            decision = PlayerDecision.WITHDRAW;
        }
        System.out.println(String.format("%1$s %2$f %3$s %4$f ratio: %5$f decision %6$s", firstMetric, valueFirstMetric, secondMetric, valueSecondMetric, ratio, decision));
        return decision;
    }

    @Override
    public boolean isApplicable(RoundState state) {
        return true;
    }
}
