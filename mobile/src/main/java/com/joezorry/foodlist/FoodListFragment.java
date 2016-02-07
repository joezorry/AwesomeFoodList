package com.joezorry.foodlist;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.joezorry.foodlist.models.Food;
import com.joezorry.foodlist.models.OrmaDatabase;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class FoodListFragment extends Fragment implements FoodListAdapter.FoodStarClickListener {

    private RecyclerView mRecyclerView;
    private FoodListAdapter mFoodListAdapter;

    @Inject
    OrmaDatabase mOrmaDatabase;
    private FavoritedListener mFavoritedListener;
    private TextView mNoSearchResult;

    public static FoodListFragment newInstance() {
        return new FoodListFragment();
    }

    @Override
    public void onCreate(@Nullable final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FoodListApplication.getAppComponent(getContext()).inject(this);
    }

    @Override
    public View onCreateView(
        final LayoutInflater inflater,
        final ViewGroup container,
        final Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_main, container, false);
        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.food_list_recycler_view);
        mNoSearchResult = (TextView) rootView.findViewById(R.id.food_list_no_search_text);

        mFoodListAdapter = new FoodListAdapter(new ArrayList<Food>(), this);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable final Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mRecyclerView.setAdapter(mFoodListAdapter);
    }

    public void setFavoriteListener(@NonNull final FavoritedListener favoritedListener) {
        mFavoritedListener = favoritedListener;
    }

    public void updateFoodList(@Nullable final List<Food> foodList) {
        if (foodList != null && foodList.size() > 0) {
            mFoodListAdapter.updateFoodList(foodList);
            mNoSearchResult.setVisibility(View.GONE);
        } else {
            mNoSearchResult.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void foodStarClicked(final Food food, final boolean isChecked) {
        final Handler handler = new Handler(Looper.getMainLooper()) {
            @Override
            public void handleMessage(final Message msg) {
                if (mFavoritedListener != null) {
                    mFavoritedListener.favoritedListener();
                }
            }
        };

        new Thread(new Runnable() {
            @Override
            public void run() {
                final boolean alreadyExists = doesFoodAlreadyExists(food);
                addToDb(alreadyExists, isChecked, food);
                deleteFromDb(alreadyExists, isChecked, food);
                handler.sendEmptyMessage(0);
            }
        }).start();
    }

    private void addToDb(final boolean alreadyExists, final boolean isChecked, final Food food) {
        if (!alreadyExists && isChecked) {
            food.setFavorite(true);
            mOrmaDatabase.insertIntoFood(food);
        }
    }

    private void deleteFromDb(final boolean alreadyExists, final boolean isChecked, final Food food) {
        if (!isChecked && alreadyExists) {
            food.setFavorite(false);
            mOrmaDatabase.deleteFromFood().idEq(food.getId()).execute();
        }
    }

    private boolean doesFoodAlreadyExists(final Food food) {
        final List<Food> foods = mOrmaDatabase.relationOfFood().orderByTitleAsc().selector().toList();
        for (int i = 0; i < foods.size(); i++) {
            final Food cachedFood = foods.get(i);
            if (cachedFood.getId() == food.getId()) {
                return true;
            }
        }
        return false;
    }
}