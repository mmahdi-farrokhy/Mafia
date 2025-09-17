package mmf.mafia.scenario.roles;

import mmf.mafia.scenario.Player;
import mmf.mafia.scenario.Side;

public class Detective implements RoleWithAbility {
    @Override
    public Side side() {
        return Side.CITY;
    }

    @Override
    public void showsLike() {

    }

    @Override
    public boolean useAbility(Player player) {
        return player.investigateByDetective();
    }
}
