package com.test.assignment.Utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by Keyur Tailor on 01-Nov-17.
 */
public class Network {

    private Context context;
    private ConnectivityManager connectionManager;

    boolean isOutcome = false;

    public Network(Context context){
        this.context = context;
    }

    public boolean getConnectivityStatus(){
        if(context != null){
            connectionManager = (ConnectivityManager)
                    context.getSystemService(Context.CONNECTIVITY_SERVICE);

            NetworkInfo[] networkInfos = connectionManager.getAllNetworkInfo();

            for(NetworkInfo tempNetworkInfo : networkInfos ){
                if(tempNetworkInfo.isConnected()){
                    isOutcome = true;
                    break;
                }
            }
        }
        return  isOutcome;
    }
}
