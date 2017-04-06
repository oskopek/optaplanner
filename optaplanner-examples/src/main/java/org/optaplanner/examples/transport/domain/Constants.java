package org.optaplanner.examples.transport.domain;

import org.optaplanner.examples.common.domain.AbstractPersistable;

/**
 * Created by skopeko on 6.4.17.
 */
public class Constants extends AbstractPersistable {

    private int lastActionIndex;

    public Constants(long id, int lastActionIndex) {
        super(id);
        this.lastActionIndex = lastActionIndex;
    }

    public int getLastActionIndex() {
        return lastActionIndex;
    }

    public void setLastActionIndex(int lastActionIndex) {
        this.lastActionIndex = lastActionIndex;
    }
}
