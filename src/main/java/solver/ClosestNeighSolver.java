package solver;

import algs.ProblemInstance;
import solution.ProblemSolution;

public class ClosestNeighSolver implements ProblemSolver
{
    int startingPoint = 0;
    
    public ClosestNeighSolver(int startingPoint)
    {
        super();
        this.startingPoint = startingPoint;
    }
    
    public ClosestNeighSolver()
    {

    }

    @Override
    public ProblemSolution solveInstance(ProblemInstance pI)
    {
        ProblemSolution solution = new ProblemSolution(startingPoint, pI);
        
        boolean[] visited = new boolean[pI.getDimension()];
        
        visited[startingPoint] = true;
        int current = startingPoint;
        for (int i = 0; i < pI.getDimension() - 1; i++)
        {
            int minNeigh = -1;
            for (int j = 0; j < pI.getDimension(); j++)
            {
                if (current != j && minNeigh == -1 && !visited[j])
                {
                    minNeigh = j;
                }
                else if (current != j && !visited[j] && pI.getValue(current, j) < pI.getValue(current, minNeigh))
                {
                    minNeigh = j;
                }
            }
            current = minNeigh;
            visited[current] = true;
            solution.addStep(current);
        }
        solution.addStep(startingPoint);
        
        
        return solution;
    }
    
}
