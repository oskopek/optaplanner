package org.optaplanner.examples.transport.domain;

import org.optaplanner.core.api.domain.entity.PlanningEntity;
import org.optaplanner.core.api.domain.variable.PlanningVariable;
import org.optaplanner.examples.common.domain.AbstractPersistable;

/**
 * Created by skopeko on 6.4.17.
 */
@PlanningEntity
public class Pkg extends AbstractPersistable {

    private String name;
    private int index;
    private int size = 1;
    private Loc loc;
    private Loc target;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    @PlanningVariable(valueRangeProviderRefs = "locs")
    public Loc getLoc() {
        return loc;
    }

    public void setLoc(Loc loc) {
        this.loc = loc;
    }

    public Loc getTarget() {
        return target;
    }

    public void setTarget(Loc target) {
        this.target = target;
    }
}
