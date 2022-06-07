package solver.bees;

import java.util.ArrayList;
import java.util.concurrent.CountDownLatch;

import algs.ProblemInstance;
import solution.ProblemSolution;
import solver.ProblemSolver;

public class BeeColonySolver implements ProblemSolver
{

    //Problem fields//
    private ArrayList<ArrayList<Integer>> foodSources;
    private ProblemInstance pInstance;
    public BeePhase currentPhase = BeePhase.Idle;

    //Bees//
    private ArrayList<Bee> beeColony;
    private final Thread[] beeThreads;

    //Algorithm constants//
    int populationSize;
    private final int threshold;
    private int iterations;
    
    private final int beesPerThread;
    private final int threadsNumber;
    public volatile int threadsRunning = 0;
    public CountDownLatch latch;


    public BeeColonySolver(int populationSize, int iterations, int threshold, int beesPerThread)
    {
        this.populationSize = populationSize;
        this.iterations = iterations;
        this.threadsNumber = (int) Math.ceil((float)populationSize / beesPerThread);
        beeThreads = new Thread[threadsNumber];
        this.threshold = threshold;
        this.beesPerThread = beesPerThread;
    }

    @Override
    public ProblemSolution solveInstance(ProblemInstance problemInstance)
    {
        pInstance = problemInstance;
        initializationPhase();
        beeColony = new ArrayList<>();
        int subListBegin = 0;
        int subListEnd = Math.min(beesPerThread, populationSize);
        System.out.println("foodSources size - " + foodSources.size());
        System.out.println("beesPerThread - " + beesPerThread);
        System.out.println("threadsNumber - " + threadsNumber);

        for(int i = 0; i < threadsNumber; i++)
        {
            beeColony.add(new Bee(new ArrayList<ArrayList<Integer>>(foodSources.subList(subListBegin, subListEnd)),
                    pInstance, threshold, this));
            System.out.println("thread " + i + ", range: " + subListBegin + " " + subListEnd);
            subListBegin += beesPerThread;
            subListEnd = Math.min(subListEnd + beesPerThread, populationSize);
        }
        for(int i = 0; i < threadsNumber; i++)
        {
            beeThreads[i] = new Thread(beeColony.get(i));
        }
        ArrayList<Integer> bestSolution = findBest(foodSources.get(0));
        try
        {
            runBeeThreads();
        } catch (InterruptedException e1)
        {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
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
        double[] fitness = new double[populationSize];

        latch = new CountDownLatch(threadsNumber);
        currentPhase = BeePhase.EmployBee;

        synchronized (pInstance) 
        {
            pInstance.notifyAll();
        }
        latch.await();

        for(int i = 0; i < beeColony.size(); i++)
        {
            for (int j = 0; j < beesPerThread; j++)
            {
                if (i * beesPerThread + j < populationSize)
                {
                    foodSources.set(i, beeColony.get(i).getMyPlace(j));
                    fitness[i * beesPerThread + j] = beeColony.get(i).getFitness(j);
                }
            }
        }

        return fitness;
    }

    private void OnlookerBeePhase(double[] fitness) throws InterruptedException
    {
        double[] probs = calcProbDistForOnlookerPhase(fitness);
        for(int i = 0; i < beeColony.size(); i++)
        {
            beeColony.get(i).swapToOnlooker(foodSources, probs);
        }

        latch = new CountDownLatch(threadsNumber);
        currentPhase = BeePhase.Onlooker;
        
        synchronized (pInstance) 
        {
            pInstance.notifyAll();
        }
        latch.await();

        for(int i = 0; i < beeColony.size(); i++)
        {
            for (int j = 0; j < beesPerThread; j++)
            {
                if (i * beesPerThread + j < populationSize)
                {
                    foodSources.set(i, beeColony.get(i).getMyPlace(j));
                }
            }
        }
        
    }

    private void ScoutBeePhase() throws InterruptedException
    {
        latch = new CountDownLatch(threadsNumber);
        currentPhase = BeePhase.Scout;
        
        synchronized (pInstance) 
        {
            pInstance.notifyAll();
        }
        latch.await();

        for(int i = 0; i < beeColony.size(); i++)
        {
            for (int j = 0; j < beesPerThread; j++)
            {
                if (i * beesPerThread + j < populationSize)
                {
                    foodSources.set(i, beeColony.get(i).getMyPlace(j));
                }
            }
        }
    }

    private void runBeeThreads() throws InterruptedException
    {
        for (Thread beeThread : beeThreads)
        {
            beeThread.start();
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
