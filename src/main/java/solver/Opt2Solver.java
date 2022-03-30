package solver;

import algs.ProblemInstance;
import solution.ProblemSolution;

import java.lang.reflect.Array;
import java.util.*;

public class Opt2Solver implements ProblemSolver
{
    
    @Override
    public ProblemSolution solveInstance(ProblemInstance problemInstance)
    {
        int dimension = problemInstance.getDimension();
        ArrayList<Integer> currentSolution = generateRandomPath(dimension);
        ArrayList<Integer> localBest;
        int currentCost = ProblemSolution.getObjectiveValue(currentSolution.get(currentSolution.size()-1), currentSolution, problemInstance);

        boolean isRunning = true;
        while(isRunning)
        {
            isRunning = false;
            localBest = new ArrayList<>(currentSolution);
            int localBestValue = ProblemSolution.getObjectiveValue(currentSolution.get(currentSolution.size()-1), currentSolution, problemInstance);
            for(int i = 0; i < dimension; i++)
            {
                for(int j = i + 1; j < dimension; j++)
                {
                    ArrayList<Integer> newSolution = invert(i,j,currentSolution);
                    int newSolutionObjValue = ProblemSolution.getObjectiveValue(newSolution.get(newSolution.size()-1), newSolution, problemInstance);
                    if(newSolutionObjValue < localBestValue)
                    {
                        localBestValue = newSolutionObjValue;
                        localBest = new ArrayList<>(newSolution);
                    }
                }

            }
            if (localBestValue < currentCost)
            {
                currentSolution = new ArrayList<>(localBest);
                currentCost = localBestValue;
                isRunning = true;
            }
        }

        return new ProblemSolution(currentSolution.get(currentSolution.size()-1), currentCost, currentSolution, problemInstance);
    }



    private ArrayList<Integer> invert(int i, int j, ArrayList<Integer> prevSol)
    {
        ArrayList<Integer> newSol = new ArrayList<>(prevSol);
        Collections.reverse(newSol.subList(i, j));

        return newSol;
    }


}
