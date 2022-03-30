package experiment;

import algs.ProblemInstance;
import generator.BasicATSPProblemGenerator;
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



            String filename = filePath + "AllObjectiveValExp" + ".txt";
            PrintWriter writer = new PrintWriter(filename, StandardCharsets.UTF_8);
            for (int size = 10; size <= 400; size += 10)
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
        String filepath = System.getProperty("user.dir") + "/data/atsp/";
        ProblemInstance pI = null;
        try
        {
            switch (size)
            {
                case 17 -> pI = DataReader.readFileForGraphMatrix(filepath + "br17.atsp" + "/br17.atsp");
                case 33 -> pI = DataReader.readFileForGraphMatrix(filepath + "ftv33.atsp" + "/ftv33.atsp");
                case 55 -> pI = DataReader.readFileForGraphMatrix(filepath + "ftv55.atsp" + "/ftv55.atsp");
                case 70 -> pI = DataReader.readFileForGraphMatrix(filepath + "ftv70.atsp" + "/ftv70.atsp");
                case 124 -> pI = DataReader.readFileForGraphMatrix(filepath + "kro124p.atsp" + "/kro124p.atsp");
                case 403 -> pI = DataReader.readFileForGraphMatrix(filepath + "rbg403.atsp" + "/rbg403.atsp");
                default -> {
                    BasicATSPProblemGenerator generator = new BasicATSPProblemGenerator();
                    pI = generator.generateATSPProblemInstance(size);
                }
            }
        }
        catch (WrongNumberException | FileNotFoundException e)
        {
            System.out.println("Exception caught, generating random Instance");
            System.out.println(e.getMessage());
            BasicATSPProblemGenerator generator = new BasicATSPProblemGenerator();
            pI = generator.generateATSPProblemInstance(size);
        }
        return pI;
    }
}
