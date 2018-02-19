package org.bubulescu.contentprovider;

import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.test.AndroidTestCase;

import org.bubulescu.contentprovider.data.TaskContract;


public class TestProvider extends AndroidTestCase {

    public void testDeleteAllRecordsFromDb() {

        mContext.getContentResolver().delete(TaskContract.CONTENT_URI, null, null);

        Cursor cursor = mContext.getContentResolver().query(TaskContract.CONTENT_URI, null, null, null, null);

        assertEquals("Error: contents not deleted", 0, cursor.getCount());

        cursor.close();
    }

    public void testInsert() {

        ContentValues contentValues = new ContentValues();
        contentValues.put(TaskContract.Columns.TASK_NAME, "nine");
        contentValues.put(TaskContract.Columns.TASK_DESCRIPTION, "code");
        Uri uri = mContext.getContentResolver().insert(TaskContract.CONTENT_URI, contentValues);

        assertTrue("Insert failed", uri != null);
    }

    public void testUpdate() {

        ContentValues contentValues = new ContentValues();
        contentValues.put(TaskContract.Columns.TASK_NAME, "nine");
        contentValues.put(TaskContract.Columns.TASK_DESCRIPTION, "code");
        int count = mContext.getContentResolver().update(TaskContract.CONTENT_URI, contentValues, null, null);

        assertTrue(count > 0);
    }

    public void testQuery() {

        Cursor cursor = mContext.getContentResolver().query(TaskContract.CONTENT_URI, null, null, null, null);

        assertTrue(cursor.moveToFirst());

    }

}
