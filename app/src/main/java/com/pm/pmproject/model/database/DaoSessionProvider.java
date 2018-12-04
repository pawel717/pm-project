package com.pm.pmproject.model.database;

import android.content.Context;

import com.pm.pmproject.model.entity.Attribute;
import com.pm.pmproject.model.entity.DaoMaster;
import com.pm.pmproject.model.entity.DaoSession;

public class DaoSessionProvider {

    private static DaoSession daoSession;

    public static DaoSession getDaoSession(Context appContext) {
        if(daoSession == null)
            createDaoSession(appContext);

        return DaoSessionProvider.daoSession;
    }

    private static void createDaoSession(Context appContext){
        DaoSessionProvider.daoSession = new DaoMaster(
                new DbOpenHelper(appContext, "database.db").getWritableDb())
                .newSession();
    }
}
