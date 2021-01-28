package net.assistant.model;

import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.CoreMatchers.equalTo;

public class PlayerStateTest {
    @Test
    public void when_created_given_noChanges_then_returnsExpectedResults() {
        PlayerState state = new PlayerState("test-player");

        assertThat(state.getName(), equalTo("test-player"));
        assertThat(state.getTemporaryGems(), equalTo(0));
        assertThat(state.getSavedGems(), equalTo(0));
        assertThat(state.getTemporaryArtifacts().size(), equalTo(0));
        assertThat(state.getSavedArtifacts().size(), equalTo(0));
    }

    @Test
    public void when_awardedGemsAndArtifacts_given_failedExcavateFollowingWithdraw_then_onlyWithdrawnRewardsKept() {
        PlayerState state = new PlayerState("test-player");

        state.collectGems(2);
        state.collectArtifact(21);
        state.successfulWithdraw();

        state.collectGems(3);
        state.collectArtifact(22);
        state.failedExcavate();

        assertThat(state.getName(), equalTo("test-player"));
        assertThat(state.getTemporaryGems(), equalTo(0));
        assertThat(state.getSavedGems(), equalTo(2));

        assertThat(state.getTemporaryArtifacts().size(), equalTo(0));
        assertThat(state.getSavedArtifacts().size(), equalTo(1));
        assertThat(state.getSavedArtifacts().contains(21), equalTo(true));
    }
}
