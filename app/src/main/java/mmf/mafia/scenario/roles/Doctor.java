package mmf.mafia.scenario.roles;

import mmf.mafia.scenario.Player;
import mmf.mafia.scenario.Side;

public class Doctor implements RoleWithAbility {
    @Override
    public Side side() {
        return Side.CITY;
    }

    @Override
    public boolean useAbility(Player player) {
        if (!player.isDoctor()) {
            player.save();
            return true;
        } else {
            if (!player.wasSavedBefore()) {
                player.save();
                return true;
            } else {
                return false;
            }
        }
    }
}
