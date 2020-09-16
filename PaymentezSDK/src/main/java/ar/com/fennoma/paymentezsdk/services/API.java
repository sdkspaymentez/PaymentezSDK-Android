package ar.com.fennoma.paymentezsdk.services;

import java.util.List;

import ar.com.fennoma.paymentezsdk.models.ErrorMessage;
import ar.com.fennoma.paymentezsdk.models.Store;

public class API {

    public interface ServiceCallback<T> {
        void onSuccess(T response);
        void onError(ErrorMessage error);
        void onFailure();
        void sessionExpired();
    }

    public static void getStores(final ServiceCallback<List<Store>> callback) {
        new BaseTask<>(callback, new BaseTask.IServiceCaller<List<Store>>() {
            @Override
            public List<Store> callService() throws ServiceException {
                return Services.getStores();
            }
        }).execute();
    }
}
