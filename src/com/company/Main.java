package com.company;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.Serializable;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;


/**
 * #143584
 * Tyler Arsenault
 *
 * This is the main class used to start the program, the main class also instantiates a menu bar that is used to save and load
 * the program. There is only 1 binary file that is written out, this file is used to store and retrieve the state.
 */
public class Main extends JFrame{
    private JMenuBar mb;
    private JMenu menu;
    private JMenuItem loadState;
    private JMenuItem saveState;

    private boolean save = false;
    private boolean load = false;

    private Lock lock;

    private final FireflyComponent component = new FireflyComponent();

    private ActionListener fileListener = new File();

    /**
     * Main program to start the whole program
     * @param args System arguments
     */
    public static void main(String[] args){
        //Instantiating the Main object and lock object.
        Main main = new Main();
        main.lock = new ReentrantLock();
        main.makeMenuBar();
        main.setDefaultCloseOperation(main.EXIT_ON_CLOSE);

        //Making the frame and setting the background to black

        main.setSize(400, 400);
        main.setResizable(false);
        main.getContentPane().setBackground(Color.BLACK);
        main.add(main.component, BorderLayout.CENTER);

        main.setVisible(true);
        main.component.startFlies();
    }

    /**
     * Making the menu bar that saves and loads the flies
     */
    public void makeMenuBar(){
        menu = new JMenu("File");
        mb = new JMenuBar();
        loadState = new JMenuItem("Load State");
        saveState = new JMenuItem("Save State");
        loadState.addActionListener(fileListener);
        saveState.addActionListener(fileListener);
        menu.add(loadState);
        menu.add(saveState);
        mb.add(menu);
        setJMenuBar(mb);
    }

    /**
     * Action listener that is coupled with the JMenuItems, if an item is clicked it sets the appropriate boolean values in the component
     * program.
     */
    public class File implements ActionListener {
        /**
         * Standard method that comes with ActionListener interface that implements and uses an action event
         * @param e This is the action event that is created with the mouse click on the JMenuItems
         */
        public void actionPerformed(ActionEvent e){
            //A key is used to make sure that the program doesn't lock into always loading or always saving.
            if (e.getSource() == loadState) {
                component.initializeSaveLoad(false, true, 1);
            } else if (e.getSource() == saveState) {
                component.initializeSaveLoad(true, false, 1);
            }
        }

    }

}