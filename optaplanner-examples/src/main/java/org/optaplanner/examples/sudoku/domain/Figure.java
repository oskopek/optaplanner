/*
 * Copyright 2014 JBoss Inc
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
import org.optaplanner.examples.sudoku.domain.solution.FigureDifficultyWeightFactory;
import org.optaplanner.examples.sudoku.domain.solution.MovableFigureSelectionFilter;
import org.optaplanner.examples.sudoku.domain.solution.ValueStrengthWeightFactory;

@PlanningEntity(difficultyWeightFactoryClass = FigureDifficultyWeightFactory.class,
                movableEntitySelectionFilter = MovableFigureSelectionFilter.class)
@XStreamAlias("Figure")
public class Figure extends AbstractPersistable {

    private Column column;

    private Square square;

    private Row row;

    //MovableFigureSelectionFilter
    private boolean locked;

    // Planning variables: changes during planning, between score calculations.
    private Value value;

    @PlanningVariable(valueRangeProviderRefs = {"numberRange"},
            strengthWeightFactoryClass = ValueStrengthWeightFactory.class)
    public Value getValue() {
        return value;
    }

    public void setValue(Value value) {
        this.value = value;
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

    public boolean isLocked() {
        return locked;
    }

    public void setLocked(boolean locked) {
        this.locked = locked;
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
        return (value == null ? "null" : value) + ":" + row + "@" + column + "@" + square;
    }

}
