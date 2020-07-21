package com.example.movieapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.movieapp.R;
import com.example.movieapp.data.Review;
import com.example.movieapp.databinding.ReviewListItemBinding;

import java.util.List;

public class ReviewsAdapter extends RecyclerView.Adapter<ReviewsAdapter.ReviewAdapterViewHolder> {


    private List<Review> mReviewData;
    private Context mContext;

    final private ReviewsAdapter.ReviewAdapterClickHanldler mClickHanldler;

    public ReviewsAdapter(Context context, ReviewsAdapter.ReviewAdapterClickHanldler clickHandler) {
        mContext = context;
        mClickHanldler = clickHandler;
    }


    public interface ReviewAdapterClickHanldler {
        void onClick(Review review);
    }

    @Override
    public ReviewsAdapter.ReviewAdapterViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {

        int layoutIdForReviewListItem = R.layout.review_list_item;
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        boolean shouldAttachToParentImmediately = false;

        ReviewListItemBinding view = DataBindingUtil.inflate(
                inflater, layoutIdForReviewListItem, viewGroup, shouldAttachToParentImmediately);

        return new ReviewsAdapter.ReviewAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ReviewsAdapter.ReviewAdapterViewHolder holder, int position) {
        Review review = mReviewData.get(position);
        holder.bind(review);
        holder.itemRowBinding.setItemClickListener(mClickHanldler);
    }

    @Override
    public int getItemCount() {
        if (mReviewData == null) {
            return 0;
        }
        return mReviewData.size();
    }

    public class ReviewAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public ReviewListItemBinding itemRowBinding;

        public ReviewAdapterViewHolder(ReviewListItemBinding itemRowBinding) {
            super(itemRowBinding.getRoot());
            this.itemRowBinding = itemRowBinding;
        }

        @Override
        public void onClick(View v) {
            int adapterPosition = getAdapterPosition();
            Review review = mReviewData.get(adapterPosition);
            mClickHanldler.onClick(review);
        }

        public void bind(Review obj) {
            itemRowBinding.setReview(obj);
            itemRowBinding.executePendingBindings();
        }
    }


    public void setReviewsData(List<Review> reviewData) {
        mReviewData = reviewData;
        notifyDataSetChanged();
    }
}
