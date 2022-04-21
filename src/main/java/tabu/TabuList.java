package tabu;

public interface TabuList
{
    void insert(int swap1, int swap2);
    void delete(int swap1, int swap2);
    boolean isTabu(int swap1, int swap2);
    void clear();
}
