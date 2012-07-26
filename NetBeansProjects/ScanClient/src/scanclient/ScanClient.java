/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package scanclient;
import scanclient.CheckoutInterface;
import scanclient.shared;
/**
 *
 * @author phillip.oegema
 * @version 1.0
 */
public class ScanClient {
    
    static shared shared = new shared();
    
    
    public static void main(String[] args) {
     
        shared.setup();
        
        CheckoutInterface.main(shared);      
    }
    
}
