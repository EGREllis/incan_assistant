package net.assistant.model;

import java.util.Collections;
import java.util.Set;
import java.util.TreeSet;

public class PlayerState {
    private String name;
    private int temporaryGems = 0;
    private int savedGems = 0;
    private Set<Integer> temporaryArtifacts = new TreeSet<>();
    private Set<Integer> savedArtifacts = new TreeSet<>();

    public PlayerState(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public int getTemporaryGems() {
        return temporaryGems;
    }

    public int getSavedGems() {
        return savedGems;
    }

    public Set<Integer> getTemporaryArtifacts() {
        return Collections.unmodifiableSet(temporaryArtifacts);
    }

    public Set<Integer> getSavedArtifacts() {
        return Collections.unmodifiableSet(savedArtifacts);
    }

    public void collectGems(int gems) {
        this.temporaryGems += gems;
    }

    public void collectArtifact(int card) {
        this.temporaryArtifacts.add(card);
    }

    public void failedExcavate() {
        this.temporaryGems = 0;
        this.temporaryArtifacts.clear();
    }

    public void successfulWithdraw() {
        this.savedGems = temporaryGems;
        this.savedArtifacts.addAll(temporaryArtifacts);
        this.temporaryGems = 0;
        this.temporaryArtifacts.clear();
    }
}
