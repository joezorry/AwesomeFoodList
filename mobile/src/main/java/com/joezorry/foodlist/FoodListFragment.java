package com.joezorry.foodlist;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.joezorry.foodlist.models.Food;

import java.util.ArrayList;
import java.util.List;

public class FoodListFragment extends Fragment {

    private static final String TAG = "FoodListFragment";

    private static final String ARG_SECTION_NUMBER = "section_number";
    private static final String ARG_FOOD_LIST = "food_list";

    private RecyclerView mRecyclerView;
    private FoodListAdapter mFoodListAdapter;

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
    }

    @Override
    public View onCreateView(
        final LayoutInflater inflater,
        final ViewGroup container,
        final Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_main, container, false);
        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.food_list_recycler_view);
        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable final Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mFoodListAdapter = new FoodListAdapter(new ArrayList<Food>());
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerView.setAdapter(mFoodListAdapter);
    }

    public void updateFoodList(final List<Food> foodList) {
        mFoodListAdapter.updateFoodList(foodList);
    }
}