package com.example.movieapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.movieapp.R;
import com.example.movieapp.data.Trailer;
import com.example.movieapp.databinding.TrailerListItemBinding;

import java.util.List;

public class TrailersAdapter extends RecyclerView.Adapter<TrailersAdapter.TrailerAdapterViewHolder> {

    private List<Trailer> mTrailerData;
    private Context mContext;

    final private TrailerAdapterClickHandler mClickHanlder;

    public TrailersAdapter(Context context, TrailerAdapterClickHandler clickHandler) {
        mContext = context;
        mClickHanlder = clickHandler;
    }


    public interface TrailerAdapterClickHandler {
        void onClick(Trailer trailer);
    }

    @Override
    public TrailerAdapterViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {

        int layoutIdForTrailerListItem = R.layout.trailer_list_item;
        LayoutInflater inflater = LayoutInflater.from(mContext);
        boolean shouldAttachToParentImmediately = false;

        TrailerListItemBinding view = DataBindingUtil.inflate(
                inflater, layoutIdForTrailerListItem, viewGroup, shouldAttachToParentImmediately);

        return new TrailerAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(TrailerAdapterViewHolder holder, int position) {
        Trailer trailer = mTrailerData.get(position);
        holder.bind(trailer);
        holder.itemRowBinding.setItemClickListener(mClickHanlder);
    }

    @Override
    public int getItemCount() {
        if (mTrailerData == null) {
            return 0;
        }
        return mTrailerData.size();
    }

    public class TrailerAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TrailerListItemBinding itemRowBinding;

        public TrailerAdapterViewHolder(TrailerListItemBinding itemRowBinding) {
            super(itemRowBinding.getRoot());
            this.itemRowBinding = itemRowBinding;
        }

        @Override
        public void onClick(View v) {
            int adapterPosition = getAdapterPosition();
            Trailer trailer = mTrailerData.get(adapterPosition);
            mClickHanlder.onClick(trailer);
        }

        public void bind(Trailer obj) {
            itemRowBinding.setModel(obj);
            itemRowBinding.executePendingBindings();
        }
    }


    public void setTrailersData(List<Trailer> trailerData) {
        mTrailerData = trailerData;
        notifyDataSetChanged();
    }
}