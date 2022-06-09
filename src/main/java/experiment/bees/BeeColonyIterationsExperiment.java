package experiment.bees;

import algs.ProblemInstance;
import parser.DataReader;
import parser.WrongNumberException;
import solver.bees.BeeColonySolver;
import solver.bees.BeeNeigh;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;

public class BeeColonyIterationsExperiment
{
    public static void generateDataTests()
    {
        System.out.println("Bee Iterations Experiment");
        try
        {
            String filePath = System.getProperty("user.dir") + "/results/";

            String filenameRaw = filePath + "IterationsRaw" + ".txt";
            String filenameEff = filePath + "IterationsEff" + ".txt";
            PrintWriter writerRaw = new PrintWriter(filenameRaw, StandardCharsets.UTF_8);
            PrintWriter writerEff = new PrintWriter(filenameEff, StandardCharsets.UTF_8);

            writerRaw.println("Bees Iterations Raw Expermient");
            writerEff.println("Bees Iterations Eff Expermient");
            writerRaw.println("berlin52 " + "pr107 " + "pr152 " + "gr120 " +  "eil101 " + "a280");
            writerEff.println("berlin52 " + "pr107 " + "pr152 " + "gr120 " +  "eil101 " + "a280");

            String[] problemNames = {"/data/tsp/" + "berlin52.tsp", "/data/tsp/" + "pr107.tsp", "/data/tsp/" + "pr152.tsp", "/data/tsp/" + "gr120.tsp", "/data/tsp/" + "eil101.tsp", "/data/tsp/" + "a280.tsp"/*, "/data/atsp/" + "ftv70.atsp"*/};
            int[] problemExpectedValues = {7542, 44303, 73682, 6942, 629, 2579/*, 1950*/};
            int[] problemSizes = {52, 107, 152, 120, 101, 280};
            ProblemInstance[] problems = new ProblemInstance[problemNames.length];
            for (int i = 0; i < problemNames.length; i++)
            {
                problems[i] = DataReader.readFileForGraphMatrix(System.getProperty("user.dir") + problemNames[i]);
            }

            writerRaw.println("OBJECTIVE1 ITERATIONS1 OBJECTIVE2 ITERATIONS2 OBJECTIVE3 ITERATIONS3 OBJECTIVE4 ITERATIONS4 OBJECTIVE5 ITERATIONS5 OBJECTIVE6 ITERATIONS6");
            writerEff.println("EFFICIENCY1 ITERATIONS1 EFFICIENCY2 ITERATIONS2 EFFICIENCY3 ITERATIONS3 EFFICIENCY4 ITERATIONS4 EFFICIENCY5 ITERATIONS5 EFFICIENCY6 ITERATIONS6");
            for (int iterations = 100; iterations <= 35100; iterations += 5000)
            {
//                System.out.println("ageLimit " + ageLimit);
//                writer.print(ageLimit + " ");
//                float sum = 0;

                for (int problem = 0; problem < problems.length; problem++)
                {

                    float sumTmp = 0;
                    int max = 3;
                    long start = System.nanoTime();

                    for (int repeats = 0; repeats < max ; repeats++)
                    {
                        BeeColonySolver solver = new BeeColonySolver(BeeNeigh.INVERT, problemSizes[problem], iterations, problemSizes[problem], 30);

//                        System.out.println("\t" + solver.solveInstance(problems[problem]).getObjectiveValue());
                        int res = solver.solveInstance(problems[problem]).getObjectiveValue();
                        sumTmp += res;
//                        System.out.println(res + " " + problemNames[problem] + " " + repeats);
                    }
                    long finish = System.nanoTime();
                    long timeElapsed = finish - start;
                    writerRaw.print(sumTmp/ max + " ");
                    writerRaw.print(iterations + " ");
                    sumTmp = (sumTmp / max - problemExpectedValues[problem]) / problemExpectedValues[problem];
                    writerEff.print((100 - sumTmp) / timeElapsed * 100 + " ");
                    writerEff.print(iterations + " ");

                }
                writerRaw.println();
                writerEff.println();
                writerRaw.flush();
                writerEff.flush();
                System.out.println(iterations);

            }
            writerRaw.close();
            writerEff.close();
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
