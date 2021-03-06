package solver;

import algs.ProblemInstance;
import solution.ProblemSolution;

import java.util.ArrayList;

public class KRandomSolver implements ProblemSolver
{
    private final int numOfIterations;

    public KRandomSolver(int numOfIterations)
    {
        this.numOfIterations = numOfIterations;
    }

    @Override
    public ProblemSolution solveInstance(ProblemInstance problemInstance)
    {
        int dimension = problemInstance.getDimension();

        ArrayList<Integer> bestPath = new ArrayList<>();
        int bestValue = Integer.MAX_VALUE;
        for(int i = 0; i < numOfIterations; i++)
        {
            ArrayList<Integer> currPath = generateRandomPath(dimension);
            int currValue = ProblemSolution.getObjectiveValue(currPath.get(currPath.size()-1), currPath, problemInstance);
            if(currValue < bestValue)
            {
                bestValue = currValue;
                bestPath = currPath;
            }
        }

        return new ProblemSolution(bestPath.get(bestPath.size()-1), bestValue, bestPath, problemInstance);
    }


}
