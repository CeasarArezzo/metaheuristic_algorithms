package solver.bees;

import java.util.ArrayList;

import algs.ProblemInstance;
import solution.ProblemSolution;
import solver.ProblemSolver;

public class BeeColonySolver implements ProblemSolver
{

    //Problem fields//
    private ArrayList<ArrayList<Integer>> foodSources;
    private ProblemInstance pInstance;

    //Bees//
    private ArrayList<Bee> beeColony;
    private final Thread[] beeThreads;

    //Algorithm constants//
    int populationSize;
    private final int threshold;
    private int iterations;


    public BeeColonySolver(int populationSize, int iterations, int threshold)
    {
        this.populationSize = populationSize;
        this.iterations = iterations;
        beeThreads = new Thread[populationSize];
        this.threshold = threshold;

    }

    @Override
    public ProblemSolution solveInstance(ProblemInstance problemInstance)
    {
        pInstance = problemInstance;
        initializationPhase();
        beeColony = new ArrayList<>();

        for(int i = 0; i < populationSize; i++)
        {
            beeColony.add(new Bee(foodSources.get(i), pInstance, threshold));
        }
        ArrayList<Integer> bestSolution = findBest(foodSources.get(0));
        while( !terminationCriteriaFullfiled())
        {
            System.out.print(ProblemSolution.getObjectiveValue(bestSolution.get(bestSolution.size()-1), bestSolution, pInstance));
            System.out.println(" " + iterations);
            try
            {
                double[] fitness = EmployedBeePhase();
                bestSolution = findBest(bestSolution);
                OnlookerBeePhase(fitness);
                bestSolution = findBest(bestSolution);
                ScoutBeePhase();
                bestSolution = findBest(bestSolution);
            }
            catch (InterruptedException e)
            {
                e.printStackTrace();
            }
            iterations--;

        }
        
        return new ProblemSolution(bestSolution, pInstance);
    }

    //Bees flow control//
    private double[] EmployedBeePhase() throws InterruptedException
    {
        double[] fitness = new double[beeColony.size()];
        for(int i = 0; i < beeColony.size(); i++)
        {
            beeColony.get(i).swapToEmployBee();
            beeThreads[i] = new Thread(beeColony.get(i));
        }

        runBeeThreads();

        for(int i = 0; i < beeColony.size(); i++)
        {
            foodSources.set(i, beeColony.get(i).getMyPlace());
            fitness[i] = beeColony.get(i).getFitness();
        }

        return fitness;
    }

    private void OnlookerBeePhase(double[] fitness) throws InterruptedException
    {
        double[] probs = calcProbDistForOnlookerPhase(fitness);
        for(int i = 0; i < beeColony.size(); i++)
        {
            beeColony.get(i).swapToOnlooker(foodSources, probs);
            beeThreads[i] = new Thread(beeColony.get(i));
        }

        runBeeThreads();

        for(int i = 0; i < beeColony.size(); i++)
        {
            foodSources.set(i, beeColony.get(i).getMyPlace());
        }
        
    }

    private void ScoutBeePhase() throws InterruptedException
    {
        for(int i = 0; i < beeColony.size(); i++)
        {
            beeColony.get(i).swapToScout();
            beeThreads[i] = new Thread(beeColony.get(i));
        }

        runBeeThreads();

        for(int i = 0; i < beeColony.size(); i++)
        {
            foodSources.set(i, beeColony.get(i).getMyPlace());
        }

    }

    private void runBeeThreads() throws InterruptedException
    {
        for (Thread beeThread : beeThreads)
        {
            beeThread.start();
        }

        for (Thread beeThread : beeThreads)
        {
            beeThread.join();

        }
    }


    //Utils//
    private ArrayList<Integer> findBest(ArrayList<Integer> prevBest)
    {
        ArrayList<Integer> best = new ArrayList<>(prevBest);
        int bestValue = ProblemSolution.getObjectiveValue(best.get(best.size()-1), best, pInstance);
        for(ArrayList<Integer> foodSource : foodSources)
        {
            int currValue = ProblemSolution.getObjectiveValue(foodSource.get(foodSource.size()-1), foodSource, pInstance);
            if(currValue < bestValue)
            {
                best = foodSource;
                bestValue = currValue;
            }
        }

        return best;
    }

    private double[] calcProbDistForOnlookerPhase(double[] allFoodSourcesFitness)
    {
        double[] probs = new double[allFoodSourcesFitness.length];
        double sum = 0.0;

        for(double fit : allFoodSourcesFitness)
        {
            sum += fit;
        }
        probs[0] = allFoodSourcesFitness[0] / sum;
        for(int i = 1; i < probs.length; i++)
        {
            probs[i] = probs[i-1] + allFoodSourcesFitness[i] / sum;
        }

        return probs;
    }

    private boolean terminationCriteriaFullfiled()
    {
        return iterations == 0;
    }

    private void initializationPhase()
    {
        foodSources = new ArrayList<>();
        int dimension = pInstance.getDimension();
        for(int i = 0; i < populationSize; i++)
        {
            foodSources.add(generateRandomPath(dimension));
        }
    }
}
