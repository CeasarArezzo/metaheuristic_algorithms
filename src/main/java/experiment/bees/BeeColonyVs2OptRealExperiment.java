package experiment.bees;

import algs.ProblemInstance;
import generator.BasicTSPProblemGenerator;
import parser.DataReader;
import parser.WrongNumberException;
import solver.Opt2Solver;
import solver.bees.BeeColonySolver;
import solver.bees.BeeNeigh;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;

public class BeeColonyVs2OptRealExperiment
{
    public static void generateDataTests()
    {
        System.out.println("Bee vs 2Opt Real Experiment");
        BasicTSPProblemGenerator problemGenerator = new BasicTSPProblemGenerator();

        try
        {
            String filePath = System.getProperty("user.dir") + "/results/";

            String filename = filePath + "Beevs2OptRealExperiment" + ".txt";
            PrintWriter writer = new PrintWriter(filename, StandardCharsets.UTF_8);

            writer.println("berlin52 " + "pr107 " + "pr152 " + "gr120 " +  "eil101 " + "a280");

            String[] problemNames = {"/data/tsp/" + "berlin52.tsp", "/data/tsp/" + "pr107.tsp", "/data/tsp/" + "pr152.tsp", "/data/tsp/" + "gr120.tsp", "/data/tsp/" + "eil101.tsp", "/data/tsp/" + "a280.tsp"/*, "/data/atsp/" + "ftv70.atsp"*/};
            int[] problemExpectedValues = {7542, 44303, 73682, 6942, 629, 2579/*, 1950*/};
            int[] problemSizes = {52, 107, 152, 120, 101, 280};
            ProblemInstance[] problems = new ProblemInstance[problemNames.length];
            for (int i = 0; i < problemNames.length; i++)
            {
                problems[i] = DataReader.readFileForGraphMatrix(System.getProperty("user.dir") + problemNames[i]);
            }
            int bestIterations = 40000;
            int beesPerThread = 10;

            writer.println("Problem Bees Opt");



            for (int problem = 0; problem < problems.length; problem++)
            {
                float beeSumTmp = 0;
                float optSumTmp = 0;
                int max = 3;


                for (int repeats = 0; repeats < max; repeats++)
                {
                    BeeColonySolver beeSolver = new BeeColonySolver(BeeNeigh.INVERT, 2 * problemSizes[problem], bestIterations, problemSizes[problem], beesPerThread);
                    Opt2Solver opt2Solver = new Opt2Solver();


                    beeSumTmp += beeSolver.solveInstance(problems[problem]).getObjectiveValue();
                    optSumTmp += opt2Solver.solveInstance(problems[problem]).getObjectiveValue();

                }

                writer.print(problemNames[problem] + " ");
                writer.print(beeSumTmp/max + " ");
                writer.print(optSumTmp/max + " ");
                writer.println();
                writer.flush();
                System.out.println(problemNames[problem]);

            }


            writer.close();
        } catch (IOException e)
        {
            System.out.println(e.getMessage());
        }
        catch (WrongNumberException e)
        {
            throw new RuntimeException(e);
        }
    }
}
