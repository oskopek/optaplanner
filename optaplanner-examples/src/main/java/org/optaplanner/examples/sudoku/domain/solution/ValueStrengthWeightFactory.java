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
import org.optaplanner.examples.sudoku.domain.Value;

public class ValueStrengthWeightFactory implements SelectionSorterWeightFactory<Sudoku, Value> {

    public Comparable createSorterWeight(Sudoku sudoku, Value value) {
        int distanceFromMiddle = calculateDistanceFromMiddle(sudoku.getN(), value.getValue());
        return new NumberStrengthWeight(value, distanceFromMiddle);
    }

    private static int calculateDistanceFromMiddle(int n, int columnIndex) {
        int middle = n / 2;
        int distanceFromMiddle = Math.abs(columnIndex - middle);
        if ((n % 2 == 0) && (columnIndex < middle)) {
            distanceFromMiddle--;
        }
        return distanceFromMiddle;
    }

    public static class NumberStrengthWeight implements Comparable<NumberStrengthWeight> {

        private final Value value;
        private final int distanceFromMiddle;

        public NumberStrengthWeight(Value value, int distanceFromMiddle) {
            this.value = value;
            this.distanceFromMiddle = distanceFromMiddle;
        }

        public int compareTo(NumberStrengthWeight other) {
            return new CompareToBuilder()
                    // The stronger sudokuNumbers are on the side, so they have a higher distance to the middle
                    .append(distanceFromMiddle, other.distanceFromMiddle)
                    .append(value.getValue(), other.value.getValue())
                    .toComparison();
        }

    }

}
