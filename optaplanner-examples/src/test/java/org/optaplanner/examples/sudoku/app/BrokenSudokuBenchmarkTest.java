/*
 * Copyright 2013 JBoss Inc
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

package org.optaplanner.examples.sudoku.app;

import org.junit.Ignore;
import org.junit.Test;
import org.optaplanner.benchmark.api.PlannerBenchmarkException;
import org.optaplanner.benchmark.api.PlannerBenchmarkFactory;
import org.optaplanner.benchmark.config.PlannerBenchmarkConfig;
import org.optaplanner.examples.common.app.PlannerBenchmarkTest;
import org.optaplanner.examples.sudoku.domain.Row;

import java.io.File;

@Ignore("Temporarily disabled")
public class BrokenSudokuBenchmarkTest extends PlannerBenchmarkTest {

    @Override
    protected String createBenchmarkConfigResource() {
        return "/org/optaplanner/examples/sudoku/benchmark/sudokuBenchmarkConfig.xml";
    }

    @Override
    protected PlannerBenchmarkFactory buildPlannerBenchmarkFactory(File unsolvedDataFile) {
        PlannerBenchmarkFactory benchmarkFactory = super.buildPlannerBenchmarkFactory(unsolvedDataFile);
        PlannerBenchmarkConfig benchmarkConfig = benchmarkFactory.getPlannerBenchmarkConfig();
        benchmarkConfig.setWarmUpSecondsSpentLimit(0L);
        benchmarkConfig.getInheritedSolverBenchmarkConfig().getSolverConfig().getPlanningEntityClassList()
                .add(Row.class); // Intentionally crash the solver
        return benchmarkFactory;
    }

    // ************************************************************************
    // Tests
    // ************************************************************************

    @Test(timeout = 100000, expected = PlannerBenchmarkException.class)
    public void benchmarkBroken9sudoku() {
        runBenchmarkTest(new File("data/sudoku/unsolved/9sudoku.xml"));
    }

}
