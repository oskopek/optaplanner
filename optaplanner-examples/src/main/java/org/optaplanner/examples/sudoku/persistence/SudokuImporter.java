package org.optaplanner.examples.sudoku.persistence;

import org.optaplanner.core.impl.solution.Solution;
import org.optaplanner.examples.common.persistence.AbstractTxtSolutionImporter;
import org.optaplanner.examples.sudoku.domain.Column;
import org.optaplanner.examples.sudoku.domain.Figure;
import org.optaplanner.examples.sudoku.domain.Row;
import org.optaplanner.examples.sudoku.domain.Square;
import org.optaplanner.examples.sudoku.domain.Sudoku;
import org.optaplanner.examples.sudoku.domain.Value;

import java.io.File;
import java.io.IOException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by oskopek on 3/22/14.
 */
public class SudokuImporter extends AbstractTxtSolutionImporter {

    private static final String INPUT_FILE_SUFFIX = "sud";

    public static void main(String[] args) {
        new SudokuImporter().convertAll();
    }

    public SudokuImporter() {
        super(new SudokuDao());
    }

    @Override
    public String getInputFileSuffix() {
        return INPUT_FILE_SUFFIX;
    }

    @Override
    public boolean acceptInputFileDuringBulkConvert(File inputFile) {
        // Blacklist: too slow to write as XML
        return !Arrays.asList("").contains(inputFile.getName());
    }

    public AbstractTxtSolutionImporter.TxtInputBuilder createTxtInputBuilder() {
        return new SudokuBuilder();
    }

    public static class SudokuBuilder extends AbstractTxtSolutionImporter.TxtInputBuilder {

        private Sudoku sudoku;

        private int nLocked;

        public Solution readSolution() throws IOException {
            sudoku = new Sudoku();
            sudoku.setId(0L);
            readHeaders();
            readLockedFigureList();
            readConstantLine("EOF");
            createFigureList();
            BigInteger possibleSolutionSize = factorial(sudoku.getFigureList().size() - 1);
            logger.info("Sudoku {} has {} figures, {} locked,  with a search space of {}.",
                    getInputId(),
                    sudoku.getFigureList().size(),
                    nLocked,
                    getFlooredPossibleSolutionSize(possibleSolutionSize));
            return sudoku;
        }

        private void readHeaders() throws IOException {
            sudoku.setName(readStringValue("NAME :"));
            sudoku.setN(readIntegerValue("N :"));
            nLocked = readIntegerValue("NLOCKED :");
        }

        private void readLockedFigureList() throws IOException {
            readConstantLine("LOCKED_FIGURE_SECTION");
            List<Figure> lockedFigureList = new ArrayList<Figure>(nLocked);
            for (int i = 0; i < nLocked; i++) {
                String line = bufferedReader.readLine();
                String[] lineTokens = splitBySpace(line, 1, 3);
                Figure figure = new Figure();
                Row row = new Row();
                row.setIndex(Integer.parseInt(lineTokens[0]));
                Column col = new Column();
                col.setIndex(Integer.parseInt(lineTokens[1]));
                Value val = new Value();
                val.setValue(Integer.parseInt(lineTokens[2]));
                figure.setRow(row);
                figure.setColumn(col);
                figure.setValue(val);
                figure.setLocked(true);
                lockedFigureList.add(figure);

            }
            sudoku.setFigureList(lockedFigureList);
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

        private List<Column> createColumnList() {
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

        private List<Row> createRowList() {
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

        private List<Value> createNumberList() {
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

        private List<Square> createSquareList() {
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

    }
}
