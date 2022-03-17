package algs;

import parser.DataReader;

public class MetaAlgs
{
    
    public static void main(String[] args)
    {
        System.out.println(System.getProperty("user.dir"));
        args[0] = "C:\\Users\\ceasa\\git\\metaheuristic_algorithms\\data\\tsp\\bier127.tsp\\bier127.tsp";
//        args.
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
