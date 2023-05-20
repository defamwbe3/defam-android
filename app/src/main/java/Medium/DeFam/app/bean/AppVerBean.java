package Medium.DeFam.app.bean;

public class AppVerBean {
       private String versionName;//":"超级裂变",
    private String versionCode;//":1.01,
    private String iosversionCode;//":1,
    private String downloadUrl;//":"https",
    private String iosdownloadUrl;//":"http",
    private String versionInfo;//":"描述就",
    private String forceUpdate;//":

    public String getVersionName() {
        return versionName;
    }

    public void setVersionName(String versionName) {
        this.versionName = versionName;
    }

    public String getVersionCode() {
        return versionCode;
    }

    public void setVersionCode(String versionCode) {
        this.versionCode = versionCode;
    }

    public String getIosversionCode() {
        return iosversionCode;
    }

    public void setIosversionCode(String iosversionCode) {
        this.iosversionCode = iosversionCode;
    }

    public String getDownloadUrl() {
        return downloadUrl;
    }

    public void setDownloadUrl(String downloadUrl) {
        this.downloadUrl = downloadUrl;
    }

    public String getIosdownloadUrl() {
        return iosdownloadUrl;
    }

    public void setIosdownloadUrl(String iosdownloadUrl) {
        this.iosdownloadUrl = iosdownloadUrl;
    }

    public String getVersionInfo() {
        return versionInfo;
    }

    public void setVersionInfo(String versionInfo) {
        this.versionInfo = versionInfo;
    }

    public String getForceUpdate() {
        return forceUpdate;
    }

    public void setForceUpdate(String forceUpdate) {
        this.forceUpdate = forceUpdate;
    }
}
