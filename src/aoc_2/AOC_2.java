

package aoc_2;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import java.util.Iterator;
import java.util.Scanner;

/**
 *
 * @author Manuel Bazarra
 */
public class AOC_2 {

    private static String filePath ="src/aoc_2/input.txt";
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws FileNotFoundException, IOException {
        
        ArrayList<String> safeLevels = new ArrayList<>();
        ArrayList<String> unsafeLevels = new ArrayList<>();
        
        FileReader fr = new FileReader(filePath);
        BufferedReader br = new BufferedReader(fr);
        
        populateAllAsUnsafe(br, unsafeLevels); //Inserta todos los valores en el array de unsafe
        clasifyOperations( safeLevels,unsafeLevels); //Clasifica las safe y unsafe en su array correspondiente
        
        System.out.println("Safe levels: " + safeLevels.size()); //306
        System.out.println("Damped safe levels: " + problemDamper(unsafeLevels) ); //60
        
    }
    
    
    /**
     * Llena el array  unsafeLevels, con todos los datos que todavía deberían
     * estar sin procesar.
     * Es por ello que asumimos que son "unsafe"
     * 
     * @param br
     * @param unsafeLevels
     * @throws IOException 
     */
    private static void populateAllAsUnsafe( BufferedReader br, ArrayList<String> unsafeLevels) throws IOException{
        
        String line;
        
        while((line = br.readLine()) != null){
            if(line.equals(""))
                continue;
            unsafeLevels.add(line);
        }
    }
    
    
    /**
     * Metodo para saber si una progresion es ascendente o descendente
     * Debemos analizar una cadena entera para saber si es ascendente o no, puesto que con los 2 primeros
     * valores, podríamos equivocarnos ( por ejemplo en la cadena  5 3 6 7)
     * 
     * @param previous
     * @param current
     * @return 1 ascendente 0 neutro -1 descendente
     */
    private static byte isAscending(String str){
        
        ArrayList<Integer> data = new ArrayList<>();
        Scanner sc = new Scanner(str);
        
        int prev = -1;
        int curr = -1;
        int sum = 0;
        while(sc.hasNext()){
            curr = sc.nextInt();
            if( prev == -1){
                prev = curr;
                continue;
            }
            if( prev - curr < 0 ){
                sum ++;
            }else if (prev - curr > 0 ){
                sum--;
            }
        }
        
        if(sum > 0){
            sum = 1;
        }  
        else if(sum < 0)
        {
            sum = -1;
        }
        else
        {
            //System.err.println("ALERTA!: No se ha podido determinar la dirección, se establece en asc");
            sum = 1;
        }
        
        return (byte) sum;
    }
    
    
    /**
     * Clasifica cada nivel y lo almacena en el array correspondiente
     * @param safeLevels
     * @param unsafeLevels
     * @throws IOException 
     */
    public static void clasifyOperations ( ArrayList<String> safeLevels, ArrayList<String> unsafeLevels) throws IOException{
        
        //Por cada "level" del array, comprobamos si es "safe". 
        //Utilizamos un iterador, para eliminar elementos de forma segura en el bucle
        String currLevel = "";
        Iterator<String> iterator = unsafeLevels.iterator(); 
        
        while(iterator.hasNext()){

            currLevel = iterator.next();
            
            if( checkSafeLevel(currLevel) ){
                safeLevels.add(currLevel);
                iterator.remove(); //Eliminamos de manera segura de unsafeLevels
            }
        }
        
    }
    
    
    /**
     * Prueba por fuerza bruta si quitando alguno de los valores, el nivel es seguro
     * 
     *   347 349
     * @param unsafeLevels 
     */
    private static int problemDamper( ArrayList<String> unsafeLevels){
          
        
        int dampedCounter = 0;
        String[] levelNums;
        String dampedString;
        
        for(String level : unsafeLevels){
            levelNums = level.split(" ");
            
            for(int i= 0 ;  i < levelNums.length ; i ++){
                dampedString = popByIndex(i, levelNums);
                if (checkSafeLevel(dampedString)){
                    dampedCounter++;
                    break;
                }
            }  
        } 
        
        return dampedCounter;
    }
 
    
    /**
     * Permite eliminar el elemento de indice "index" y devolver
     * los valores en un String de numeros separados por un blanco
     * 
     * @param index
     * @param line
     * @return 
     */
    private static String popByIndex(int index, String[] line){
        
        String result ="";
        
        for(int i = 0; i < line.length ; i ++){
            if(i == index)
                continue;
    
            result += " " + line[i];
        }
        result = result.strip();
        return result;
    }
    
    
    /**
     * Refactorización de funcion que comprueba si un nivel es seguro
     * Se simplifica el código y se extraen métodos para poder ser reutilizados
     * 
     * @param line 
     */
    private static boolean checkSafeLevel(String line){
        
        int previous;
        int current;
        int diff;
        boolean isSafe = true;
        
        byte asc = isAscending(line); //Antes de nada verificamos si la progresion es asc o desc
        String[] lineNums = line.strip().split(" "); //Convertimos datos en un array, lo que facilita su analisis
      
        //comenzamos siempre por el 2 valor, puesto que analizar el primero de manera aislada no es relevante
        for(int i =  1; i < lineNums.length ; i++){
            
            previous = Integer.parseInt(lineNums[i-1]);
            current = Integer.parseInt(lineNums[i]);
            diff = current - previous;

            if (checkDiffExceed(diff) || checkWayChanged(diff, asc) )
            {
                isSafe = false;
                break;
            }    
        }

        return isSafe;   
    }
    
    
    
    /**
     * Comprueba si ha superado el máximo diferencial permitido
     * @param n
     * @return 
     */
    private static boolean checkDiffExceed(int n){
        
        return Math.abs(n) > 3;
    }
    
    
    /**
     * Comprobar si una secuencia ha cambiado de sentido según la diferencia con
     * su numero anterior
     * @param diff
     * @param asc
     * @return 
     */
    private static boolean checkWayChanged(int diff, int asc){
        int currWay;
        
        if(diff > 0 )
            currWay = 1;
        else if(diff < 0 )
            currWay = -1;
        else
            currWay = 0;
        
        return currWay != asc;   
    }
    
    
}
