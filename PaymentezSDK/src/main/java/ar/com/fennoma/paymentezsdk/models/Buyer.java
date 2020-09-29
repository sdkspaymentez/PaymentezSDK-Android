package ar.com.fennoma.paymentezsdk.models;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONException;
import org.json.JSONObject;

public class Buyer implements Parcelable, IJsonParsingModel {

    private String name;
    private String phone;
    private String userReference;
    private String email;
    private String fiscalNumber;

    public static Buyer fromJSONObject(JSONObject json) {
        Buyer buyer = new Buyer();
        if(json != null) {
            try {
                if(json.has("buyer_email")) {
                    buyer.setEmail(json.getString("buyer_email"));
                }
                if(json.has("buyer_fiscal_number")) {
                    buyer.setFiscalNumber(json.getString("buyer_fiscal_number"));
                }
                if(json.has("buyer_name")) {
                    buyer.setName(json.getString("buyer_name"));
                }
                if(json.has("buyer_phone")) {
                    buyer.setPhone(json.getString("buyer_phone"));
                }
                if(json.has("buyer_user_reference")) {
                    buyer.setUserReference(json.getString("buyer_user_reference"));
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return buyer;
    }

    @Override
    public JSONObject getJSON() throws JSONException {
        JSONObject params = new JSONObject();
        params.put("buyer_email", email);
        params.put("buyer_fiscal_number", fiscalNumber);
        params.put("buyer_name", fiscalNumber);
        params.put("buyer_phone", phone);
        params.put("buyer_user_reference", userReference);
        return params;
    }

    public JSONObject addToJSON(JSONObject params) throws JSONException {
        params.put("buyer_email", email);
        params.put("buyer_fiscal_number", fiscalNumber);
        params.put("buyer_name", fiscalNumber);
        params.put("buyer_phone", phone);
        params.put("buyer_user_reference", userReference);
        return params;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getUserReference() {
        return userReference;
    }

    public void setUserReference(String userReference) {
        this.userReference = userReference;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFiscalNumber() {
        return fiscalNumber;
    }

    public void setFiscalNumber(String fiscalNumber) {
        this.fiscalNumber = fiscalNumber;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.name);
        dest.writeString(this.phone);
        dest.writeString(this.userReference);
        dest.writeString(this.email);
        dest.writeString(this.fiscalNumber);
    }

    public Buyer() {
    }

    protected Buyer(Parcel in) {
        this.name = in.readString();
        this.phone = in.readString();
        this.userReference = in.readString();
        this.email = in.readString();
        this.fiscalNumber = in.readString();
    }

    public static final Parcelable.Creator<Buyer> CREATOR = new Parcelable.Creator<Buyer>() {
        @Override
        public Buyer createFromParcel(Parcel source) {
            return new Buyer(source);
        }

        @Override
        public Buyer[] newArray(int size) {
            return new Buyer[size];
        }
    };
}
