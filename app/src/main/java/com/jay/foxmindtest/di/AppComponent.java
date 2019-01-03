package com.jay.foxmindtest.di;

import com.jay.foxmindtest.view.fragment.SearchCategoriesFragment;

import dagger.Component;

@Component(modules = {PresenterModule.class})
public interface AppComponent {

    void inject(SearchCategoriesFragment fragment);
}
