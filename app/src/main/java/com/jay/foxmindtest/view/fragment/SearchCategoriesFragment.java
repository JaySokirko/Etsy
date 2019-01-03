package com.jay.foxmindtest.view.fragment;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.jay.foxmindtest.R;
import com.jay.foxmindtest.SearchCategoriesContract;
import com.jay.foxmindtest.di.DaggerAppComponent;
import com.jay.foxmindtest.di.PresenterModule;
import com.jay.foxmindtest.presenter.SearchCategoriesPresenter;
import com.jay.foxmindtest.view.activity.FoundProductsActivity;

import java.util.ArrayList;

import javax.inject.Inject;

/**
 * A simple {@link Fragment} subclass.
 */
public class SearchCategoriesFragment extends Fragment implements SearchCategoriesContract.View {


    private ProgressBar progressBar;
    private ListView listView;
    private EditText searchEditText;

    private Context context;

    private ArrayAdapter<String> adapter;

    @Inject
    public SearchCategoriesPresenter presenter;


    public SearchCategoriesFragment() {
        // Required empty public constructor
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_search_categories, container, false);

        progressBar = view.findViewById(R.id.progress_bar);
        listView = view.findViewById(R.id.categories_list);
        searchEditText = view.findViewById(R.id.search_categories);

        DaggerAppComponent.builder().presenterModule(new PresenterModule(this)).build()
                .inject(this);

        presenter.loadCategories();

        onSearchEditTextChangeListener();
        onCategorySelectListener();

        return view;
    }


    @Override
    public void showProgress() {
        progressBar.setVisibility(View.VISIBLE);
    }


    @Override
    public void hideProgress() {
        progressBar.setVisibility(View.GONE);
    }


    @Override
    public void onSuccessResponse(ArrayList<String> categoriesList) {

        adapter = new ArrayAdapter<>(context, android.R.layout.simple_list_item_1, categoriesList);
        listView.setAdapter(adapter);
    }


    @Override
    public void onFailureResponse(Throwable throwable) {

        Toast.makeText(context, throwable.getMessage(), Toast.LENGTH_SHORT).show();
    }


    public void onSearchEditTextChangeListener() {

        searchEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //Filter the list by typing characters
                adapter.getFilter().filter(s);
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }


    private void onCategorySelectListener() {

        listView.setOnItemClickListener((parent, view, position, id) -> {

            String category = (String) listView.getItemAtPosition(position);
            category = category.replaceAll(" ", "_");

            //Pass selected category to FoundProductsActivity
            startActivity(new Intent(getContext(), FoundProductsActivity.class)
                    .putExtra("category", category));

        });
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        presenter.onDestroy();
    }
}
