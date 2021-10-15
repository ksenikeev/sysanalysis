package ru.itis.sysanalysis.tsp;

public class PKIStatusInfo {
    /*
    granted=0, токен присутствует
    grantedWithMods=1, токен присутствует с изменениями
    rejection=2, штамп времени не получен
    waiting=3, запрос на получение штампа времени еще не обработан
    revocationWarning=4, аннулирование неизбежно
    revocationNotification=5, аннулирование имело место
    keyUpdateWarning=6
     */
    private int status;
    /* PKIFreeText */
    private String statusString;
    /* PKIFailureInfo */
    private String failInfo;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getStatusString() {
        return statusString;
    }

    public void setStatusString(String statusString) {
        this.statusString = statusString;
    }

    public String getFailInfo() {
        return failInfo;
    }

    public void setFailInfo(String failInfo) {
        this.failInfo = failInfo;
    }
}
