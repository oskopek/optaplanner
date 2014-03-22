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

package org.optaplanner.examples.sudoku.persistence;

import org.junit.runners.Parameterized;
import org.optaplanner.examples.common.persistence.SolutionDao;
import org.optaplanner.examples.common.persistence.SolutionDaoTest;

import java.io.File;
import java.util.Collection;

public class SudokuDaoTest extends SolutionDaoTest {

    @Override
    protected SolutionDao createSolutionDao() {
        return new SudokuDao();
    }

    @Parameterized.Parameters(name = "{index}: {0}")
    public static Collection<Object[]> getSolutionFilesAsParameters() {
        return getSolutionFilesAsParameters(new SudokuDao());
    }

    public SudokuDaoTest(File solutionFile) {
        super(solutionFile);
    }

}
