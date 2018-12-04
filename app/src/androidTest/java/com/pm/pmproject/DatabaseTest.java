package com.pm.pmproject;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import com.pm.pmproject.model.database.DbOpenHelper;
import com.pm.pmproject.model.entity.Attribute;
import com.pm.pmproject.model.entity.DaoMaster;
import com.pm.pmproject.model.entity.DaoSession;

import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertEquals;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class DatabaseTest {
    @Test
    public void attributeDaoTest() {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getTargetContext();

        DaoSession mDaoSession = new DaoMaster(
                new DbOpenHelper(appContext, "test.db").getWritableDb())
                .newSession();

        if(mDaoSession.getAttributeDao().loadAll().size() == 0){
            mDaoSession.getAttributeDao().insert(new Attribute(1L, "burned_calories"));
        }

        Attribute attr = mDaoSession.getAttributeDao().load(1L);


        assertEquals("burned_calories", attr.getType());
    }
}
