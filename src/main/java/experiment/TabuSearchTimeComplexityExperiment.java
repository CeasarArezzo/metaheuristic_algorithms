package experiment;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;

import algs.ProblemInstance;
import generator.BasicTSPProblemGenerator;
import parser.DataReader;
import parser.WrongNumberException;
import solver.ClosestNeighSolver;
import solver.Opt2Solver;
import solver.ProblemSolver;
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
            writer.println("size tabu 2opt closestNeigh");
            for (int size = 50; size <= 500; size += 25)
            {
                System.out.println("size " + size);
                writer.print(size + " ");
                float sum = 0;
                ProblemSolver solvers[] = {new TabuSolver(ageLimit, iterations, false, false, false), new Opt2Solver(), new ClosestNeighSolver()};
                ProblemInstance pI = getProblemInstance(size);
                for(ProblemSolver solver: solvers)
                {
                    long start = System.nanoTime();
                    for (int repeats = 0; repeats < 3; repeats++)
                    {
                        solver.solveInstance(pI).getObjectiveValue();
                    }
                    long finish = System.nanoTime();
                    long timeElapsed = (finish - start) / 3 / 100;
//                    System.out.println(sum);
                    writer.print(timeElapsed + " ");
                }
                writer.println();

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
