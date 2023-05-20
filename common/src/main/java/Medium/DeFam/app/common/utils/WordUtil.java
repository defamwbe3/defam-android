package Medium.DeFam.app.common.utils;

import android.content.res.Resources;

import Medium.DeFam.app.common.CommonAppContext;

public class WordUtil {
    private static Resources sResources;

    static {
        sResources = CommonAppContext.sInstance.getResources();
    }

    public static String getString(int... resources) {
        StringBuilder s = new StringBuilder();
        for (int res : resources) {
            s.append(sResources.getString(res));
        }
        return s.toString();
    }
}
