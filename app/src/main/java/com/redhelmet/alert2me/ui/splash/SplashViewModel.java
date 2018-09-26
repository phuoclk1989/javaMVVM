package com.redhelmet.alert2me.ui.splash;

import com.redhelmet.alert2me.R;
import com.redhelmet.alert2me.data.DataManager;
import com.redhelmet.alert2me.global.Constant;
import com.redhelmet.alert2me.global.Event;
import com.redhelmet.alert2me.ui.base.BaseViewModel;
import com.redhelmet.alert2me.ui.base.NavigationType;
import com.redhelmet.alert2me.ui.hint.HintsActivity;
import com.redhelmet.alert2me.ui.home.HomeActivity;
import com.redhelmet.alert2me.ui.termsandcondition.TermConditionActivity;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;

public class SplashViewModel extends BaseViewModel {
    public SplashViewModel(DataManager dataManager) {
        super(dataManager);
        isLoading = true;
        disposeBag.add(dataManager.loadConfig()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(configResponse -> {
                    isLoading = false;
                    startTimer();
                }, error -> {
                    isLoading = false;
                    startTimer();
                    navigationEvent.setValue(
                            new Event<>(NavigationType.SHOW_TOAST.
                                    setData(R.string.timeOut)));
                }));
    }

    private void startTimer() {
        disposeBag.add(Observable.timer(Constant.SPLASH_DISPLAY_LENGTH, TimeUnit.MILLISECONDS)
                .subscribeOn(AndroidSchedulers.mainThread())
                .subscribe(number -> {
                    Class dest;
                    if (dataManager.getInitialLaunch()) {
                        if (dataManager.getAccepted()) {
                            dest = HomeActivity.class;
                        } else {
                            dest = TermConditionActivity.class;
                        }
                    } else {
                        dest = HintsActivity.class;
                    }

                    navigationEvent.setValue(new Event<>(NavigationType.START_ACTIVITY_AND_FINISH.setData(dest)));
                }));
    }
}
