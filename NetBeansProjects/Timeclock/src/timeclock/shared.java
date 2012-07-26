/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package timeclock;
import java.util.ArrayList;
//import oit.key.checkout.Objects.*;

import javax.swing.JOptionPane;
import java.util.Date;
import java.util.Calendar;

//import java.lang.Boolean;
/**
 *
 * @author phillip.oegema
 */
public class shared {
    ArrayList<barcodeObject> objects = new ArrayList();
    ArrayList<clockedObject> timeclockList = new ArrayList();
    String[][] populatedList;
    ArrayList<String> users = new ArrayList();
    ArrayList<String> keys = new ArrayList();
    ArrayList<String> filter = new ArrayList();
    int filteredSize = 0;
    file file = new file();
    
    recordsFile recordsFile = new recordsFile();
    
    settingsFile settingsFile = new settingsFile();
    
    private ArrayList<clockedObject> techsOnDuty = new ArrayList();
    private ArrayList<clockedObject> keysCheckedOut = new ArrayList();
    PlaySound sound = new PlaySound();

    public int getFilteredSize() {
        return filteredSize;
    }
    
    public void setup(){
        ArrayList<barcodeObject> bl = new ArrayList();
        
        settingsFile.readFromFile();
        
        recordsFile.setFilename(settingsFile.getRecordsPath());
        file.setFilename(settingsFile.getFilePath());
        
        recordsFile.setup();
        file.setup();
        
        
        bl = file.readFromFile();
        
        if(bl !=null)
        for(barcodeObject b: bl){
            objects.add(b);
        }
       
        
    }
    
    public void timeClockSetObjectFile(String s){
        ArrayList<barcodeObject> bl = new ArrayList();
        file.setFilename(s);
        file.setup();
        
        bl = file.readFromFile();
        
        if(bl !=null)
        for(barcodeObject b: bl){
            objects.add(b);
            filter.add(b.getName());
            if(b.getType().equals("user")){
                users.add(b.getName());
            }
            else
                keys.add(b.getName());
            }
        }
    
    
    public void timeClockSetRecordsFile(String s){
        recordsFile.setFilename(s);
        recordsFile.setup();
        timeclockList = recordsFile.readFromFile(this);
    }
    
    public void setupCheckout(){
        coldList(recordsFile.readFromFile(this));  
    }
    
    
    public void setupArray(){
        int offset = 0;
        boolean inFilterList = false;
        filteredSize = 0;
        populatedList = new String[timeclockList.size()][4];
        for(int i = 0; i < populatedList.length; i ++){
            inFilterList = false;
            for(int j = 0; j < filter.size(); j++){
                if(timeclockList.get(i).getName().equals(filter.get(j))){
                    inFilterList = true;
                    filteredSize++;
                    populatedList[i - offset][0] = timeclockList.get(i).getName();
                    populatedList[i - offset][1] = timeclockList.get(i).getInOrOut();
            
                    if(populatedList[i - offset][1].equals("in"))
                        populatedList[i - offset][2] = timeclockList.get(i).getCheckinTime().toString();
                    else
                        populatedList[i - offset][2] = timeclockList.get(i).getCheckoutTime().toString();
                    if(timeclockList.get(i).getType().equals("key"))
                        populatedList[i - offset][3] = timeclockList.get(i).getCheckedOutTo().getName();
                    else
                        populatedList[i - offset][3] = "";
                }
            }
            if(inFilterList == false)
                offset++;
        }
    }

    public void setFilter(String[] userArray, String[] keyArray){
         filter.clear();
         for(int i = 0; i < userArray.length; i++)
             filter.add(userArray[i]);
         for(int i = 0; i < keyArray.length; i++)
             filter.add(keyArray[i]);
         setupArray();
    }
    public String[] getKeys() {
        String[] keysArray = new String[keys.size()];
        keys.toArray(keysArray);
        return keysArray;
    }

    public String[] getUsers() {
        String[] userArray = new String[users.size()];
        users.toArray(userArray);
        return userArray;
    }

    
    public String[][] getPopulatedList() {
        return populatedList;
    }
    
   // public String getDate(Date d){
       // Calendar c;
      //  c.setTime(d);
        
      //  return c.get(Calendar.DAY_OF_WEEK);
   // }

    public boolean isKeyCheckedOut(String barcode){
        for(clockedObject c: keysCheckedOut){
            if(c.getBarcodeObject().getBarcode().equals(barcode))
                return true;
        }
        return false;
    }
    
    public ArrayList<barcodeObject> getObjects() {
        return objects;
    }
    
    public boolean objectExists(barcodeObject item){
    if(objects != null){
        for(barcodeObject b: objects){
            if(b.getName().equals(item.getName())){
                playSound("alert");
                JOptionPane.showMessageDialog(null, "Name already in use.");
                return true;
            } 
               
            if(b.getBarcode().equals(item.getBarcode())){
                playSound("alert");
                JOptionPane.showMessageDialog(null, "Barcode already in use.");
                return true;             
            }
        }
    }
        return false;
    }
    
    public void addObject(barcodeObject bco){
        objects.add(bco);
       file.writeToFile(bco);
    }
    
    public void addObjectToArray(barcodeObject bco){
        objects.add(bco);
    }
    
    public void closeFile(){
        file.closeFile();
    }
    
    public boolean isUser(String barcode){
        for(barcodeObject b: objects){
            if(barcode.equals(b.getBarcode()) && b.getType().equals("user"))
                return true;
        }
        return false;
    }
    
    public boolean isKey(String barcode){
        for(barcodeObject b: objects){
            if(barcode.equals(b.getBarcode()) && b.getType().equals("key"))
                return true;
        }
        return false;
    }

    public ArrayList<clockedObject> getTechsOnDuty() {
        return techsOnDuty;
    }
    
    public void clockTechOnDuty(barcodeObject co){
        boolean onDuty = false;
        int index = -1;
        
        for(clockedObject t: techsOnDuty){
            if(t.getBarcodeObject().getBarcode().equals(co.getBarcode())){
                onDuty = true;
                index = techsOnDuty.indexOf(t);
            }
        }
            if(onDuty){
                checkForKeys(index);
                clockedObject goingOut = techsOnDuty.get(index);
                goingOut.setInOrOut("out");
                goingOut.setCheckoutTime(new Date());
                writeToRecordsFile(goingOut);
                techsOnDuty.remove(index);
                
            }
            else{
                clockedObject clocking = new clockedObject();
                clocking.setBarcodeObject(co);
                clocking.setCheckinTime(new Date());
                clocking.setName(co.getName());
                clocking.setType("user");
                clocking.setInOrOut("in");
                techsOnDuty.add(clocking);  
                writeToRecordsFile(clocking);
            }
    }
    
    private void checkForKeys(int index){
        if(keysCheckedOut.size() > 0){
            for(clockedObject c: keysCheckedOut){
                if(c.getType().equals("key") && c.getCheckedOutTo().getBarcode().equals(techsOnDuty.get(index).getBarcodeObject().getBarcode()))
                    playSound("alert");
                    JOptionPane.showMessageDialog(null, "You currently have keys checked out.  Please return them.");
            }
        }
    }
    
    public barcodeObject getBarcodeObject(String barcode){
        for(barcodeObject b: objects){
         //   System.out.println("scanning: " + b.getBarcode());
            if(b.getBarcode().equals(barcode)){
                return b;
            }
        }
        return null;
    }
    
    public String getNamesOnDuty(){
        String techs = "";
        
        for(clockedObject t: techsOnDuty){
            techs = techs.concat(t.getName());
          /*  for(clockedObject k: keysCheckedOut){
                if(t.getBarcodeObject().getBarcode().equals(k.getCheckedOutTo().getBarcode()))
                    techs = techs.concat(" (" + k.getName() + ")");
            }*/
            techs = techs.concat("\n");         
        }
        return techs;
    }
    
    public String getKeysCheckedOut(){
        String keys = "";
        for(clockedObject k: keysCheckedOut){
            keys = keys.concat(k.getName() + ": " + k.getCheckedOutTo().getName() + ": " + k.getCheckoutTime().toString() + "\n");
        }
        return keys;
    }
    
    public void writeToRecordsFile(clockedObject co){
        recordsFile.writeToFile(co);
    }
    
    public void checkoutKey(barcodeObject key, barcodeObject user){
        clockedObject co = new clockedObject();
        boolean out = false;
        int index = -1;
        for(clockedObject c: keysCheckedOut){
            //Checkin key
            if(c.getBarcodeObject().getBarcode().equals(key.getBarcode())){
                index = keysCheckedOut.indexOf(c);
                co = c;
                out = true;           
            }
        }
        if(out){ 
            co.setCheckinTime(new Date());
            co.setInOrOut("in");
            
        }
        else{
           co.setBarcodeObject(key);
            co.setCheckedOutTo(user);
            co.setCheckoutTime(new Date());
            co.setInOrOut("out");
            co.setName(key.getName());
            co.setType("key");
            keysCheckedOut.add(co); 
        }
        writeToRecordsFile(co);
        
        if(out && index >= 0 && index <= keysCheckedOut.size())
            keysCheckedOut.remove(index);
    }
    
    public void coldList(ArrayList<clockedObject> clo){
        for(clockedObject c: clo){
            if(c.getType().equals("user")){
                if(c.getInOrOut().equals("in"))
                    techsOnDuty.add(c);
                else{
                    //for(clockedObject t: techsOnDuty){
                    for(int i = 0; i < techsOnDuty.size(); i++){
                        if(techsOnDuty.get(i).getBarcodeObject().getBarcode().equals(c.getBarcodeObject().getBarcode())){
                            techsOnDuty.remove(i);
                            c = null;
                        }
                    }
                }
            }
            else if(c.getType().equals("key")){
                if(c.getInOrOut().equals("out"))
                    keysCheckedOut.add(c);
                else{
                    for(int i = 0; i < keysCheckedOut.size(); i++){// clockedObject k: keysCheckedOut){
                        if(keysCheckedOut.get(i).getBarcodeObject().getBarcode().equals(c.getBarcodeObject().getBarcode())){
                            keysCheckedOut.remove(i);
                            i = keysCheckedOut.size();
                        }
                    }
                }
            }        
        }
        
        
    }
    public void playSound(String noise){
        try{
            sound.main(noise);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
