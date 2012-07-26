/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package adminview;
import java.util.Timer;
import java.util.TimerTask;
/**
 *
 * @author phillip.oegema
 */
public class AdminView {
static shared shared = new shared();
 

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
       shared.setup();
       CheckoutInterface.main(shared);
        
    }
}
