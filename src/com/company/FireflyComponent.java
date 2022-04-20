package com.company;

import javax.swing.*;
import java.awt.*;
import java.io.Serializable;
import java.util.ArrayList;

/**
 * #143584
 * Tyler Arsenault
 * This is the component class that constantly draws fireflies on the screen.
 */
public class FireflyComponent extends JComponent{
    //Instantiates the flyMain class that is responsible for updating the program logic
    private FireflyMain flyMain;
    //Arraylist of flies that has the data for the fireflies
    private ArrayList<Fly> flies = new ArrayList<Fly>();
    private JMenu menu;
    private JMenuBar mb;
    private JMenuItem loadState;
    private JMenuItem saveState;
    //Key to make sure that the program doesn't lock into an infinite loop of saving or loading
    private int key;

    private boolean save;
    private boolean load;

    public FireflyComponent(){
        //Instantiating the flyMain class
        flyMain = new FireflyMain(this, flies);
    }

    /**
     * Initializes the boolean values of saving and laoding
     * @param methodSave boolean variable to save the program
     * @param methodLoad boolean variable to load the program
     * @param methodKey integer key value that makes sure the program doesn't infinitely lock into saving or loading
     */
    public void initializeSaveLoad(boolean methodSave, boolean methodLoad, int methodKey){
        save = methodSave;
        load = methodLoad;
        key = methodKey;
    }

    /**
     * Paintcomponent that constantly paints to the screen, also includes the saving and loading logic
     * @param g
     */
    public void paintComponent(Graphics g){
        //Try and catch to make sure paintComponent is not interrupted
        try {
            //If key is one then make sure to only save or load once
            if(key == 1) {
                flyMain.initializeSaveLoad(save, load);
                key = 0;
            }
            //Else don't load or save at all
            else{
                flyMain.initializeSaveLoad(false, false);
            }
            flyMain.draw(g);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * Start the flies animation
     */
    public void startFlies(){
        //Fill the array list with information
        for(int i = 0; i < 100; i++){
            fillFlyInformation();
        }
        //This is the runnable class that each thread will use.
        class AnimationRunnable implements Runnable{
            public void run(){
                try {
                    //Constantly updates the flyMain class
                    flyMain.update();
                }
                catch(InterruptedException e){
                    System.out.print("Exception " + e);
                }
            }
        }
        //Test information
/*        for(int i = 0; i < flies.size(); i++){
            System.out.println("Current Phase Before: " + flies.get(i).getCurrentPhase());
        }*/
        //Instantiates a new runnable
        Runnable r = new AnimationRunnable();
        //Creating a thread in a for loop in case more threads need to be used for whatever reason
        for(int i = 0; i < 1; i++){
            Thread t = new Thread(r);
            //Starting the thread which will use the run method
            t.start();
        }

    }
    //Filling the arraylist with information on each specific fly that will be used in fly main
    public void fillFlyInformation(){
        flies.add(new Fly(Math.random()*(2*Math.PI), .785, .785, false, (int)(Math.random()*340), (int)(Math.random()*340), 0, 0));
    }

}