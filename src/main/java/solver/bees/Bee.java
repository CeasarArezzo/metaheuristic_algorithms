package solver.bees;

import algs.ProblemInstance;
import solution.ProblemSolution;
import solver.ProblemSolver;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.IntStream;

public class Bee implements Runnable
{
    private final Random randomizer;
    private ArrayList<Integer> myPlace;

    private final ProblemInstance pInstance;

    private int failCounter = 0;

    private final int failThreshold = 10;

    private BeePhase phase;

    private double fitOfMyPlace;

    private ArrayList<ArrayList<Integer>> allFoodSources;
    private double[] allFoodSourcesFitness;


    public Bee(ArrayList<Integer> yourPlace, ProblemInstance pInstance)
    {
        this.pInstance = pInstance;
        myPlace = yourPlace;
        fitOfMyPlace = fitness(yourPlace);
        randomizer = new Random(System.currentTimeMillis());

    }
    private ArrayList<Integer> findNeighbouringFoodSource()
    {

        int i = randomizer.nextInt(myPlace.size());
        int j = i;
        while(j == i)
        {
            j = randomizer.nextInt(myPlace.size());
        }
        if(i > j)
        {
            int temp = j;
            j = i;
            i = temp;
        }
        //TODO implement flag
        return invert(i, j, myPlace);
    }

    private double fitness(ArrayList<Integer> foodSource)
    {
        return 1.0 / (1.0 + ProblemSolution.getObjectiveValue(foodSource.get(foodSource.size()-1), foodSource, pInstance));
    }

    private ArrayList<Integer> swap(int i, int j, ArrayList<Integer> prevSol)
    {
        ArrayList<Integer> newSol = new ArrayList<>(prevSol);
        Collections.swap(newSol, i, j);

        return newSol;
    }

    private ArrayList<Integer> invert(int i, int j, ArrayList<Integer> prevSol)
    {
        ArrayList<Integer> newSol = new ArrayList<>(prevSol);
        Collections.reverse(newSol.subList(i, j));

        return newSol;

    }

    private void BeeEmployBee()
    {
        ArrayList<Integer> neighPlace = findNeighbouringFoodSource();

        if(fitness(myPlace) > fitness(neighPlace))
        {
            failCounter++;
        }
        else
        {
            myPlace = new ArrayList<>(neighPlace);
            fitOfMyPlace = fitness(myPlace);
            failCounter = 0;
        }

        myPlace = new ArrayList<>(fitness(myPlace) > fitness(neighPlace) ? myPlace : neighPlace);
    }

    private void BeeOnlooker()
    {
        myPlace = new ArrayList<>(allFoodSources.get(onlookerChooseFsIndex()));
        fitOfMyPlace = fitness(myPlace);
        BeeEmployBee();
    }

    private void BeeScout()
    {
        if(failCounter >= failThreshold)
        {
            myPlace = new ArrayList<>(IntStream.rangeClosed(0, pInstance.getDimension() - 1)
                    .boxed().toList());

            Collections.shuffle(myPlace);
            fitOfMyPlace = fitness(myPlace);
        }
        else
        {
            BeeEmployBee();
        }
    }

    @Override
    public void run()
    {
        switch (phase)
        {
            case EmployBee -> BeeEmployBee();
            case Onlooker -> BeeOnlooker();
            case Scout -> BeeScout();
        }
    }




    public void swapToEmployBee()
    {
        phase = BeePhase.EmployBee;
    }

    public void swapToOnlooker(ArrayList<ArrayList<Integer>> allFoodSources, double[] allFoodSourcesFitness)
    {
        this.allFoodSources = new ArrayList<>(allFoodSources);
        this.allFoodSourcesFitness = allFoodSourcesFitness;
        phase = BeePhase.Onlooker;
    }

    public void swapToScout()
    {
        phase = BeePhase.Scout;
    }


    public ArrayList<Integer> getMyPlace()
    {
        return myPlace;
    }

    private double[] onlookerCalcProbs()
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
            probs[i] = allFoodSourcesFitness[i] / sum;
        }

        return probs;
    }

    private int onlookerChooseFsIndex()
    {
        double[] probs = onlookerCalcProbs();

        double random = ThreadLocalRandom.current().nextDouble(0.0, probs[probs.length - 1]);

        for(int i = 0; i < probs.length; i++)
        {
            if(random < probs[i])
            {
                return i;
            }
        }

        return probs.length- 1;
    }

    public double getFitness()
    {
        return fitOfMyPlace;
    }




}
