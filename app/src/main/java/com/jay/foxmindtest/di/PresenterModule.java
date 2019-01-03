package com.jay.foxmindtest.di;

import com.jay.foxmindtest.SearchCategoriesContract;

import dagger.Module;
import dagger.Provides;

@Module
public class PresenterModule {

    private SearchCategoriesContract.View view;

    public PresenterModule(SearchCategoriesContract.View view) {
        this.view = view;
    }

    @Provides
    public SearchCategoriesContract.View provideSearchContractView() {
        return view;
    }
}
