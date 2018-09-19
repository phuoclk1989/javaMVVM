package com.redhelmet.alert2me.ui.activity;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.redhelmet.alert2me.model.AddObservationModel;
import com.redhelmet.alert2me.model.Config;
import com.redhelmet.alert2me.model.Observations;

import org.json.JSONArray;

/**
 * Created by inbox on 27/11/17.
 */

public abstract class BaseActivity extends AppCompatActivity{

    public JSONArray wz_notification_selection;
    Config config;
    Snackbar snackbar=null;
    Observations observations;
    AddObservationModel addObservation;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        wz_notification_selection=new JSONArray();
        config=config.getInstance();
        observations=observations.getInstance();
        addObservation = addObservation.getInstance();


    }
    public void showSnack(View watchzoneLayout, String message){

        snackbar = Snackbar.make(watchzoneLayout, message, Snackbar.LENGTH_INDEFINITE);
        snackbar.show();
    }

    public void dismisSnackbar(){
        Thread t = new Thread()
        {
            public void run()
            {
                try{
                    sleep(3000);
                }catch(InterruptedException ie)
                {
                    ie.printStackTrace();
                }finally
                {
                    if(snackbar.isShown() && snackbar!=null)
                        snackbar.dismiss();
                }
            }
        }; t.start();

    }

    public void changeText(String message){
        if(snackbar.isShown() && snackbar!=null)
            snackbar.setText(message);
    }
}
