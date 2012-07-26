/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package scanclient;

import scanclient.JSONObject;
import java.io.*;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import scanclient.barcodeObject;
import scanclient.shared;

/**
 *
 * @author phillip.oegema
 */
public class settingsFile {
     private String filename = "C:\\Key Files\\settings";
    
    File file = new File(filename);
    
   // BufferedOutputStream bufferedOutput = null;
   
    PrintWriter out = null;
    shared shared = null;
    
    String filePath = "";
    
    String recordsPath = "";
    
    private void createFile(){
           
        try{
            file.createNewFile();
            //get paths for records and path
        }
        catch(Exception e){
            JOptionPane.showMessageDialog(null, "Could not create File.\n" + e.toString());
        }
    }
    
    public boolean openForWrite(){
        if(file.exists()){
           try{
              out= new PrintWriter(new FileWriter(filename, true));          
           }
           catch(Exception e){
               JOptionPane.showMessageDialog(null, "Error opening file.\n" + e.toString());
               return false;
           }
           return true;
        }
       else{
          if(JOptionPane.showConfirmDialog(null, "File does not exist. Create one?") == JOptionPane.YES_OPTION)
              createFile();
            return true;
       }
    }
    
    public void writeToFile(barcodeObject o){
       if(file.exists()){
           try{         
               openForWrite();
                out.println(o.getJSONObject().toString());
                out.close();
           }
           catch(Exception e){
               JOptionPane.showMessageDialog(null, "Error writing to file.\n" + e.toString());
           }
          // BufferedOutputStream bs = new BufferedOutputStream(out);
       }
       else{
          if(JOptionPane.showConfirmDialog(null, "File does not exist. Create one?") == JOptionPane.YES_OPTION){
              createFile();
              writeToFile(o);
          }
       }    
    }
    
    public void closeFile(){
        try{
            out.close();
        }
        catch(Exception e){
            JOptionPane.showMessageDialog(null, "Error closing file." + e.toString());
        }
    }
    
    public void readFromFile() {
        if(file.exists()){        
        
        BufferedReader bufferedReader = null;
        
        try {
            
            //Construct the BufferedReader object
            bufferedReader = new BufferedReader(new FileReader(filename));
            
            String line = null;
            
            while ((line = bufferedReader.readLine()) != null) {
                
                //Process the data, here we just print it out

                try{
                    JSONObject json = new JSONObject(line);
                    if(json.getString("type").equals("objects"))
                        filePath = json.getString("objects");
                    else if(json.getString("type").equals("records"))
                        recordsPath = json.getString("records");
                    else
                        JOptionPane.showMessageDialog(null, "Improper settings file format");
                    
                }catch(scanclient.JSONException je){
                    System.out.println("Error reading in file" + je.toString());
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
        else{
            if(JOptionPane.showConfirmDialog(null, "Settings file does not exist. Create one?") == JOptionPane.YES_OPTION){
              createFile();
            }
        }
    }

    public String getFilePath() {
        return filePath;
    }

    public String getRecordsPath() {
        return recordsPath;
    }
    
}
