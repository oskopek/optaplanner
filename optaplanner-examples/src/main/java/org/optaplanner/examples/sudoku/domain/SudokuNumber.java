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

package org.optaplanner.examples.sudoku.domain;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import org.optaplanner.core.api.domain.entity.PlanningEntity;
import org.optaplanner.core.api.domain.variable.PlanningVariable;
import org.optaplanner.examples.common.domain.AbstractPersistable;
import org.optaplanner.examples.sudoku.domain.solution.SudokuNumberStrengthWeightFactory;
import org.optaplanner.examples.sudoku.domain.solution.SudokuNumberDifficultyWeightFactory;

@PlanningEntity//(difficultyWeightFactoryClass = SudokuNumberDifficultyWeightFactory.class)
@XStreamAlias("SudokuNumber")
public class SudokuNumber extends AbstractPersistable {

    private Column column;

    private Square square;

    private Row row;

    // Planning variables: changes during planning, between score calculations.
    private Number number;

    @PlanningVariable(valueRangeProviderRefs = {"numberRange"},
            strengthWeightFactoryClass = SudokuNumberStrengthWeightFactory.class)
    public Number getNumber() {
        return number;
    }

    public void setNumber(Number number) {
        this.number = number;
    }

    public Column getColumn() {
        return column;
    }

    public void setColumn(Column column) {
        this.column = column;
    }

    public Row getRow() {
        return row;
    }

    public void setRow(Row row) {
        this.row = row;
    }

    public Square getSquare() {
        return square;
    }

    public void setSquare(Square square) {
        this.square = square;
    }
// ************************************************************************
    // Complex methods
    // ************************************************************************

    public int getColumnIndex() {
        return column.getIndex();
    }

    public int getRowIndex() {
        if (row == null) {
            return Integer.MIN_VALUE;
        }
        return row.getIndex();
    }

    public int getSquareIndex() {
        if (square == null) {
            return Integer.MIN_VALUE;
        }
        return square.getIndex();
    }

    @Override
    public String toString() {
        return (number == null ? "null" : number) + ":" + row + "@" + column + "@" + square;
    }

}
