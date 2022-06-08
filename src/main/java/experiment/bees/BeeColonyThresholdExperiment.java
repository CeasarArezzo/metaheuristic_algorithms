package experiment.bees;

import algs.ProblemInstance;
import parser.DataReader;
import parser.WrongNumberException;
import solver.bees.BeeColonySolver;
import solver.bees.BeeNeigh;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;

public class BeeColonyThresholdExperiment
{
    public static void generateDataTests()
    {
        System.out.println("Bee Threshold Experiment");
        try
        {
            String filePath = System.getProperty("user.dir") + "/results/";

            String filename = filePath + "Threshold" + ".txt";
            PrintWriter writer = new PrintWriter(filename, StandardCharsets.UTF_8);

            writer.println("Bees Treshold Expermient");
            writer.println("berlin52 " + "pr107 " + "pr152 " + "gr120 " +  "eil101 " + "a280");

            String[] problemNames = {"/data/tsp/" + "berlin52.tsp", "/data/tsp/" + "pr107.tsp", "/data/tsp/" + "pr152.tsp", "/data/tsp/" + "gr120.tsp", "/data/tsp/" + "eil101.tsp", "/data/tsp/" + "a280.tsp"/*, "/data/atsp/" + "ftv70.atsp"*/};
            int[] problemExpectedValues = {7542, 44303, 73682, 6942, 629, 2579/*, 1950*/};
            int[] problemSizes = {52, 107, 152, 120, 101, 280};
            ProblemInstance[] problems = new ProblemInstance[problemNames.length];
            for (int i = 0; i < problemNames.length; i++)
            {
                problems[i] = DataReader.readFileForGraphMatrix(System.getProperty("user.dir") + problemNames[i]);
            }

            int bestIterations = 4000;
            int beesPerThread = 30;

            writer.println("EFFICIENCY1 THRESHOLD1 THRESHOLD1% EFFICIENCY2 THRESHOLD2 THRESHOLD2% EFFICIENCY3 THRESHOLD3 THRESHOLD3% EFFICIENCY4 THRESHOLD4 THRESHOLD4% EFFICIENCY5 THRESHOLD5 THRESHOLD5% EFFICIENCY6 THRESHOLD6 THRESHOLD6%");
            for (int threshold = 1; threshold <= 501; threshold += 10)
            {
//                System.out.println("ageLimit " + ageLimit);
//                writer.print(ageLimit + " ");
//                float sum = 0;

                for (int problem = 0; problem < problems.length; problem++)
                {

                    BeeColonySolver solver = new BeeColonySolver(BeeNeigh.INVERT, problemSizes[problem], bestIterations, threshold, beesPerThread);
                    float sumTmp = 0;
                    for (int repeats = 0; repeats < 5; repeats++)
                    {
//                        System.out.println("\t" + solver.solveInstance(problems[problem]).getObjectiveValue());
                        sumTmp += solver.solveInstance(problems[problem]).getObjectiveValue();
                    }
                    sumTmp = (sumTmp / 5 - problemExpectedValues[problem]) / problemExpectedValues[problem];
                    writer.print(sumTmp + " ");
                    writer.print(threshold + " ");
                    writer.print(threshold/problemSizes[problem] + " ");

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
