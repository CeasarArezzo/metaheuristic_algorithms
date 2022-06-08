package experiment.bees;

import algs.ProblemInstance;
import parser.DataReader;
import parser.WrongNumberException;
import solver.bees.BeeColonySolver;
import solver.bees.BeeNeigh;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;

public class BeeColonyBeeNumberExperiment
{
    public static void generateDataTests()
    {
        System.out.println("Bee Number Experiment");
        try
        {
            String filePath = System.getProperty("user.dir") + "/results/";

            String filename = filePath + "NumberOfBees" + ".txt";
            PrintWriter writer = new PrintWriter(filename, StandardCharsets.UTF_8);

            writer.println("Bees Number Expermient");
            writer.println("berlin52 " + "pr107 " + "pr152 " + "gr120 " +  "eil101 " + "a280");

            String[] problemNames = {"/data/tsp/" + "berlin52.tsp", "/data/tsp/" + "pr107.tsp", "/data/tsp/" + "pr152.tsp", "/data/tsp/" + "gr120.tsp", "/data/tsp/" + "eil101.tsp", "/data/tsp/" + "a280.tsp"/*, "/data/atsp/" + "ftv70.atsp"*/};
            int[] problemExpectedValues = {7542, 44303, 73682, 6942, 629, 2579/*, 1950*/};
            int[] problemSizes = {52, 107, 152, 120, 101, 280};
            ProblemInstance[] problems = new ProblemInstance[problemNames.length];
            for (int i = 0; i < problemNames.length; i++)
            {
                problems[i] = DataReader.readFileForGraphMatrix(System.getProperty("user.dir") + problemNames[i]);
            }

            int bestIterations = 10000;
            int beesPerThread = 30;

            writer.println("EFFICIENCY1 BEES1 BEES1% EFFICIENCY2 BEES2 BEES2% EFFICIENCY3 BEES3 BEES3% EFFICIENCY4 BEES4 BEES4% EFFICIENCY5 BEES5 BEES5% EFFICIENCY6 BEES6 BEES6%");
            for (int beesCount = 1; beesCount <= 501; beesCount += 10)
            {
//                System.out.println("ageLimit " + ageLimit);
//                writer.print(ageLimit + " ");
//                float sum = 0;

                for (int problem = 0; problem < problems.length; problem++)
                {
                    long start = System.nanoTime();

                    BeeColonySolver solver = new BeeColonySolver(BeeNeigh.INVERT, beesCount, bestIterations, problemSizes[problem], beesPerThread);
                    float sumTmp = 0;
                    int max = 3;
                    for (int repeats = 0; repeats < max; repeats++)
                    {
//                        System.out.println("\t" + solver.solveInstance(problems[problem]).getObjectiveValue());
                        sumTmp += solver.solveInstance(problems[problem]).getObjectiveValue();
                    }
                    long finish = System.nanoTime();
                    long timeElapsed = finish - start;
                    sumTmp = (sumTmp / max - problemExpectedValues[problem]) / problemExpectedValues[problem];
                    writer.print((100 - sumTmp) / timeElapsed * 100 + " ");
                    writer.print(beesCount + " ");
                    writer.print(beesCount/problemSizes[problem] + " ");

                }
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
