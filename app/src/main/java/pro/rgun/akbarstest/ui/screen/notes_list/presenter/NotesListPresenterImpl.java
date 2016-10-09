package pro.rgun.akbarstest.ui.screen.notes_list.presenter;

import com.vk.sdk.VKSdk;
import com.vk.sdk.api.VKError;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import pro.rgun.akbarstest.R;
import pro.rgun.akbarstest.domain.model.Note;
import pro.rgun.akbarstest.domain.model.StorageType;
import pro.rgun.akbarstest.domain.repository.ResponseListener;
import pro.rgun.akbarstest.ui.screen.notes_list.model.NotesListModel;
import pro.rgun.akbarstest.ui.screen.notes_list.view.NotesListView;
import timber.log.Timber;

/**
 * Created by rgun on 10.09.16.
 */
public class NotesListPresenterImpl implements NotesListPresenter,Observer {

    private final NotesListView mView;
    private final NotesListModel mModel;

    private NotesListView.CallBack  mCallBack = new NotesListView.CallBack() {

        @Override
        public void onAuthorized() {
            chooseStorage(StorageType.VKWALL);
        }

        @Override
        public void onAuthorizationError(VKError error) {
            if(error != null && error.errorMessage != null && !error.errorMessage.isEmpty()) {
                mView.showToast(error.errorMessage);
            }
        }
    };

    public NotesListPresenterImpl(NotesListView view, NotesListModel model) {
        mView = view;
        mView.setPresenter(this);
        mModel = model;
        mView.setCallback(mCallBack);
    }

    @Override
    public void onMenuChooseStorageClicked() {
        mView.showChooseStorageDialog(mModel.getCurrentStorageType(), (storageType) -> {
            mView.fillNotes(new ArrayList<>());
            if(storageType == StorageType.VKWALL && !VKSdk.isLoggedIn()){
                mView.openVkAuthScreen();
                return;
            }
            chooseStorage(storageType);
        });
    }

    private void chooseStorage(StorageType storageType){
        mModel.setCurrentStorageType(storageType);
        mView.setCurrentStorageInfoInToolbarSubtitle(storageType);
        mModel.getNotes(new ResponseListener<List<Note>>() {
            @Override
            public void onGetResponse(List<Note> response) {
                mView.fillNotes(response);
            }

            @Override
            public void onError() {
                mView.showToast(R.string.dataReceiveError);
            }
        });
    }

    @Override
    public void onInitViewComplete() {
        mView.setCurrentStorageInfoInToolbarSubtitle(mModel.getCurrentStorageType());
        mModel.getNotes(new ResponseListener<List<Note>>() {
            @Override
            public void onGetResponse(List<Note> response) {
                mView.fillNotes(response);
            }

            @Override
            public void onError() {
                mView.showToast(R.string.dataReceiveError);
            }
        });
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
        mModel.deleteNote(id, new ResponseListener<Void>() {
            @Override
            public void onGetResponse(Void response) {
                Timber.d("Note was deleted");
            }

            @Override
            public void onError() {
                mView.showToast(R.string.dataReceiveError);
            }
        });
    }

    @Override
    public void onPullToRefresh() {
        mModel.getNotes(new ResponseListener<List<Note>>() {
            @Override
            public void onGetResponse(List<Note> response) {
                mView.fillNotes(response);
            }

            @Override
            public void onError() {
                mView.showToast(R.string.dataReceiveError);
                mView.setRefreshing(false);
            }
        });
    }

    @Override
    public void update(Observable observable, Object o) {
        Timber.d("update");
        mModel.getNotes(new ResponseListener<List<Note>>() {
            @Override
            public void onGetResponse(List<Note> response) {
                mView.fillNotes(response);
            }

            @Override
            public void onError() {
                mView.showToast(R.string.dataReceiveError);
            }
        });
    }
}
