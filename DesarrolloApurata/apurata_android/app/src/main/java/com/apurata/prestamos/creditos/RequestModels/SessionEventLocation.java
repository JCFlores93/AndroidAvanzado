package com.apurata.prestamos.creditos.RequestModels;

import com.google.gson.annotations.SerializedName;

/**
 * Created by apurata on 9/20/17.
 */

public class SessionEventLocation {

    @SerializedName("type")
    private String type;

    @SerializedName("sessionId")
    private String sessionId;

    @SerializedName("data")
    private CoordBGService coordBGService;

    @SerializedName("created_at")
    private String created_at;

    private Boolean isComingFromApp;

    private Boolean fromAppDB;

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public Boolean getIsComingFromApp() {
        return isComingFromApp;
    }

    public void setIsComingFromApp(Boolean isComingFromApp) {
        this.isComingFromApp = isComingFromApp;
    }

    public CoordBGService getCoordBGService() {
        return coordBGService;
    }

    public void setCoordBGService(CoordBGService coordBGService) {
        this.coordBGService = coordBGService;
    }

    public Boolean getFromAppDB() {
        return fromAppDB;
    }

    public void setFromAppDB(Boolean fromAppDB) {
        this.fromAppDB = fromAppDB;
    }
}
