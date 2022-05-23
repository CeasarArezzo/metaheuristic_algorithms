package experiment;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;

import algs.ProblemInstance;
import parser.DataReader;
import parser.WrongNumberException;
import solver.TabuSolver;

public class TabuSearchTimeEfficiencyExperiment
{

    public static void generateDataTests()
    {
        System.out.println("Time efficiency");
        try
        {
            String filePath = System.getProperty("user.dir") + "/results/";

            String filename = filePath + "TabuSearchTimeEfficiency" + ".txt";
            PrintWriter writer = new PrintWriter(filename, StandardCharsets.UTF_8);
            
            writer.println("TABU SEARCH EFFICIENCY (sum * 100/time)");
            String[] problemNames = {"/data/tsp/" + "berlin52.tsp", "/data/tsp/" + "pr107.tsp", "/data/tsp/" + "pr152.tsp"/*, "/data/atsp/" + "ftv70.atsp"*/};
            int[] problemExpectedValues = {7542, 44303, 73682/*, 1950*/};
            
            ProblemInstance[] problems = new ProblemInstance[problemNames.length];
            for (int i = 0; i < problemNames.length; i++)
            {
                problems[i] = DataReader.readFileForGraphMatrix(System.getProperty("user.dir") + problemNames[i]);
            }
            
            writer.println("ITERATIONS timeNoReset timeReset");
            for (int iterations = 100; iterations <= 2800; iterations += 250)
            {
                System.out.println("iterations " + iterations);
                writer.print(iterations + " ");
                float sum = 0;
                TabuSolver solvers[] = {new TabuSolver(130, iterations, false, false, false), new TabuSolver(130, iterations, true, false, false)};
                for (TabuSolver solver: solvers)
                {
                    long start = System.nanoTime();
                    for (int problem = 0; problem < problems.length; problem++)
                    {
                        float sumTmp = 0;
                        for (int repeats = 0; repeats < 3; repeats++)
                        {
//                            System.out.println("\t" + solver.solveInstance(problems[problem]).getObjectiveValue());
                            sumTmp += solver.solveInstance(problems[problem]).getObjectiveValue();
                        }
                        sumTmp = (sumTmp / 3 - problemExpectedValues[problem]) / problemExpectedValues[problem];
                        sum += sumTmp;
                    }
                    sum /= problems.length;
                    long finish = System.nanoTime();
                    long timeElapsed = finish - start;
//                    System.out.println(sum);
                    writer.print((100 - sum) / timeElapsed * 100 + " ");
                }
                writer.println();

//                ArrayList<ProblemSolver> problemSolvers = new ArrayList<>();
//                problemSolvers.add(new KRandomSolver(100));
//                problemSolvers.add(new Opt2Solver());
//                problemSolvers.add(new ClosestNeighSolver());
//                problemSolvers.add(new EnhancedNeighSolver());
//
//                ProblemInstance pI = getProblemInstance(size);
//                ArrayList<Integer> objectiveValues = new ArrayList<>();
//
//                for(ProblemSolver solver: problemSolvers)
//                {
//                    objectiveValues.add(solver.solveInstance(pI).getObjectiveValue());
//                }
//                int min =  Collections.min(objectiveValues);
//
//                for(int objectiveVal: objectiveValues)
//                {
//                    float prd = (((float)objectiveVal - (float)min) * 100.0f) / (float)min;
//                    writer.print(prd + " ");
//                }
//                writer.println();

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
