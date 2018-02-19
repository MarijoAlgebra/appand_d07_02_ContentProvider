package org.bubulescu.contentprovider.data;


import android.content.ContentUris;
import android.net.Uri;
import android.provider.BaseColumns;

public class TaskContract {

    public static final String TABLE_NAME = "Task";

    public static class Columns {
        public static final String _ID = BaseColumns._ID;
        public static final String TASK_NAME = "Name";
        public static final String TASK_DESCRIPTION = "Description";
        public static final String TASKS_SORT_ORDER = "SortOrder";

        private Columns() {
        }
    }

    public static final String CONTENT_AUTHORITY = "org.bubulescu.provider";
    public static final Uri CONTENT_AUTHORITY_URI = Uri.parse("content://" + CONTENT_AUTHORITY);
    public static final Uri CONTENT_URI = Uri.withAppendedPath(CONTENT_AUTHORITY_URI, TABLE_NAME);

    // id:7
    // content://org.bubulescu.provider/Task/7
    public static Uri buildTaskUri(long id) {
        return ContentUris.withAppendedId(CONTENT_URI, id);
    }

    // uri: content://org.bubulescu.provider/Task/256
    // id:256
    public static long getTaskId(Uri uri) {
        return ContentUris.parseId(uri);
    }
}
