package com.company;
import java.io.Serializable;

    /**
     * Fly object that stores a wide range of values to represent the flies behaviour
     */
    public class Fly implements Serializable {


        private double currentPhase;
        private double naturalFlashFreq;
        private double currentFlashFreq;
        private boolean flash;
        private int x;
        private int y;
        private int radius;
        private int numberOfFlies;
        //Constructor to read in a variety of values
        public Fly(double currentPhase, double naturalFlashFreq, double currentFlashFreq, boolean flash, int x, int y, int radius, int numberOfFlies){
            this.currentPhase = currentPhase;
            this.naturalFlashFreq = naturalFlashFreq;
            this.currentFlashFreq = currentFlashFreq;
            this.flash = flash;
            this.x = x;
            this.y = y;
            this.radius = radius;
            this.numberOfFlies = numberOfFlies;
        }
        //Getting the current phase of the fly
        public double getCurrentPhase(){
            return currentPhase;
        }
        //Getting the x value
        public int getX(){
            return x;
        }
        //Getting the y value
        public int getY(){
            return y;
        }
        //Getting the current flash frequency
        public double getCurrentFlashFreq(){
            return currentFlashFreq;
        }
        //Getting the boolean value if a fly flashed or not
        public boolean getFlash(){
            return flash;
        }
        //Setting the flash frequency
        public void setCurrentFlashFreq(double flashFreq){
            this.currentFlashFreq = flashFreq;
        }
        //Setting flash
        public void setFlash(boolean methodFlash){
            this.flash = methodFlash;
        }
        //Setting the current phase of the fly
        public void setCurrentPhase(double phase){
            this.currentPhase = phase;
        }
        //Setting the number of flies
        public void setNumberOfFlies(int numFlies){
            this.numberOfFlies = numFlies;
        }
    }

