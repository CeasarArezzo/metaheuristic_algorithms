package experiment;

import algs.ProblemInstance;
import generator.BasicATSPProblemGenerator;
import generator.BasicTSPProblemGenerator;
import parser.DataReader;
import parser.WrongNumberException;
import solver.*;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;

public class AllObjectiveValNormExperiment
{

    public static void generateData()
    {
        try
        {
            String filePath = System.getProperty("user.dir") + "/results/";



            String filename = filePath + "AllObjectiveValNormExpTSP" + ".txt";
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
                ArrayList<Integer> objectiveValues = new ArrayList<>();

                for(ProblemSolver solver: problemSolvers)
                {
                    objectiveValues.add(solver.solveInstance(pI).getObjectiveValue());
                }
                int min =  Collections.min(objectiveValues);

                for(int objectiveVal: objectiveValues)
                {
                    float prd = (((float)objectiveVal - (float)min) * 100.0f) / (float)min;
                    writer.print(prd + " ");
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
