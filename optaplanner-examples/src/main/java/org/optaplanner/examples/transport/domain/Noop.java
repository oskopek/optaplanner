package org.optaplanner.examples.transport.domain;

/**
 * Created by skopeko on 6.4.17.
 */
public class Noop extends Action {

    @Override
    public long getCost() {
        return 0L;
    }
}
