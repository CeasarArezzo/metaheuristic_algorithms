package solver;

import algs.ProblemInstance;
import solution.ProblemSolution;

import java.util.*;

public class Opt2Solver implements ProblemSolver
{
    
    @Override
    public ProblemSolution solveInstance(ProblemInstance problemInstance)
    {
        int dimension = problemInstance.getDimension();
        ArrayList<Integer> startingSolution = generateRandomPath(dimension);
        ArrayList<Integer> bestPath = new ArrayList<>();
        int bestValue = -1;
        NeighbourHolder bestNeighbour;

        while(true)
        {
            bestNeighbour = getBestNeighbour(startingSolution, problemInstance);
            if(bestNeighbour.objectiveValue < bestValue)
            {
                bestValue = bestNeighbour.objectiveValue;
                bestPath = bestNeighbour.path;
                continue;
            }
            break;
        }

        return new ProblemSolution(bestPath.get(bestPath.size()-1), bestNeighbour.objectiveValue, bestPath, problemInstance);
    }

    private NeighbourHolder getBestNeighbour(ArrayList<Integer> startingSolution, ProblemInstance problemInstance)
    {
        ArrayList<ArrayList<Integer>> neighbours = generateAllNeighbours(startingSolution, problemInstance);
        int dimension = problemInstance.getDimension();   
        ArrayList<Integer> bestNeighbour = neighbours.get(0);
        int bestNeighbourValue = ProblemSolution.getObjectiveValue(bestNeighbour.get(bestNeighbour.size()-1), bestNeighbour, problemInstance);
        
        for(int i = 1; i < dimension; i++)
        {
            ArrayList<Integer> currNeighbour = neighbours.get(i);
            int currValue = ProblemSolution.getObjectiveValue(currNeighbour.get(currNeighbour.size()-1), currNeighbour, problemInstance);

            if( currValue > bestNeighbourValue)
            {
                bestNeighbourValue = currValue;
                bestNeighbour = currNeighbour;
            }
        }
        
        return new NeighbourHolder(bestNeighbour, bestNeighbourValue);
    }

    private ArrayList<ArrayList<Integer>> generateAllNeighbours(ArrayList<Integer> startingSolution, ProblemInstance problemInstance)
    {
        ArrayList<ArrayList<Integer>> neighbours = new ArrayList<>();
        int dimension = problemInstance.getDimension();
        for(int i = 0; i < dimension; i++)
        {
            for(int j = i + 1; j < dimension; j++)
            {
                ArrayList<Integer> newNeighbour = new ArrayList<>(startingSolution);
                Collections.swap(newNeighbour, i, j);
                neighbours.add(newNeighbour);
            }
        }

        return neighbours;
    }

    private record NeighbourHolder(ArrayList<Integer> path, int objectiveValue)
    {
    }
}
