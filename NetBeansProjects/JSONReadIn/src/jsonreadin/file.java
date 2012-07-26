/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jsonreadin;

import java.lang.Boolean;
import javax.swing.JOptionPane;
import java.io.File;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.OutputStream;
import java.io.FileOutputStream;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.io.FileReader;
import JSON.JSONObject;
import JSON.JSONString;
/**
 *
 * @author phillip.oegema
 */



public class file {
    private final String FILENAME = "C:\\Key Files\\records";
    
    File file = new File(FILENAME);
    
    ObjectOutputStream objectOut = null;
    
    
    private void createFile(){
           
        try{
            file.createNewFile();
            openForWrite();
        }
        catch(Exception e){
            JOptionPane.showMessageDialog(null, "Could not create File.\n" + e.toString());
        }
    }
    
    public boolean openForWrite(){
        if(file.exists()){
           try{
                objectOut = new ObjectOutputStream(new FileOutputStream(FILENAME, true));
                System.err.println("Opened for write");
                //http://www.javadb.com/write-to-file-using-bufferedoutputstream
                //http://www.javadb.com/writing-objects-to-file-with-objectoutputstream
           }
           catch(Exception e){
               JOptionPane.showMessageDialog(null, "Error opening file.\n" + e.toString());
               return false;
           }
          // BufferedOutputStream bs = new BufferedOutputStream(out);
           return true;
        }
       else{
          if(JOptionPane.showConfirmDialog(null, "File does not exist. Create one?") == JOptionPane.YES_OPTION)
              createFile();
            return true;
       }
    }
   
    
    public void closeFile(){
        try{
            objectOut.close();
        }
        catch(Exception e){
            JOptionPane.showMessageDialog(null, "Error closing file." + e.toString());
        }
    }
    
    public void readFromFile(String filename) {
        
        BufferedReader bufferedReader = null;
        
        try {
            
            //Construct the BufferedReader object
            bufferedReader = new BufferedReader(new FileReader(filename));
            
            String line = null;
            
            while ((line = bufferedReader.readLine()) != null) {
                //Process the data, here we just print it out
                System.out.println(line);
                try{
                    JSONObject json = new JSONObject(line);
                    
                    System.out.println(json.getString("type"));
                    System.out.println(json.getString("name"));
                    System.out.println(json.getString("barcode"));
                    System.out.println(json.getString("description"));
                }catch(JSON.JSONException je){
                    System.out.println(je.toString());
                }
                
            }
            
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            //Close the BufferedReader
            try {
                if (bufferedReader != null)
                    bufferedReader.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }
     
    } 


