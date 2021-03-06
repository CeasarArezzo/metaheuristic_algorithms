package experiment.bees;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;

import algs.ProblemInstance;
import parser.DataReader;
import parser.WrongNumberException;
import solver.bees.BeeColonySolver;
import solver.bees.BeeNeigh;

public class BeeColonyBeesPerThreadExperiment
{
    public static void generateDataTests()
    {
        System.out.println("Bee BPT Experiment");
        try
        {
            String filePath = System.getProperty("user.dir") + "/results/";

            String filename = filePath + "BPT" + ".txt";
            PrintWriter writer = new PrintWriter(filename, StandardCharsets.UTF_8);

            writer.println("Bees BPT Expermient");
            writer.println("berlin52 " + "pr107 " + "pr152 " + "gr120 " +  "eil101 " + "a280");

            String[] problemNames = {"/data/tsp/" + "berlin52.tsp", "/data/tsp/" + "pr107.tsp", "/data/tsp/" + "pr152.tsp", "/data/tsp/" + "gr120.tsp", "/data/tsp/" + "eil101.tsp", "/data/tsp/" + "a280.tsp"/*, "/data/atsp/" + "ftv70.atsp"*/};
            int[] problemExpectedValues = {7542, 44303, 73682, 6942, 629, 2579/*, 1950*/};
            int[] problemSizes = {52, 107, 152, 120, 101, 280};
            ProblemInstance[] problems = new ProblemInstance[problemNames.length];
            for (int i = 0; i < problemNames.length; i++)
            {
                problems[i] = DataReader.readFileForGraphMatrix(System.getProperty("user.dir") + problemNames[i]);
            }

            int bestIterations = 2000;
            int threshold = 30;

            writer.println("OBJECTIVE1 BPT1 BPT1% OBJECTIVE2 BPT2 BPT2% OBJECTIVE3 BPT3 BPT3% OBJECTIVE4 BPT4 BPT4% OBJECTIVE5 BPT5 BPT5% OBJECTIVE6 BPT6 BPT6%");
            for (int bpt = 1; bpt <= 20; bpt += 1)
            {
                System.out.println(bpt);
//                System.out.println("ageLimit " + ageLimit);
//                writer.print(ageLimit + " ");
//                float sum = 0;

                for (int problem = 0; problem < problems.length; problem++)
                {

                    float sumTmp = 0;
                    int max = 3;

                    for (int repeats = 0; repeats < max; repeats++)
                    {
                        BeeColonySolver solver = new BeeColonySolver(BeeNeigh.INVERT, problemSizes[problem], bestIterations, threshold, bpt);

                        sumTmp += solver.solveInstance(problems[problem]).getObjectiveValue();
                    }

                    writer.print(sumTmp/max+ " ");
                    writer.print(bpt + " ");
                    writer.print((float)bpt/problemSizes[problem] + " ");

                }
                writer.println();
                writer.flush();
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
