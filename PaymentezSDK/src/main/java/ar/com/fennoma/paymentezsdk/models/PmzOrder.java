package ar.com.fennoma.paymentezsdk.models;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import ar.com.fennoma.paymentezsdk.controllers.PaymentezSDK;

public class PmzOrder implements Parcelable {

    private Long id;
    private Integer status;
    private Double tax;
    private String buyerEmail;
    private String buyerName;
    private String buyerPhone;
    private String buyerFiscalNumber;
    private String userReference;
    private String orderAppReference;
    private String confirmationCode;
    private String tableReference;
    private String deliveryDate;
    private String reserveCode;
    private String datePlaced;
    private String dateStarted;
    private Integer orderType;
    private Long totalAmount;
    private String addressLine1;
    private String addressLine2;
    private String addressCity;
    private String addressState;
    private String addressZip;
    private String addressCountry;
    private Double addressLatitude;
    private Double addressLongitude;
    private String deliveryInstructions;
    private Double deliveryPrice;
    private String statusDescription;
    private List<PmzItem> items;

    private String currency;
    private String paymentMethodReference;
    private String paymentReference;
    private Integer service;

    public static PmzOrder hardcoded() {
        try {
            return fromJSONObject(new JSONObject(json));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return new PmzOrder();
    }

    public static PmzOrder fromJSONObject(JSONObject json) {
        PmzOrder order = new PmzOrder();
        if(json != null) {
            try {
                if(json.has("id")) {
                    order.setId(json.getLong("id"));
                }
                if(json.has("status")) {
                    order.setStatus(json.getInt("status"));
                }
                if(json.has("tax")) {
                    order.setTax(json.getDouble("tax"));
                }
                if(json.has("items") && !json.isNull("items")) {
                    order.setItems(PmzItem.fromJSONArray(json.getJSONArray("items")));
                }
                if(json.has("buyer_email")) {
                    order.setBuyerEmail(json.getString("buyer_email"));
                }
                if(json.has("buyer_name")) {
                    order.setBuyerName(json.getString("buyer_name"));
                }
                if(json.has("buyer_fiscal_number")) {
                    order.setBuyerFiscalNumber(json.getString("buyer_fiscal_number"));
                }
                if(json.has("user_reference")) {
                    order.setUserReference(json.getString("user_reference"));
                }
                if(json.has("order_app_reference")) {
                    order.setOrderAppReference(json.getString("order_app_reference"));
                }
                if(json.has("confirmation_code") && !json.isNull("confirmation_code")) {
                    order.setConfirmationCode(json.getString("confirmation_code"));
                }
                if(json.has("table_reference") && !json.isNull("table_reference")) {
                    order.setTableReference(json.getString("table_reference"));
                }
                if(json.has("delivery_date") && !json.isNull("delivery_date")) {
                    order.setDeliveryDate(json.getString("delivery_date"));
                }
                if(json.has("reserve_code") && !json.isNull("reserve_code")) {
                    order.setReserveCode(json.getString("reserve_code"));
                }
                if(json.has("date_placed") && !json.isNull("date_placed")) {
                    order.setDatePlaced(json.getString("date_placed"));
                }
                if(json.has("date_started") && !json.isNull("date_started")) {
                    order.setDateStarted(json.getString("date_started"));
                }
                if(json.has("order_type")) {
                    order.setOrderType(json.getInt("order_type"));
                }
                if(json.has("total_amount")) {
                    order.setTotalAmount(json.getLong("total_amount"));
                }
                if(json.has("address_line1")) {
                    order.setAddressLine1(json.getString("address_line1"));
                }
                if(json.has("address_line2")) {
                    order.setAddressLine2(json.getString("address_line2"));
                }
                if(json.has("address_city")) {
                    order.setAddressCity(json.getString("address_city"));
                }
                if(json.has("address_state")) {
                    order.setAddressState(json.getString("address_state"));
                }
                if(json.has("address_zip")) {
                    order.setAddressZip(json.getString("address_zip"));
                }
                if(json.has("address_country")) {
                    order.setAddressCountry(json.getString("address_country"));
                }
                if(json.has("delivery_instructions")) {
                    order.setDeliveryInstructions(json.getString("delivery_instructions"));
                }
                if(json.has("address_latitude")) {
                    order.setAddressLatitude(json.getDouble("address_latitude"));
                }
                if(json.has("address_longitude")) {
                    order.setAddressLongitude(json.getDouble("address_longitude"));
                }
                if(json.has("delivery_price")) {
                    order.setDeliveryPrice(json.getDouble("delivery_price"));
                }
                if(json.has("status_description") && !json.isNull("status_description")) {
                    order.setStatusDescription(json.getString("status_description"));
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return order;
    }

    public Object getJSONForPayment() throws JSONException {
        JSONObject params = new JSONObject();
        params.put("amount", totalAmount);
        params.put("currency", currency);
        params.put("id_order", id);
        params.put("payment_method_reference", paymentMethodReference);
        params.put("payment_reference", paymentReference);
        params.put("service", service);
        params.put("session", PaymentezSDK.getInstance().getToken());
        return params;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Double getTax() {
        return tax;
    }

    public void setTax(Double tax) {
        this.tax = tax;
    }

    public String getBuyerName() {
        return buyerName;
    }

    public void setBuyerName(String buyerName) {
        this.buyerName = buyerName;
    }

    public String getBuyerPhone() {
        return buyerPhone;
    }

    public void setBuyerPhone(String buyerPhone) {
        this.buyerPhone = buyerPhone;
    }

    public String getBuyerFiscalNumber() {
        return buyerFiscalNumber;
    }

    public void setBuyerFiscalNumber(String buyerFiscalNumber) {
        this.buyerFiscalNumber = buyerFiscalNumber;
    }

    public String getUserReference() {
        return userReference;
    }

    public void setUserReference(String userReference) {
        this.userReference = userReference;
    }

    public String getOrderAppReference() {
        return orderAppReference;
    }

    public void setOrderAppReference(String orderAppReference) {
        this.orderAppReference = orderAppReference;
    }

    public String getConfirmationCode() {
        return confirmationCode;
    }

    public void setConfirmationCode(String confirmationCode) {
        this.confirmationCode = confirmationCode;
    }

    public String getTableReference() {
        return tableReference;
    }

    public void setTableReference(String tableReference) {
        this.tableReference = tableReference;
    }

    public String getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(String deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    public String getReserveCode() {
        return reserveCode;
    }

    public void setReserveCode(String reserveCode) {
        this.reserveCode = reserveCode;
    }

    public String getDatePlaced() {
        return datePlaced;
    }

    public void setDatePlaced(String datePlaced) {
        this.datePlaced = datePlaced;
    }

    public String getDateStarted() {
        return dateStarted;
    }

    public void setDateStarted(String dateStarted) {
        this.dateStarted = dateStarted;
    }

    public Integer getOrderType() {
        return orderType;
    }

    public void setOrderType(Integer orderType) {
        this.orderType = orderType;
    }

    public Long getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(Long totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getAddressLine1() {
        return addressLine1;
    }

    public void setAddressLine1(String addressLine1) {
        this.addressLine1 = addressLine1;
    }

    public String getAddressLine2() {
        return addressLine2;
    }

    public void setAddressLine2(String addressLine2) {
        this.addressLine2 = addressLine2;
    }

    public String getAddressCity() {
        return addressCity;
    }

    public void setAddressCity(String addressCity) {
        this.addressCity = addressCity;
    }

    public String getAddressState() {
        return addressState;
    }

    public void setAddressState(String addressState) {
        this.addressState = addressState;
    }

    public String getAddressZip() {
        return addressZip;
    }

    public void setAddressZip(String addressZip) {
        this.addressZip = addressZip;
    }

    public String getAddressCountry() {
        return addressCountry;
    }

    public void setAddressCountry(String addressCountry) {
        this.addressCountry = addressCountry;
    }

    public Double getAddressLatitude() {
        return addressLatitude;
    }

    public void setAddressLatitude(Double addressLatitude) {
        this.addressLatitude = addressLatitude;
    }

    public Double getAddressLongitude() {
        return addressLongitude;
    }

    public void setAddressLongitude(Double addressLongitude) {
        this.addressLongitude = addressLongitude;
    }

    public String getDeliveryInstructions() {
        return deliveryInstructions;
    }

    public void setDeliveryInstructions(String deliveryInstructions) {
        this.deliveryInstructions = deliveryInstructions;
    }

    public Double getDeliveryPrice() {
        return deliveryPrice;
    }

    public void setDeliveryPrice(Double deliveryPrice) {
        this.deliveryPrice = deliveryPrice;
    }

    public String getStatusDescription() {
        return statusDescription;
    }

    public void setStatusDescription(String statusDescription) {
        this.statusDescription = statusDescription;
    }

    public List<PmzItem> getItems() {
        return items;
    }

    public void setItems(List<PmzItem> items) {
        this.items = items;
    }

    public String getBuyerEmail() {
        return buyerEmail;
    }

    public void setBuyerEmail(String buyerEmail) {
        this.buyerEmail = buyerEmail;
    }

    public PmzOrder() {
    }

    private static String json = "{\n" +
            "    \"id\": 8493,\n" +
            "    \"status\": 0,\n" +
            "    \"tax\": 0,\n" +
            "    \"items\": [\n" +
            "      {\n" +
            "        \"id\": 15053,\n" +
            "        \"tax\": 0,\n" +
            "        \"annotations\": \"sin servilletas\",\n" +
            "        \"status\": 0,\n" +
            "        \"configurations\": [\n" +
            "          {\n" +
            "            \"id\": 13015,\n" +
            "            \"annotations\": null,\n" +
            "            \"description\": \"Ajonjolí\",\n" +
            "            \"type\": 2,\n" +
            "            \"cost\": 1000,\n" +
            "            \"configuration_id\": 11520,\n" +
            "            \"discount\": null\n" +
            "          },\n" +
            "          {\n" +
            "            \"id\": 13016,\n" +
            "            \"annotations\": null,\n" +
            "            \"description\": \"Cubiertos\",\n" +
            "            \"type\": 2,\n" +
            "            \"cost\": 0,\n" +
            "            \"configuration_id\": 11494,\n" +
            "            \"discount\": null\n" +
            "          }\n" +
            "        ],\n" +
            "        \"total_amount\": 17000,\n" +
            "        \"unit_amount\": 16000,\n" +
            "        \"quantity\": 1,\n" +
            "        \"product_id\": 7351,\n" +
            "        \"product_name\": \"Ceviche\",\n" +
            "        \"discount\": null\n" +
            "      }\n" +
            "    ],\n" +
            "    \"buyer_email\": \"breyes@paymentez.com\",\n" +
            "    \"buyer_name\": \"Bruno Reyes\",\n" +
            "    \"buyer_phone\": \"3212000915\",\n" +
            "    \"buyer_fiscal_number\": \"1054092666\",\n" +
            "    \"user_reference\": \"f6dc275d-5e64-4127-bf5c-dbbfac02aacd\",\n" +
            "    \"order_app_reference\": \"test-1744\",\n" +
            "    \"confirmation_code\": null,\n" +
            "    \"table_reference\": null,\n" +
            "    \"delivery_date\": null,\n" +
            "    \"reserve_code\": null,\n" +
            "    \"date_placed\": null,\n" +
            "    \"date_started\": \"2020-09-17T10:48:24.000Z\",\n" +
            "    \"order_type\": 0,\n" +
            "    \"total_amount\": 17000,\n" +
            "    \"address_line1\": \"Calle 75 20C-81\",\n" +
            "    \"address_line2\": \"Calle 75 - 20C-81\",\n" +
            "    \"address_city\": \"Bogotá\",\n" +
            "    \"address_state\": \"DC\",\n" +
            "    \"address_zip\": \"00000\",\n" +
            "    \"address_country\": \"Colombia\",\n" +
            "    \"address_latitude\": 4.6568103,\n" +
            "    \"address_longitude\": -74.0561968,\n" +
            "    \"delivery_instructions\": \"Apto 206\",\n" +
            "    \"delivery_price\": 0,\n" +
            "    \"status_description\": null\n" +
            "  }";

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getPaymentMethodReference() {
        return paymentMethodReference;
    }

    public void setPaymentMethodReference(String paymentMethodReference) {
        this.paymentMethodReference = paymentMethodReference;
    }

    public String getPaymentReference() {
        return paymentReference;
    }

    public void setPaymentReference(String paymentReference) {
        this.paymentReference = paymentReference;
    }

    public Integer getService() {
        return service;
    }

    public void setService(Integer service) {
        this.service = service;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(this.id);
        dest.writeValue(this.status);
        dest.writeValue(this.tax);
        dest.writeString(this.buyerEmail);
        dest.writeString(this.buyerName);
        dest.writeString(this.buyerPhone);
        dest.writeString(this.buyerFiscalNumber);
        dest.writeString(this.userReference);
        dest.writeString(this.orderAppReference);
        dest.writeString(this.confirmationCode);
        dest.writeString(this.tableReference);
        dest.writeString(this.deliveryDate);
        dest.writeString(this.reserveCode);
        dest.writeString(this.datePlaced);
        dest.writeString(this.dateStarted);
        dest.writeValue(this.orderType);
        dest.writeValue(this.totalAmount);
        dest.writeString(this.addressLine1);
        dest.writeString(this.addressLine2);
        dest.writeString(this.addressCity);
        dest.writeString(this.addressState);
        dest.writeString(this.addressZip);
        dest.writeString(this.addressCountry);
        dest.writeValue(this.addressLatitude);
        dest.writeValue(this.addressLongitude);
        dest.writeString(this.deliveryInstructions);
        dest.writeValue(this.deliveryPrice);
        dest.writeString(this.statusDescription);
        dest.writeTypedList(this.items);
        dest.writeString(this.currency);
        dest.writeString(this.paymentMethodReference);
        dest.writeString(this.paymentReference);
        dest.writeValue(this.service);
    }

    protected PmzOrder(Parcel in) {
        this.id = (Long) in.readValue(Long.class.getClassLoader());
        this.status = (Integer) in.readValue(Integer.class.getClassLoader());
        this.tax = (Double) in.readValue(Double.class.getClassLoader());
        this.buyerEmail = in.readString();
        this.buyerName = in.readString();
        this.buyerPhone = in.readString();
        this.buyerFiscalNumber = in.readString();
        this.userReference = in.readString();
        this.orderAppReference = in.readString();
        this.confirmationCode = in.readString();
        this.tableReference = in.readString();
        this.deliveryDate = in.readString();
        this.reserveCode = in.readString();
        this.datePlaced = in.readString();
        this.dateStarted = in.readString();
        this.orderType = (Integer) in.readValue(Integer.class.getClassLoader());
        this.totalAmount = (Long) in.readValue(Long.class.getClassLoader());
        this.addressLine1 = in.readString();
        this.addressLine2 = in.readString();
        this.addressCity = in.readString();
        this.addressState = in.readString();
        this.addressZip = in.readString();
        this.addressCountry = in.readString();
        this.addressLatitude = (Double) in.readValue(Double.class.getClassLoader());
        this.addressLongitude = (Double) in.readValue(Double.class.getClassLoader());
        this.deliveryInstructions = in.readString();
        this.deliveryPrice = (Double) in.readValue(Double.class.getClassLoader());
        this.statusDescription = in.readString();
        this.items = in.createTypedArrayList(PmzItem.CREATOR);
        this.currency = in.readString();
        this.paymentMethodReference = in.readString();
        this.paymentReference = in.readString();
        this.service = (Integer) in.readValue(Integer.class.getClassLoader());
    }

    public static final Creator<PmzOrder> CREATOR = new Creator<PmzOrder>() {
        @Override
        public PmzOrder createFromParcel(Parcel source) {
            return new PmzOrder(source);
        }

        @Override
        public PmzOrder[] newArray(int size) {
            return new PmzOrder[size];
        }
    };
}
