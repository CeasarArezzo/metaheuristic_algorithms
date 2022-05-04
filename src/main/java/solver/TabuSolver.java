package solver;

import algs.ProblemInstance;
import solution.ProblemSolution;
import tabu.BasicTabuList;
import tabu.TabuList;

import java.util.ArrayList;
import java.util.Collections;

public class TabuSolver implements ProblemSolver{

    private final int ageLimit;
    private final int iterations;
    public TabuSolver(int ageLimit, int iterations)
    {
        this.ageLimit = ageLimit;
        this.iterations = iterations;
    }

    @Override
    public ProblemSolution solveInstance(ProblemInstance problemInstance) {

        int dimension = problemInstance.getDimension();
        TabuList tabu = new BasicTabuList(dimension, ageLimit);

        ArrayList<Integer> currentSolution = generateRandomPath(dimension);
        ArrayList<Integer> localBest;
        int currentCost = ProblemSolution.getObjectiveValue(currentSolution.get(currentSolution.size()-1), currentSolution, problemInstance);

        int iterationsLeft = iterations;
        while(iterationsLeft > 0)
        {
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

                    ArrayList<Integer> newSolution = swap(i,j,localBest);
                    int newSolutionObjValue = ProblemSolution.getObjectiveValue(newSolution.get(newSolution.size()-1), newSolution, problemInstance);
                    if(newSolutionObjValue < localBestValue)
                    {
                        localBestValue = newSolutionObjValue;
                        localBest = new ArrayList<>(newSolution);
                        bestI = i;
                        bestJ = j;
                    }
                }

            }

            tabu.insert(bestI, bestJ);
            if (localBestValue < currentCost)
            {
                currentSolution = new ArrayList<>(localBest);
                currentCost = localBestValue;

            }
            else
            {
                iterationsLeft--;
            }



        }

        return new ProblemSolution(currentSolution.get(currentSolution.size()-1), currentCost, currentSolution, problemInstance);
    }


    private ArrayList<Integer> swap(int i, int j, ArrayList<Integer> prevSol)
    {
        ArrayList<Integer> newSol = new ArrayList<>(prevSol);
        Collections.swap(newSol, i, j);

        return newSol;
    }
}
