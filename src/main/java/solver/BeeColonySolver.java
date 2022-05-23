package solver;

import java.util.ArrayList;

import algs.ProblemInstance;
import solution.ProblemSolution;

public class BeeColonySolver implements ProblemSolver
{
    int populationSize = 0;
    private ArrayList<ArrayList<Integer>> foodSources;
    private ProblemInstance pInstance;
    
    public BeeColonySolver(int populationSize)
    {
        this.populationSize = populationSize;
    }
    
    @Override
    public ProblemSolution solveInstance(ProblemInstance problemInstance)
    {
        initializationPhase();
        
        while( !terminationCriteriaFullfiled())
        {
            EmployeedBeePhase();
            OnlookerBeePhase();
            ScoutBeePhase();
        }
        
        return new ProblemSolution(foodSources.get(0), pInstance);
    }

    private void EmployeedBeePhase()
    {
        // TODO Auto-generated method stub
        
    }

    private void OnlookerBeePhase()
    {
        // TODO Auto-generated method stub
        
    }

    private void ScoutBeePhase()
    {
        // TODO Auto-generated method stub
        
    }

    private boolean terminationCriteriaFullfiled()
    {
        return false;
    }

    private void initializationPhase()
    {
        foodSources = new ArrayList<ArrayList<Integer>>(populationSize);
    }
    
}
