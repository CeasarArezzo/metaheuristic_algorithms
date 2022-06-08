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

            String filenameNorm = filePath + "IterationsNorm" + ".txt";
            String filenameEff = filePath + "IterationsEff" + ".txt";
            PrintWriter writerNorm = new PrintWriter(filenameNorm, StandardCharsets.UTF_8);
            PrintWriter writerEff = new PrintWriter(filenameEff, StandardCharsets.UTF_8);

            writerNorm.println("Bees Iterations Expermient");
            writerEff.println("Bees Iterations Expermient");
            writerNorm.println("berlin52 " + "pr107 " + "pr152 " + "gr120 " +  "eil101 " + "a280");
            writerEff.println("berlin52 " + "pr107 " + "pr152 " + "gr120 " +  "eil101 " + "a280");

            String[] problemNames = {"/data/tsp/" + "berlin52.tsp", "/data/tsp/" + "pr107.tsp", "/data/tsp/" + "pr152.tsp", "/data/tsp/" + "gr120.tsp", "/data/tsp/" + "eil101.tsp", "/data/tsp/" + "a280.tsp"/*, "/data/atsp/" + "ftv70.atsp"*/};
            int[] problemExpectedValues = {7542, 44303, 73682, 6942, 629, 2579/*, 1950*/};
            int[] problemSizes = {52, 107, 152, 120, 101, 280};
            ProblemInstance[] problems = new ProblemInstance[problemNames.length];
            for (int i = 0; i < problemNames.length; i++)
            {
                problems[i] = DataReader.readFileForGraphMatrix(System.getProperty("user.dir") + problemNames[i]);
            }

            writerNorm.println("EFFICIENCY1 ITERATIONS1 EFFICIENCY2 ITERATIONS2 EFFICIENCY3 ITERATIONS3 EFFICIENCY4 ITERATIONS4 EFFICIENCY5 ITERATIONS5 EFFICIENCY6 ITERATIONS6");
            writerEff.println("EFFICIENCY1 ITERATIONS1 EFFICIENCY2 ITERATIONS2 EFFICIENCY3 ITERATIONS3 EFFICIENCY4 ITERATIONS4 EFFICIENCY5 ITERATIONS5 EFFICIENCY6 ITERATIONS6");
            for (int iterations = 100; iterations <= 50100; iterations += 5000)
            {
//                System.out.println("ageLimit " + ageLimit);
//                writer.print(ageLimit + " ");
//                float sum = 0;

                for (int problem = 0; problem < problems.length; problem++)
                {

                    BeeColonySolver solver = new BeeColonySolver(BeeNeigh.INVERT, problemSizes[problem], iterations, problemSizes[problem], 30);
                    float sumTmp = 0;
                    int max = 3;
                    long start = System.nanoTime();

                    for (int repeats = 0; repeats < max ; repeats++)
                    {
//                        System.out.println("\t" + solver.solveInstance(problems[problem]).getObjectiveValue());
                        sumTmp += solver.solveInstance(problems[problem]).getObjectiveValue();
                    }
                    long finish = System.nanoTime();
                    long timeElapsed = finish - start;
                    writerNorm.print(sumTmp/ max + " ");
                    writerNorm.print(iterations + " ");
                    sumTmp = (sumTmp / max - problemExpectedValues[problem]) / problemExpectedValues[problem];
                    writerEff.print((100 - sumTmp) / timeElapsed * 100 + " ");
                    writerEff.print(iterations + " ");

                }
                writerNorm.println();
                writerEff.println();
                System.out.println(iterations);

            }
            writerNorm.close();
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
