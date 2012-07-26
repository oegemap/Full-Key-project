/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package adminview;
import java.util.TimerTask;
import java.lang.Thread;
/**
 *
 * @author phillip.oegema
 */
public class Task {
    
    shared shared;
    
    public void setup(shared s){
        shared = s;
    }
    
    
    public void runThis(){
        CheckoutInterface m = new CheckoutInterface(shared);
        
        
       m.main(shared);
      
    /*    while(true){  
            try{
                Thread.sleep(6000);
                
                System.out.print("Hello\n");
                m.refresh();
               
            }catch(Exception e){}
        }*/
    }
}
