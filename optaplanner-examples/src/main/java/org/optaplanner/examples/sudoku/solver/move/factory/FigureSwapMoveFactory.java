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
import org.optaplanner.examples.sudoku.domain.Figure;
import org.optaplanner.examples.sudoku.solver.move.FigureSwapMove;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

public class FigureSwapMoveFactory implements MoveListFactory<Sudoku> {

    public List<Move> createMoveList(Sudoku sudoku) {
    List<Figure> figureList = sudoku.getFigureList();
    List<Move> moveList = new ArrayList<Move>();
    for (ListIterator<Figure> leftIt = figureList.listIterator(); leftIt.hasNext();) {
        Figure leftFigure = leftIt.next();
        for (ListIterator<Figure> rightIt = figureList.listIterator(leftIt.nextIndex()); rightIt.hasNext();) {
            Figure rightFigure = rightIt.next();
            moveList.add(new FigureSwapMove(leftFigure, rightFigure));
        }
    }
        System.out.println(moveList.size());
        return moveList;
    }

}
