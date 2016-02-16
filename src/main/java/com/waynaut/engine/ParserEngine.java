package com.waynaut.engine;

import com.waynaut.log.Log4J;
import com.waynaut.exception.ParserException;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;

/**
 *
 * @author Rahman Md Redwanur
 * 
 */

public class ParserEngine {
   
    /**
     * Regular expression of a string splitter using comma.
     */
    private static final String regExpCommaSplitter = ",\\s*";
    private final Log4J log;

    /**
     * Constructor of parserEngine
     * @param log instance of Log4J
     */
    public ParserEngine(Log4J log) {
        this.log = log;
    }

    /**
     * Split single line using comma separator. 
     * @param path file absolute path that want to parse.
     * @return ArrayList of String ArrayList.
     * @throws ParserException @see Constructor(String userException, Throwable exp, String exceptionCode)
     * <p> exception code PE_001 -> IOException while data parsing.
     * <p> exception code PE_002 -> Exception occurs during BufferReader and FileInputStream closing.
     */
    public ArrayList <ArrayList<String>> parse(String path) throws ParserException{
        
        ArrayList <ArrayList<String>> list = new ArrayList<>();

        FileInputStream inputStream = null ;
        BufferedReader buffReader = null;
        int fieldCounter =0;
        
        try {

            inputStream = new FileInputStream(path);

            buffReader = new BufferedReader(new InputStreamReader(inputStream,"UTF-8"));
            String strline;
            int lineCounter = 0;
            
            while ((strline = buffReader.readLine()) != null){
               
                String splitedList[] = strline.split(regExpCommaSplitter);
                
                if(lineCounter == 0){
                    fieldCounter = splitedList.length;
                }
                
                ArrayList<String> dataList= new ArrayList<>(Arrays.asList(splitedList));

                if(splitedList.length != fieldCounter){
                    for(int i = splitedList.length;i<fieldCounter;i++)    
                        dataList.add("");
                }
            
                list.add(dataList);
                lineCounter++;
            }
        } 
        catch (IOException ex) {
            String systemError = String.format("%s->%s error : %s",ParserEngine.class.getName(),ParserException.getCallingMethod(),ex.getMessage());
            
            if(log != null){
                log.getLogger().error(systemError);
            }

            throw new ParserException(ex.getMessage(), ex, "PE_001");
        }
        finally{

            try {
                
                if(inputStream!= null)    
                    inputStream.close();
                
                if(buffReader!= null)  
                    buffReader.close();
                
            } catch (IOException ex) {
                throw new ParserException(ex.getMessage(), ex, "PE_002");
            }
        }

        return list;
    }

}
