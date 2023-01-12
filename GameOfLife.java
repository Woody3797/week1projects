package week1projects;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GameOfLife {
    
    public static void main(String[] args) throws FileNotFoundException, IOException {
        
        BufferedReader br = new BufferedReader(new FileReader("./blinker.gol"));

        String line;
        int iter = 0;
        int row = 0;
        int col = 0;
        int startx = 0;
        int starty = 0;
        List<int[]> dataArr = new ArrayList<>();

        while ((line = br.readLine()) != null) {

            if (!line.startsWith("#")) {
                if (line.startsWith("GRID")) {
                    String startGrid[] = line.split(" ");
                    col = Integer.parseInt(startGrid[1]);
                    row = Integer.parseInt(startGrid[2]);
                } else if (line.startsWith("START")) {
                    String start[] = line.split(" ");
                    startx = Integer.parseInt(start[1]);
                    starty = Integer.parseInt(start[2]);
                }
            }

            if (line.startsWith("DATA")) {
                while ((line = br.readLine()) != null) {
                    line = line.replace('*', '1');
                    line = line.replace(' ', '0');
                    dataArr.add(convertToArray(line)); //adds each line as an array to arraylist dataArr
                }
            }
        }

        int[][] grid = new int[row][col];

        //copies initial starting conditions from dataArr to grid
        for (int i = 0; i < dataArr.size(); i++) {
            for (int j = 0; j < dataArr.get(i).length; j++) {
                grid[i+starty][j+startx] = dataArr.get(i)[j];
            }
        }

        //printing of starting conditions and 5 generations
        System.out.println("Generation 1 (start): ");
        for (var s : grid) {
            System.out.print(Arrays.toString(s));
            System.out.println("");
        }
        while (iter < 5) {
            iter++;
            grid = nextGen(grid, row, col);
            System.out.println("Generation " + (iter+1) + ": ");
            for (var r : grid) {
                System.out.print(Arrays.toString(r));
                System.out.println("");
            }
        }

        br.close();
    }


    public static int[] convertToArray(String n) {
        int[] arr = new int[n.length()];

        for (int i = 0; i < n.length(); i++) {
            arr[i] = Integer.parseInt(String.valueOf(n.charAt(i)));
        }
        return arr;
    }



    public static int[][] nextGen(int[][] grid, int row, int col) {

        int[][] next = new int[row][col];
        
        for (int x = 0; x < row; x++) {
            for (int y = 0; y < col; y++) {
                int liveNeighbour = liveNeighbour(grid, x, y);

                if (liveNeighbour == 2) {
                    next[x][y] = grid[x][y]; //cell stays in past state
                }
                if (liveNeighbour == 3) {
                    next[x][y] = 1; //populated in next
                }
                if (liveNeighbour > 3 || liveNeighbour < 2) {
                    next[x][y] = 0; //dies of solitude or overcrowding
                }
            }
        }
        return next;
    }


    public static int liveNeighbour(int[][] grid, int x, int y) {
        int row = grid.length;
        int col = grid[0].length;
        int liveNeighbour = 0;

        for (int i = Math.max(x - 1, 0); i <= Math.min(x + 1, row - 1); i++) {
            for (int j = Math.max(y - 1, 0); j <= Math.min(y + 1, col - 1); j++) {
                liveNeighbour += grid[i][j]; //checks each neighbouring cell for live cells,max/min for index out of bound error
            }
        }
        liveNeighbour -= grid[x][y]; //takes away current cell value
        return liveNeighbour;
    }
 
}
