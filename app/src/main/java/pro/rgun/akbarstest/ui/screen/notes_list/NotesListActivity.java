package pro.rgun.akbarstest.ui.screen.notes_list;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import pro.rgun.akbarstest.R;

public class NotesListActivity extends AppCompatActivity {

    public static final int MY_PERMISSIONS_REQUEST_READ_CONTACTS = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes_list);
    }
}
