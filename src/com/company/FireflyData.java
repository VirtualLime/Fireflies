package com.company;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;

/**
 * #143584
 * Tyler Arsenault
 * The class that uses the data from the firefly class
 */
public class FireflyData implements Serializable{
    //Temporary data variable that is used in storing information
    private ArrayList<Fly> flies;
    //Instantiating the flies array list
    public FireflyData() {
        flies = new ArrayList<Fly>();
    }
    //Loading the array list
    public ArrayList<Fly> load() throws FileNotFoundException, IOException, ClassNotFoundException {
        //Instantiating fileinputstream input to read data from a binary file
        boolean done = false;
        FileInputStream input = new FileInputStream("src/b.bin");
        ObjectInputStream objectInput = new ObjectInputStream(input);
        //Reading the actual objects from the binary file
        while (!done) {
            try {
                Fly fly = (Fly) objectInput.readObject();
                flies.add(fly);
            }
            //If end of file exception is reached then break out of the sentinel loop
            catch(EOFException e){
                break;
            }
        }
        //Return the array list that was read
        return flies;

    }

    /**
     * Saving data to a temporary array list
     * @param data data that is being saved
     * @throws FileNotFoundException
     * @throws IOException
     */
    public void save(ArrayList<Fly> data) throws FileNotFoundException, IOException {
        //Clearing the arraylist beforehand
        flies.clear();
        //Fileoutputstream to write to a binary file
        FileOutputStream output = new FileOutputStream("src/b.bin");
        ObjectOutputStream objectOutput = new ObjectOutputStream(output);
        //Iterates through all of the data
        for (int i = 0; i < data.size(); i++) {
            objectOutput.writeObject(data.get(i));
        }
        objectOutput.close();
    }
}