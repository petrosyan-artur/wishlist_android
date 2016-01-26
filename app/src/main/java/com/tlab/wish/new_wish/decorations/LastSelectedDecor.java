package com.tlab.wish.new_wish.decorations;

import com.tlab.wish.configs.ConfigurationManager;
import com.tlab.wish.utils.ExceptionTracker;
import com.tlab.wish.utils.Serialiser;

/**
 * Created by andranik on 1/26/16.
 */
public class LastSelectedDecor {

    private static LastSelectedDecor instanse = new LastSelectedDecor();

    public static LastSelectedDecor getInstanse(){
        return instanse;
    }

    private Serialiser<DecorItem> serialiser;
    private DecorItem selectedDecodeItem;

    public LastSelectedDecor() {
        serialiser = new Serialiser<>("LastSelectedDecor", "lastSelectedDecor.ser");
    }


    public DecorItem getSelectedDecodeItem() {
        if(selectedDecodeItem == null){
            try {
                selectedDecodeItem = serialiser.deserialize();
            } catch (Exception e) {
                ExceptionTracker.trackException(e);
            }
        }

        if(selectedDecodeItem == null){
            selectedDecodeItem = new ColorDecorItem(
                    ConfigurationManager.getInstanse().getConfigs().getDecorations().getColors().get(0));
        }

        return selectedDecodeItem;
    }

    public void updateSelectedDecor(DecorItem decorItem){
        selectedDecodeItem = decorItem;

        serialiser.serialize(decorItem);
    }

}
