package pro.rgun.akbarstest.ui.screen.note_detail.view;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import pro.rgun.akbarstest.R;
import pro.rgun.akbarstest.ui.extras.view_holder.VHContentWithToolbar;

/**
 * Created by rgun on 10.09.16.
 */
public class NoteDetailVH extends VHContentWithToolbar {

    public static final int LAYOUT = R.layout.fragment_note_detail;

    public Note note;

    public NoteDetailVH(LayoutInflater inflater, ViewGroup view) {
        super(inflater, view);
        note = new Note(getView());
    }

    @Override
    public int getLayoutRes() {
        return LAYOUT;
    }

    @Override
    protected void unbindAll() {
        note.unbind();
    }

    public class Note {

        @BindView(R.id.title)
        EditText title;

        @BindView(R.id.editor)
        MyRichEditor editor;

        @BindView(R.id.noteContent)
        View noteContent;

        ///////////////////////////////////////////////////////////////////////////
        // RichTextPanel
        ///////////////////////////////////////////////////////////////////////////

        @BindView(R.id.richTextPanel)
        View richTextPanel;

        @BindView(R.id.heading)
        ImageView heading;

        @BindView(R.id.bold)
        ImageView bold;

        @BindView(R.id.italic)
        ImageView italic;

        @BindView(R.id.unorderedList)
        ImageView unorderedList;

        @BindView(R.id.orderedList)
        ImageView orderedList;

        @BindView(R.id.undo)
        ImageView undo;

        @BindView(R.id.redo)
        ImageView redo;


        private Unbinder mUnbinder;

        public Note(View v) {
            mUnbinder = ButterKnife.bind(this, v);
        }

        public void unbind() {
            mUnbinder.unbind();
        }
    }
}
