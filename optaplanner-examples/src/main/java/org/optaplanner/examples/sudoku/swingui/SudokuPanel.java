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

package org.optaplanner.examples.sudoku.swingui;

import org.optaplanner.core.impl.solution.Solution;
import org.optaplanner.examples.common.swingui.SolutionPanel;
import org.optaplanner.examples.common.swingui.TangoColorFactory;
import org.optaplanner.examples.sudoku.domain.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.List;

public class SudokuPanel extends SolutionPanel {

    //public static final String LOGO_PATH = "/org/optaplanner/examples/sudoku/swingui/sudokuLogo.png";

    public SudokuPanel() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBackground(Color.BLACK);

    }

    private Sudoku getSudoku() {
        return (Sudoku) solutionBusiness.getSolution();
    }

    public void resetPanel(Solution solution) {
        removeAll();
        repaint(); // When GridLayout doesn't fill up all the space
        Sudoku sudoku = (Sudoku) solution;
        int n = sudoku.getN();
        int nSquares = Sudoku.nSquares(n);
        if (n > 100) {
            JLabel tooBigToShowLabel = new JLabel("The dataset is too big to show.");
            tooBigToShowLabel.setForeground(Color.WHITE);
            add(tooBigToShowLabel);
            return;
        }
        List<Figure> figureList = sudoku.getFigureList();
        setLayout(new GridLayout(n, n));
        int sudokuNumberId = 0;
        for (int row = 0; row < n; row++) {
            for (int column = 0; column < n; column++) {
                Figure figure = figureList.get(sudokuNumberId);
                sudokuNumberId++;
                Value value = figure.getValue();
                if (figure.getColumnIndex() != column || figure.getRowIndex() != row) {
                    logger.warn("Figure expected row{}, col{}, sq{}. Got row{}, col{}, sq{}",
                            row, column, figure.getSquareIndex(), figure.getRowIndex(), figure.getColumnIndex(), figure.getSquareIndex());
                    throw new IllegalStateException("The figureList is not in the expected order.");
                }
                String text = value == null ? "null" : value.toString();
                String toolTipText = figure.toString();
                JPanel panel = new JPanel();
                panel.setBorder(BorderFactory.createMatteBorder(row % nSquares == 0 ? 3 : 1, column % nSquares == 0 ? 3 : 1, 1, 1, TangoColorFactory.ALUMINIUM_6));

                if (figure.isLocked()) {
                    panel.setBackground(Color.RED);
                } else {
                    panel.setBackground(Color.WHITE);
                }

                JLabel numberLabel = new JLabel(text);
                panel.add(numberLabel);
                //panel.addMouseListener(new FigureAction(figure)); // TODO
                panel.setToolTipText(toolTipText);
                add(panel);

            }
        }
    }

    private class FigureAction extends AbstractAction {

        private Figure figure;

        public FigureAction(Figure figure) {
            super(null);
            this.figure = figure;
        }

        public void actionPerformed(ActionEvent e) {
            List<Row> rowList = getSudoku().getRowList();
            JPanel messagePanel = new JPanel(new BorderLayout());
            messagePanel.add(new JLabel("Move to row: "), BorderLayout.WEST);
            JComboBox rowListField = new JComboBox(rowList.toArray());
            rowListField.setSelectedItem(figure.getRow());
            messagePanel.add(rowListField, BorderLayout.CENTER);
            int result = JOptionPane.showConfirmDialog(SudokuPanel.this.getRootPane(), messagePanel,
                    "Figure in column " + figure.getColumn().getIndex(),
                    JOptionPane.OK_CANCEL_OPTION);
            if (result == JOptionPane.OK_OPTION) {
                Row toRow = (Row) rowListField.getSelectedItem();
                solutionBusiness.doChangeMove(figure, "row", toRow);
                solverAndPersistenceFrame.resetScreen();
            }
        }

    }

}
