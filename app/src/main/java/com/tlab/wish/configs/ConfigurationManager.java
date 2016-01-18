package com.tlab.wish.configs;

import com.tlab.wish.App;
import com.tlab.wish.api_staff.WishesAPI;
import com.tlab.wish.utils.ExceptionTracker;
import com.tlab.wish.utils.Serialiser;

import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by andranik on 1/18/16.
 */
public class ConfigurationManager {

    public static ConfigurationManager instanse = new ConfigurationManager();

    public static ConfigurationManager getInstanse(){
        return instanse;
    }

    private Serialiser<Configs> serialiser;
    private Configs configs;

    private ConfigurationManager(){
        serialiser = new Serialiser<>("ConfigurationManager", "configs.ser");
    }

    public Configs getConfigs(){
        if(configs == null){
            try {
                configs = serialiser.deserialize();
            } catch (Exception e) {
                ExceptionTracker.trackException(e);
                configs = new Configs(); // In this case we will go with default values
            }
        }

        return configs;
    }

    public void updateConfiguration(){
        if(!App.getInstance().isOnline()){return;}

        Observable<Configuration> observable = WishesAPI.getInstanse().getConfiguration();

        final Subscription subscription = observable
                .observeOn(Schedulers.io())
                .subscribeOn(Schedulers.io())
                .filter(new Func1<Configuration, Boolean>() {
                    @Override
                    public Boolean call(Configuration configuration) {
                        return configuration.isSuccess();
                    }
                })
                .subscribe(new Subscriber<Configuration>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        ExceptionTracker.trackException(e);
                    }

                    @Override
                    public void onNext(Configuration configuration) {
                        configs = configuration.getConfigs();
                        serialiser.serialize(configuration.getConfigs());
                    }
                });

    }
}
