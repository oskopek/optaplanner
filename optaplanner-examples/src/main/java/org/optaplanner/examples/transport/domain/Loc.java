package org.optaplanner.examples.transport.domain;

import org.optaplanner.examples.common.domain.AbstractPersistable;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by skopeko on 6.4.17.
 */
public class Loc extends AbstractPersistable {

    private String name;
    private Map<Loc, Integer> distanceTo = new HashMap<>();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getDistanceTo(Loc other) {
        return distanceTo.get(other);
    }

    public void setDistanceTo(Loc other, Integer dist) {
        distanceTo.put(other, dist);
    }

    public Map<Loc, Integer> getDistanceTo() {
        return distanceTo;
    }
}
