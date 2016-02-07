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

import com.joezorry.foodlist.models.Food;
import com.joezorry.foodlist.models.OrmaDatabase;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class FoodListFragment extends Fragment implements FoodListAdapter.FoodStarClickListener {

    private static final String TAG = "FoodListFragment";

    private static final String ARG_SECTION_NUMBER = "section_number";
    private static final String ARG_FOOD_LIST = "food_list";

    private RecyclerView mRecyclerView;
    private FoodListAdapter mFoodListAdapter;

    @Inject
    OrmaDatabase mOrmaDatabase;
    private FavoritedListener mFavoritedListener;

    public static FoodListFragment newInstance(final int sectionNumber) {
        final FoodListFragment fragment = new FoodListFragment();
        final Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
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
        if (foodList != null) {
            mFoodListAdapter.updateFoodList(foodList);
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

                if (!alreadyExists && isChecked) {
                    food.setFavorite(true);
                    mOrmaDatabase.insertIntoFood(food);

                }

                if (!isChecked && alreadyExists) {
                    food.setFavorite(false);
                    mOrmaDatabase.deleteFromFood().idEq(food.getId()).execute();
                }

                handler.sendEmptyMessage(0);
            }
        }).start();
    }

    private boolean doesFoodAlreadyExists(final Food food) {
        boolean alreadyExists = false;
        final List<Food> foods = mOrmaDatabase.relationOfFood().orderByTitleAsc().selector().toList();
        for (int i = 0; i < foods.size(); i++) {
            final Food cachedFood = foods.get(i);
            if (cachedFood.getId() == food.getId()) {
                alreadyExists = true;
            }
        }
        return alreadyExists;
    }
}