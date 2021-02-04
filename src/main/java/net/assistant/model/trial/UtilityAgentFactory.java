package net.assistant.model.trial;

import net.assistant.model.Agent;
import net.assistant.model.agent.CompositeAgent;
import net.assistant.model.agent.NoOpportunityCostAgent;
import net.assistant.model.agent.RandomAgent;
import net.assistant.model.agent.RationalFailureAgent;

import java.util.Arrays;
import java.util.Map;
import java.util.TreeMap;

public class UtilityAgentFactory implements AgentFactory {
    @Override
    public Map<String, Agent> newAgents() {
        Map<String, Agent> agents = new TreeMap<>();
        agents.put("80% excavate", new RandomAgent(0.8));
        agents.put("No Opportunity (%80 excavate)", new CompositeAgent(Arrays.asList(
                new NoOpportunityCostAgent(),
                new RandomAgent(0.8)
        )));
        agents.put("No Opportunity (pFail 50%)", new RationalFailureAgent(0.39));
                //new RationalFailureAgent(0.35) // 6.41
                //new RationalFailureAgent(0.38) // 7.589534
                //new RationalFailureAgent(0.3825) // 7.528707
                //new RationalFailureAgent(0.385) // 7.863152
                //new RationalFailureAgent(0.3875) // 7.836127
                //new RationalFailureAgent(0.39) // 7.856539, 7.862761, 7.862482
                //new RationalFailureAgent(0.395) // 7.603358
                //new RationalFailureAgent(0.40) // 7.592
        return agents;
    }
}
