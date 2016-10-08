package pro.rgun.akbarstest.ui.screen.notes_list.presenter;

import com.vk.sdk.VKSdk;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import pro.rgun.akbarstest.domain.model.StorageType;
import pro.rgun.akbarstest.ui.screen.notes_list.model.NotesListModel;
import pro.rgun.akbarstest.ui.screen.notes_list.view.NotesListView;
import timber.log.Timber;

/**
 * Created by rgun on 10.09.16.
 */
public class NotesListPresenterImpl implements NotesListPresenter,Observer {

    private final NotesListView mView;
    private final NotesListModel mModel;

    public NotesListPresenterImpl(NotesListView view, NotesListModel model) {
        mView = view;
        mView.setPresenter(this);
        mModel = model;
    }

    @Override
    public void onMenuChooseStorageClicked() {
        mView.showChooseStorageDialog(mModel.getCurrentStorageType(), (storageType) -> {
            mView.fillNotes(new ArrayList<>());
            mModel.setCurrentStorageType(storageType);
            if(storageType == StorageType.VKWALL && !VKSdk.isLoggedIn()){
                mView.openVkAuthScreen();
                return;
            }
            mView.setCurrentStorageInfoInToolbarSubtitle(storageType);
            mModel.getNotes(mView::fillNotes);
        });
    }

    @Override
    public void onInitViewComplete() {
        mView.setCurrentStorageInfoInToolbarSubtitle(mModel.getCurrentStorageType());
        mModel.getNotes(mView::fillNotes);
        mModel.subscribeToNotesUpdate(this);
    }

    @Override
    public void onAddClick() {
        mView.showCreateNoteScreen();
    }

    @Override
    public void onItemClick(String id) {
        mView.showNoteDetailScreen(id);
    }

    @Override
    public void onItemDelete(String id) {
        mModel.deleteNote(id, obj -> Timber.d("Note was deleted"));
    }

    @Override
    public void onPullToRefresh() {
        mModel.getNotes(mView::fillNotes);
    }

    @Override
    public void update(Observable observable, Object o) {
        Timber.d("update");
        mModel.getNotes(mView::fillNotes);
    }
}
