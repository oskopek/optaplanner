package org.optaplanner.examples.transport.domain;

/**
 * Created by skopeko on 6.4.17.
 */
public class Pickup extends Action {

    private Pkg pkg;

    public Pkg getPkg() {
        return pkg;
    }

    public void setPkg(Pkg pkg) {
        this.pkg = pkg;
    }

    @Override
    public long getCost() {
        return 1;
    }
}
