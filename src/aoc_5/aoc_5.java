

package aoc_5;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Scanner;

/**
 *
 * @author Manuel Bazarra
 */
public class aoc_5 {
    
    public static String FILE_PATH = "src/aoc_5/input.txt";
    ArrayList<int[]> constraints = new ArrayList<>();
    ArrayList<int[]> updates = new ArrayList<>();
    
    public static void main (String[] args) throws FileNotFoundException{
        aoc_5 printController = new aoc_5();
        printController.parseFile(FILE_PATH);
        
        
    }
    
    private void parseFile(String filePath) throws FileNotFoundException{
        FileReader fr = new FileReader(filePath);
        Scanner sc = new Scanner(fr);
        ArrayList<Integer[]> constraints = new ArrayList<>();
        String line;
        int counter = 0;
        
        //Parse constraints
        while(sc.hasNext()){
            if((line = sc.nextLine()).equals("")) {
                break;
            } 
            String[] strVals = line.split("|");
            constraints.add(new Integer[]{Integer.valueOf(strVals[0]), Integer.valueOf(strVals[1])});
        }
 
        
        
        //Parse updates
        
    }
    
    
    
}
