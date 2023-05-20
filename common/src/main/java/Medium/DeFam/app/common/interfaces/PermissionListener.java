package Medium.DeFam.app.common.interfaces;


import java.util.List;

/**
 * Created by MacBook on 17/11/20.
 */

public interface PermissionListener {

    /**
     * 授权成功
     */
    void onGranted();

    /**
     * 授权被拒绝的列表
     *
     * @param deniedList
     */
    void onDenied(List<String> deniedList);

}

