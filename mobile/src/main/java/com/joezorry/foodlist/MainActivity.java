package com.joezorry.foodlist;

import android.app.SearchManager;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.joezorry.foodlist.models.Food;
import com.joezorry.foodlist.models.OrmaDatabase;
import com.joezorry.foodlist.models.SearchResult;

import java.util.List;

import javax.inject.Inject;

import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class MainActivity extends AppCompatActivity implements FavoritedListener {

    private static final String TAG = "MainActivity";

    private static final int SEARCH_FRAGMENT_POSITION = 0;
    private static final int FAVORITES_FRAGMENT_POSITION = 1;

    private SectionsPagerAdapter mSectionsPagerAdapter;
    private ViewPager mViewPager;

    @Inject
    Retrofit mRetrofit;

    @Inject
    OrmaDatabase mOrmaDatabase;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FoodListApplication.getAppComponent(this).inject(this);

        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager(), this);

        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        final TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPostCreate(@Nullable final Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        updateFavoritedFragmentList();
    }

    private void updateFavoritedFragmentList() {
        mViewPager.post(new Runnable() {
            @Override
            public void run() {
                final FoodListFragment foodListFragment = mSectionsPagerAdapter.getItem(FAVORITES_FRAGMENT_POSITION);
                final List<Food> foods = retreiveCachedFood();
                foodListFragment.updateFoodList(foods);
            }
        });
    }

    private void doSearch(final String searchString) {
        final FoodListService foodListService = mRetrofit.create(FoodListService.class);
        foodListService.searchResult(searchString).enqueue(new Callback<SearchResult>() {
            @Override
            public void onResponse(final Response<SearchResult> response) {
                if (response.body() == null ||
                    response.body().getFood() == null ||
                    response.body().getFood().size() == 0) {
                    Toast.makeText(MainActivity.this, R.string.could_not_find_any_matching_foods, Toast.LENGTH_SHORT)
                         .show();
                    return;
                }

                final FoodListFragment foodListFragment = mSectionsPagerAdapter.getItem(SEARCH_FRAGMENT_POSITION);
                final List<Food> foodList = updateFavoritedStatus(response.body().getFood());
                foodListFragment.updateFoodList(foodList);
            }

            @Override
            public void onFailure(final Throwable t) {
                Log.e(TAG, "FAAAAAIL");
            }
        });
    }

    //Crude way of checking favorites
    private List<Food> updateFavoritedStatus(final List<Food> resultFoods) {
        final List<Food> cachedFoods = retreiveCachedFood();
        for (int i = 0; i < resultFoods.size(); i++) {
            final Food resultFood = resultFoods.get(i);
            for (int y = 0; y < cachedFoods.size(); y++) {
                final Food cachedFood = cachedFoods.get(y);
                if (resultFood.getId() == cachedFood.getId()) {
                    resultFood.setFavorite(true);
                }
            }
        }
        return resultFoods;
    }

    private List<Food> retreiveCachedFood() {
        return mOrmaDatabase.relationOfFood().orderByTitleAsc().selector().toList();
    }

    @Override
    public boolean onCreateOptionsMenu(final Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);

        final MenuItem searchItem = menu.findItem(R.id.action_search);
        final SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = null;
        if (searchItem != null) {
            searchView = (SearchView) searchItem.getActionView();
        }

        if (searchView != null) {
            searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
            final SearchView.OnQueryTextListener queryTextListener = createOnQueryTextListener();
            searchView.setOnQueryTextListener(queryTextListener);
        }

        return true;
    }

    @NonNull
    private SearchView.OnQueryTextListener createOnQueryTextListener() {
        return new SearchView.OnQueryTextListener() {
                    @Override
                    public boolean onQueryTextChange(final String newText) {
                        //Really crude search, could benefit from RxJava
                        if (newText.length() > 3) {
                            doSearch(newText);
                        }
                        return true;
                    }

                    @Override
                    public boolean onQueryTextSubmit(final String query) {
                        doSearch(query);
                        return true;
                    }
                };
    }

    @Override
    public boolean onOptionsItemSelected(final MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void favoritedListener() {
        final FoodListFragment foodListFragment = mSectionsPagerAdapter.getItem(FAVORITES_FRAGMENT_POSITION);
        foodListFragment.updateFoodList(retreiveCachedFood());
    }

    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        final FoodListFragment mFoodListSearch;
        final FoodListFragment mFoodListFavorite;

        public SectionsPagerAdapter(final FragmentManager fm, final FavoritedListener favoritedListener) {
            super(fm);
            mFoodListSearch = FoodListFragment.newInstance();
            mFoodListFavorite = FoodListFragment.newInstance();
            mFoodListSearch.setFavoriteListener(favoritedListener);
            mFoodListFavorite.setFavoriteListener(favoritedListener);
        }

        @Override
        public FoodListFragment getItem(final int position) {
            switch (position) {
                case SEARCH_FRAGMENT_POSITION:
                    return mFoodListSearch;
                case FAVORITES_FRAGMENT_POSITION:
                    return mFoodListFavorite;
                default:
                    return null;
            }
        }

        @Override
        public int getCount() {
            return 2;
        }

        @Override
        public CharSequence getPageTitle(final int position) {
            switch (position) {
                case SEARCH_FRAGMENT_POSITION:
                    return getString(R.string.search_results_tab);
                case FAVORITES_FRAGMENT_POSITION:
                    return getString(R.string.favorites);
            }
            return null;
        }
    }
}