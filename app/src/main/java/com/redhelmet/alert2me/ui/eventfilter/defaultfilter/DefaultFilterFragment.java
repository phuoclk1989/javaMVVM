package com.redhelmet.alert2me.ui.eventfilter.defaultfilter;

import android.arch.lifecycle.ViewModelProvider;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.ExpandableListView;

import com.redhelmet.alert2me.R;
import com.redhelmet.alert2me.adapters.DefaultNotificationAdapter;
import com.redhelmet.alert2me.databinding.FragmentDefaultFilterBinding;
import com.redhelmet.alert2me.ui.base.BaseFragment;
import com.redhelmet.alert2me.ui.eventfilter.EventFilterActivity;

import javax.inject.Inject;

public class DefaultFilterFragment extends BaseFragment<DefaultFilterViewModel, FragmentDefaultFilterBinding> implements EventFilterActivity.OnSaveClickListener {

    @Inject
    ViewModelProvider.Factory factory;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_default_filter;
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        obtainViewModel(factory, DefaultFilterViewModel.class);
    }

    @Override
    public void onSaveClick() {
        viewModel.saveData();
    }
}
