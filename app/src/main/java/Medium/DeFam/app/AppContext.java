package Medium.DeFam.app;

import androidx.multidex.MultiDex;

import Medium.DeFam.app.base.DeFamInit;
import Medium.DeFam.app.common.CommonAppContext;

public class AppContext extends CommonAppContext {
    private static AppContext instance;

    public static AppContext instance() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        MultiDex.install(this);
        DeFamInit.init(this);
    }

}
