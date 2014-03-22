/*
 * Copyright 2012 JBoss Inc
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

import org.optaplanner.core.api.solver.Solver;
import org.optaplanner.core.api.solver.SolverFactory;
import org.optaplanner.core.config.solver.XmlSolverFactory;
import org.optaplanner.examples.sudoku.domain.Sudoku;
import org.optaplanner.examples.sudoku.domain.SudokuNumber;
import org.optaplanner.examples.sudoku.persistence.SudokuGenerator;

import java.util.List;

public class SudokuHelloWorld {

    public static void main(String[] args) {
        // Build the Solver
        SolverFactory solverFactory = new XmlSolverFactory(
                "/org/optaplanner/examples/sudoku/solver/sudokuSolverConfig.xml");
        Solver solver = solverFactory.buildSolver();

        // Load a problem with 9 sudoku
        Sudoku unsolved9Sudoku = new SudokuGenerator().createSudoku(9);

        // Solve the problem
        solver.setPlanningProblem(unsolved9Sudoku);
        solver.solve();
        Sudoku solved9Sudoku = (Sudoku) solver.getBestSolution();

        // Display the result
        System.out.println("\nSolved 9 sudoku:\n" + toDisplayString(solved9Sudoku));
    }

    public static String toDisplayString(Sudoku sudoku) {
        StringBuilder displayString = new StringBuilder();
        int n = sudoku.getN();
        int nSquares = Sudoku.nSquares(n);
        List<SudokuNumber> sudokuNumberList = sudoku.getSudokuNumberList();
        int sudokuNumberId = 0;
        for (int row = 0; row < sudoku.getRowList().size(); row++) {
            if (row != 0 && row % nSquares == 0) {
                for (int i = 0; i < (2*n)+(nSquares-1); i++) {
                    displayString.append("_");
                }
                displayString.append("\n");
            }
            for (int column = 0; column < sudoku.getColumnList().size(); column++) {
                SudokuNumber sudokuNumber = sudokuNumberList.get(sudokuNumberId);
                sudokuNumberId++;
                if (sudokuNumber.getColumnIndex() != column || sudokuNumber.getRowIndex() != row) {
                    throw new IllegalStateException("The sudokuNumberList is not in the expected order.");
                }
                displayString.append(" ");
                if (column != 0 && column % nSquares == 0) {
                    displayString.append("|");
                }
                displayString.append(sudokuNumber.getNumber());
            }
            displayString.append("\n");
        }
        return displayString.toString();
    }

}
