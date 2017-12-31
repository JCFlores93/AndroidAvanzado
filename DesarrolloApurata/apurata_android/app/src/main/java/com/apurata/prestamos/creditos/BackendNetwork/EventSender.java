package com.apurata.prestamos.creditos.BackendNetwork;

import android.content.Context;
import android.util.Log;

import com.apurata.prestamos.creditos.BackendNetwork.HandleResponse.CallbackInterfaces;
import com.apurata.prestamos.creditos.RequestModels.CoordBGService;
import com.apurata.prestamos.creditos.RequestModels.SessionEventLocation;
import com.apurata.prestamos.creditos.RequestModels.SessionEventMessage;
import com.apurata.prestamos.creditos.BackendNetwork.RequestInterfaces.ApurataConnection;
import com.apurata.prestamos.creditos.Utils.Constants;

/**
 * Created by apurata on 9/28/17.
 */

public class EventSender {
    private static final String TAG = "GPS_TRACKER";
    private String userId;

    public void sendEventGpsFromDB(CoordBGService coordBGService, Context context) {
        sendEventGps(coordBGService, true, context);
    }

    public void sendEventGpsStream(CoordBGService coordBGService, Context context) {
        sendEventGps(coordBGService, false, context);
    }

    public void sendEventGps(CoordBGService coordBGService, boolean isFromDataBase, Context context) {
        Log.i(TAG, "Before sending coordinates");
        final ApurataConnection httpApurata = BackendConnection.conApurata();
        final SessionEventLocation event = new SessionEventLocation();
        event.setCoordBGService(coordBGService);
        event.setCreated_at(coordBGService.getCreated_at());
        event.setSessionId(userId);
        event.setType("location");
        event.setIsComingFromApp(true);
        event.setFromAppDB(isFromDataBase);
        Log.d(TAG, "#event: " + String.valueOf(event.getCoordBGService().getCounter()));
        Log.d(TAG, "Sending to " + Constants.APURATA_URL);


        if (isFromDataBase) {
            CallbackInterfaces.enqueueWithRetryDb(httpApurata.postEventLocation(event),
                    event.getCoordBGService(), context);
        } else {
            CallbackInterfaces.enqueueWithRetryStream(httpApurata.postEventLocation(event),
                    event.getCoordBGService(), context);
        }
    }

    public void sendMessage(String message) {
        Log.i(TAG, "Sending message to server: " + message);
        ApurataConnection httpApurata = BackendConnection.conApurata();
        SessionEventMessage event = new SessionEventMessage();
        event.setMessage(message);
        event.setSessionId(userId);
        event.setType("locationError");
        event.setComingFromApp(true);
        CallbackInterfaces.enqueueWithRetryMessage(httpApurata.postEventMessage(event),"message");
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
