package com.soict.hoangviet.supportinglecturer.ui.base;

import android.content.Intent;
import android.util.Log;

import androidx.annotation.Nullable;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;

import java.util.Arrays;

public abstract class BaseSocialActivity extends BaseActivity {
    private static final int RC_SIGN_IN = 9001;
    private static final String TAG = BaseSocialActivity.class.getSimpleName();
    private GoogleSignInClient mGoogleSignInClient;
    private CallbackManager callbackManager;

    @Override
    public void initData() {
        configGoogleSignIn();
        configCallbackManager();
    }

    private void configCallbackManager() {
        callbackManager = CallbackManager.Factory.create();
    }

    private void configGoogleSignIn() {
        // Configure sign-in to request the user's ID, email address, and basic
        // profile. ID and basic profile are included in DEFAULT_SIGN_IN.
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        // Build a GoogleSignInClient with the options specified by gso.
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

    }

    protected void signInGoogle() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            // The Task returned from this call is always completed, no need to attach
            // a listener.
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }
    }

    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);
            Log.w(TAG, "signInResult:success ");
            // Signed in successfully, show authenticated UI.
            getSocialGoogleListener().onSuccess(account);
        } catch (ApiException e) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            Log.w(TAG, "signInResult:failed code=" + e.getStatusCode());
            getSocialGoogleListener().onError();
        }
    }

    protected void signInFacebook() {
        LoginManager.getInstance().logInWithReadPermissions(this, Arrays.asList("public_profile", "email"));
        LoginManager.getInstance().registerCallback(callbackManager,
                new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {
                        AccessToken accessToken = loginResult.getAccessToken();
                        Log.w(TAG, accessToken.getToken());
                        getSocialFacebookListener().onSuccess(accessToken);
                    }

                    @Override
                    public void onCancel() {
                        getSocialFacebookListener().onCancel();
                    }

                    @Override
                    public void onError(FacebookException exception) {
                        getSocialFacebookListener().onError(exception);
                    }
                });
    }

    protected abstract SocialGoogleListener getSocialGoogleListener();

    protected abstract SocialFacebookListener getSocialFacebookListener();

    public interface SocialGoogleListener {
        void onSuccess(GoogleSignInAccount account);

        void onError();
    }

    public interface SocialFacebookListener {
        void onSuccess(AccessToken accessToken);

        void onCancel();

        void onError(FacebookException exception);
    }
}
