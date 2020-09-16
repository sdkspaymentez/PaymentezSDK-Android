package ar.com.fennoma.paymentezsdk.services;

import android.os.AsyncTask;
import android.text.TextUtils;

import ar.com.fennoma.paymentezsdk.models.ErrorMessage;

public class BaseTask<T> extends AsyncTask<Void, Void, T> {

    public interface IServiceCaller<T> {
        T callService() throws ServiceException;
    }

    private final API.ServiceCallback<T> callback;
    private final IServiceCaller<T> serviceCaller;

    private boolean running = false;
    public String errorCode;
    public String errorMessage;
    public Boolean processedError;

    public BaseTask(API.ServiceCallback<T> callback, IServiceCaller<T> serviceCaller) {
        this.callback = callback;
        this.serviceCaller = serviceCaller;
        processedError = false;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        running = true;
    }

    @Override
    protected T doInBackground(Void... voids) {
        try {
            return serviceCaller.callService();
        }  catch (ServiceException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    protected void onPostExecute(T response) {
        running = false;
        super.onPostExecute(response);
        if(response == null && TextUtils.isEmpty(errorCode)) {
            callback.onFailure();
        } else if(response == null || errorCode != null) {
            ErrorMessage error = ErrorMessage.getError(errorCode);
            if(error.isInvalidSession()) {
                callback.sessionExpired();
            } else {
                callback.onError(error);
            }
        } else {
            callback.onSuccess(response);
        }
    }

    @Override
    protected void onCancelled() {
        super.onCancelled();
        this.running = false;
    }

    public boolean isRunning(){
        return running;
    }

}
