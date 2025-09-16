package mmf.mafia.scenario;

import mmf.mafia.scenario.roles.Doctor;
import mmf.mafia.scenario.roles.Role;
import mmf.mafia.scenario.roles.SimpleMafia;

public class Player {
    private String name;
    private Role role;
    private boolean isSavedForTonight = false;
    private boolean wasSavedBefore = false;

    public boolean isSimpleMafia() {
        return role instanceof SimpleMafia;
    }

    public boolean isDoctor() {
        return role instanceof Doctor;
    }

    public void save() {
        this.isSavedForTonight = true;
        this.wasSavedBefore = true;
    }

    public boolean wasSavedBefore() {
        return this.wasSavedBefore;
    }
}
