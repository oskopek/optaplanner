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
import org.optaplanner.examples.sudoku.domain.Figure;
import org.optaplanner.examples.sudoku.domain.Value;

import java.util.Arrays;
import java.util.Collection;

public class FigureSwapMove implements Move {

    private Figure figureLeft;
    private Figure figureRight;

    public FigureSwapMove(Figure figureLeft, Figure figureRight) {
        this.figureLeft = figureLeft;
        this.figureRight = figureRight;
    }

    public boolean isMoveDoable(ScoreDirector scoreDirector) {
        return !ObjectUtils.equals(figureLeft, figureRight);
    }

    public Move createUndoMove(ScoreDirector scoreDirector) {
        return new FigureSwapMove(figureRight, figureLeft);
    }

    public void doMove(ScoreDirector scoreDirector) {
        Value oldLeftValue = figureLeft.getValue();
        Value oldRightValue = figureRight.getValue();
        scoreDirector.beforeVariableChanged(figureLeft, "value"); // before changes are made
        figureLeft.setValue(oldRightValue);
        scoreDirector.afterVariableChanged(figureLeft, "value"); // after changes are made
        scoreDirector.beforeVariableChanged(figureRight, "value"); // before changes are made
        figureRight.setValue(oldLeftValue);
        scoreDirector.afterVariableChanged(figureRight, "value"); // after changes are made
    }

    public Collection<? extends Object> getPlanningEntities() {
        return Arrays.asList(figureLeft, figureRight);
    }

    public Collection<? extends Object> getPlanningValues() {
        return Arrays.asList(figureLeft.getValue(), figureRight.getValue());
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        } else if (o instanceof FigureSwapMove) {
            FigureSwapMove other = (FigureSwapMove) o;
            return new EqualsBuilder()
                    .append(figureLeft, other.figureLeft)
                    .append(figureRight, other.figureRight)
                    .isEquals();
        } else {
            return false;
        }
    }

    public int hashCode() {
        return new HashCodeBuilder()
                .append(figureLeft)
                .append(figureRight)
                .toHashCode();
    }

    public String toString() {
        return figureLeft + " <=> " + figureRight;
    }

}
