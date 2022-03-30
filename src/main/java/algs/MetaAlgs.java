package algs;

import experiment.AllObjectiveValExperiment;
import experiment.AllObjectiveValNormExperiment;
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
//    warto�� funkcji celu / rozmiar
//    warto�� funkcji celu z normalizacj� / rozmiar
//    z�o�ono�� obliczeniowa / rozmiar
//    k random / k

    
    public static void main(String[] args)
    {
        System.out.println(System.getProperty("user.dir"));
        ProblemInstance problemInstance = null;
        
//        args[0] = "data\\tsp\\bier127.tsp\\bier127.tsp";
//        args.
        
        if (hasOption(args, "kRand"))
        {
            KRandomExperiment.generateData();
            return;
        }

        if (hasOption(args, "AllOV"))
        {
            AllObjectiveValExperiment.generateData();
            return;
        }

        if (hasOption(args, "NAllOV"))
        {
            AllObjectiveValNormExperiment.generateData();
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

    private static boolean hasOption(String[] args, String prefix)
    {
        for (String iterator : args)
        {
            if (iterator.startsWith(prefix))
            {
                return true;
            }
        }
        return false;
    }

    
}
