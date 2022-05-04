package remission;

import tabu.TabuList;

import java.util.ArrayList;
import java.util.PriorityQueue;

public class RemissionList
{


    private final PriorityQueue<RemissionListNode> remissionList = new PriorityQueue<>();

    public void addNewRemission(ArrayList<Integer> path, int objectiveValue, TabuList tabuList)
    {
        remissionList.add(new RemissionListNode(path, objectiveValue, tabuList));
    }

    public RemissionListNode getBestRemission()
    {
        return  remissionList.poll();
    }
}
