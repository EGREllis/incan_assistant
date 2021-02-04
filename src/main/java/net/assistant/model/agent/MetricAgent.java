package net.assistant.model.agent;

import net.assistant.model.*;
import net.assistant.model.calc.Metric;
import net.assistant.model.calc.ProbabilityOfFailureMetric;

public class MetricAgent implements ConditionalAgent {
    private final Metric metric;
    private final double threshold;

    public MetricAgent(Metric metric, double threshold) {
        this.metric = metric;
        this.threshold = threshold;
    }

    @Override
    public PlayerDecision decide(RoundState round) {
        PlayerDecision decision;
        double metricCalc = metric.calculate(round);
        if (metricCalc < threshold) {
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

    public static ConditionalAgent probabilityOfFailureAgent(double threshold) {
        Metric metric = new ProbabilityOfFailureMetric();
        return new MetricAgent(metric, threshold);
    }
}
