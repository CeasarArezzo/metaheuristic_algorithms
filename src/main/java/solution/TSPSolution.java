package solution;

import java.util.ArrayList;

import algs.ProblemInstance;

public class TSPSolution
{
    int startingPoint;
    ArrayList<Integer> path;
    ProblemInstance problemInstance;
    
    public TSPSolution(ProblemInstance pInstance)
    {
        this.startingPoint = 0;
        this.problemInstance = pInstance;
        path = new ArrayList<>();
    }
    
    public TSPSolution(int startingPoint, ProblemInstance pInstance)
    {
        this.startingPoint = startingPoint;
        this.problemInstance = pInstance;
        path = new ArrayList<>();
    }
    
    void addStep(int nextVertex)
    {
        path.add(nextVertex);
    }
    
    int getObjectiveValue()
    {
        int value = 0;
        int lastVertex = startingPoint;
        for (int i = 0; i < path.size(); i++)
        {
            value += problemInstance.getGraphMatrix()[lastVertex][path.get(i)];
            lastVertex = path.get(i);
        }
        return value;
    }
    
    static int getObjectiveValue(int startingPoint, ArrayList<Integer> path, ProblemInstance problemInstance)
    {
        int value = 0;
        int lastVertex = startingPoint;
        for (int i = 0; i < path.size(); i++)
        {
            value += problemInstance.getGraphMatrix()[lastVertex][path.get(i)];
            lastVertex = path.get(i);
        }
        return value;
    }
    
}
