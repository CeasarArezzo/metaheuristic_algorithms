package experiment;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;

import algs.ProblemInstance;
import parser.DataReader;
import parser.WrongNumberException;
import solver.TabuSolver;

public class TabuSearchRemissionExperiment
{
    public static void generateDataTests()
    {
        System.out.println("Remissions NoRemissions");
        try
        {
            String filePath = System.getProperty("user.dir") + "/results/";

            String filename = filePath + "TabuSearchRemissionsObj" + ".txt";
            PrintWriter writer = new PrintWriter(filename, StandardCharsets.UTF_8);
            
            writer.println("TABU Remission Obj");
            String[] problemNames = {"/data/tsp/" + "berlin52.tsp", "/data/tsp/" + "pr107.tsp", "/data/tsp/" + "pr152.tsp"/*, "/data/atsp/" + "ftv70.atsp"*/};
            int[] problemExpectedValues = {7542, 44303, 73682/*, 1950*/};
            
            ProblemInstance[] problems = new ProblemInstance[problemNames.length];
            for (int i = 0; i < problemNames.length; i++)
            {
                problems[i] = DataReader.readFileForGraphMatrix(System.getProperty("user.dir") + problemNames[i]);
            }
            
            int ageLimit = 50;
            writer.println("ITERATIONS Remissions NoRemissions");
            for (int iterations = 100; iterations <= 2500; iterations += 200)
            {
                System.out.println("iterations " + iterations);
                writer.print(iterations + " ");
                
                TabuSolver[] solvers = {new TabuSolver(ageLimit, iterations, false, true, false), new TabuSolver(ageLimit, iterations, false, false, false)};
                for (TabuSolver solver: solvers)
                {
                    float sum = 0;
                    for (int problem = 0; problem < problems.length; problem++)
                    {
                        float sumTmp = 0;
                        for (int repeats = 0; repeats < 5; repeats++)
                        {
//                            System.out.println("\t" + solver.solveInstance(problems[problem]).getObjectiveValue());
                            sumTmp += solver.solveInstance(problems[problem]).getObjectiveValue();
                        }
                        sumTmp = (sumTmp / 5 - problemExpectedValues[problem]) / problemExpectedValues[problem];
                        sum += sumTmp / problems.length;
                    }
                    writer.print(sum + " ");
                }
                writer.println();

            }
            writer.close();
        } catch (IOException e)
        {
            System.out.println(e.getMessage());
        } catch (WrongNumberException e)
        {
            e.printStackTrace();
        }
    }
}
