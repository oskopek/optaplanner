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
import javax.swing.border.MatteBorder;
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
        List<SudokuNumber> sudokuNumberList = sudoku.getSudokuNumberList();
        setLayout(new GridLayout(n, n));
        int sudokuNumberId = 0;
        for (int row = 0; row < n; row++) {
            for (int column = 0; column < n; column++) {
                SudokuNumber sudokuNumber = sudokuNumberList.get(sudokuNumberId);
                sudokuNumberId++;
                org.optaplanner.examples.sudoku.domain.Number value = sudokuNumber.getNumber();
                if (sudokuNumber.getColumnIndex() != column || sudokuNumber.getRowIndex() != row) {
                    logger.warn("SudokuNumber expected row{}, col{}, sq{}. Got row{}, col{}, sq{}",
                            row, column, sudokuNumber.getSquareIndex(), sudokuNumber.getRowIndex(), sudokuNumber.getColumnIndex(), sudokuNumber.getSquareIndex());
                    throw new IllegalStateException("The sudokuNumberList is not in the expected order.");
                }
                String text = value == null ? "null" : value.toString();
                String toolTipText = sudokuNumber.toString();
                JPanel panel = new JPanel();
                panel.setBorder(BorderFactory.createMatteBorder(row % nSquares == 0 ? 3 : 1, column % nSquares == 0 ? 3 : 1, 1, 1, TangoColorFactory.ALUMINIUM_6));
                panel.setBackground(Color.WHITE);
                JLabel numberLabel = new JLabel(text);
                panel.add(numberLabel);
                //panel.addMouseListener(new SudokuNumberAction(sudokuNumber));
                panel.setToolTipText(toolTipText);
                add(panel);

            }
        }
    }

    private class SudokuNumberAction extends AbstractAction {

        private SudokuNumber sudokuNumber;

        public SudokuNumberAction(SudokuNumber sudokuNumber) {
            super(null);
            this.sudokuNumber = sudokuNumber;
        }

        public void actionPerformed(ActionEvent e) {
            List<Row> rowList = getSudoku().getRowList();
            JPanel messagePanel = new JPanel(new BorderLayout());
            messagePanel.add(new JLabel("Move to row: "), BorderLayout.WEST);
            JComboBox rowListField = new JComboBox(rowList.toArray());
            rowListField.setSelectedItem(sudokuNumber.getRow());
            messagePanel.add(rowListField, BorderLayout.CENTER);
            int result = JOptionPane.showConfirmDialog(SudokuPanel.this.getRootPane(), messagePanel,
                    "SudokuNumber in column " + sudokuNumber.getColumn().getIndex(),
                    JOptionPane.OK_CANCEL_OPTION);
            if (result == JOptionPane.OK_OPTION) {
                Row toRow = (Row) rowListField.getSelectedItem();
                solutionBusiness.doChangeMove(sudokuNumber, "row", toRow);
                solverAndPersistenceFrame.resetScreen();
            }
        }

    }

}
