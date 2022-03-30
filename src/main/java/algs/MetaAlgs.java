package algs;

import experiment.KRandomExperiment;
import generator.BasicATSPProblemGenerator;
import generator.BasicTSPProblemGenerator;
import generator.ProblemGenerator;
import generator.RandomTypeE;
import parser.DataReader;

public class MetaAlgs
{
    private boolean generateRandom = false;
    static String algVersion = "";
    
//    TODO:
//    wartoœæ funkcji celu / rozmiar
//    wartoœæ funkcji celu z normalizacj¹ / rozmiar
//    z³o¿onoœæ obliczeniowa / rozmiar
//    k random / k

    
    public static void main(String[] args)
    {
        System.out.println(System.getProperty("user.dir"));
        ProblemInstance problemInstance = null;
        
//        args[0] = "data\\tsp\\bier127.tsp\\bier127.tsp";
//        args.
        
        if (hasOption(args, "kRand").isEmpty())
        {
            KRandomExperiment.generateData();
            return;
        }
        
        algVersion = hasOption(args, "-a=");
        if( !algVersion.isEmpty() )
        {
            RandomTypeE type = RandomTypeE.valueOf(algVersion);
            int size = Integer.parseInt(hasOption(args, "-size="));
            problemInstance = generateRandomProblemInstance(type, size);
        }
        else 
        {
            try
            {
                problemInstance = DataReader.readFileForGraphMatrix(args[0]);
            }
            catch (Exception e)
            {
                System.out.println(e.getMessage());
            }
        }

    }

    private static ProblemInstance generateRandomProblemInstance(RandomTypeE type, int size)
    {
        ProblemGenerator problemGenerator = null;
        switch(type)
        {
            case RandomATSP: 
                problemGenerator = new BasicATSPProblemGenerator();
                break;
            case RandomTSP:
                problemGenerator = new BasicTSPProblemGenerator();
                break;
            default:
                problemGenerator = new BasicTSPProblemGenerator();
                break;
        }
        return problemGenerator.generateProblemInstance(size);
    }

    private static String hasOption(String[] args, String prefix)
    {
        for (String iterator : args)
        {
            if (iterator.startsWith(prefix))
            {
                return iterator.substring(prefix.length());
            }
        }
        return "";
    }

    
}
