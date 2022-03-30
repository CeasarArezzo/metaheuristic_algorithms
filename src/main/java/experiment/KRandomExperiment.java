package experiment;

import java.io.*;
import java.nio.charset.StandardCharsets;

import algs.ProblemInstance;
import generator.BasicATSPProblemGenerator;
import parser.DataReader;
import parser.WrongNumberException;
import solution.ProblemSolution;
import solver.KRandomSolver;

public class KRandomExperiment
{
    public static void generateData()
    {
        try
        {
            String filePath = System.getProperty("user.dir") + "\\results\\";
            
            int[] sizes = {10, 25, 50, 75, 100, 150, 350};
            
            for (int size : sizes)
            {
                String filename = filePath + "KRandomExp" + size + ".txt";
                PrintWriter writer =  new PrintWriter(filename, StandardCharsets.UTF_8);
                ProblemInstance pI = getProblemInstance(size);
                for (int k = 10; k <= 150; k+=10)
                {
                    KRandomSolver solver = new KRandomSolver(k);
                    ProblemSolution solution = solver.solveInstance(pI);
                    writer.write(k + " " + solution.getObjectiveValue());
                }
                writer.close();
            }
            
        } catch (IOException e)
        {
            System.out.println(e.getMessage());
        }
    }

    private static ProblemInstance getProblemInstance(int size)
    {
        String filepath = System.getProperty("user.dir") + "\\data\\atsp\\";
        ProblemInstance pI = null;
        try
        {
            switch (size)
            {
            case 17: 
                pI = DataReader.readFileForGraphMatrix(filepath + "br17.atsp"); break;
            case 33: 
                pI = DataReader.readFileForGraphMatrix(filepath + "ftv33.atsp"); break;
            case 55: 
                pI = DataReader.readFileForGraphMatrix(filepath + "ftv55.atsp"); break;
            case 70: 
                pI = DataReader.readFileForGraphMatrix(filepath + "ftv70.atsp"); break;
            case 124: 
                pI = DataReader.readFileForGraphMatrix(filepath + "kro124p.atsp"); break;
            case 403: 
                pI = DataReader.readFileForGraphMatrix(filepath + "rbg403.atsp"); break;
            default:
                BasicATSPProblemGenerator generator = new BasicATSPProblemGenerator();
                pI = generator.generateATSPProblemInstance(size);
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
