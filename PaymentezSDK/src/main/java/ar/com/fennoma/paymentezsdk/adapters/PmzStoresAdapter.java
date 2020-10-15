package ar.com.fennoma.paymentezsdk.adapters;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import ar.com.fennoma.paymentezsdk.R;
import ar.com.fennoma.paymentezsdk.models.PmzStore;
import ar.com.fennoma.paymentezsdk.utils.ImageUtils;

public class PmzStoresAdapter extends RecyclerView.Adapter<PmzStoresAdapter.PmzStoreHolder> {

    private final Activity activity;
    private final PmzStoreItemListener listener;
    private List<PmzStore> stores;

    private Integer textColor;

    public interface PmzStoreItemListener {
        void onStoreClicked(PmzStore store);
    }

    public PmzStoresAdapter(Activity activity, PmzStoreItemListener listener) {
        this.activity = activity;
        this.listener = listener;
        stores = new ArrayList<>();
    }

    public void setTextColor(int textColor) {
        this.textColor = textColor;
    }

    @NonNull
    @Override
    public PmzStoreHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new PmzStoreHolder(activity.getLayoutInflater().inflate(R.layout.item_pmz_store, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull final PmzStoreHolder holder, int position) {
        PmzStore store = stores.get(position);
        ImageUtils.loadStoreImage(activity, holder.image, store.getImageUrl());

        holder.title.setText(store.getName());
        holder.description.setText(store.getCommerceName());

        if(textColor != null) {
            holder.title.setTextColor(textColor);
            holder.description.setTextColor(textColor);
        }

        holder.container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onStoreClicked(stores.get(holder.getAdapterPosition()));
            }
        });
    }

    @Override
    public int getItemCount() {
        return stores.size();
    }

    public void setStores(List<PmzStore> stores) {
        this.stores = stores;
        notifyDataSetChanged();
    }

    static class PmzStoreHolder extends RecyclerView.ViewHolder {

        protected View container;
        protected ImageView image;
        protected TextView title;
        protected TextView description;
        protected TextView distance;

        public PmzStoreHolder(@NonNull View itemView) {
            super(itemView);
            container = itemView.findViewById(R.id.container);
            image = itemView.findViewById(R.id.image);
            title = itemView.findViewById(R.id.title);
            description = itemView.findViewById(R.id.description);
            distance = itemView.findViewById(R.id.distance);
        }
    }
}
