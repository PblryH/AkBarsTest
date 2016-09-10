package pro.rgun.akbarstest.ui.extras.recycler;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import java.util.List;

/**
 * Common RecyclerViewAdapter
 * Created by rgun on 03.12.15.
 */
public abstract class RecyclerViewAdapter<D, VH extends RecyclerView.ViewHolder>
        extends RecyclerView.Adapter<VH> {

    protected List<D> mDataList;

    public RecyclerViewAdapter(List<D> dataList) {
        mDataList = dataList;
    }

    @Override
    public int getItemCount() {
        return mDataList.size();
    }

    public void clear() {
        int size = mDataList.size();
        mDataList.clear();
        notifyItemRangeRemoved(0, size);
    }

    public void addAll(List<D> data) {
        mDataList.addAll(data);
        notifyDataSetChanged();
    }

    public D getItemAt(int position) {
        return mDataList.get(position);
    }

    public D replaceItemAt(int position, D newItem) {
        D item = mDataList.set(position, newItem);
        notifyItemChanged(position);
        return item;
    }

    public void addItem(int index, D newItem) {
        mDataList.add(index, newItem);
        notifyItemInserted(index);
    }

    public void addItem(D newItem) {
        mDataList.add(newItem);
        notifyItemInserted(mDataList.size() - 1);
    }

    public D removeItem(int index) {
        if (mDataList.size() > 0) {
            D item = mDataList.remove(index);
            notifyItemRemoved(index);

            if(mDataList.isEmpty()){
                notifyDataSetChanged();
            }
            return item;
        }

        return null;
    }

    @Override
    public VH onCreateViewHolder(ViewGroup parent, int viewType) {
        VH vh = createNormalViewHolder(parent, viewType);
        return vh;
    }

    @Override
    public void onBindViewHolder(VH holder, int position) {
        if (mDataList != null && !mDataList.isEmpty()) {
            bindNormalViewHolder(holder, position);
        }
    }

    protected abstract VH createNormalViewHolder(ViewGroup parent, int viewType);

    protected abstract void bindNormalViewHolder(VH holder, int position);
}