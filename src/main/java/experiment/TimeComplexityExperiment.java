package experiment;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;

import algs.ProblemInstance;
import generator.BasicATSPProblemGenerator;
import solution.ProblemSolution;
import solver.ClosestNeighSolver;
import solver.EnhancedNeighSolver;
import solver.KRandomSolver;
import solver.Opt2Solver;
import solver.ProblemSolver;

public class TimeComplexityExperiment
{
    static final int ITERATIONS = 600;
    static final int REPEATS = 20;
    
    public static void generateData()
    {
        try
        {
            String filePath = System.getProperty("user.dir") + "/results/";
            String filename = filePath + "TimeComplexity.txt";
            PrintWriter writer =  new PrintWriter(filename, StandardCharsets.UTF_8);
            writer.write("TimeComplexity");
            writer.write("size KRandom ClosestNeigh EnchancedNeigh Opt2");
            ProblemSolver[] solvers = {new KRandomSolver(100), new ClosestNeighSolver(0), new EnhancedNeighSolver(), new Opt2Solver()};
            
            for (int size = 10; size <= ITERATIONS; size += 20)
            {
                BasicATSPProblemGenerator generator = new BasicATSPProblemGenerator();
                writer.write(size + " ");
                int[] sums = new int[solvers.length];
                for (int i = 0; i < REPEATS; i++)
                {
                    ProblemInstance pI = generator.generateATSPProblemInstance(size);
                    for (int k = 0; k < solvers.length; k++)
                    {
                        long start = System.nanoTime();
                        solvers[k].solveInstance(pI);
                        long finish = System.nanoTime();
                        long timeElapsed = finish - start;
                        sums[k] += timeElapsed/1000;   
                    }
                }
                for (int k = 0; k < solvers.length; k++)
                {
                    writer.write(sums[k]/REPEATS + " ");
                }
                
                writer.write("\n");
            }
            writer.close();
            
        } catch (IOException e)
        {
            System.out.println(e.getMessage());
        }
    }
}
