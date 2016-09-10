package pro.rgun.akbarstest.ui.extras.architecture;

import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by railkamalov on 04.08.16.
 */
public interface BaseView<P> {

    /**
     * Установить презентер взаимодействующий с вью
     * @param presenter
     */
    void setPresenter(P presenter);

    /**
     * Привязать активити
     * Вызывается в методе Fragment#onAttach  и Fragment#onCreate
     * @param activity
     */
    void bindActivity(AppCompatActivity activity);

    /**
     * Отвязать активити
     * Вызывается в методе Fragment#onDetach
     */
    void unbindActivity();

    /**
     * Инициализация вью
     * Вызывается в методе Fragment#onCreateView
     * @param inflater
     * @param view
     */
    void initView(LayoutInflater inflater, ViewGroup view);


    /**
     * Получить вью компонент
     * @return {@link View}
     */
    View getView();
}
