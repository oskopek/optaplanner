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

package org.optaplanner.examples.sudoku.persistence;

import org.optaplanner.examples.common.app.LoggingMain;
import org.optaplanner.examples.common.persistence.AbstractSolutionImporter;
import org.optaplanner.examples.common.persistence.SolutionDao;
import org.optaplanner.examples.sudoku.domain.*;
import org.optaplanner.examples.sudoku.domain.Value;

import java.io.File;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class SudokuGenerator extends LoggingMain {

    private static final File outputDir = new File("data/sudoku/unsolved/");

    protected SolutionDao solutionDao;

    public static void main(String[] args) {
        new SudokuGenerator().generate();
    }

    public void generate() {
        solutionDao = new SudokuDao();
        writeSudoku(1);
        writeSudoku(4);
        writeSudoku(9);
        writeSudoku(16);
        writeSudoku(25);
        writeSudoku(36);
        writeSudoku(49);
        writeSudoku(64);
    }

    private void writeSudoku(int n) {
        String outputFileName = n + "sudoku.xml";
        File outputFile = new File(outputDir, outputFileName);
        Sudoku sudoku = createSudoku(n);
        solutionDao.writeSolution(sudoku, outputFile);
    }

    public Sudoku createSudoku(int n) {
        Sudoku sudoku = new Sudoku();
        sudoku.setId(0L);
        sudoku.setN(n);
        sudoku.setColumnList(createColumnList(sudoku));
        sudoku.setRowList(createRowList(sudoku));
        sudoku.setValueList(createNumberList(sudoku));
        sudoku.setSquareList(createSquareList(sudoku));
        sudoku.setFigureList(createFigureList(sudoku));
        lockRandomFigures(sudoku); //TODO doesn't guarantee solvable sudokus
        BigInteger possibleSolutionSize = BigInteger.valueOf(sudoku.getN()).pow(sudoku.getN());
        logger.info("Sudoku {} has {} numbers with a search space of {}.",
                n, sudoku.getN(),
                AbstractSolutionImporter.getFlooredPossibleSolutionSize(possibleSolutionSize));
        return sudoku;
    }

    private List<Column> createColumnList(Sudoku sudoku) {
        int n = sudoku.getN();
        List<Column> columnList = new ArrayList<Column>(n);
        for (int i = 0; i < n; i++) {
            Column column = new Column();
            column.setId((long) i);
            column.setIndex(i);
            columnList.add(column);
        }
        return columnList;
    }

    private List<Row> createRowList(Sudoku sudoku) {
        int n = sudoku.getN();
        List<Row> rowList = new ArrayList<Row>(n);
        for (int i = 0; i < n; i++) {
            Row row = new Row();
            row.setId((long) i);
            row.setIndex(i);
            rowList.add(row);
        }
        return rowList;
    }

    private List<Value> createNumberList(Sudoku sudoku) {
        int n = sudoku.getN();
        List<Value> valueList = new ArrayList<Value>(n);
        for (int i = 1; i <= n; i++) {
            Value num = new Value();
            num.setId((long) i);
            num.setValue(i);
            valueList.add(num);
        }
        return valueList;
    }

    private List<Square> createSquareList(Sudoku sudoku) {
        int n = sudoku.getN();
        List<Square> squareList = new ArrayList<Square>(n);
        for (int i = 0; i < n; i++) {
            Square square = new Square();
            square.setId((long) i);
            square.setIndex(i);
            squareList.add(square);
        }
        return squareList;
    }

    private List<Figure> createFigureList(Sudoku sudoku) {
        int n = sudoku.getN();
        int nSquares = Sudoku.nSquares(n);
        List<Figure> figureList = new ArrayList<Figure>(n);
        long id = 0;
        for (Row row : sudoku.getRowList()) {
            for (Column col : sudoku.getColumnList()) {
                Figure sNumber = new Figure();
                sNumber.setId(id);
                id++;
                sNumber.setRow(row);
                sNumber.setColumn(col);
                int squareId = (((row.getIndex())/nSquares)*nSquares) + (col.getIndex()/nSquares);
                sNumber.setSquare(sudoku.getSquareList().get(squareId));
                sNumber.setValue(sudoku.getValueList().get(row.getIndex()));
                sNumber.setLocked(false);
                figureList.add(sNumber);
            }
        }
        return figureList;
    }

    private void lockRandomFigures(Sudoku sudoku) {
        Random rand = new Random();
        int counter = 0;
        for(Figure fig : sudoku.getFigureList()) {
            if(rand.nextFloat() < 0.15f) {
                counter++;
                fig.setLocked(true);
            }
        }
        logger.info("Locked {} figures", counter);
    }

}
