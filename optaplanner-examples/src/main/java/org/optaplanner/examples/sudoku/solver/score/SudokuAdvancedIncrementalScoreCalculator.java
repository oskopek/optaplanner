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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SudokuAdvancedIncrementalScoreCalculator extends AbstractIncrementalScoreCalculator<Sudoku> {

    private Map<Integer, List<SudokuNumber>> rowIndexMap;
    private Map<Integer, List<SudokuNumber>> ascendingDiagonalIndexMap;
    private Map<Integer, List<SudokuNumber>> descendingDiagonalIndexMap;

    private int score;

    public void resetWorkingSolution(Sudoku sudoku) {
        int n = sudoku.getN();
        rowIndexMap = new HashMap<Integer, List<SudokuNumber>>(n);
        ascendingDiagonalIndexMap = new HashMap<Integer, List<SudokuNumber>>(n * 2);
        descendingDiagonalIndexMap = new HashMap<Integer, List<SudokuNumber>>(n * 2);
        for (int i = 0; i < n; i++) {
            rowIndexMap.put(i, new ArrayList<SudokuNumber>(n));
            ascendingDiagonalIndexMap.put(i, new ArrayList<SudokuNumber>(n));
            descendingDiagonalIndexMap.put(i, new ArrayList<SudokuNumber>(n));
            if (i != 0) {
                ascendingDiagonalIndexMap.put(n - 1 + i, new ArrayList<SudokuNumber>(n));
                descendingDiagonalIndexMap.put((-i), new ArrayList<SudokuNumber>(n));
            }
        }
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
            int rowIndex = sudokuNumber.getRowIndex();
            List<SudokuNumber> rowIndexList = rowIndexMap.get(rowIndex);
            score -= rowIndexList.size();
            rowIndexList.add(sudokuNumber);
        }
    }

    private void retract(SudokuNumber sudokuNumber) {
        Row row = sudokuNumber.getRow();
        if (row != null) {
            List<SudokuNumber> rowIndexList = rowIndexMap.get(sudokuNumber.getRowIndex());
            rowIndexList.remove(sudokuNumber);
            score += rowIndexList.size();
        }
    }

    public SimpleScore calculateScore() {
        return SimpleScore.valueOf(score);
    }

}
