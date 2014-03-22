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

package org.optaplanner.examples.sudoku.solver.move;

import org.apache.commons.lang.ObjectUtils;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.optaplanner.core.impl.heuristic.move.Move;
import org.optaplanner.core.impl.score.director.ScoreDirector;
import org.optaplanner.examples.sudoku.domain.Number;
import org.optaplanner.examples.sudoku.domain.SudokuNumber;

import java.util.Arrays;
import java.util.Collection;

public class SudokuNumberSwapMove implements Move {

    private SudokuNumber sudokuNumberLeft;
    private SudokuNumber sudokuNumberRight;

    public SudokuNumberSwapMove(SudokuNumber sudokuNumberLeft, SudokuNumber sudokuNumberRight) {
        this.sudokuNumberLeft = sudokuNumberLeft;
        this.sudokuNumberRight = sudokuNumberRight;
    }

    public boolean isMoveDoable(ScoreDirector scoreDirector) {
        return !ObjectUtils.equals(sudokuNumberLeft.getColumn(), sudokuNumberRight.getColumn());
    }

    public Move createUndoMove(ScoreDirector scoreDirector) {
        return new SudokuNumberSwapMove(sudokuNumberRight, sudokuNumberLeft);
    }

    public void doMove(ScoreDirector scoreDirector) {
        Number oldLeftNumber = sudokuNumberLeft.getNumber();
        Number oldRightNumber = sudokuNumberRight.getNumber();
        scoreDirector.beforeVariableChanged(sudokuNumberLeft, "number"); // before changes are made
        sudokuNumberLeft.setNumber(oldRightNumber);
        scoreDirector.afterVariableChanged(sudokuNumberLeft, "number"); // after changes are made
        scoreDirector.beforeVariableChanged(sudokuNumberRight, "number"); // before changes are made
        sudokuNumberRight.setNumber(oldLeftNumber);
        scoreDirector.afterVariableChanged(sudokuNumberRight, "number"); // after changes are made
    }

    public Collection<? extends Object> getPlanningEntities() {
        return Arrays.asList(sudokuNumberLeft, sudokuNumberRight);
    }

    public Collection<? extends Object> getPlanningValues() {
        return Arrays.asList(sudokuNumberLeft.getNumber(), sudokuNumberRight.getNumber());
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        } else if (o instanceof SudokuNumberSwapMove) {
            SudokuNumberSwapMove other = (SudokuNumberSwapMove) o;
            return new EqualsBuilder()
                    .append(sudokuNumberLeft, other.sudokuNumberLeft)
                    .append(sudokuNumberRight, other.sudokuNumberRight)
                    .isEquals();
        } else {
            return false;
        }
    }

    public int hashCode() {
        return new HashCodeBuilder()
                .append(sudokuNumberLeft)
                .append(sudokuNumberRight)
                .toHashCode();
    }

    public String toString() {
        return sudokuNumberLeft + " <=> " + sudokuNumberRight;
    }

}
