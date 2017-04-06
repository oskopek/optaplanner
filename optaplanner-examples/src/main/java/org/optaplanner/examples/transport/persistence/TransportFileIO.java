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

import org.optaplanner.examples.transport.domain.TransportSolution;
import org.optaplanner.persistence.common.api.domain.solution.SolutionFileIO;

import java.io.File;

public class TransportFileIO implements SolutionFileIO<TransportSolution> {

    private TransportImporter importer = new TransportImporter();
    private TransportExporter exporter = new TransportExporter();

    @Override
    public String getInputFileExtension() {
        return TransportImporter.INPUT_FILE_SUFFIX;
    }

    @Override
    public String getOutputFileExtension() {
        return TransportExporter.OUTPUT_FILE_SUFFIX;
    }

    @Override
    public TransportSolution read(File inputSolutionFile) {
        return importer.readSolution(inputSolutionFile);
    }

    @Override
    public void write(TransportSolution solution, File outputSolutionFile) {
        exporter.writeSolution(solution, outputSolutionFile);
    }

}
