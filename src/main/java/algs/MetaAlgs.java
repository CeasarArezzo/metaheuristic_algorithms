package algs;

import java.io.FileNotFoundException;

import experiment.bees.*;
import org.apache.commons.math3.stat.inference.WilcoxonSignedRankTest;

import experiment.AllObjectiveValExperiment;
import experiment.AllObjectiveValNormExperiment;
import experiment.KRandomExperiment;
import experiment.TabuSearchAgeLimitExperiment;
import experiment.TabuSearchExperiment;
import experiment.TabuSearchRemissionExperiment;
import experiment.TabuSearchSwapInvert;
import experiment.TabuSearchTimeComplexityExperiment;
import experiment.TabuSearchTimeEfficiencyExperiment;
import experiment.TimeComplexityExperiment;
import generator.BasicATSPProblemGenerator;
import generator.BasicTSPProblemGenerator;
import generator.ProblemGenerator;
import generator.RandomTypeE;
import parser.DataReader;
import parser.WrongNumberException;
import solver.EnhancedNeighSolver;
import solver.Opt2Solver;

public class MetaAlgs
{
    private boolean generateRandom = false;
    static String algVersion = "";
    
//    TODO:
//      - testy (tabelka w sprawku)
//      - porownanie procentowe wartosci rozwiazan wzgledem iteracji/dlugosci listy/liczby iteracji (bez znalezienia nowego rozwiazania)
//      - ^ dla obu s�siedztw
//        - flagi dla swap/insert, zmniejszanie iteracji zawsze lub jak nie znajdziemy lepszego, nawroty

    
    public static void main(String[] args)
    {
        System.out.println(System.getProperty("user.dir"));

//        TabuSearchExperiment.generateDataTests(); //checks iterations
//        TabuSearchTimeEfficiencyExperiment.generateDataTests(); //DONE
//        TabuSearchAgeLimitExperiment.generateDataTests();
//        TabuSearchSwapInvert.generateDataTests();
//        TabuSearchTimeComplexityExperiment.generateDataTests();
//        TabuSearchRemissionExperiment.generateDataTests();
//        BeeColonyVs2OptExperiment.generateDataTests();
        BeeColonyIterationsExperiment.generateDataTests();
        BeeColonyBeeNumberExperiment.generateDataTests();
//        BeeColonyNeighExperiment.generateDataTests();
        BeeColonyThresholdExperiment.generateDataTests();
//        double[] x = new double[51];
//        double[] y = new double[51]; 
//        for (int size = 1; size <= 25; size++)
//        {
//            System.out.println(size);
//            BasicTSPProblemGenerator generator = new BasicTSPProblemGenerator();
//            ProblemInstance pI = generator.generateTSPProblemInstance(size*10);
//            EnhancedNeighSolver solver1 = new EnhancedNeighSolver();
//            Opt2Solver solver2 = new Opt2Solver();
//            
//            x[size] = solver1.solveInstance(pI).getObjectiveValue();
//            y[size] = solver2.solveInstance(pI).getObjectiveValue();
//        }
//        
        if (hasOption(args, "kRand"))
        {
            System.out.println("kRand");
            KRandomExperiment.generateData();
        }
        if (hasOption(args, "AllOV"))
        {
            System.out.println("AllOV");
            AllObjectiveValExperiment.generateData();
        }
        if (hasOption(args, "TimeComplex"))
        {
            System.out.println("TimeComplex");
            TimeComplexityExperiment.generateData();
        }
        if (hasOption(args, "NAllOV"))
        {
            System.out.println("NAllOV");
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
