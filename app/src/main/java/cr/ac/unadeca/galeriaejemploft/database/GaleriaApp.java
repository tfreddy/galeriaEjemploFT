package cr.ac.unadeca.galeriaejemploft.database;

import android.app.Application;
import android.app.ApplicationErrorReport;

import com.raizlabs.android.dbflow.config.FlowManager;

/**
 * Created by Freddy on 3/26/2018.
 */

public class GaleriaApp extends Application{

    @Override
    public void onCreate(){
        super.onCreate();
        FlowManager.init(this);
    }


}
