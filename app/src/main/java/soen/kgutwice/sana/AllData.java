package soen.kgutwice.sana;

import android.app.Application;

/**
 * Created by KGU on 2017-12-08.
 */

public class AllData extends Application {
    private int dontLookPastEventChecked;
    private String userId;

    public String getUserId(){
        return userId;
    }
    public void setUserId(String userId) {
        this.userId = userId;
    }
    public int getDontLookPastEventChecked() {
        return dontLookPastEventChecked;
    }
    public void setDontLookPastEventChecked(int dontLookPastEventChecked) {
        this.dontLookPastEventChecked = dontLookPastEventChecked;
    }
}
