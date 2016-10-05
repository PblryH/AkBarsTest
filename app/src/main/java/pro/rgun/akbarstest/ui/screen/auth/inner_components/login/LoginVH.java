package pro.rgun.akbarstest.ui.screen.auth.inner_components.login;

import android.view.View;
import android.widget.Button;

import butterknife.BindView;
import butterknife.ButterKnife;
import pro.rgun.akbarstest.R;

/**
 * ViewHolder
 */
class LoginVH {

    public static final int layout = R.layout.fragment_vk_login;

    @BindView(R.id.buttonLogin)
    public Button buttonLogin;

    public LoginVH(View v) {
        ButterKnife.bind(this, v);
    }
}
