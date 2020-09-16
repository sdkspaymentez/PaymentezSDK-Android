package ar.com.fennoma.paymentezsdk.services;

import androidx.annotation.NonNull;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

import ar.com.fennoma.paymentezsdk.models.Store;
import ar.com.fennoma.paymentezsdk.presenter.PaymentezSDK;

public class Services {

    //Internal handling
    private static final int SUCCESS_CODE = 200;

    private static final String BASE_URL = "https://middleware-stg.paymentez.com/";

    //GETs
    private static final String GET_STORES = "store/list";

    public static List<Store> getStores() throws ServiceException {
        HttpURLConnection urlConnection = null;
        List<Store> response = null;
        InputStream in = null;

        try {
            String url = GET_STORES;
            url += "?session=" + PaymentezSDK.getInstance().getToken();
            urlConnection = getHttpURLConnection(url);
            urlConnection.connect();
            if (isValidStatusLineCode(urlConnection.getResponseCode())) {
                in = new BufferedInputStream(urlConnection.getInputStream());
                JSONArray json = getJsonArrayFromResponse(in);
                response = Store.fromJSONArray(json);
            }
        } catch (JSONException | IOException e) {
            e.printStackTrace();
            throw new ServiceException();
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            closeInputStream(in);
        }
        return response;
    }

    @NonNull
    private static HttpURLConnection getHttpURLConnection(String method) throws IOException {
        URL url = new URL(BASE_URL + method);
        return (HttpURLConnection) url.openConnection();
    }

    public static boolean isValidStatusLineCode(int statusCode) {
        return statusCode == SUCCESS_CODE;
    }

    public static void closeWriterAndStream(OutputStream os, BufferedWriter writer) {
        closeWriter(writer);
        closeOutputStream(os);
    }

    private static void closeOutputStream(OutputStream os) {
        if(os != null) {
            try {
                os.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private static void closeWriter(BufferedWriter writer) {
        if(writer != null) {
            try {
                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void closeInputStream(InputStream is) {
        if (is != null) {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static JSONObject getJsonFromResponse(InputStream in) throws IOException, JSONException {
        StringBuilder strBuilder = new StringBuilder();
        BufferedReader reader = new BufferedReader(new InputStreamReader(in, "UTF-8"));
        String line;
        while ((line = reader.readLine()) != null) {
            strBuilder.append(line);
        }
        String value = strBuilder.toString();
        return new JSONObject(value);
    }

    public static JSONArray getJsonArrayFromResponse(InputStream in) throws IOException, JSONException {
        StringBuilder strBuilder = new StringBuilder();
        BufferedReader reader = new BufferedReader(new InputStreamReader(in, "UTF-8"));
        String line;
        while ((line = reader.readLine()) != null) {
            strBuilder.append(line);
        }
        String value = strBuilder.toString();
        return new JSONArray(value);
    }
}
