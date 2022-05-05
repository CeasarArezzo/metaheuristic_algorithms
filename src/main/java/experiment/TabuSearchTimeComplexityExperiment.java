package experiment;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;

import algs.ProblemInstance;
import generator.BasicTSPProblemGenerator;
import parser.DataReader;
import parser.WrongNumberException;
import solver.TabuSolver;

public class TabuSearchTimeComplexityExperiment
{

    public static void generateDataTests()
    {
        System.out.println("Time complexity");
        try
        {
            String filePath = System.getProperty("user.dir") + "/results/";

            String filename = filePath + "TabuSearchTimeComplexity" + ".txt";
            PrintWriter writer = new PrintWriter(filename, StandardCharsets.UTF_8);
            
            int iterations = 600;
            int ageLimit = 50;
            
            writer.println("TABU SEARCH TIME COMPLEXITY");
            writer.println("size time");
            for (int size = 50; size <= 3000; size += 200)
            {
                System.out.println("size " + size);
                writer.print(size + " ");
                float sum = 0;
                TabuSolver solver = new TabuSolver(ageLimit, iterations, false, false, false);
                ProblemInstance pI = getProblemInstance(size);
                long start = System.nanoTime();
                for (int repeats = 0; repeats < 5; repeats++)
                {
                    solver.solveInstance(pI).getObjectiveValue();
                }
                long finish = System.nanoTime();
                long timeElapsed = (finish - start) / 5 / 100;
//                System.out.println(sum);
                writer.println(timeElapsed);

            }
            writer.close();
        } catch (IOException e)
        {
            System.out.println(e.getMessage());
        }
    }

    private static ProblemInstance getProblemInstance(int size)
    {
        BasicTSPProblemGenerator generator = new BasicTSPProblemGenerator();
        return generator.generateTSPProblemInstance(size);
    }
}
