package experiment;

import algs.ProblemInstance;
import generator.BasicATSPProblemGenerator;
import generator.BasicTSPProblemGenerator;
import parser.DataReader;
import parser.WrongNumberException;
import solution.ProblemSolution;
import solver.*;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.stream.IntStream;

public class AllObjectiveValExperiment
{

    public static void generateData()
    {
        try
        {
            String filePath = System.getProperty("user.dir") + "/results/";



            String filename = filePath + "AllObjectiveValExpATSP" + ".txt";
            PrintWriter writer = new PrintWriter(filename, StandardCharsets.UTF_8);
            writer.println("SIZE KRANDOM OPT2 NEIGH ENCHNEIGH");
            for (int size = 10; size <= 250; size += 10)
            {
                writer.print(size + " ");

                ArrayList<ProblemSolver> problemSolvers = new ArrayList<>();
                problemSolvers.add(new KRandomSolver(100));
                problemSolvers.add(new Opt2Solver());
                problemSolvers.add(new ClosestNeighSolver());
                problemSolvers.add(new EnhancedNeighSolver());

                ProblemInstance pI = getProblemInstance(size);

                for(ProblemSolver solver: problemSolvers)
                {
                    writer.print(solver.solveInstance(pI).getObjectiveValue() + " ");
                }
                System.out.println(size);
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
        BasicATSPProblemGenerator generator = new BasicATSPProblemGenerator();
        return generator.generateATSPProblemInstance(size);
    }
}
