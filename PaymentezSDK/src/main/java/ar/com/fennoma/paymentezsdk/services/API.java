package ar.com.fennoma.paymentezsdk.services;

import java.util.List;

import ar.com.fennoma.paymentezsdk.exceptions.PmzException;
import ar.com.fennoma.paymentezsdk.models.Capacity;
import ar.com.fennoma.paymentezsdk.models.ErrorMessage;
import ar.com.fennoma.paymentezsdk.models.PmzOrder;
import ar.com.fennoma.paymentezsdk.models.Session;
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
            public List<Store> callService() throws PmzException {
                return Services.getStores();
            }
        }).execute();
    }

    public static void getSession(final Session session, final ServiceCallback<String> callback) {
        new BaseTask<>(callback, new BaseTask.IServiceCaller<String>() {
            @Override
            public String callService() throws PmzException {
                return Services.getToken(session);
            }
        }).execute();
    }

    public static void startOrder(final PmzOrder starter, final ServiceCallback<PmzOrder> callback) {
        new BaseTask<>(callback, new BaseTask.IServiceCaller<PmzOrder>() {
            @Override
            public PmzOrder callService() throws PmzException {
                return Services.startOrder(starter);
            }
        }).execute();
    }

    public static void getCapacities(final ServiceCallback<List<Capacity>> callback) {
        new BaseTask<>(callback, new BaseTask.IServiceCaller<List<Capacity>>() {
            @Override
            public List<Capacity> callService() throws PmzException {
                return Services.getCapacities();
            }
        }).execute();
    }

    public static void getOrder(final Long orderId, final ServiceCallback<PmzOrder> callback) {
        new BaseTask<>(callback, new BaseTask.IServiceCaller<PmzOrder>() {
            @Override
            public PmzOrder callService() throws PmzException {
                return Services.getOrder(orderId);
            }
        }).execute();
    }
}
