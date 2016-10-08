package pro.rgun.akbarstest.ui.screen.notes_list;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import pro.rgun.akbarstest.R;

import static pro.rgun.akbarstest.R.id.fragmentContainer;

public class NotesListActivity extends AppCompatActivity {

    public static final int MY_PERMISSIONS_REQUEST_READ_CONTACTS = 1;
    public static final String INTENT_EXTRA_STORAGE_TYPE = "extraStorageType";
    private NotesListFragment mNotesListFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes_list);
        mNotesListFragment = (NotesListFragment) getSupportFragmentManager().findFragmentById(fragmentContainer);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        mNotesListFragment.onActivityResult(requestCode, resultCode, data);
    }
}
