package solver.bees;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

import algs.ProblemInstance;
import solution.ProblemSolution;
import solver.ProblemSolver;

public class BeeColonySolver implements ProblemSolver
{
    int populationSize;
    private ArrayList<ArrayList<Integer>> foodSources;
    private ProblemInstance pInstance;

    private ArrayList<Bee> beeColony;
    private final Thread[] beeThreads;

    private int iterations;
    public BeeColonySolver(int populationSize, int iterations)
    {
        this.populationSize = populationSize;
        this.iterations = iterations;
        beeThreads = new Thread[populationSize];

    }

    @Override
    public ProblemSolution solveInstance(ProblemInstance problemInstance)
    {
        pInstance = problemInstance;
        initializationPhase();
        beeColony = new ArrayList<>();

        for(int i = 0; i < populationSize; i++)
        {
            beeColony.add(new Bee(foodSources.get(i), pInstance));
        }
        ArrayList<Integer> bestSolution = findBest(foodSources.get(0));
        while( !terminationCriteriaFullfiled())
        {
            System.out.println(iterations);
            try
            {
                double[] fitness = EmployeedBeePhase();
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

    private double[] EmployeedBeePhase() throws InterruptedException
    {
        double[] fitness = new double[beeColony.size()];
        for(int i = 0; i < beeColony.size(); i++)
        {
            beeColony.get(i).swapToEmployBee();
            beeThreads[i] = new Thread(beeColony.get(i));
        }

        runBees();

        for(int i = 0; i < beeColony.size(); i++)
        {
            foodSources.set(i, beeColony.get(i).getMyPlace());
            fitness[i] = beeColony.get(i).getFitness();
        }

        return fitness;
    }

    private void OnlookerBeePhase(double[] fitness) throws InterruptedException
    {
        for(int i = 0; i < beeColony.size(); i++)
        {
            beeColony.get(i).swapToOnlooker(foodSources, fitness);
            beeThreads[i] = new Thread(beeColony.get(i));
        }

        runBees();

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

        runBees();

        for(int i = 0; i < beeColony.size(); i++)
        {
            foodSources.set(i, beeColony.get(i).getMyPlace());
        }

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

    private void runBees() throws InterruptedException
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

}
