package pro.rgun.akbarstest.ui.screen.notes_list.presenter;

import pro.rgun.akbarstest.ui.screen.notes_list.model.NotesListModel;
import pro.rgun.akbarstest.ui.screen.notes_list.view.NotesListView;

/**
 * Created by rgun on 10.09.16.
 */
public class NotesListPresenterImpl implements NotesListPresenter {

    private final NotesListView mView;
    private final NotesListModel mModel;

    public NotesListPresenterImpl(NotesListView view, NotesListModel model) {
        mView = view;
        mView.setPresenter(this);
        mModel = model;
    }
}
