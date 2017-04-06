package org.optaplanner.examples.transport.domain;

/**
 * Created by skopeko on 6.4.17.
 */
public class Drive extends Action {

    private Loc target;

    public Loc getTarget() {
        return target;
    }

    public void setTarget(Loc target) {
        this.target = target;
    }

    @Override
    public long getCost() {
        return getLocation().getDistanceTo(target);
    }
}
