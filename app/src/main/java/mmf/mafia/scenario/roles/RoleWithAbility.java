package mmf.mafia.scenario.roles;

import mmf.mafia.scenario.Player;

public interface RoleWithAbility extends Role {
    boolean useAbility(Player player);
}
