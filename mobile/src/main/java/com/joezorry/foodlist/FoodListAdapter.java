package com.joezorry.foodlist;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.joezorry.foodlist.models.Food;

import java.util.ArrayList;

public class FoodListAdapter extends RecyclerView.Adapter<FoodListAdapter.FoodViewHolder> {

    final ArrayList<Food> mFoods;

    public FoodListAdapter(final ArrayList<Food> foodArrayList) {
        mFoods = foodArrayList;
    }

    @Override
    public FoodViewHolder onCreateViewHolder(final ViewGroup parent, final int viewType) {
        final View view = View.inflate(parent.getContext(), R.layout.food_view_holder, null);
        return new FoodViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final FoodViewHolder holder, final int position) {
        holder.mFoodTitleTextView.setText(mFoods.get(position).getTitle());
    }

    @Override
    public int getItemCount() {
        return mFoods.size();
    }

    public class FoodViewHolder extends RecyclerView.ViewHolder {

        public final TextView mFoodTitleTextView;

        public FoodViewHolder(final View itemView) {
            super(itemView);
            mFoodTitleTextView = (TextView) itemView.findViewById(R.id.food_vh_title);
        }
    }
}