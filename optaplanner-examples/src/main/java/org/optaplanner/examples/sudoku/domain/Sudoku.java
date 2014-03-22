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

package org.optaplanner.examples.sudoku.domain;

import com.google.common.primitives.Ints;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamConverter;
import org.optaplanner.core.api.domain.solution.PlanningEntityCollectionProperty;
import org.optaplanner.core.api.domain.solution.PlanningSolution;
import org.optaplanner.core.api.domain.valuerange.ValueRangeProvider;
import org.optaplanner.core.api.score.buildin.simple.SimpleScore;
import org.optaplanner.core.impl.score.buildin.simple.SimpleScoreDefinition;
import org.optaplanner.core.impl.solution.Solution;
import org.optaplanner.examples.common.domain.AbstractPersistable;
import org.optaplanner.persistence.xstream.XStreamScoreConverter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@PlanningSolution
@XStreamAlias("Sudoku")
public class Sudoku extends AbstractPersistable implements Solution<SimpleScore> {

    private int n;

    private String name;

    // Problem facts
    private List<Column> columnList;
    private List<Row> rowList;
    private List<Value> valueList;
    private List<Square> squareList;

    // Planning entities
    private List<Figure> figureList;

    @XStreamConverter(value = XStreamScoreConverter.class, types = {SimpleScoreDefinition.class})
    private SimpleScore score;

    public int getN() {
        return n;
    }

    public void setN(int n) {
        int nSquares = nSquares(n);
        if(nSquares * nSquares != n) {
            throw new IllegalArgumentException("N must be a squared whole number: " + n);
        }
        this.n = n;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Column> getColumnList() {
        return columnList;
    }

    public void setColumnList(List<Column> columnList) {
        this.columnList = columnList;
    }

    public List<Row> getRowList() {
        return rowList;
    }

    public void setRowList(List<Row> rowList) {
        this.rowList = rowList;
    }

    @ValueRangeProvider(id = "numberRange")
    public List<Value> getValueList() {
        return valueList;
    }

    public void setValueList(List<Value> valueList) {
        this.valueList = valueList;
    }

    public List<Square> getSquareList() {
        return squareList;
    }

    public void setSquareList(List<Square> squareList) {
        this.squareList = squareList;
    }

    @PlanningEntityCollectionProperty
    public List<Figure> getFigureList() {
        return figureList;
    }

    public void setFigureList(List<Figure> figureList) {
        this.figureList = figureList;
    }

    public SimpleScore getScore() {
        return score;
    }

    public void setScore(SimpleScore score) {
        this.score = score;
    }

    // ************************************************************************
    // Complex methods
    // ************************************************************************

    public static int nSquares(int n) {
        return Ints.checkedCast(Math.round(Math.sqrt(n)));
    }

    public Collection<? extends Object> getProblemFacts() {
        List<Object> facts = new ArrayList<Object>();
        facts.addAll(columnList);
        facts.addAll(rowList);
        facts.addAll(valueList);
        facts.addAll(squareList);
        // Do not add the planning entity's (figureList) because that will be done automatically
        return facts;
    }

}
