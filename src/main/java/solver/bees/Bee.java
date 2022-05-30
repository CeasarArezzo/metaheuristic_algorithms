package solver.bees;

import algs.ProblemInstance;
import solution.ProblemSolution;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.IntStream;

public class Bee implements Runnable
{
    //External utils//
    private final ProblemInstance pInstance;
    private final Random randomizer;

    //Bee state//
    private ArrayList<Integer> myPlace;
    private BeePhase phase;
    private double fitOfMyPlace;

    //Fail control utils//
    private int failCounter = 0;
    private final int failThreshold;


    //onlooker fields//
    private ArrayList<ArrayList<Integer>> allFoodSources;
    private double[] foodSourceProbDist;


    public Bee(ArrayList<Integer> yourPlace, ProblemInstance pInstance, int failThreshold)
    {
        this.pInstance = pInstance;
        myPlace = yourPlace;
        fitOfMyPlace = fitness(yourPlace);
        randomizer = new Random(System.currentTimeMillis());
        this.failThreshold = failThreshold;

    }

    //main thread function//
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

    //Bee state jobs//
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


    //Bee state swappers//
    public void swapToEmployBee()
    {
        phase = BeePhase.EmployBee;
    }

    public void swapToOnlooker(ArrayList<ArrayList<Integer>> allFoodSources, double[] probs)
    {
        this.allFoodSources = allFoodSources;
        this.foodSourceProbDist = probs;
        phase = BeePhase.Onlooker;
    }

    public void swapToScout()
    {
        phase = BeePhase.Scout;
    }


    //Utils SECTION//
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

    public ArrayList<Integer> getMyPlace()
    {
        return myPlace;
    }


    public double getFitness()
    {
        return fitOfMyPlace;
    }


    private int onlookerChooseFsIndex()
    {

        double random = ThreadLocalRandom.current().nextDouble(0.0, foodSourceProbDist[foodSourceProbDist.length - 1]);

        for(int i = 0; i < foodSourceProbDist.length; i++)
        {
            if(random < foodSourceProbDist[i])
            {
                return i;
            }
        }

        return foodSourceProbDist.length- 1;
    }

}
