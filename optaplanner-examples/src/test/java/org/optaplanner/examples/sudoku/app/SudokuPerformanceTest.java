/*
 * Copyright 2010 JBoss Inc
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
import org.optaplanner.core.config.solver.EnvironmentMode;
import org.optaplanner.examples.common.app.SolverPerformanceTest;
import org.optaplanner.examples.common.persistence.SolutionDao;
import org.optaplanner.examples.sudoku.persistence.SudokuDao;

import java.io.File;

public class SudokuPerformanceTest extends SolverPerformanceTest {

    @Override
    protected String createSolverConfigResource() {
        return "/org/optaplanner/examples/sudoku/solver/sudokuSolverConfig.xml";
    }

    @Override
    protected SolutionDao createSolutionDao() {
        return new SudokuDao();
    }

    // ************************************************************************
    // Tests
    // ************************************************************************

    @Test(timeout = 600000)
    @Ignore("Temporarily disabled")
    public void solveModel_16sudoku() {
        runSpeedTest(new File("data/sudoku/unsolved/16sudoku.xml"),
                "0", EnvironmentMode.PRODUCTION);
    }

    @Test(timeout = 600000)
    public void solveModel_9sudokuFastAssert() {
        runSpeedTest(new File("data/sudoku/unsolved/9sudoku.xml"),
                "0", EnvironmentMode.FAST_ASSERT);
    }

    @Test(timeout = 600000)
    public void solveModel_4sudokuFullAssert() {
        runSpeedTest(new File("data/sudoku/unsolved/4sudoku.xml"),
                "0", EnvironmentMode.FULL_ASSERT);
    }

}
