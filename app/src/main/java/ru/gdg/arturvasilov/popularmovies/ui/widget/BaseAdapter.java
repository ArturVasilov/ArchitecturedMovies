package ru.gdg.arturvasilov.popularmovies.ui.widget;

import android.support.annotation.CallSuper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Artur Vasilov
 */
public abstract class BaseAdapter<VH extends RecyclerView.ViewHolder, T> extends RecyclerView.Adapter<VH> {

    @NonNull
    private final List<T> items = new ArrayList<>();

    @Nullable
    private OnItemClickListener<T> onItemClickListener;

    private final View.OnClickListener internalListener = (view) -> {
        if (onItemClickListener != null) {
            int position = (int) view.getTag();
            T item = items.get(position);
            onItemClickListener.onItemClick(view, item);
        }
    };

    @Nullable
    private EmptyRecyclerView recyclerView;

    public BaseAdapter(@NonNull List<T> items) {
        this.items.addAll(items);
    }

    @CallSuper
    @Override
    public void onBindViewHolder(VH holder, int position) {
        holder.itemView.setTag(position);
        holder.itemView.setOnClickListener(internalListener);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void attachToRecyclerView(@NonNull EmptyRecyclerView recyclerView) {
        this.recyclerView = recyclerView;
        this.recyclerView.setAdapter(this);
        refreshRecycler();
    }

    public void refreshRecycler() {
        notifyDataSetChanged();
        if (recyclerView != null) {
            recyclerView.checkIfEmpty();
        }
    }

    public final void add(@NonNull T value) {
        items.add(value);
        refreshRecycler();
    }

    public final void clear() {
        items.clear();
        refreshRecycler();
    }

    @NonNull
    public T getItem(int position) {
        return items.get(position);
    }

    public final void setNewValues(@NonNull List<T> values) {
        items.clear();
        items.addAll(values);
        refreshRecycler();
    }

    public void setOnItemClickListener(@Nullable OnItemClickListener<T> onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener<T> {

        void onItemClick(@NonNull View view, @NonNull T item);

    }

}
