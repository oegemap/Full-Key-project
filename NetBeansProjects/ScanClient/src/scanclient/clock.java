/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package scanclient;

import scanclient.barcodeObject;
import scanclient.shared;
import javax.swing.JOptionPane;
/**
 *
 * @author phillip.oegema
 */
public class clock {
    shared shared;
    public boolean clockObject(String barcode, shared s){
        shared = s;
        barcodeObject b;
        if(shared.isUser(barcode)){
            b = shared.getBarcodeObject(barcode);
            shared.clockTechOnDuty(b);
            return false;
        }
        else if(shared.isKey(barcode)){
            return true;
        }
        return false;
    }
    
    public void checkoutKey(String key, String user){
        
        barcodeObject userObject = shared.getBarcodeObject(user);
        barcodeObject keyObject = shared.getBarcodeObject(key);
        
        if(userObject.getType().equals("user") && 
                keyObject.getType().equals("key")){
            
            shared.checkoutKey(keyObject, userObject);
        
        }
    }
        
        public void checkinKey(String key){
            barcodeObject keyObject = shared.getBarcodeObject(key);
        
          if(keyObject.getType().equals("key")){
            
            shared.checkoutKey(keyObject, null);
        
        }        
    }
}
