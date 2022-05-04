package solver;

import algs.ProblemInstance;
import remission.RemissionList;
import remission.RemissionListNode;
import solution.ProblemSolution;
import tabu.BasicTabuList;
import tabu.TabuList;

import java.util.ArrayList;
import java.util.Collections;

public class TabuSolver implements ProblemSolver{

    private final int ageLimit;
    private final int iterations;

    private final boolean isIterDecAlways;

    private final boolean isRemissionEnabled;

    private final boolean isSwapNeighbourhood;
    private TabuList tabu;

    private final RemissionList remissionList = new RemissionList();

    private ArrayList<Integer> currentSolution;
    private int currentCost;
    ArrayList<Integer> previousLocalBest;
    private int previousLocalBestCost;
    public TabuSolver(int ageLimit, int iterations, boolean isIterDecAfter, boolean isRemissionEnabled, boolean isSwapNeighbourhood)
    {
        this.ageLimit = ageLimit;
        this.iterations = iterations;
        this.isIterDecAlways = isIterDecAfter;
        this.isRemissionEnabled = isRemissionEnabled;
        this.isSwapNeighbourhood = isSwapNeighbourhood;
    }

    @Override
    public ProblemSolution solveInstance(ProblemInstance problemInstance) {

        int dimension = problemInstance.getDimension();
        tabu = new BasicTabuList(dimension, ageLimit);

        currentSolution = generateRandomPath(dimension);
        ArrayList<Integer> localBest;
        currentCost = ProblemSolution.getObjectiveValue(currentSolution.get(currentSolution.size()-1), currentSolution, problemInstance);

        previousLocalBest = new ArrayList<>(currentSolution);
        previousLocalBestCost = currentCost;
        int iterationsLeft = iterations;


        while(iterationsLeft > 0)
        {
            boolean isChosen = false;
            localBest = new ArrayList<>(currentSolution);
            int localBestValue = Integer.MAX_VALUE;
            int bestI = 0;
            int bestJ = 0;

            for(int i = 0; i < dimension; i++)
            {
                for(int j = i + 1; j < dimension; j++)
                {
                    if(tabu.isTabu(i, j))
                    {
                        continue;
                    }


                    ArrayList<Integer> newSolution = invert(i,j,localBest);
                    int newSolutionObjValue = ProblemSolution.getObjectiveValue(newSolution.get(newSolution.size()-1), newSolution, problemInstance);
                    if(newSolutionObjValue < localBestValue)
                    {
                        localBestValue = newSolutionObjValue;
                        localBest = new ArrayList<>(newSolution);
                        bestI = i;
                        bestJ = j;
                        isChosen = true;
                    }
                }

            }

            if(remissionPull(isChosen))
            {
                continue;
            }

            tabu.insert(bestI, bestJ);

            remissionPush();

            iterationsLeft = updateIterationCounter(localBestValue, iterationsLeft);

            updatePaths(localBest, localBestValue);
        }

        return new ProblemSolution(currentSolution.get(currentSolution.size()-1), currentCost, currentSolution, problemInstance);
    }


    private ArrayList<Integer> swap(int i, int j, ArrayList<Integer> prevSol)
    {
        ArrayList<Integer> newSol = new ArrayList<>(prevSol);
        Collections.swap(newSol, i, j);

        return newSol;
    }

    private ArrayList<Integer> invert(int i, int j, ArrayList<Integer> prevSol)
    {
        ArrayList<Integer> newSol = new ArrayList<>(prevSol);
        Collections.reverse(newSol.subList(i, j));

        return newSol;
    }

    private boolean remissionPull(boolean isChosen)
    {
        if(isRemissionEnabled && !isChosen)
        {
            RemissionListNode remissionListNode = remissionList.getBestRemission();
            tabu = remissionListNode.getTabuList();
            currentSolution = new ArrayList<>(remissionListNode.getPath());
            currentCost = remissionListNode.getObjectiveValue();
            return true;
        }

        return false;
    }

    private void remissionPush()
    {
        if(isRemissionEnabled)
        {
            remissionList.addNewRemission(previousLocalBest, previousLocalBestCost, tabu);
        }
    }

    private void updatePaths(ArrayList<Integer> localBest, int localBestValue)
    {
        updateCurrentBest(localBest, localBestValue);
        updatePreviousLocalBest(localBest, localBestValue);
    }
    private void updatePreviousLocalBest(ArrayList<Integer> localBest, int localBestValue)
    {
        previousLocalBest = new ArrayList<>(localBest);
        previousLocalBestCost = localBestValue;
    }

    private void updateCurrentBest(ArrayList<Integer> localBest, int localBestValue)
    {
        if (localBestValue < currentCost)
        {
            currentSolution = new ArrayList<>(localBest);
            currentCost = localBestValue;

        }
    }

    private int updateIterationCounter(int localBestValue, int iterationsLeft)
    {
        if(isIterDecAlways || localBestValue < currentCost)
        {
            iterationsLeft--;
        }

        return iterationsLeft;
    }

    private ArrayList<Integer> getNeighbour(int i, int j, ArrayList<Integer> localBest)
    {
        if(isSwapNeighbourhood)
        {
            return swap(i,j,localBest);
        }

        return invert(i,j,localBest);
    }
}
