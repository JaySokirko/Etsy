package com.jay.foxmindtest.view.activity;

import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.jay.foxmindtest.R;
import com.jay.foxmindtest.SearchProductsContract;
import com.jay.foxmindtest.model.adapter.ProductsAdapter;
import com.jay.foxmindtest.presenter.SearchProductsPresenter;

import java.util.ArrayList;

public class FoundProductsActivity extends AppCompatActivity implements SearchProductsContract.View,
        ProductsAdapter.OnItemClickListener, SwipeRefreshLayout.OnRefreshListener {

    private RecyclerView recyclerView;
    private ProgressBar bottomProgressBar;
    private ProgressBar centerProgressBar;
    private SwipeRefreshLayout refreshLayout;

    private SearchProductsPresenter presenter;
    private ProductsAdapter adapter;

    private String category;
    private int CAN_SCROLL_VERTICALLY = 1;
    private int pageIndex = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_found_products);

        presenter = new SearchProductsPresenter(this);

        setupRecyclerView();

        setupSwipeRefreshLayout();

        bottomProgressBar = findViewById(R.id.bottom_progress_bar);
        centerProgressBar = findViewById(R.id.center_progress_bar);

        //get a category in which to look for products
        category = getIntent().getStringExtra("category");

        presenter.loadProducts(category);

        presenter.onPaginationListener();
    }


    private void setupRecyclerView() {

        recyclerView = findViewById(R.id.founded_products);
        GridLayoutManager layoutManager = new GridLayoutManager(this, 2);
        recyclerView.setLayoutManager(layoutManager);

        adapter = new ProductsAdapter(FoundProductsActivity.this, presenter.getTitlesList(),
                presenter.getImagesList(), this);

        recyclerView.setAdapter(adapter);
    }


    private void setupSwipeRefreshLayout() {

        refreshLayout = findViewById(R.id.refresh_products);
        refreshLayout.setOnRefreshListener(this);
        refreshLayout.setColorSchemeResources(R.color.colorPrimary,
                android.R.color.holo_green_dark,
                android.R.color.holo_orange_dark,
                android.R.color.holo_blue_dark);
    }


    @Override
    public void pagination(){

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                // Recycle view scrolling down...
                if (dy > 0) {

                    if (!recyclerView.canScrollVertically(CAN_SCROLL_VERTICALLY)) {

                        pageIndex++;

                        // Download the next piece of data...
                        presenter.loadNextPage(category, pageIndex);
                    }
                }
            }
        });
    }


    @Override
    public void showProgressBar() {
        centerProgressBar.setVisibility(View.VISIBLE);
    }


    @Override
    public void hideProgressBar() {
        centerProgressBar.setVisibility(View.GONE);
    }


    @Override
    public void showBottomProgressBar() {
        bottomProgressBar.setVisibility(View.VISIBLE);
    }


    @Override
    public void hideBottomProgressBar() {
        bottomProgressBar.setVisibility(View.GONE);
    }


    @Override
    public void onSuccessResponse(ArrayList<String> titlesList, ArrayList<String> imagesList,
                                  ArrayList<String> descriptionsList, ArrayList<String> pricesList,
                                  ArrayList<String> currenciesCodesList) {

        adapter.notifyDataSetChanged();
    }


    @Override
    public void onFailureResponse(Throwable throwable) {

        Toast.makeText(this, throwable.getMessage(), Toast.LENGTH_LONG).show();
    }


    @Override
    public void onRefresh() {

        presenter.onSwipeRefreshing(category);

        adapter.notifyDataSetChanged();
    }


    @Override
    public void stopRefreshing() {

        refreshLayout.setRefreshing(false);
    }


    @Override
    public void onItemClick(int position) {

        startActivity(new Intent(FoundProductsActivity.this, DetailedScreenActivity.class)
                .putExtra("image", presenter.getImagesList().get(position))
                .putExtra("description", presenter.getDescriptionsList().get(position))
                .putExtra("title", presenter.getTitlesList().get(position))
                .putExtra("price", presenter.getPricesList().get(position))
                .putExtra("currency", presenter.getCurrenciesCodesList().get(position)));
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.onDestroy();
    }
}
