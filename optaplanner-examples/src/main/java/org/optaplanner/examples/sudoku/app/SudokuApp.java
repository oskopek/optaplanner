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

package org.optaplanner.examples.sudoku.app;

import org.optaplanner.core.api.solver.Solver;
import org.optaplanner.core.config.constructionheuristic.ConstructionHeuristicSolverPhaseConfig;
import org.optaplanner.core.config.heuristic.selector.common.SelectionOrder;
import org.optaplanner.core.config.heuristic.selector.move.MoveSelectorConfig;
import org.optaplanner.core.config.heuristic.selector.move.generic.ChangeMoveSelectorConfig;
import org.optaplanner.core.config.localsearch.LocalSearchSolverPhaseConfig;
import org.optaplanner.core.config.localsearch.decider.acceptor.AcceptorConfig;
import org.optaplanner.core.config.phase.SolverPhaseConfig;
import org.optaplanner.core.config.score.director.ScoreDirectorFactoryConfig;
import org.optaplanner.core.config.solver.SolverConfig;
import org.optaplanner.core.config.solver.XmlSolverFactory;
import org.optaplanner.core.config.solver.termination.TerminationConfig;
import org.optaplanner.examples.common.app.CommonApp;
import org.optaplanner.examples.common.persistence.SolutionDao;
import org.optaplanner.examples.common.swingui.SolutionPanel;
import org.optaplanner.examples.sudoku.domain.Figure;
import org.optaplanner.examples.sudoku.domain.Sudoku;
import org.optaplanner.examples.sudoku.persistence.SudokuDao;
import org.optaplanner.examples.sudoku.swingui.SudokuPanel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class SudokuApp extends CommonApp {

    public static final String SOLVER_CONFIG
            = "/org/optaplanner/examples/sudoku/solver/sudokuSolverConfig.xml";

    public static void main(String[] args) {
        prepareSwingEnvironment();
        new SudokuApp().init();
    }

    public SudokuApp() {
        super("Sudoku",
                "Solve a Sudoku puzzle.\n\n" +
                        "The numbers must only occur once in rows, columns and squares.", null);
                //SudokuPanel.LOGO_PATH);
    }

    @Override
    protected Solver createSolver() {
        return createSolverByXml();
    }

    /**
     * Normal way to create a {@link org.optaplanner.core.api.solver.Solver}.
     * @return never null
     */
    protected Solver createSolverByXml() {
        XmlSolverFactory solverFactory = new XmlSolverFactory(SOLVER_CONFIG);
        return solverFactory.buildSolver();
    }

    /**
     * Unused alternative. Abnormal way to create a {@link org.optaplanner.core.api.solver.Solver}.
     * <p/>
     * Not recommended! It is recommended to use {@link #createSolverByXml()} instead.
     * @return never null
     */
    protected Solver createSolverByApi() {
        SolverConfig solverConfig = new SolverConfig();

        solverConfig.setSolutionClass(Sudoku.class);
        solverConfig.setPlanningEntityClassList(Collections.<Class<?>>singletonList(Figure.class));

        ScoreDirectorFactoryConfig scoreDirectorFactoryConfig = new ScoreDirectorFactoryConfig();
        scoreDirectorFactoryConfig.setScoreDefinitionType(ScoreDirectorFactoryConfig.ScoreDefinitionType.SIMPLE);
        scoreDirectorFactoryConfig.setScoreDrlList(
                Arrays.asList("/org/optaplanner/examples/sudoku/solver/sudokuScoreRules.drl"));
        solverConfig.setScoreDirectorFactoryConfig(scoreDirectorFactoryConfig);

        TerminationConfig terminationConfig = new TerminationConfig();
        terminationConfig.setBestScoreLimit("0");
        solverConfig.setTerminationConfig(terminationConfig);
        List<SolverPhaseConfig> solverPhaseConfigList = new ArrayList<SolverPhaseConfig>();
        ConstructionHeuristicSolverPhaseConfig constructionHeuristicSolverPhaseConfig
                = new ConstructionHeuristicSolverPhaseConfig();
        constructionHeuristicSolverPhaseConfig.setConstructionHeuristicType(
                ConstructionHeuristicSolverPhaseConfig.ConstructionHeuristicType.FIRST_FIT_DECREASING);
        solverPhaseConfigList.add(constructionHeuristicSolverPhaseConfig);
        LocalSearchSolverPhaseConfig localSearchSolverPhaseConfig = new LocalSearchSolverPhaseConfig();
        ChangeMoveSelectorConfig changeMoveSelectorConfig = new ChangeMoveSelectorConfig();
        changeMoveSelectorConfig.setSelectionOrder(SelectionOrder.ORIGINAL);
        localSearchSolverPhaseConfig.setMoveSelectorConfigList(
                Arrays.<MoveSelectorConfig>asList(changeMoveSelectorConfig));
        AcceptorConfig acceptorConfig = new AcceptorConfig();
        acceptorConfig.setEntityTabuSize(5);
        localSearchSolverPhaseConfig.setAcceptorConfig(acceptorConfig);
        solverPhaseConfigList.add(localSearchSolverPhaseConfig);
        solverConfig.setSolverPhaseConfigList(solverPhaseConfigList);
        return solverConfig.buildSolver();
    }

    @Override
    protected SolutionPanel createSolutionPanel() {
        return new SudokuPanel();
    }

    @Override
    protected SolutionDao createSolutionDao() {
        return new SudokuDao();
    }

}
