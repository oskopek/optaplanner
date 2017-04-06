package org.optaplanner.examples.transport.domain;

import org.optaplanner.core.api.domain.entity.PlanningEntity;
import org.optaplanner.core.api.domain.variable.PlanningVariable;
import org.optaplanner.examples.common.domain.AbstractPersistable;

@PlanningEntity
public class ActionAllocation extends AbstractPersistable {

    private ActionAllocation prevActionAllocation;
    private Action actionn;
    private int index;

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public ActionAllocation getPrevActionAllocation() {
        return prevActionAllocation;
    }

    public void setPrevActionAllocation(ActionAllocation prevActionAllocation) {
        this.prevActionAllocation = prevActionAllocation;
    }

    @PlanningVariable(valueRangeProviderRefs = {"drives", "drops", "pickups", "noop"})
    public Action getActionn() {
        return actionn;
    }

    public void setActionn(Action actionn) {
        this.actionn = actionn;
    }
}
