
package aoc_1;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;


/**
 *
 * @author Manuel Bazarra
 */
public class AOC_1 {

    
    private static final String FILE_PATH = "src/aoc_1/input.txt"; //Ruta al archivo
    /**
     * Primer puzzle de Adventofcode.com
     * 
     * @param args
     * @throws FileNotFoundException
     * @throws IOException 
     */
    public static void main(String[] args) throws FileNotFoundException, IOException {
        //Cada una de las listas de ids
        ArrayList<Integer> l1 = new ArrayList<>();
        ArrayList<Integer> l2 = new ArrayList<>();
        int total = 0; //Resultado con la suma total de distancias
        
        FileReader fr = new FileReader(FILE_PATH);
        BufferedReader br = new BufferedReader(fr);
        
        populateLists(br, l1, l2);
        //Ordenamos ambas listas de manera ascendente (orden natural)
        Collections.sort(l1);
        Collections.sort(l2);
        
        total = calculateDistances(l1, l2); //Realizamos el calculo
        System.out.println("Total distance: "+ total);
        
        
        // *******SEGUNDA PARTE**********
        
        int score = calculateSimilarityScore(l1, l2);
        
        
        System.out.println("Similarity score: " + score);
       
    }
    
    
  
    /**
     * Lee el archivo y separa los nuemeros de cada columna
     * y inserta estos en su lista correspondiente
     * 
     * @param br buffer de lectura del archivo
     * @throws IOException 
     */
    private static void populateLists(BufferedReader br, ArrayList<Integer> l1, ArrayList<Integer> l2) throws IOException{
        
        String line; //representa una linea completa leida del fichero
        String[] valuesArr = new String[2];//los valores separados de cada linea
        
        while((line = br.readLine()) != null){
            valuesArr = line.replaceAll("\\s+", " ").split(" ");
            l1.add(Integer.valueOf(valuesArr[0]));
            l2.add(Integer.valueOf(valuesArr[1]));
        }
    }
    
    
    private static int calculateDistances(ArrayList<Integer> l1, ArrayList<Integer> l2){
        
        int total = 0;
        
        for(int n=0 ; n < l1.size(); n++){
            //Realizamos la operacion, le quitamos el signo puesto que siempre sumamos los valores absolutos
            total += Math.abs(l1.get(n) - l2.get(n));
        }
        
        return total;
    }
    
    
    /**
     * Calculo de la puntuacion de similitud.
     * Se multiplica el numero de la primera lista por el numero de veces
     * que aparece en la segunda. Esta operacion se repite por cada numero de la primera lista
     * y  se va sumando a un resultado final
     * 
     * @return 
     */
    private static int calculateSimilarityScore(ArrayList<Integer> l1, ArrayList<Integer> l2){
        int counter; //Contador de numero de repeticiones
        int score = 0;
        
        for(Integer num : l1){
            
            counter = 0;
            
            for(Integer n2 : l2){
                
                if(num.equals(n2)){
                    counter++;
                }    
            }
            score += num * counter; 
        }
        
        return score;
    }
    
    
    
}
