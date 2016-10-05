package pro.rgun.akbarstest.ui.screen.auth.view;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import pro.rgun.akbarstest.R;
import pro.rgun.akbarstest.ui.screen.auth.inner_components.login.LoginFragment;
import pro.rgun.akbarstest.ui.screen.notes_list.NotesListActivity;


/**
 * Created by rgun on 22.08.16.
 */
public class AuthViewImpl implements AuthView {

    private AppCompatActivity mActivity;

    public AuthViewImpl(AppCompatActivity activity) {
        mActivity = activity;
        mActivity.setContentView(R.layout.activity_vk_login);
    }


    @Override
    public void showLogin() {
        mActivity.getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.container, new LoginFragment())
                .commit();
    }

    @Override
    public void showFriendsListScreen() {
        mActivity.startActivity(new Intent(mActivity, NotesListActivity.class));
        mActivity.finish();
    }

    @Override
    public void showToast(String s) {
        Toast.makeText(mActivity, s, Toast.LENGTH_LONG).show();
    }

}
