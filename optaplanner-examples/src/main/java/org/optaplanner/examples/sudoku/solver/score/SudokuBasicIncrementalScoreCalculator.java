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

package org.optaplanner.examples.sudoku.solver.score;

import org.optaplanner.core.api.score.buildin.simple.SimpleScore;
import org.optaplanner.core.impl.score.director.incremental.AbstractIncrementalScoreCalculator;
import org.optaplanner.examples.sudoku.domain.Sudoku;
import org.optaplanner.examples.sudoku.domain.SudokuNumber;
import org.optaplanner.examples.sudoku.domain.Row;

import java.util.ArrayList;
import java.util.List;

public class SudokuBasicIncrementalScoreCalculator extends AbstractIncrementalScoreCalculator<Sudoku> {

    private List<SudokuNumber> insertedSudokuNumberList;
    private int score;

    public void resetWorkingSolution(Sudoku sudoku) {
        insertedSudokuNumberList = new ArrayList<SudokuNumber>(sudoku.getN());
        score = 0;
        for (SudokuNumber sudokuNumber : sudoku.getSudokuNumberList()) {
            insert(sudokuNumber);
        }
    }

    public void beforeEntityAdded(Object entity) {
        // Do nothing
    }

    public void afterEntityAdded(Object entity) {
        insert((SudokuNumber) entity);
    }

    public void beforeVariableChanged(Object entity, String variableName) {
        retract((SudokuNumber) entity);
    }

    public void afterVariableChanged(Object entity, String variableName) {
        insert((SudokuNumber) entity);
    }

    public void beforeEntityRemoved(Object entity) {
        retract((SudokuNumber) entity);
    }

    public void afterEntityRemoved(Object entity) {
        // Do nothing
    }

    private void insert(SudokuNumber sudokuNumber) {
        Row row = sudokuNumber.getRow();
        if (row != null) {
            for (SudokuNumber otherSudokuNumber : insertedSudokuNumberList) {
                if (sudokuNumber.getRowIndex() == otherSudokuNumber.getRowIndex()) {
                    score--;
                }
            }
            insertedSudokuNumberList.add(sudokuNumber);
        }
    }

    private void retract(SudokuNumber sudokuNumber) {
        Row row = sudokuNumber.getRow();
        if (row != null) {
            insertedSudokuNumberList.remove(sudokuNumber);
            for (SudokuNumber otherSudokuNumber : insertedSudokuNumberList) {
                if (sudokuNumber.getRowIndex() == otherSudokuNumber.getRowIndex()) {
                    score++;
                }
            }
        }
    }

    public SimpleScore calculateScore() {
        return SimpleScore.valueOf(score);
    }

}
