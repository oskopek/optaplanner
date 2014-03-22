/*
 * Copyright 2011 JBoss Inc
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

package org.optaplanner.examples.sudoku.domain.solution;

import org.apache.commons.lang.builder.CompareToBuilder;
import org.optaplanner.core.impl.heuristic.selector.common.decorator.SelectionSorterWeightFactory;
import org.optaplanner.examples.sudoku.domain.Sudoku;
import org.optaplanner.examples.sudoku.domain.SudokuNumber;

public class SudokuNumberDifficultyWeightFactory implements SelectionSorterWeightFactory<Sudoku, SudokuNumber> {

    public Comparable createSorterWeight(Sudoku sudoku, SudokuNumber sNumber) {
        int distanceFromMiddle = calculateDistanceFromMiddle(sudoku.getN(), sNumber.getColumnIndex());
        return new SudokuNumberDifficultyWeight(sNumber, distanceFromMiddle);
    }

    private static int calculateDistanceFromMiddle(int n, int columnIndex) {
        int middle = n / 2;
        int distanceFromMiddle = Math.abs(columnIndex - middle);
        if ((n % 2 == 0) && (columnIndex < middle)) {
            distanceFromMiddle--;
        }
        return distanceFromMiddle;
    }

    public static class SudokuNumberDifficultyWeight implements Comparable<SudokuNumberDifficultyWeight> {

        private final SudokuNumber number;
        private final int distanceFromMiddle;

        public SudokuNumberDifficultyWeight(SudokuNumber number, int distanceFromMiddle) {
            this.number = number;
            this.distanceFromMiddle = distanceFromMiddle;
        }

        public int compareTo(SudokuNumberDifficultyWeight other) {
            return new CompareToBuilder()
                    // The more difficult numbers have a lower distance to the middle
                    .append(other.distanceFromMiddle, distanceFromMiddle) // Decreasing
                    // Tie breaker
                    .append(number.getColumnIndex(), other.number.getColumnIndex())
                    .append(number.getNumber(), other.number.getNumber())
                    .toComparison();
        }

    }

}
