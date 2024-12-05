
package aoc_4;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author Manuel Bazarra
 */
public class AOC_4 {

    
    ArrayList<ArrayList<Character>> charMatrix = new ArrayList<>(); //Insertaremos los caracteres en una matriz bidimensional
    
    /**
     * @param args the command line arguments
     * @throws java.io.FileNotFoundException
     */
    public static void main(String[] args) throws FileNotFoundException, IOException {
        
       
        AOC_4 letterSoup = new AOC_4();
        
        letterSoup.setMatrixFromFile("src/aoc_4/input.txt");
        letterSoup.findWords();
        letterSoup.findCrossed();
  
    }

    
    /**
     * Fill the matrix from a given file
     * @param fr
     * @return
     * @throws IOException 
     */
    private void setMatrixFromFile( String file) throws IOException {
        
        FileReader fr = new FileReader(file);
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
     * Find XMAS words
     * 
     * @param charMatrix 
     */
    private void findWords() {
        
        String[] lines;
        int found = 0;
        
        lines = getHorizontal(charMatrix);
        found += findWord("XMAS", lines);
        lines = reverse(lines);
        found += findWord("XMAS", lines);
        
        lines = getVertical(charMatrix);
        found += findWord("XMAS", lines);
        lines = reverse(lines);
        found += findWord("XMAS", lines);
        
        lines = getDiagonalLR(charMatrix);
        found += findWord("XMAS", lines);
        lines = reverse(lines);
        found += findWord("XMAS", lines);
        
        lines = getDiagonalRL(charMatrix);
        found += findWord("XMAS", lines);
        lines = reverse(lines);
        found += findWord("XMAS", lines);
        
        System.out.println(found);
        
        
        
        
    }

    
    /**
     * Go through List in horizzontal manner, from left to right.
     * Each line is stored in a String array
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
            }
            
            stringRows[i] = line;
            line="";  
        }
        return stringRows;      
    }
    
    /**
     * Return an array of strings by processing up to down lines on the matrix
     * 
     * @param charMatrix
     * @return 
     */
    private String[] getVertical(ArrayList<ArrayList<Character>> charMatrix) {
        
        String[] stringLines = new String[charMatrix.size()];
        String line;
        
        for(ArrayList<Character> row : charMatrix){
            for( int i = 0 ; i < row.size() ; i++){
                line = "";
                for( int j = 0 ; j < charMatrix.size(); j++){
                    line += charMatrix.get(j).get(i);
                }
                stringLines[i] = line;
            }
        }
        return stringLines;    
    }
    
    /**
     * Counts number of matches that 
     * a word has in a String array
     * 
     * @param word
     * @param lines
     * @return 
     */
    public int findWord (String word, String[] lines){
        int found = 0;
        Pattern p =  Pattern.compile("XMAS", Pattern.CASE_INSENSITIVE);
        Matcher matcher;
        
        for(String line : lines){
            matcher = p.matcher(line);
            while(matcher.find()){
                found ++;
            }
        }
        
        return found;
    }

    /**
     * Returns the same array as input but with the row characters reversed
     * @param lines
     * @return 
     */
    private String[] reverse(String[] lines) {
        
        String reversedLine;
        
        for(int i = 0; i < lines.length ; i++){
            
            reversedLine = "";
            for (int j = lines[i].length()-1 ; j >= 0 ; j--){
                reversedLine += lines[i].charAt(j);
            }
            
            lines[i] = reversedLine; 
        }
        
        return lines;
    }

    /**
     * Get an array of strings by processing diagonals on the matrix from left to right
     * 
     * @param charMatrix
     * @return 
     */
    private String[] getDiagonalLR(ArrayList<ArrayList<Character>> charMatrix) {
        
        String[] stringLines;
        String line;
        
        int xSize = charMatrix.get(0).size();
        int ySize = charMatrix.size();
        int xPos = 0;
        int yPos = 0;
        stringLines = new String[xSize+ySize-1];
        
        //Beginning in top right corner
        for(int i = (xSize -1) ; i >= 0; i-- ){
            line = "";
            
            for(xPos = i; xPos < xSize; xPos++ ){
                line += charMatrix.get(yPos).get(xPos);
                yPos ++;
                if(yPos >= ySize){
                    break;
                }
            }
            yPos = 0;
            stringLines[xSize-1-i] = line;
        }

        //when top left corner is reached, walk to bottom left corner
        xPos = 0;
        for(int i = 1 ; i < ySize; i++ ){
            line = "";
            
            for(yPos = i; yPos < ySize; yPos++ ){
                line += charMatrix.get(yPos).get(xPos);
                xPos ++;
                if(xPos >= xSize){
                    break;
                }
            }
            xPos = 0;
            stringLines[xSize+i-1] = line;
        }
          
        return stringLines; 
    }
    
    /**
     * Get an array of strings by processing diagonals from right to left.
     * 
     * @param charMatrix
     * @return 
     */
    private String[] getDiagonalRL(ArrayList<ArrayList<Character>> charMatrix) {
        
        String[] stringLines;
        String line;
        
        int xSize = charMatrix.get(0).size();
        int ySize = charMatrix.size();
        int xPos = 0;
        int yPos = 0;
        stringLines = new String[xSize+ySize-1];
        
        //Beginning in top left corner
        for(int i = 0 ; i < xSize; i++ ){
            line = "";
            
            for(xPos = i; xPos >= 0; xPos-- ){
                line += charMatrix.get(yPos).get(xPos);
                yPos ++;
                if(yPos >= ySize){
                    break;
                }
            }
            yPos = 0;
            stringLines[i] = line;
        }

        //when top right corner is reached, walk to bottom right corner
        xPos = xSize-1;
        for(int i = 1 ; i < ySize; i++ ){
            line = "";
            
            for(yPos = i; yPos < ySize; yPos++ ){
                line += charMatrix.get(yPos).get(xPos);
                xPos --;
                if(xPos <= 0){
                    break;
                }
            }
            xPos = xSize-1;
            stringLines[xSize+i-1] = line;
        }
          
        return stringLines; 
    }

    
    /**
     * The approach is find the letter A, wich allways be the center of the cross
     * and we know a cross allways form a 3x3 square, so we avoid begin searching
     * in borders.
     * 
     * When a "A" is found, we send the coordinates to a finder function
     */
    private void findCrossed() {
        
        String[] stringLines;
        String line;
        int found = 0;
        
        int xSize = charMatrix.get(0).size();
        int ySize = charMatrix.size();
        int xPos = 0;
        int yPos = 0;
        
        for(yPos = 1 ; yPos < xSize-1 ; yPos++){
            for(xPos = 1; xPos < ySize-1; xPos++){
                if(charMatrix.get(yPos).get(xPos).equals('A')){
                    if(crossSearch(xPos,yPos)){
                        found++;
                    }
                }
            }
        }
        
        System.out.println(found);
    }

    /**
     * Extraxt a portion of the matrix in an imaginary square of 3x3 
     * but only picking the vertex, wich has the only relevant data
     * Then check with a regexp that matches only with the valid letter combinations
     * Example
     *  M.S
     *  .A.  ==> this will be MSMS combination
     *  M.S
     * 
     * @param xPos
     * @param yPos 
     */
    private boolean crossSearch(int xPos, int yPos) {
         
        String extractedVertex = "";
        String regexp = "MMSS|MSMS|SMSM|SSMM";
        Pattern p = Pattern.compile(regexp, Pattern.CASE_INSENSITIVE);
        
        xPos--;
        yPos--;
        extractedVertex += charMatrix.get(yPos).get(xPos);
        extractedVertex += charMatrix.get(yPos).get(xPos+2);
        extractedVertex += charMatrix.get(yPos+2).get(xPos);
        extractedVertex += charMatrix.get(yPos+2).get(xPos+2);
        
        Matcher m = p.matcher(extractedVertex);
            
        return m.find();  
    }

    
    
}
