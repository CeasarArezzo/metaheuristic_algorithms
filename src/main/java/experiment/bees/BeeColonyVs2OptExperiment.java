package experiment.bees;

import algs.ProblemInstance;
import generator.BasicTSPProblemGenerator;
import solver.Opt2Solver;
import solver.bees.BeeColonySolver;
import solver.bees.BeeNeigh;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;

public class BeeColonyVs2OptExperiment
{
    public static void generateDataTests()
    {
        System.out.println("Bee vs 2Opt Experiment");
        BasicTSPProblemGenerator problemGenerator = new BasicTSPProblemGenerator();

        try
        {
            int bestIterations = 40000;
            int beesPerThread = 10;

            String filePath = System.getProperty("user.dir") + "/results/";

            String filename = filePath + "BeesVs2Opt" + ".txt";
            PrintWriter writer = new PrintWriter(filename, StandardCharsets.UTF_8);

            writer.println("Bees vs 2Opt 2Expermient");


            writer.println("Iterations Bees Opt");
            for (int size = 50; size <= 400; size += 50)
            {
//                System.out.println("ageLimit " + ageLimit);
//                writer.print(ageLimit + " ");
//                float sum = 0;
                ProblemInstance pI = problemGenerator.generateTSPProblemInstance(size);

                float beeSumTmp = 0;
                float optSumTmp = 0;
                int max = 3;
                for (int repeats = 0; repeats < max; repeats++)
                {
                    BeeColonySolver beeSolver = new BeeColonySolver(BeeNeigh.INVERT, 2 * size, bestIterations, size, beesPerThread);
                    Opt2Solver opt2Solver = new Opt2Solver();

                    beeSumTmp += beeSolver.solveInstance(pI).getObjectiveValue();
                    optSumTmp += opt2Solver.solveInstance(pI).getObjectiveValue();
                }

                writer.print(size + " ");
                writer.print(beeSumTmp/max + " ");
                writer.print(optSumTmp/max + " ");
                writer.println();
                writer.flush();
                System.out.println(size);
            }
            writer.close();
        } catch (IOException e)
        {
            System.out.println(e.getMessage());
        }
    }
}
