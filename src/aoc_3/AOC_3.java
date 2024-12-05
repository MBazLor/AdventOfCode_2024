
package aoc_3;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author Manuel Bazarra
 */
public class AOC_3 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws FileNotFoundException, IOException {
        FileReader fr = new FileReader("input.txt");
        BufferedReader br = new BufferedReader(fr);
        String line; //Utilizado sólo para extraer los datos del buffer
        StringBuilder data = new StringBuilder();
        
        //Almacenamos todos los datos en una sola cadena para evitar complicaciones con BufferedReader
        while((line = br.readLine()) != null){
            data.append(line);
        }
        
        System.out.printf("%.0f",parseAll(data)); 
        System.out.printf("\n%.0f",parseConditional(data));   
    }

    
    /**
     * Procesa todos los datos de input.txt linea a linea
     * Se refactoriza para aceptar un StringBuilder en lugar
     * de un BufferedReader
     * @param br
     * @return
     * @throws IOException 
     */
    private static double parseAll(StringBuilder data) throws IOException {

        double result;
        result = parseLine(data.toString());
        
        return result;
    }
    
    
    /**
     * Procesa una linea, busca el patron mul(n1,n2)
     * y acumula el resultado de cada mul()
     * 
     * @param line
     * @return 
     */
    private static double parseLine(String line){

        Pattern pattern = Pattern.compile("mul[(]\\d+,\\d+[)]", Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(line);
        String matchStr;
        double result = 0;

        while(matcher.find()){                  //Por cada ocurrencia
            matchStr = matcher.group();         //Obtenemos el String que concuerda con la regexp
            result += processMul(matchStr);   //Procesamos la operacion y sumamos
        }
        
        return result;  
    }
    
    
    /**
     * Procesa un string que representa la operacion: mul(n1,n2)
     * 
     * @param mul
     * @return 
     */
    private static double processMul(String mul){
        String[] nums;
        
        mul = mul.substring(4, mul.length()-1); //Nos quedamos sólo con lo de dentro de los parentesis
        nums = mul.split(","); //Separamos cada valor en un array
       
        return Double.parseDouble(nums[0]) * Double.parseDouble(nums[1]);
    }

    
    
    /**
     * Procesa las lineas y sólo opera con las que están comprendidas entre do-dont
     * Se tiene en cuenta que los datos empiezan en do, aunque no está expresamente
     * representado en input.txt
     * 
     * @return
     * @throws IOException 
     */
    private static double parseConditional(StringBuilder data) throws IOException {
        
        int indexBegin = 0; //Indices que se utilizarán para marcar donde empieza y termina una linea procesable
        int indexEnd;
        double result = 0;
        //Rexexps para do y don't
        Pattern pattern = Pattern.compile("don't", Pattern.CASE_INSENSITIVE);
        Matcher matchDont = pattern.matcher(data);
        Pattern pattern2 = Pattern.compile("do(?!n't)", Pattern.CASE_INSENSITIVE);
        Matcher matchDo = pattern2.matcher(data);
        
        //Segun el enunciado los datos empiezan como DO, asi que lo primero es buscar el primer dont
        while(matchDont.find(indexBegin)){
            
            indexEnd = matchDont.end();
            result += parseLine(data.substring(indexBegin, indexEnd));
            
            if(matchDo.find(indexEnd)){ //Una vez localizado buscamos el siguiente DO a partir del último DONT
                indexBegin = matchDo.end(); 
            } else
                break;    
        }
        
        return result;
    }
    
    
    
}
