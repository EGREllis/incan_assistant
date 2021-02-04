package net.assistant.model.calc;

import net.assistant.model.RoundState;

public interface Metric {
    double calculate(RoundState round);
}
