package algs;

public class MetaAlgs
{
    
    public static void main(String[] args)
    {
        // TODO Auto-generated method stub
        System.out.println(System.getProperty("user.dir"));
        try
        {
            ProblemInstance pi = DataReader.readFileForGraphMatrix(args[0]);
            System.out.println("AAA");
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
        }

    }

    
}
