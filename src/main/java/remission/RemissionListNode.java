package remission;

import tabu.TabuList;

import java.util.ArrayList;

public class RemissionListNode implements Comparable<RemissionListNode>
{
    private final ArrayList<Integer> path;
    private final int objectiveValue;
    private final TabuList tabuList;

    RemissionListNode(ArrayList<Integer> path, int objectiveValue, TabuList tabuList)
    {
        this.path = path;
        this.objectiveValue = objectiveValue;
        this.tabuList = tabuList;
    }

    @Override
    public int compareTo(RemissionListNode o)
    {
        return Integer.compare(this.objectiveValue, o.objectiveValue);
    }

    public int getObjectiveValue()
    {
        return objectiveValue;
    }

    public TabuList getTabuList()
    {
        return tabuList;
    }

    public ArrayList<Integer> getPath()
    {
        return path;
    }
}