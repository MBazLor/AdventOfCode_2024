
package aoc_4;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

/**
 *
 * @author Manuel Bazarra
 */
public class AOC_4 {

    
    ArrayList<ArrayList<Character>> charMatrix = new ArrayList<>(); //Insertaremos los caracteres en una matriz bidimensional
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws FileNotFoundException, IOException {
        
        FileReader fr = new FileReader("input.txt");
        
        AOC_4 letterSoup = new AOC_4();
        
        letterSoup.setMatrixFromFile(fr);

        letterSoup.findWords();
        
        
    }

    
    /**
     * 
     * @param fr
     * @return
     * @throws IOException 
     */
    private void setMatrixFromFile( FileReader fr) throws IOException {
        
        int c;
        int index = 0;

        charMatrix.add(new ArrayList<>());
        while( (c= fr.read()) != -1 ){
            if(c == '\n'){
                charMatrix.add(new ArrayList<>());
                index++;
                continue;
            }
            charMatrix.get(index).add((char) c);
        }
        
    }

    
    /**
     * 
     * @param charMatrix 
     */
    private void findWords() {
        
        String[] line;
        
        line = getHorizontal(charMatrix);
        System.out.println(Arrays.toString(line));
    }

    
    /**
     * Recorremos el arraylist de manera horizontal
     * y lo convertimos en array de strings
     * 
     * @param charMatrix
     * @return 
     */
    private static String[] getHorizontal(ArrayList<ArrayList<Character>> charMatrix) {
        
        String[] stringRows = new String[charMatrix.size()];
        String line="";
        
        for(int i = 0 ; i < charMatrix.size() ; i++){
            for(Character c : charMatrix.get(i)){
                line += c;
                System.out.println(line);
            }
            stringRows[i] = line;
            line="";
            
        }
        
        
        
        return stringRows;      
    }
    
    
    
    
}
