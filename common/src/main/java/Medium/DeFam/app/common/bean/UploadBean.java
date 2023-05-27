package Medium.DeFam.app.common.bean;

import java.io.Serializable;

public class UploadBean implements Serializable {
    private String filePath;

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }
}
