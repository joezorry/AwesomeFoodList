package com.joezorry.foodlist;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.joezorry.foodlist.models.SearchResult;

import javax.inject.Inject;

import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    private static final int SEARCH_FRAGMENT_POSITION = 0;
    private static final int FAVORITES_FRAGMENT_POSITION = 1;


    private SectionsPagerAdapter mSectionsPagerAdapter;
    private ViewPager mViewPager;

    @Inject
    Retrofit mRetrofit;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FoodListApplication.getAppComponent(this).inject(this);

        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        final TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);

        final FoodListService foodListService = mRetrofit.create(FoodListService.class);
        foodListService.searchResult().enqueue(new Callback<SearchResult>() {
            @Override
            public void onResponse(final Response<SearchResult> response) {
                final FoodListFragment foodListFragment = mSectionsPagerAdapter.getItem(SEARCH_FRAGMENT_POSITION);
                foodListFragment.updateFoodList(response.body().getFood());
            }

            @Override
            public void onFailure(final Throwable t) {
                Log.e(TAG, "FAAAAAIL");
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(final Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(final MenuItem item) {
        final int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        final FoodListFragment mFoodListSearch;
        final FoodListFragment mFoodListFavorite;

        public SectionsPagerAdapter(final FragmentManager fm) {
            super(fm);
            mFoodListSearch = FoodListFragment.newInstance(1);
            mFoodListFavorite = FoodListFragment.newInstance(2);
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
                    return "SECTION 1";
                case FAVORITES_FRAGMENT_POSITION:
                    return "SECTION 2";
            }
            return null;
        }
    }
}