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
    private ArrayList<ArrayList<Integer>> places;
    private BeePhase phase;
    private double fitOfMyPlaces[];

    //Fail control utils//
    private int[] failCounter;
    private final int failThreshold;


    //onlooker fields//
    private ArrayList<ArrayList<Integer>> allFoodSources;
    private double[] foodSourceProbDist;


    public Bee(ArrayList<ArrayList<Integer>> places, ProblemInstance pInstance, int failThreshold)
    {
        this.pInstance = pInstance;
        this.places = places;
        fitOfMyPlaces = new double[places.size()];
        for (int i = 0; i < places.size(); i++)
        {
            fitOfMyPlaces[i] = fitness(places.get(i));
        }
        randomizer = new Random(System.currentTimeMillis());
        this.failThreshold = failThreshold;
        failCounter = new int[places.size()];
    }

    //main thread function//
    @Override
    public void run()
    {
        for (int i = 0; i < places.size(); i++)
        {
            switch (phase)
            {
                case EmployBee -> BeeEmployBee(i);
                case Onlooker -> BeeOnlooker(i);
                case Scout -> BeeScout(i);
            }
        }
    }

    //Bee state jobs//
    private void BeeEmployBee(int currentBee)
    {
        ArrayList<Integer> neighPlace = findNeighbouringFoodSource(currentBee);
    
        if(fitness(places.get(currentBee)) > fitness(neighPlace))
        {
            failCounter[currentBee]++;
        }
        else
        {
            places.set(currentBee, new ArrayList<>(neighPlace));
            fitOfMyPlaces[currentBee] = fitness(places.get(currentBee));
            failCounter[currentBee] = 0;
        }
    }

    private void BeeOnlooker(int currentBee)
    {
        places.set(currentBee, new ArrayList<>(allFoodSources.get(onlookerChooseFsIndex())));
        fitOfMyPlaces[currentBee] = fitness(places.get(currentBee));
        BeeEmployBee(currentBee);
    }

    private void BeeScout(int currentBee)
    {
        if(failCounter[currentBee] >= failThreshold)
        {
            places.set(currentBee, new ArrayList<>(IntStream.rangeClosed(0, pInstance.getDimension() - 1)
                    .boxed().toList()));

            Collections.shuffle(places.get(currentBee));
            fitOfMyPlaces[currentBee] = fitness(places.get(currentBee));
        }
        else
        {
            BeeEmployBee(currentBee);
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
    private ArrayList<Integer> findNeighbouringFoodSource(int currentBee)
    {
        int sizeToRoll = places.get(0).size();
        int i = randomizer.nextInt(sizeToRoll - 1); //8
        int j = randomizer.nextInt(sizeToRoll - i - 1) + i + 1; //9,1
        
        //TODO implement flag
        return invert(i, j, places.get(currentBee));
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

    public ArrayList<Integer> getMyPlace(int currentBee)
    {
        return places.get(currentBee);
    }


    public double getFitness(int currentBee)
    {
        return fitOfMyPlaces[currentBee];
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
