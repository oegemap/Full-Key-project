/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package adminclient;


import java.lang.Boolean;
import javax.swing.JOptionPane;

/**
 *
 * @author phillip.oegema
 */
public class NewKey {
    shared shared = null;
   
    public barcodeObject createNewKey(String name, String barcode, String description, shared s){
        shared = s;
        
        Boolean unique;
        
        if(name.length() < 2){
            JOptionPane.showMessageDialog(null, "Name field too short.");
            return null;
        }
        else if(barcode.length() <= 1){
            JOptionPane.showMessageDialog(null, "Please enter a valid barcode key.");
            return null;
        }
     
        //All tests pass, create new new object
        else{
        
            barcodeObject key = new barcodeObject();
        
            key.setName(name);
            key.setBarcode(barcode);
            key.setDescription(description);
            key.setType("key");
            
            unique = checkExistingCodes(key);
            
            if(unique)
                return key;
            else
                return null;
        }
    }
    
    
   //Modify to check for any existing barcodes with the same numbering as what was input. 
   private Boolean checkExistingCodes(barcodeObject k){
       if(shared.objectExists(k)){
           return false;
       }
       return true;
   }
}
