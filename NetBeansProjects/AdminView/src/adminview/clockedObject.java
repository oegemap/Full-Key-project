/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package adminview;
import java.util.Date;

/**
 *
 * @author phillip.oegema
 */
public class clockedObject {
    private String name = "";
    private Date checkinTime = null;
    private Date checkoutTime = null;
    private barcodeObject barcodeObject = null;
    private String type = "";
    private barcodeObject sup = null;
    private String inOrOut = "";
    private barcodeObject checkedOutTo = null;

    public barcodeObject getCheckedOutTo() {
        return checkedOutTo;
    }

    public void setCheckedOutTo(barcodeObject checkedOutTo) {
        this.checkedOutTo = checkedOutTo;
    }

    public String getInOrOut() {
        return inOrOut;
    }

    public void setInOrOut(String inOrOut) {
        this.inOrOut = inOrOut;
    }

    public barcodeObject getSup() {
        return sup;
    }

    public void setSup(barcodeObject sup) {
        this.sup = sup;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
    
    public Date getCheckinTime() {
        return checkinTime;
    }

    public void setCheckinTime(Date checkinTime) {
        this.checkinTime = checkinTime;
    }

    public Date getCheckoutTime() {
        return checkoutTime;
    }

    public void setCheckoutTime(Date checkoutTime) {
        this.checkoutTime = checkoutTime;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public barcodeObject getBarcodeObject() {
        return barcodeObject;
    }

    public void setBarcodeObject(barcodeObject barcodeObject) {
        this.barcodeObject = barcodeObject;
    }
    
     public JSONObject getJSONObject(){
        JSONObject json = new JSONObject();
        
        try{
            if(type.equals("user")){
                json.put("type", type);
                json.put("name", name);
                json.put("barcode", barcodeObject.getBarcode());
                json.put("inOrOut", inOrOut);
                json.put("checkInTime", checkinTime.toString());
                if(inOrOut.equals("out"))
                    json.put("checkOutTime", checkoutTime.toString());
            }
            else{
                json.put("type", type);
                json.put("name", name);
                json.put("barcode", barcodeObject.getBarcode());
                json.put("checkedOutTo", checkedOutTo.getName());
                json.put("checkedOutToBarcode", checkedOutTo.getBarcode());
                json.put("inOrOut", inOrOut);
                json.put("checkOutTime", checkoutTime.toString());
                if(inOrOut.equals("in"))
                    json.put("checkInTime", checkinTime.toString());
                
            }
        }catch(JSONException je){
            System.out.println(je.toString());
            return null;
        }
        return json;
    }
    
    
}
