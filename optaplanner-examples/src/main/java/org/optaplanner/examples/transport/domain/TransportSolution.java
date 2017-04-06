/*
 * Copyright 2016 Red Hat, Inc. and/or its affiliates.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.optaplanner.examples.transport.domain;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamConverter;
import org.optaplanner.core.api.domain.solution.PlanningEntityCollectionProperty;
import org.optaplanner.core.api.domain.solution.PlanningScore;
import org.optaplanner.core.api.domain.solution.PlanningSolution;
import org.optaplanner.core.api.domain.solution.drools.ProblemFactCollectionProperty;
import org.optaplanner.core.api.domain.solution.drools.ProblemFactProperty;
import org.optaplanner.core.api.domain.valuerange.ValueRange;
import org.optaplanner.core.api.domain.valuerange.ValueRangeFactory;
import org.optaplanner.core.api.domain.valuerange.ValueRangeProvider;
import org.optaplanner.core.api.score.buildin.hardsoftlong.HardSoftLongScore;
import org.optaplanner.examples.common.domain.AbstractPersistable;
import org.optaplanner.persistence.xstream.api.score.buildin.hardsoftlong.HardSoftLongScoreXStreamConverter;

import java.util.Collections;
import java.util.List;

@PlanningSolution
@XStreamAlias("TransportSolution")
public class TransportSolution extends AbstractPersistable {

    private String name;
    private List<Loc> locationList;
    private List<Veh> vehList;
    private List<Pkg> pkgList;
    private List<Drive> driveList;
    private List<Pickup> pickupList;
    private List<Drop> dropList;
    private List<Noop> noop;
    private Constants constants;

    private List<ActionAllocation> actionAllocationList;

    @XStreamConverter(HardSoftLongScoreXStreamConverter.class)
    private HardSoftLongScore score;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @ProblemFactCollectionProperty
    @ValueRangeProvider(id = "locs")
    public List<Loc> getLocationList() {
        return locationList;
    }

    public void setLocationList(List<Loc> locationList) {
        this.locationList = locationList;
    }

    public void setNoop(List<Noop> noop) {
        this.noop = noop;
    }

    @ProblemFactProperty
    public Constants getConstants() {
        return constants;
    }

    public void setConstants(Constants constants) {
        this.constants = constants;
    }

    @ProblemFactProperty
    @ValueRangeProvider(id = "noop")
    public List<Noop> getNoop() {
        return noop;
    }

    @PlanningEntityCollectionProperty
    public List<ActionAllocation> getActionAllocationList() {
        return actionAllocationList;
    }

    public void setActionAllocationList(
            List<ActionAllocation> actionAllocationList) {
        this.actionAllocationList = actionAllocationList;
    }

    @ProblemFactCollectionProperty
    @ValueRangeProvider(id = "drives")
    public List<Drive> getDriveList() {
        return driveList;
    }

    public void setDriveList(List<Drive> driveList) {
        this.driveList = driveList;
    }

    @ProblemFactCollectionProperty
    @ValueRangeProvider(id = "pickups")
    public List<Pickup> getPickupList() {
        return pickupList;
    }

    public void setPickupList(List<Pickup> pickupList) {
        this.pickupList = pickupList;
    }

    @ProblemFactCollectionProperty
    @ValueRangeProvider(id = "drops")
    public List<Drop> getDropList() {
        return dropList;
    }

    public void setDropList(List<Drop> dropList) {
        this.dropList = dropList;
    }

    @PlanningEntityCollectionProperty
    @ValueRangeProvider(id = "vehs")
    public List<Veh> getVehList() {
        return vehList;
    }

    public void setVehList(List<Veh> vehList) {
        this.vehList = vehList;
    }

    @PlanningEntityCollectionProperty
    public List<Pkg> getPkgList() {
        return pkgList;
    }

    public void setPkgList(List<Pkg> pkgList) {
        this.pkgList = pkgList;
    }

    @PlanningScore
    public HardSoftLongScore getScore() {
        return score;
    }

    public void setScore(HardSoftLongScore score) {
        this.score = score;
    }

    @ValueRangeProvider(id = "posints")
    public final ValueRange<Integer> posints = ValueRangeFactory.createIntValueRange(0, Integer.MAX_VALUE);

    // ************************************************************************
    // Complex methods
    // ************************************************************************

}
