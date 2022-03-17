package solver;

import algs.ProblemInstance;
import solution.ProblemSolution;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

public class Opt2Solver implements ProblemSolver
{
    
    @Override
    public ProblemSolution solveInstance(ProblemInstance problemInstance)
    {
        int dimension = problemInstance.getDimension();
        ArrayList<Integer> startingSolution = generateRandomPath(dimension);
        return null;
    }


}
