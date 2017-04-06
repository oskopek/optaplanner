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

package org.optaplanner.examples.transport.persistence;

import org.optaplanner.examples.common.persistence.AbstractTxtSolutionImporter;
import org.optaplanner.examples.transport.domain.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TransportImporter extends AbstractTxtSolutionImporter<TransportSolution> {

    public static final String INPUT_FILE_SUFFIX = "pddl.csp";

    public static void main(String[] args) {
        TransportImporter
                importer = new TransportImporter();
        importer.convert("ipc08/p01.pddl.csp", "p01.xml");
    }

    public TransportImporter() {
        super(new TransportDao());
    }

    public TransportImporter(boolean withoutDao) {
        super(withoutDao);
    }

    @Override
    public String getInputFileSuffix() {
        return INPUT_FILE_SUFFIX;
    }

    @Override
    public TxtInputBuilder<TransportSolution> createTxtInputBuilder() {
        return new TransportInputBuilder();
    }

    public static class TransportInputBuilder extends TxtInputBuilder<TransportSolution> {

        private TransportSolution solution;

        @Override
        public TransportSolution readSolution() throws IOException {
            solution = new TransportSolution();
            solution.setId(0L);
            String firstLine = readStringValue();
            solution.setName(removePrefixSuffixFromLine(firstLine, "\\s*NAME\\s*:", ""));
            readTransportFormat();
            logger.info("TransportSolution {} has {} locations with a search space of {}.",
                    getInputId(),
                    solution.getLocationList().size());
            return solution;
        }

        private void readTransportFormat() throws IOException {
            long id = 1L;
            int planLength = readIntegerValue("\\s*PLAN_LENGTH\\s*:");
            if (planLength <= 0) {
                throw new IllegalArgumentException("Invalid plan length.");
            }
            int locations = readIntegerValue("\\s*LOCATIONS\\s*:");
            Map<String, Loc> locMap = new HashMap<>(locations);
            List<Loc> locs = new ArrayList<>(locations);
            for (int i = 0; i < locations; i++) {
                Loc loc = new Loc();
                loc.setId(id++);
                loc.setName(readStringValue());
                locs.add(loc);
                locMap.put(loc.getName(), loc);
            }

            int roads = readIntegerValue("\\s*ROADS\\s*:");
            for (int i = 0; i < roads; i++) {
                String[] split = readStringValue().split(" ");
                locMap.get(split[0]).setDistanceTo(locMap.get(split[1]), Integer.parseInt(split[2]));
            }

            int packages = readIntegerValue("\\s*PACKAGES\\s*:");
            List<Pkg> pkgs = new ArrayList<>(packages);
            for (int i = 0; i < packages; i++) {
                String[] split = readStringValue().split(" ");
                for (int index = 0; index < planLength; index++) {
                    Pkg pkg = new Pkg();
                    pkg.setId(id++);
                    pkg.setIndex(index);
                    pkg.setSize(Integer.parseInt(split[1]));
                    pkg.setName(split[0]);
                    pkg.setLoc(locMap.get(split[2]));
                    pkg.setTarget(locMap.get(split[3]));
                    pkgs.add(pkg);
                }
            }

            int vehicles = readIntegerValue("\\s*VEHICLES\\s*:");
            List<Veh> vehs = new ArrayList<>(vehicles);
            for (int i = 0; i < packages; i++) {
                String[] split = readStringValue().split(" ");
                for (int index = 0; index < planLength; index++) {
                    Veh vehicle = new Veh();
                    vehicle.setId(id++);
                    vehicle.setIndex(index);
                    vehicle.setName(split[0]);
                    vehicle.setLoc(locMap.get(split[2]));
                    vehs.add(vehicle);
                }
            }
            readConstantLine("EOF");

            solution.setLocationList(locs);
            solution.setNoop(new Noop());
            solution.getNoop().get(0).setId(id++);
            solution.setVehList(vehs);
            solution.setPkgList(pkgs);

            List<Drive> drives = new ArrayList<>();
            List<Pickup> pickups = new ArrayList<>();
            List<Drop> drops = new ArrayList<>();
                for (Loc loc : locs) {
                    for (Map.Entry<Loc, Integer> entry : loc.getDistanceTo().entrySet()) {
                        for (Veh v : vehs) {
                            Drive drive = new Drive();
                            drive.setId(id++);
                            drive.setLocation(loc);
                            drive.setTarget(entry.getKey());
                            drive.setVeh(v);
                            drives.add(drive);
                        }
                    }

                    for (Veh v : vehs) {
                        for (Pkg pkg : pkgs) {
                            Pickup pickup = new Pickup();
                            Drop drop = new Drop();
                            pickup.setId(id++);
                            drop.setId(id++);
                            pickup.setPkg(pkg);
                            drop.setPkg(pkg);
                            pickup.setLocation(loc);
                            drop.setLocation(loc);
                            pickup.setVeh(v);
                            drop.setVeh(v);
                            pickups.add(pickup);
                            drops.add(drop);
                        }
                    }
                }

            solution.setDriveList(drives);
            solution.setPickupList(pickups);
            solution.setDropList(drops);

            ActionAllocation last = null;
            List<ActionAllocation> allocations = new ArrayList<>(planLength);
            for (int i = 0; i < planLength; i++) {
                ActionAllocation allocation = new ActionAllocation();
                allocation.setId(id++);
                allocation.setIndex(i);
                allocation.setPrevActionAllocation(last);
                allocations.add(allocation);
                last = allocation;
            }

            solution.setActionAllocationList(allocations);
        }

    }

}
