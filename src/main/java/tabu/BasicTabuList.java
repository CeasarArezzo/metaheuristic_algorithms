package tabu;

import java.util.LinkedList;

public class BasicTabuList implements TabuList
{
    private LinkedList<Integer> tabu;
    private int tabuMaxSize = 10;
    private int problemSize = -1;
    
    public BasicTabuList(int problemSize, int ageLimit)
    {
        this.problemSize = problemSize;
        this.tabuMaxSize = ageLimit;
        
        tabu = new LinkedList<Integer>();
    }
    
    @Override
    public void insert(int swap1, int swap2)
    {
        tabu.add(swapToInt(swap1, swap2));
        if (tabu.size() > tabuMaxSize)
        {
            tabu.remove();
        }
    }
    
    @Override
    public void delete(int swap1, int swap2)
    {
        tabu.remove(swapToInt(swap1, swap2));
        
    }
    
    @Override
    public boolean isTabu(int swap1, int swap2)
    {
        return tabu.contains(swapToInt(swap1, swap2));
    }

	@Override
	public void clear() 
	{
		tabu.clear();
	}
    
    private int swapToInt(int swap1, int swap2)
    {
        return Math.min(swap1, swap2) * problemSize + Math.max(swap1, swap2);
    }
}
