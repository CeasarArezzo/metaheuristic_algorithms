package solution;

import java.util.ArrayList;

import algs.ProblemInstance;

public class ProblemSolution
{
    int startingPoint;
    int objectiveValue;
    ArrayList<Integer> path;
    ProblemInstance problemInstance;
    
    public ProblemSolution(ProblemInstance pInstance)
    {
        this.startingPoint = 0;
        this.problemInstance = pInstance;
        path = new ArrayList<>();
    }
    
    public ProblemSolution(int startingPoint, ProblemInstance pInstance)
    {
        this.startingPoint = startingPoint;
        this.problemInstance = pInstance;
        path = new ArrayList<>();
    }

    public ProblemSolution(int startingPoint, int objectiveValue, ProblemInstance pInstance)
    {
        this.startingPoint = startingPoint;
        this.objectiveValue = objectiveValue;
        this.problemInstance = pInstance;
        path = new ArrayList<>();
    }


    public void addStep(int nextVertex)
    {
        path.add(nextVertex);
    }
    
    public ArrayList<Integer> getPath()
    {
        return path;
    }
    
    public int getObjectiveValue()
    {
        int value = 0;
        int lastVertex = startingPoint;
        for (Integer integer : path)
        {
            value += problemInstance.getGraphMatrix()[lastVertex][integer];
            lastVertex = integer;
        }
        return value;
    }
    
    public static int getObjectiveValue(int startingPoint, ArrayList<Integer> path, ProblemInstance problemInstance)
    {
        int value = 0;
        int lastVertex = startingPoint;
        for (Integer integer : path)
        {
            value += problemInstance.getGraphMatrix()[lastVertex][integer];
            lastVertex = integer;
        }
        return value;
    }
    
}
