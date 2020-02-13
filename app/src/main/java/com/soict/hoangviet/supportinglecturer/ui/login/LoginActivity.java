package com.soict.hoangviet.supportinglecturer.ui.login;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.widget.AppCompatButton;

import com.facebook.AccessToken;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.common.Scopes;
import com.google.api.services.youtube.YouTubeScopes;
import com.google.gson.Gson;
import com.soict.hoangviet.supportinglecturer.R;
import com.soict.hoangviet.supportinglecturer.entity.response.FacebookResponse;
import com.soict.hoangviet.supportinglecturer.ui.base.BaseActivity;
import com.soict.hoangviet.supportinglecturer.ui.base.BaseSocialActivity;

import org.json.JSONObject;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class LoginActivity extends BaseSocialActivity implements LoginView {
    @Inject
    LoginPresenter<LoginView> mPresenter;
    public static final String TAG = LoginActivity.class.getSimpleName();
    public static final String SCOPE_YOUTUBE = YouTubeScopes.YOUTUBE;
    public static final String SCOPE_PROFILE = Scopes.PROFILE;

    @BindView(R.id.btnFacebook)
    AppCompatButton btnFacebook;
    @BindView(R.id.btnGoogle)
    Button btnGoogle;

    @Override
    protected Unbinder getButterKnifeBinder() {
        return ButterKnife.bind(this);
    }

    @Override
    protected void initView() {
        onAttachPresenter();
    }

    @Override
    public void initData() {
        super.initData();

    }

    private void onAttachPresenter() {
        mPresenter.onAttach(this);
    }

    @Override
    protected SocialGoogleListener getSocialGoogleListener() {
        return new SocialGoogleListener() {
            @Override
            public void onSuccess(GoogleSignInAccount account) {
                mPresenter.loginGoogleSuccess(account);
            }

            @Override
            public void onError() {
            }
        };
    }

    @Override
    protected SocialFacebookListener getSocialFacebookListener() {
        return new SocialFacebookListener() {
            @Override
            public void onSuccess(AccessToken accessToken) {
                GraphRequest request = GraphRequest.newMeRequest(accessToken, new GraphRequest.GraphJSONObjectCallback() {
                    //OnCompleted is invoked once the GraphRequest is successful
                    @Override
                    public void onCompleted(JSONObject object, GraphResponse response) {
                        try {
                            FacebookResponse facebookResponse = new Gson().fromJson(object.toString(), FacebookResponse.class);
                            mPresenter.loginFacebookSuccess(facebookResponse);
                        } catch (Exception e) {
                        }
                    }
                });
                // We set parameters to the GraphRequest using a Bundle.
                Bundle parameters = new Bundle();
                parameters.putString("fields", "id,name,email,picture.width(163)");
                request.setParameters(parameters);
                // Initiate the GraphRequest
                request.executeAsync();
            }

            @Override
            public void onCancel() {
            }

            @Override
            public void onError(FacebookException exception) {
            }
        };
    }

    @Override
    protected void initListener() {
        btnGoogle.setOnClickListener(view -> {
            signInGoogle();
        });
        btnFacebook.setOnClickListener(view -> {
            signInFacebook();
        });
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.activity_login;
    }

    @Override
    public void onResultToHomeScreen() {
        Intent returnIntent = new Intent();
        setResult(RESULT_OK, returnIntent);
        finish();
    }
}
