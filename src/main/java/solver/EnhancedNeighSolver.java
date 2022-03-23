package solver;

import algs.ProblemInstance;
import solution.ProblemSolution;

public class EnhancedNeighSolver implements ProblemSolver
{
    
    @Override
    public ProblemSolution solveInstance(ProblemInstance problemInstance)
    {
        int iterations = problemInstance.getDimension();
        ProblemSolver solver = new ClosestNeighSolver(0);
        ProblemSolution toReturn = solver.solveInstance(problemInstance);
        
        for (int i = 1; i < iterations; i++)
        {
            ProblemSolver solverTmp = new ClosestNeighSolver(i);
            ProblemSolution newSolution = solverTmp.solveInstance(problemInstance);
            if (newSolution.getObjectiveValue() < toReturn.getObjectiveValue())
            {
                toReturn = newSolution;
            }
        }
        
        return toReturn;
    }
    
}
