package com.company;

import javax.swing.*;
import java.awt.*;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import static java.lang.System.currentTimeMillis;

/**
 * #143584
 * Tyler Arsenault
 * This is the main driving class that is used for updating and painting each fly onto the screen. It is also used in saving and loading the
 * program
 */
public class FireflyMain{
    //Current phase and current flash frequency is saved into two variables
    private double currentPhase;
    private double currentFlashFreq;

    //Used in saving number of flies that a specific fly has seen
    private int numberOfFliesSeen;
    //Radius of a circle around the fly
    private final int RADIUS = 100;
    //Saving and loading the program
    boolean save;
    boolean load;
    //This is the class used in saving and loading the program
    private FireflyData data = new FireflyData();

    //Instantiating a component, a lock, and an array list
    private JComponent component;
    private Lock sortStateLock;
    private ArrayList<Fly> flies;
    //Constructor for this class that instantiates a component and an arraylist, and a lock
    public FireflyMain(JComponent methodComponent, ArrayList<Fly> methodFlies) {
        flies = methodFlies;
        sortStateLock = new ReentrantLock();
        component = methodComponent;
    }

    /**
     * Used in drawing the flies
     * @param g
     * @throws InterruptedException
     */
    public void draw(Graphics g) throws InterruptedException {
        //Locks the program to make sure it is uninterrupted
        sortStateLock.lock();
        try {
            for(int i = 0; i < flies.size(); i++) {
                //Paints the fly yellow if its flash is true
                if(flies.get(i).getFlash() == true){
                    g.setColor(Color.YELLOW);
                }
                else {
                    //Else paint it a transparent grey color
                    Color color = new Color(255, 255, 255, 20);
                    g.setColor(color);
                }
                //Draw the fly
                g.fillRect(flies.get(i).getX(), flies.get(i).getY(), 10, 10);
            }
            for(int i = 0; i < flies.size(); i++){
                /**This is test code that visualizes a flies detection range*/
              /*  int alpha = 50;
                Color semiTransparentRed = new Color(200, 0, 0, alpha);
                g.setColor(semiTransparentRed);*/
                //Middle of a circle, x and y coordinates
                int circleX = flies.get(i).getX() - RADIUS;
                int circleY = flies.get(i).getY() - RADIUS;

//                g.fillOval(circleX, circleY, 200, 200);
                //x and y coordinate of the rectangle
                int x = flies.get(i).getX();
                int y = flies.get(i).getY();
                //Getting the number of flies seen that flashed
                numberOfFliesSeen = checkCollision(circleX, circleY, RADIUS, i);
                //Setting the number of flies seen
                flies.get(i).setNumberOfFlies(numberOfFliesSeen);
            }
/*            for(int i = 0; i < flies.size(); i++){
                System.out.println(flies.get(i).getNumberOfFlies());
            }*/

        } finally {
            sortStateLock.unlock();
            Thread.sleep(150);
            update();
        }
    }

    /**
     * Checking the collisions between the circles and the rectangle
     * @param circleX The x coordinate of the circle
     * @param circleY The y coordinate of the circle
     * @param radius The radius of the circle
     * @param position The position of the rectangle in the array list
     * @return Returns the count of the number of flies seen
     */
    public int checkCollision(int circleX, int circleY, int radius, int position){
        //Counting the number of flies seen that flashed
        int count = 0;
        //Iterating through all of the flies
        for(int i = 0; i < flies.size(); i++) {
            //If the fly tries to detect itself ignore it
            if(i == position){
                continue;
            }
            //Getting x and y coordinates for the flies
            int x = flies.get(i).getX();
            int y = flies.get(i).getY();
            double distance = Math.sqrt(Math.pow(circleX - x, 2) + Math.pow(circleY - y, 2));
            //If the circle overlaps with another fly then count it
            if(distance <= radius && flies.get(i).getFlash() == true){
                count++;
            }
        }
        //If it doesn't see another fly that is flashing then reset the flash frequency
        if(count == 0){
            flies.get(position).setCurrentFlashFreq(.785);
        }
        //Else modify the flash frequency then return the number of flies seen
        else {
            flies.get(position).setCurrentFlashFreq(flies.get(position).getCurrentPhase() + (.1 * count) * ((Math.sin((Math.PI * 2) - flies.get(position).getCurrentPhase()))));
        }
        return count;
    }

    /**
     * Initializing the program to set saving and loading behaviours
     * @param methodSave boolean variable to save the program
     * @param methodLoad boolean variable to load the program
     */
    public void initializeSaveLoad(boolean methodSave, boolean methodLoad){
        save = methodSave;
        load = methodLoad;
    }


    /**
     * Constantly updates the program
     * @throws InterruptedException
     */
    public void update() throws InterruptedException {
        //Iterates through all of the flies
        for (int i = 0; i < flies.size(); i++) {
            //Locks the program
            sortStateLock.lock();
            try {
                //Gets the x and y coordinates
                int x = flies.get(i).getX();
                int y = flies.get(i).getY();
                //Getting the current phase of the fly
                currentPhase = flies.get(i).getCurrentPhase();
                currentFlashFreq = flies.get(i).getCurrentFlashFreq();
                //Modifying the phase of the fly
                double newPhase = currentPhase + currentFlashFreq;


                //Setting the phase of the fly
                flies.get(i).setCurrentPhase(newPhase);
                //If the current phase goes over a certain value then reset it to 0 and the fly flashes yellow
                if(flies.get(i).getCurrentPhase() >= (2 * Math.PI)){
                    flies.get(i).setCurrentPhase(0);
                    flies.get(i).setFlash(true);
                }
                //Else set the flies flash to false
                else{
                    flies.get(i).setFlash(false);
                }
                //Check if the program needs to save or load the file
            } finally {
                saveOrLoad();
                sortStateLock.unlock();
            }
            /** Test code to print out test information*/
/*            for(int j = 0; j < flies.size(); j++) {
                System.out.println("Time: " + time);
                System.out.println("Flash: " + flash);
                System.out.println("X: " + flies.get(j).getX());
                System.out.println("Y: " + flies.get(j).getY());
                System.out.println("Current Flash Frequency: " + flies.get(j).getCurrentFlashFreq());
                System.out.println("Current Phase " + flies.get(j).getCurrentPhase());
            }*/
            //Repaints the component and goes to the draw method
            component.repaint();
        }
    }

    /**
     * Saving or loading the program
     */
    public void saveOrLoad(){
        //Locking the method
        sortStateLock.lock();
        //Try and finally to make sure that if the program is interrupted at least finally gets the chance to execute
        try {
            //If save is true then save the file
            if (save) {
                try {
                    data.save(flies);
                    save = false;
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                //If load is true then load the file
            } else if (load) {
                try {
                    ArrayList<Fly> previousState = data.load();
                    for(int i = 0; i < flies.size(); i++) {
                        flies.set(i, previousState.get(i));
                    }
                    System.out.print("Loaded");
                    load = false;
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }
        finally{
            sortStateLock.unlock();
        }
    }

}
