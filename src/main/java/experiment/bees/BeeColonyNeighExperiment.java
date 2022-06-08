package experiment.bees;

import algs.ProblemInstance;
import generator.BasicTSPProblemGenerator;
import solver.bees.BeeColonySolver;
import solver.bees.BeeNeigh;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;

public class BeeColonyNeighExperiment
{
    public static void generateDataTests()
    {
        System.out.println("Bee Neigh Experiment");
        BasicTSPProblemGenerator problemGenerator = new BasicTSPProblemGenerator();

        try
        {
            String filePath = System.getProperty("user.dir") + "/results/";

            String filename = filePath + "BeesNeigh" + ".txt";
            PrintWriter writer = new PrintWriter(filename, StandardCharsets.UTF_8);

            writer.println("Bees Neigh Expermient");
            int iterations = 10000;
            int beesPerThread = 30;

            writer.println("Iterations Invert Swap Insert");
            for (int size = 50; size <= 500; size += 50)
            {
//                System.out.println("ageLimit " + ageLimit);
//                writer.print(ageLimit + " ");
//                float sum = 0;
                ProblemInstance pI = problemGenerator.generateTSPProblemInstance(size);

                //TODO add choosing neigh
                BeeColonySolver beeInvertSolver = new BeeColonySolver(BeeNeigh.INVERT, size, iterations, size, beesPerThread);
                BeeColonySolver beeSwapSolver = new BeeColonySolver(BeeNeigh.SWAP, size, iterations, size, beesPerThread);
                BeeColonySolver beeInsertSolver = new BeeColonySolver(BeeNeigh.INSERT, size, iterations, size, beesPerThread);
                float invertSumTmp = 0;
                float swapSumTmp = 0;
                float insertSumTmp = 0;
                int max = 3;
                for (int repeats = 0; repeats < max; repeats++)
                {
                    invertSumTmp += beeInvertSolver.solveInstance(pI).getObjectiveValue();
                    swapSumTmp += beeSwapSolver.solveInstance(pI).getObjectiveValue();
                    insertSumTmp += beeInsertSolver.solveInstance(pI).getObjectiveValue();
                }

                writer.print(size + " ");
                writer.print(invertSumTmp/max + " ");
                writer.print(swapSumTmp/max + " ");
                writer.print(insertSumTmp/max + " ");
                writer.println();

            }
            writer.close();
        } catch (IOException e)
        {
            System.out.println(e.getMessage());
        }
    }
}
