package experiment;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;

import algs.ProblemInstance;
import parser.DataReader;
import parser.WrongNumberException;
import solver.TabuSolver;

public class TabuSearchAgeLimitExperiment
{
    public static void generateDataTests()
    {
        System.out.println("Age limit efficiency");
        try
        {
            String filePath = System.getProperty("user.dir") + "/results/";

            String filename = filePath + "TabuSearchAgeComparision" + ".txt";
            PrintWriter writer = new PrintWriter(filename, StandardCharsets.UTF_8);
            
            writer.println("TABUSEARCH AGELIMIT EXP");
            String[] problemNames = {"/data/tsp/" + "berlin52.tsp", "/data/tsp/" + "pr107.tsp", "/data/tsp/" + "pr152.tsp"/*, "/data/atsp/" + "ftv70.atsp"*/};
            int[] problemExpectedValues = {7542, 44303, 73682/*, 1950*/};
            
            ProblemInstance[] problems = new ProblemInstance[problemNames.length];
            for (int i = 0; i < problemNames.length; i++)
            {
                problems[i] = DataReader.readFileForGraphMatrix(System.getProperty("user.dir") + problemNames[i]);
            }
            
            int bestIterations = 600;
            
            writer.println("ageLimit objectiveValue");
            for (int ageLimit = 30; ageLimit <= 1500; ageLimit += 50)
            {
                System.out.println("ageLimit " + ageLimit);
                writer.print(ageLimit + " ");
                float sum = 0;
                TabuSolver solver = new TabuSolver(ageLimit, bestIterations, false, false, false);
                for (int problem = 0; problem < problems.length; problem++)
                {
                    float sumTmp = 0;
                    for (int repeats = 0; repeats < 3; repeats++)
                    {
//                        System.out.println("\t" + solver.solveInstance(problems[problem]).getObjectiveValue());
                        sumTmp += solver.solveInstance(problems[problem]).getObjectiveValue();
                    }
                    sumTmp = (sumTmp / 3 - problemExpectedValues[problem]) / problemExpectedValues[problem];
                    sum += sumTmp;
                }
                sum /= problems.length;
                writer.print(sum + " ");
                writer.println();

            }
            writer.close();
        } catch (IOException e)
        {
            System.out.println(e.getMessage());
        } catch (WrongNumberException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
