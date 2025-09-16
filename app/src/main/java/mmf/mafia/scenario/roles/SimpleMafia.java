package mmf.mafia.scenario.roles;

import mmf.mafia.scenario.Side;

public class SimpleMafia implements Role {
    @Override
    public Side side() {
        return Side.MAFIA;
    }
}
