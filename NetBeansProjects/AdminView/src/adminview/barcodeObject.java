/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package adminview;

import java.io.Serializable;

/**
 *
 * @author phillip.oegema
 */
public class barcodeObject implements Serializable{
    private String name;
    private String barcode;
    private String description;
    private String type; //"key" for key or "user" for user

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
    
    public JSONObject getJSONObject(){
        JSONObject json = new JSONObject();
        try{
            json.put("type", type);
            json.put("name", name);
            json.put("barcode", barcode);
            json.put("description", description);
        }catch(JSONException je){
            System.out.println(je.toString());
            return null;
        }
        return json;
    }
    
    public boolean isAdmin(){
        if(this.description.equals("administrator"))
            return true;
        else
            return false;               
    
    }
}
