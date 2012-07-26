/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jsonreadin;
import jsonreadin.file;
/**
 *
 * @author phillip.oegema
 */
public class JSONReadIn {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        file file = new file();
        file.readFromFile("C:\\Key Files\\JSONTest.txt");
    }
}
