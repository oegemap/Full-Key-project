/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package adminclient;

import javax.sound.sampled.*;
import java.net.URL;
import java.io.File;
import javax.swing.*;

/**
 *
 * @author phillip.oegema
 */
public class PlaySound {
    boolean needPrompt = false;
    Clip clip;
    public void main(String args) throws Exception {
       File url = new File("C:\\Windows\\Media\\Windows Default.wav");
       
       if(args.equals("beep"))
           url = new File("C:\\Windows\\Media\\Speech Misrecognition.wav");
       else if(args.equals("alert"))
           url = new File("C:\\Windows\\Media\\Windows Critical Stop.wav");
       else if(args.equals("ding"))
           url = new File("C:\\Windows\\Media\\Windows Ding.wav");
       else if(args.equals("onestop")){
           url = new File("C:\\Windows\\Media\\onestop.mid");
           needPrompt = true;
       }
           //chimes for sign
       //insert hardware and remove for in/out
        clip = AudioSystem.getClip();
        // getAudioInputStream() also accepts a File or InputStream
        AudioInputStream ais = AudioSystem.getAudioInputStream( url );
        clip.open(ais);
        clip.loop(0);
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                // A GUI element to prevent the Clip's daemon Thread
                // from terminating at the end of the main()
                if(needPrompt == true){
                    JOptionPane.showMessageDialog(null, "Close to exit!");
                    clip.close();
                }
                
            }
        });
    }
}
