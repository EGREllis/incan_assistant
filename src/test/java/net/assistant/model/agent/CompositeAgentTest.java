package net.assistant.model.agent;

import net.assistant.model.Agent;
import net.assistant.model.ConditionalAgent;
import net.assistant.model.PlayerDecision;
import net.assistant.model.RoundState;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.Arrays;

import static org.mockito.Mockito.*;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class CompositeAgentTest {
    @Test
    public void when_compositeAskedToDecide_given_firstAgentAcceptsResponsability_then_firstAgentResponseReturned() {
        ConditionalAgent agent1 = Mockito.mock(ConditionalAgent.class);
        ConditionalAgent agent2 = Mockito.mock(ConditionalAgent.class);
        RoundState round = Mockito.mock(RoundState.class);

        when(agent1.isApplicable(round)).thenReturn(true);
        when(agent1.decide(round)).thenReturn(PlayerDecision.EXCAVATE);

        Agent agent = new CompositeAgent(Arrays.asList(agent1, agent2));
        PlayerDecision decision = agent.decide(round);

        verify(agent1).isApplicable(round);
        verify(agent1).decide(round);
        assertThat(decision, equalTo(PlayerDecision.EXCAVATE));
    }

    @Test
    public void when_compositeAskedToDecide_given_firstAgentRefusesResponsability_then_secondAgentEngaged() {
        ConditionalAgent agent1 = Mockito.mock(ConditionalAgent.class);
        ConditionalAgent agent2 = Mockito.mock(ConditionalAgent.class);
        RoundState round = Mockito.mock(RoundState.class);

        when(agent1.isApplicable(round)).thenReturn(false);
        when(agent2.isApplicable(round)).thenReturn(true);
        when(agent2.decide(round)).thenReturn(PlayerDecision.WITHDRAW);

        Agent agent = new CompositeAgent(Arrays.asList(agent1, agent2));
        PlayerDecision decision = agent.decide(round);

        verify(agent1).isApplicable(round);
        verify(agent2).isApplicable(round);
        verify(agent2).decide(round);
        assertThat(decision, equalTo(PlayerDecision.WITHDRAW));
    }
}
