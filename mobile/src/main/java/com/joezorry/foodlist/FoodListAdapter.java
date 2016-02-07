package com.joezorry.foodlist;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.joezorry.foodlist.models.Food;

import java.util.List;

public class FoodListAdapter extends RecyclerView.Adapter<FoodListAdapter.FoodViewHolder> {

    public interface FoodStarClickListener {
        void foodStarClicked(final Food food, final boolean isChecked);
    }

    final List<Food> mFoods;
    private final FoodStarClickListener mFoodStarClickedListener;

    public FoodListAdapter(final List<Food> foodArrayList, final FoodStarClickListener foodStarClickedListener) {
        mFoods = foodArrayList;
        mFoodStarClickedListener = foodStarClickedListener;
    }

    @Override
    public FoodViewHolder onCreateViewHolder(final ViewGroup parent, final int viewType) {
        final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.food_view_holder, parent, false);
        return new FoodViewHolder(view, mFoodStarClickedListener);
    }

    @Override
    public void onBindViewHolder(final FoodViewHolder holder, final int position) {
        holder.mFoodTitleTextView.setText(mFoods.get(position).getTitle());
        holder.mProteinTextView.setText(mFoods.get(position).getProtein() + " g");
        holder.mStar.setChecked(mFoods.get(position).getIsFavorite());
    }

    @Override
    public int getItemCount() {
        return mFoods.size();
    }

    public void updateFoodList(final List<Food> foodList) {
        mFoods.clear();
        mFoods.addAll(foodList);
        notifyDataSetChanged();
    }

    public class FoodViewHolder extends RecyclerView.ViewHolder {

        public final TextView mFoodTitleTextView;
        public final TextView mProteinTextView;
        public final CheckBox mStar;

        public FoodViewHolder(final View itemView, final FoodStarClickListener foodStarClickListener) {
            super(itemView);
            mFoodTitleTextView = (TextView) itemView.findViewById(R.id.food_vh_title);
            mProteinTextView = (TextView) itemView.findViewById(R.id.food_vh_protein);
            mStar = (CheckBox) itemView.findViewById(R.id.food_vh_star);
            mStar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(final View v) {
                    foodStarClickListener.foodStarClicked(
                        mFoods.get(getAdapterPosition()),
                        mStar.isChecked());
                }
            });
        }
    }
}