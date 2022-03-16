package algs;

public class MetaAlgs
{
    
    public static void main(String[] args)
    {
        System.out.println(System.getProperty("user.dir"));
        try
        {
            ProblemInstance pi = DataReader.readFileForGraphMatrix(args[0]);
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
        }

    }

    
}
