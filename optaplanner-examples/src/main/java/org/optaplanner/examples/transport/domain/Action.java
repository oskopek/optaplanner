/*
 * Copyright 2011 Red Hat, Inc. and/or its affiliates.
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
import org.optaplanner.examples.common.domain.AbstractPersistable;

@XStreamAlias("Action")
public abstract class Action extends AbstractPersistable {


    private Veh veh;
    private Loc loc;

    public Loc getLocation() {
        return loc;
    }

    public void setLocation(Loc location) {
        this.loc = location;
    }

    public Veh getVeh() {
        return veh;
    }

    public void setVeh(Veh veh) {
        this.veh = veh;
    }



    // ************************************************************************
    // Complex methods
    // ************************************************************************

    public abstract long getCost();

    @Override
    public String toString() {
        return getClass().getName();
    }

}
