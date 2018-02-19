package org.bubulescu.contentprovider;

import android.Manifest;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CursorAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

import org.bubulescu.contentprovider.data.TaskContract;

public class MainActivity extends AppCompatActivity {

    private EditText etTaskName, etDescription;
    private Button btnAdd, btnUpdate, btnDelete, btnRefresh;
    private ListView lvResults;
    private SimpleCursorAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initWidgets();
        setupListeners();
    }

    private void initWidgets() {
        etTaskName = findViewById(R.id.etTaskName);
        etDescription = findViewById(R.id.etTaskDescription);
        btnAdd = findViewById(R.id.btnAdd);
        btnUpdate = findViewById(R.id.btnUpdate);
        btnDelete = findViewById(R.id.btnDelete);
        btnRefresh = findViewById(R.id.btnRefresh);
        lvResults = findViewById(R.id.lvResults);
    }

    private void setupListeners() {

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ContentValues contentValues = new ContentValues();
                contentValues.put(TaskContract.Columns.TASK_NAME, etTaskName.getText().toString());
                contentValues.put(TaskContract.Columns.TASK_DESCRIPTION, etDescription.getText().toString());
                getContentResolver().insert(TaskContract.CONTENT_URI, contentValues);

                Toast.makeText(MainActivity.this, "Inserted", Toast.LENGTH_SHORT).show();
            }
        });
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ContentValues contentValues = new ContentValues();
                contentValues.put(TaskContract.Columns.TASK_DESCRIPTION, etDescription.getText().toString());
                String selection = TaskContract.Columns.TASK_NAME + " = ?";
                String newTaskName = etTaskName.getText().toString();
                String args[] = {newTaskName};
                getContentResolver().update(TaskContract.CONTENT_URI, contentValues, selection, args);
            }
        });
        btnRefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cursor cursor = getContentResolver().query(TaskContract.CONTENT_URI, null, null, null, null);
                String[] fromColumns = {TaskContract.Columns.TASK_DESCRIPTION};
                int[] toView = {android.R.id.text1};
                adapter = new SimpleCursorAdapter(MainActivity.this, android.R.layout.simple_expandable_list_item_1, cursor, fromColumns, toView, 0);
                lvResults.setAdapter(adapter);
            }
        });
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String selection = TaskContract.Columns.TASK_NAME + " = ?";
                String desc = etTaskName.getText().toString();
                String args[] = {desc};
                getContentResolver().delete(TaskContract.CONTENT_URI, selection, args);
            }
        });

        lvResults.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String idToDelete = "/" + id;
                getContentResolver().delete(Uri.parse(TaskContract.CONTENT_URI + idToDelete), null, null);
            }
        });
    }
}
