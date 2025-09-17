package mmf.mafia.scenario.roles;

import mmf.mafia.scenario.Side;

public class SimpleCitizen implements Role {
    @Override
    public Side side() {
        return Side.CITY;
    }

    @Override
    public void showsLike() {

    }
}
