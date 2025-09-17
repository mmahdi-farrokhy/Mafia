package mmf.mafia.scenario.roles;

import mmf.mafia.scenario.Player;
import mmf.mafia.scenario.Side;

public class Don implements RoleWithAbility {
    @Override
    public Side side() {
        return Side.MAFIA;
    }

    @Override
    public void showsLike() {

    }

    @Override
    public boolean useAbility(Player player) {
        return shoot(player);
    }

    private boolean shoot(Player player) {
        if (player.isSavedForTonight()) {
            return false;
        } else {
            player.die();
            return true;
        }
    }
}
