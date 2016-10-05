package pro.rgun.akbarstest.ui.screen.auth.inner_components.login;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.vk.sdk.VKSdk;

import pro.rgun.akbarstest.ui.screen.auth.model.AuthModelImpl;


/**
 * Created by rgun on 23.04.16.
 */
public class LoginFragment extends android.support.v4.app.Fragment {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(LoginVH.layout, container, false);
        LoginVH vh = new LoginVH(v);
        vh.buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                VKSdk.login(getActivity(), AuthModelImpl.mScope);
            }
        });
        return v;
    }

}
