package pro.rgun.akbarstest.ui.screen.note_detail;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import pro.rgun.akbarstest.R;


public class NoteDetailActivity extends AppCompatActivity {

    public static final String EXTRA_NOTE_ID = "noteId";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_detail);
    }
}
