package fallingsand;

import java.awt.*;

public class SandLab
{
    //add constants for particle types here
    public static final int EMPTY = 0;
    public static final int METAL = 1;
    public static final int SAND = 2;
    public static final int WATER = 3;
    public static final int LILYPAD = 4;
    public static final int CLAM = 5;
    public static final int ANGEL = 6;
    
    //do not add any more fields
    private int[][] grid;
    private SandDisplay display;
    public static final int ROWS = 120;
    public static final int COLUMNS = 80;

    public static void main(String[] args)
    {
        SandLab lab = new SandLab(ROWS, COLUMNS);
        lab.run();
    }

    public SandLab(int numRows, int numCols){
        String[] names;
        names = new String[7];
        names[EMPTY] = "Empty";
        names[METAL] = "Metal";
        names[SAND] = "Sand";
        names[WATER] = "Water";
        names[LILYPAD] = "Lily Pad";
        names[CLAM] = "Clam";
        names[ANGEL] = "Angel";
        display = new SandDisplay("Falling Sand", numRows, numCols, names);
        grid = new int [ROWS][COLUMNS];
    }

    //called when the user clicks on a location using the given tool
    private void locationClicked(int row, int col, int tool){
        grid[row][col]= tool;
    }

    //copies each element of grid into the display
    public void updateDisplay(){
        Color empty = new Color(0, 0, 0);
        Color metal = new Color(112,128,144);
        Color sand = new Color(255,215,0);
        Color water = new Color(0,255,255);
        Color lilyPad = new Color(107,142,35);
        Color clam = new Color(255,182,193);
        Color angel = new Color(245,245,245);
        
        for (int row = 0; row < ROWS; row++)
        {
            for (int column = 0; column < COLUMNS; column++)
            {
                if (grid[row][column] == EMPTY){
                    display.setColor(row, column, empty);
                }
                else if(grid[row][column] == METAL){
                    display.setColor(row, column, metal);
                }
                else if(grid[row][column] == SAND){
                    display.setColor(row, column, sand);
                }
                else if(grid[row][column] == WATER){
                    display.setColor(row, column, water);
                }
                else if(grid[row][column] == LILYPAD){
                    display.setColor(row, column, lilyPad);
                }
                else if(grid[row][column] == CLAM){
                    display.setColor(row, column, clam);
                }
                else if(grid[row][column] == ANGEL){
                    display.setColor(row, column, angel);
                }
            }
        }
    }

    //called repeatedly.
    //causes one random particle to maybe do something.
    public void step(){
        //Select a random particle in the grid
        int row = (int)(Math.random() * ROWS);
        int column = (int)(Math.random() * COLUMNS);
        
        //Sand
        if(grid[row][column] == SAND && row < ROWS - 1){
            
            //Set previous location to empty
            //Only move sand if location below is empty or water
            if(grid[row + 1][column] == EMPTY || grid[row + 1][column] == WATER){
                grid[row][column] = grid[row + 1][column];
                grid[row + 1][column] = SAND;
                
            }
            
        }
        //Water
        else if(grid[row][column] == WATER && (row < ROWS - 1 && row > 0 && column < COLUMNS -1 && column > 0)){
            
            //Water move down to the bottom
            if(grid[row + 1][column] == EMPTY){
                
                grid[row + 1][column] = WATER;
                grid[row][column] = EMPTY;
                
            }
            else{
                //random left or right
                int move = (int)(Math.random() * 2);
                if(move == 0 && grid[row][column - 1] == EMPTY){
                    //move left
                    grid[row][column] = EMPTY;
                    grid[row][column - 1] = WATER;
                }   
                else if(move == 1 && grid[row][column + 1] == EMPTY){
                    //move right
                    grid[row][column] = EMPTY;
                    grid[row][column + 1] = WATER;
                }
            }
            
        }
        //Lily pad
        else if(grid[row][column] == LILYPAD && row < ROWS - 1){
            
            //Float on top of the sand and water, sink if water comes on top of lilypad
            //Move lily pad location if space below is empty
            if(grid[row + 1][column] == EMPTY){
                grid[row][column] = grid[row + 1][column];
                grid[row + 1][column] = LILYPAD;
                
            }
        }
        //Clam
        else if(grid[row][column] == CLAM  && row < ROWS - 1){
            
            //Sit on top of sand, but below the water
            //Move clam location if the space below is either empty or water-float if water is low enough
            if(grid[row + 1][column] == EMPTY || grid[row + 1][column] == WATER || grid[row + 1][column] == LILYPAD){
                grid[row][column] = grid[row + 1][column];
                grid[row + 1][column] = CLAM;
                
            }
        }
        //Angel
        else if(grid[row][column] == ANGEL && (row < ROWS - 1 && row > 0 && column < COLUMNS -1 && column > 0)){
            
            //Set previous location to empty
            //Only move Angel up if location above is empty
            if(grid[row - 1][column] == EMPTY){
                grid[row][column] = grid[row - 1][column];
                grid[row - 1][column] = ANGEL;
                
            }
            else{
                //random left or right
                int move = (int)(Math.random() * 2);
                if(move == 0 && grid[row][column - 1] == EMPTY){
                    //move left
                    grid[row][column] = EMPTY;
                    grid[row][column - 1] = ANGEL;
                }   
                else if(move == 1 && grid[row][column + 1] == EMPTY){
                    //move right
                    grid[row][column] = EMPTY;
                    grid[row][column + 1] = ANGEL;
                }
            }
        }
        
    }

    //do not modify
    public void run()
    {
        while (true)
        {
            for (int i = 0; i < display.getSpeed(); i++)
            {
                step();
            }

            updateDisplay();
            display.repaint();
            display.pause(1);  //wait for redrawing and for mouse
            int[] mouseLoc = display.getMouseLocation();

            if (mouseLoc != null)  //test if mouse clicked
            {
                locationClicked(mouseLoc[0], mouseLoc[1], display.getTool());
            }
        }
    }
}
