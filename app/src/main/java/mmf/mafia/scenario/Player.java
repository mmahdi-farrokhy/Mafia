package mmf.mafia.scenario;

import mmf.mafia.scenario.roles.Doctor;
import mmf.mafia.scenario.roles.Role;
import mmf.mafia.scenario.roles.SimpleMafia;

public class Player {
    private String name;
    private Role role;
    private boolean isSavedForTonight = false;
    private boolean wasSavedBefore = false;
    private boolean isAlive = true;
    private boolean shouldDefenseToday = false;
    private int secondVotes = 0;
    private int firstVotes = 0;

    public boolean investigateByDetective() {
        return role instanceof SimpleMafia;
    }

    public boolean isDoctor() {
        return role instanceof Doctor;
    }

    public void save() {
        this.isSavedForTonight = true;
        this.wasSavedBefore = true;
    }

    public boolean isSavedForTonight() {
        return isSavedForTonight;
    }

    public boolean wasSavedBefore() {
        return this.wasSavedBefore;
    }

    public void die() {
        this.isAlive = false;
    }

    public void assignRole(Role role) {
        this.role = role;
    }

    public boolean isMafia() {
        return role.side() == Side.MAFIA;
    }

    public boolean isCitizen() {
        return !isMafia();
    }

    public boolean isDon() {
        return role instanceof Don;
    }

    public boolean isDetective() {
        return role instanceof Detective;
    }

    public void showsLike() {
        role.showsLike();
    }

    public String getName() {
        return name;
    }

    public void talk(int talkTurnTime) {

    }

    public boolean shouldDefend() {
        return shouldDefenseToday;
    }

    public void defense() {
        this.shouldDefenseToday = true;
    }

    public void exit() {
        die();
    }

    public void setSecondVotes(int secondVotes) {
        this.secondVotes = secondVotes;
    }

    public int getSecondVotes() {
        return this.secondVotes;
    }

    public void setFirstVote(int firstVotes) {
        this.firstVotes = firstVotes;
    }
}
