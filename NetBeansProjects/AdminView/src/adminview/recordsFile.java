/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package adminview;


import java.text.DateFormat;
import java.text.ParsePosition;
import java.io.*;
import java.util.ArrayList;
import javax.swing.JOptionPane;

import java.util.Date;
import java.text.SimpleDateFormat;
/**
 *
 * @author phillip.oegema
 */
public class recordsFile {
   // private String filename = "C:\\Key Files\\records";
    private String filename = "";
    
    File file; 
    
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
    
    public void writeToFile(clockedObject o){
       if(file.exists()){
           try{         
               openForWrite();
                out.println(o.getJSONObject().toString());
                out.close();
           }
           catch(Exception e){
               shared.playSound("alert");
               JOptionPane.showMessageDialog(null, "Error writing to file.\n" + e.toString());
           }
          // BufferedOutputStream bs = new BufferedOutputStream(out);
       }
       else{
          if(JOptionPane.showConfirmDialog(null, "File does not exist. Create one?") == JOptionPane.YES_OPTION){
              shared.playSound("ding");
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
            shared.playSound("alert");
            JOptionPane.showMessageDialog(null, "Error closing file." + e.toString());
        }
    }
    
    public ArrayList<clockedObject> readFromFile(shared s) {
        
        ArrayList<clockedObject> objects = new ArrayList();
        
        BufferedReader bufferedReader = null;
        
        shared =s;
        
        try {
            
            //Construct the BufferedReader object
            bufferedReader = new BufferedReader(new FileReader(filename));
            
            String line = null;
            
            while ((line = bufferedReader.readLine()) != null) {
                clockedObject bco = new clockedObject();
                //Process the data, here we just print it out

                try{
                    JSONObject json = new JSONObject(line);
                    bco.setName(json.getString("name"));
                    bco.setType(json.getString("type"));
                    bco.setInOrOut(json.getString("inOrOut"));
                  // System.out.println(json.getString("barcode"));
                    
                    bco.setBarcodeObject(shared.getBarcodeObject(json.getString("barcode")));
                    //DateFormat d = DateFormat.getDateInstance();
                    DateFormat d = new SimpleDateFormat("EEE MMM dd hh:mm:ss z yyyy");
                    if(bco.getType().equals("user")){
                        try{ 
                            bco.setCheckinTime(d.parse(json.getString("checkInTime")));
                          //  bco.setCheckinTime(d.parse("07/10/96 4:50 PM, PDT"));
                            if(bco.getInOrOut().equals("out"))
                                bco.setCheckoutTime(d.parse(json.getString("checkOutTime")));
                        }catch(java.text.ParseException p){
                            shared.playSound("alert");
                            p.printStackTrace();
                        }
                    }
                    else{
                        bco.setCheckedOutTo(shared.getBarcodeObject(json.getString("checkedOutToBarcode")));
                        try{
                            bco.setCheckoutTime(d.parse(json.getString("checkOutTime")));
                            if(bco.getInOrOut().equals("in"))
                                bco.setCheckinTime(d.parse(json.getString("checkInTime")));
                         }catch(java.text.ParseException p){
                             shared.playSound("alert");
                            p.printStackTrace();
                        }
                    }
                    objects.add(bco);            
                    
                }catch(JSONException je){
                    shared.playSound("alert");
                    je.printStackTrace();
                }
            }
            bufferedReader.close();
        } catch (FileNotFoundException ex) {
            shared.playSound("alert");
            ex.printStackTrace();
        } catch (IOException ex) {
            shared.playSound("alert");
            ex.printStackTrace();
        } finally {
            //Close the BufferedReader
            try {
                if (bufferedReader != null)
                    bufferedReader.close();
            } catch (IOException ex) {
                shared.playSound("alert");
                ex.printStackTrace();
            }
        }

        return objects;
    }
    public void setFilename(String filename){
        this.filename = filename;
    }
}
