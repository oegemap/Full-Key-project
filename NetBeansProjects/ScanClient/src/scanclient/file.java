/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package scanclient;

import scanclient.JSONObject;
import java.io.*;
import java.lang.Boolean;
import javax.swing.JOptionPane;
import java.util.ArrayList;
import scanclient.shared;
import java.io.PrintWriter;

/**
 *
 * @author phillip.oegema
 */



public class file {
   // private String filename = "C:\\Key Files\\objects";
    private String filename = "";
    File file; 
    
   // BufferedOutputStream bufferedOutput = null;
   
    PrintWriter out = null;
    shared shared = null;
    
    public void setup(){
       file = new File(filename);
    }
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
    
    public ArrayList<barcodeObject> readFromFile() {
        
        ArrayList<barcodeObject> objects = new ArrayList();
        
        BufferedReader bufferedReader = null;
        
        try {
            
            //Construct the BufferedReader object
            bufferedReader = new BufferedReader(new FileReader(filename));
            
            String line = null;
            
            while ((line = bufferedReader.readLine()) != null) {
                barcodeObject bco = new barcodeObject();
                //Process the data, here we just print it out

                try{
                    JSONObject json = new JSONObject(line);
                    bco.setName(json.getString("name"));
                    bco.setBarcode(json.getString("barcode"));
                    bco.setType(json.getString("type"));
                    bco.setDescription(json.getString("description"));
                   
                    objects.add(bco);            
                    
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
        
        return objects;
    }
    
    public void setFilename(String filename){
        this.filename = filename;
    }
}
