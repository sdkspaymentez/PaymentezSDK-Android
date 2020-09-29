package ar.com.fennoma.paymentezsdk.models;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONException;
import org.json.JSONObject;

import ar.com.fennoma.paymentezsdk.controllers.PaymentezSDK;

public class OrderStarter implements Parcelable, IJsonParsingModel {

    private String deliveryInstructions;
    private String annotations;
    private String appOrderReference;
    private Long storeId;
    private String token;
    private Integer typeOrder;
    private Buyer buyer;
    private Address address;

    public static OrderStarter getHardcoded() {
        try {
            return fromJSONObject(new JSONObject(json), PaymentezSDK.getInstance().getToken());
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static OrderStarter fromJSONObject(JSONObject json, String token) {
        OrderStarter order = new OrderStarter();
        if(json != null) {
            try {
                if(json.has("delivery_instructions")) {
                    order.setDeliveryInstructions(json.getString("delivery_instructions"));
                }
                if(json.has("Annotations")) {
                    order.setAnnotations(json.getString("Annotations"));
                }
                if(json.has("app_order_reference")) {
                    order.setAppOrderReference(json.getString("app_order_reference"));
                }
                if(json.has("id_store")) {
                    order.setStoreId(json.getLong("id_store"));
                }
                if(json.has("type_order")) {
                    order.setTypeOrder(json.getInt("type_order"));
                }
                order.setToken(token);
                order.setBuyer(Buyer.fromJSONObject(json));
                order.setAddress(Address.fromJSONObject(json));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return order;
    }

    @Override
    public JSONObject getJSON() throws JSONException {
        JSONObject params = new JSONObject();
        if(buyer != null) {
            buyer.addToJSON(params);
        }
        if(address != null) {
            address.addToJSON(params);
        }
        params.put("delivery_instructions", deliveryInstructions);
        params.put("Annotations", annotations);
        params.put("app_order_reference", appOrderReference);
        params.put("id_store", storeId);
        params.put("type_order", typeOrder);
        if(token != null) {
            params.put("session", token);
        }
        return params;
    }

    public String getDeliveryInstructions() {
        return deliveryInstructions;
    }

    public void setDeliveryInstructions(String deliveryInstructions) {
        this.deliveryInstructions = deliveryInstructions;
    }

    public String getAnnotations() {
        return annotations;
    }

    public void setAnnotations(String annotations) {
        this.annotations = annotations;
    }

    public String getAppOrderReference() {
        return appOrderReference;
    }

    public void setAppOrderReference(String appOrderReference) {
        this.appOrderReference = appOrderReference;
    }

    public Long getStoreId() {
        return storeId;
    }

    public void setStoreId(Long storeId) {
        this.storeId = storeId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Integer getTypeOrder() {
        return typeOrder;
    }

    public void setTypeOrder(Integer typeOrder) {
        this.typeOrder = typeOrder;
    }

    public OrderStarter() {
    }

    public Buyer getBuyer() {
        return buyer;
    }

    public void setBuyer(Buyer buyer) {
        this.buyer = buyer;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.deliveryInstructions);
        dest.writeString(this.annotations);
        dest.writeString(this.appOrderReference);
        dest.writeValue(this.storeId);
        dest.writeString(this.token);
        dest.writeValue(this.typeOrder);
        dest.writeParcelable(this.buyer, flags);
        dest.writeParcelable(this.address, flags);
    }

    protected OrderStarter(Parcel in) {
        this.deliveryInstructions = in.readString();
        this.annotations = in.readString();
        this.appOrderReference = in.readString();
        this.storeId = (Long) in.readValue(Long.class.getClassLoader());
        this.token = in.readString();
        this.typeOrder = (Integer) in.readValue(Integer.class.getClassLoader());
        this.buyer = in.readParcelable(Buyer.class.getClassLoader());
        this.address = in.readParcelable(Address.class.getClassLoader());
    }

    public static final Creator<OrderStarter> CREATOR = new Creator<OrderStarter>() {
        @Override
        public OrderStarter createFromParcel(Parcel source) {
            return new OrderStarter(source);
        }

        @Override
        public OrderStarter[] newArray(int size) {
            return new OrderStarter[size];
        }
    };

    private static String json = "{\n" +
            "    \"address_city\": \"Bogotá\",\n" +
            "    \"address_country\": \"Colombia\",\n" +
            "    \"address_latitude\": 4.6568103,\n" +
            "    \"address_line1\": \"Calle 75 20C-81\",\n" +
            "    \"address_line2\": \"Calle 75 - 20C-81\",\n" +
            "    \"address_longitude\": -74.0561968,\n" +
            "    \"address_state\": \"DC\",\n" +
            "    \"address_zip\": \"\",\n" +
            "    \"delivery_instructions\": \"Apto 206\",\n" +
            "    \"Annotations\": \"\",\n" +
            "    \"buyer_email\": \"breyes@paymentez.com\",\n" +
            "    \"buyer_fiscal_number\": \"1054092666\",\n" +
            "    \"buyer_name\": \"Bruno Reyes\",\n" +
            "    \"buyer_phone\": \"3212000915\",\n" +
            "    \"buyer_user_reference\": \"f6dc275d-5e64-4127-bf5c-dbbfac02aacd\",\n" +
            "    \"app_order_reference\": \"test-1744\",\n" +
            "    \"id_store\":120,\n" +
            "    \"session\": \"{{token}}\",\n" +
            "    \"type_order\": 0\n" +
            "}";
}
