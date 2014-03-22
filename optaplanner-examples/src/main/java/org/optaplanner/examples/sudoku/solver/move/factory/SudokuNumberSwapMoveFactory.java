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

package org.optaplanner.examples.sudoku.solver.move.factory;

import org.optaplanner.core.impl.heuristic.move.Move;
import org.optaplanner.core.impl.heuristic.selector.move.factory.MoveListFactory;
import org.optaplanner.examples.sudoku.domain.Sudoku;
import org.optaplanner.examples.sudoku.domain.SudokuNumber;
import org.optaplanner.examples.sudoku.domain.Row;
import org.optaplanner.examples.sudoku.solver.move.SudokuNumberSwapMove;

import java.util.ArrayList;
import java.util.List;

public class SudokuNumberSwapMoveFactory implements MoveListFactory<Sudoku> {

    public List<Move> createMoveList(Sudoku sudoku) {
        List<Move> moveList = new ArrayList<Move>();
        int n = sudoku.getN();
        List<SudokuNumber> sudokuNumbers = sudoku.getSudokuNumberList();
        for (int col = 0; col < n; col++) {
            SudokuNumber last = null;
            for (SudokuNumber num : sudokuNumbers) {
                if (num.getColumnIndex() == col) {
                    if (last == null) {
                        last = num;
                    } else {
                        moveList.add(new SudokuNumberSwapMove(last, num));
                        last = num;
                    }
                }
            }
        }
        System.out.println(moveList.size());
        return moveList;
    }

}
