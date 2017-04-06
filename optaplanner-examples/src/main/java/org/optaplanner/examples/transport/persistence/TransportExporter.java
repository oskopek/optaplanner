/*
 * Copyright 2014 Red Hat, Inc. and/or its affiliates.
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

import org.optaplanner.examples.common.persistence.AbstractTxtSolutionExporter;
import org.optaplanner.examples.transport.domain.*;

import java.io.IOException;

public class TransportExporter extends AbstractTxtSolutionExporter<TransportSolution> {

    public static final String OUTPUT_FILE_SUFFIX = "tour";

    public static void main(String[] args) {
        new TransportExporter().convertAll();
    }

    public TransportExporter() {
        super(new TransportDao());
    }

    @Override
    public String getOutputFileSuffix() {
        return OUTPUT_FILE_SUFFIX;
    }

    @Override
    public TxtOutputBuilder<TransportSolution> createTxtOutputBuilder() {
        return new TransportOutputBuilder();
    }

    public static class TransportOutputBuilder extends TxtOutputBuilder<TransportSolution> {

        @Override
        public void writeSolution() throws IOException {
            for (ActionAllocation alloc : solution.getActionAllocationList()) {
                Action action = alloc.getActionn();
                bufferedWriter.write(action.getClass().getSimpleName() + "(" + action.getVeh().getName() + " " + action.getLocation().getName() + " ");
                if (action instanceof Drive) {
                    Drive drive = (Drive) action;
                    bufferedWriter.write(drive.getTarget().getName() + "\n");
                }
                if (action instanceof Pickup) {
                    Pickup pickup = (Pickup) action;
                    bufferedWriter.write(pickup.getPkg().getName() + "\n");
                }
                if (action instanceof Drop) {
                    Drop drop = (Drop) action;
                    bufferedWriter.write(drop.getPkg().getName() + "\n");
                }
            }
        }
    }

}
