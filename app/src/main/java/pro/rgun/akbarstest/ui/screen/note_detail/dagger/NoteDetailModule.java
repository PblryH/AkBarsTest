package pro.rgun.akbarstest.ui.screen.note_detail.dagger;

import android.content.Context;

import javax.inject.Inject;

import dagger.Module;
import dagger.Provides;
import pro.rgun.akbarstest.ui.extras.architecture.PerActivity;
import pro.rgun.akbarstest.ui.screen.note_detail.model.NoteDetailModel;
import pro.rgun.akbarstest.ui.screen.note_detail.model.NoteDetailModelImpl;
import pro.rgun.akbarstest.ui.screen.note_detail.presenter.NoteDetailPresenter;
import pro.rgun.akbarstest.ui.screen.note_detail.presenter.NoteDetailPresenterImpl;
import pro.rgun.akbarstest.ui.screen.note_detail.view.NoteDetailView;
import pro.rgun.akbarstest.ui.screen.note_detail.view.NoteDetailViewImpl;

/**
 * Created by rgun on 10.09.16.
 */
@Module
public class NoteDetailModule {

    private Context mContext;

    public NoteDetailModule(Context context) {
        mContext = context;
    }

    @PerActivity
    @Provides
    NoteDetailPresenter provideNoteDetailPresenter(NoteDetailView view, NoteDetailModel model){
        return new NoteDetailPresenterImpl(view, model);
    }

    @PerActivity
    @Provides
    @Inject
    NoteDetailModel provideNoteDetailModel(){
        return new NoteDetailModelImpl(mContext);
    }

    @PerActivity
    @Provides
    NoteDetailView provideNoteDetailView(){
        return new NoteDetailViewImpl();
    }
}
