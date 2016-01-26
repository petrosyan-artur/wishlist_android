package com.tlab.wish.authentication;

import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.annotation.NonNull;
import android.support.v7.widget.Toolbar;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.easyandroidanimations.library.Animation;
import com.easyandroidanimations.library.FlipHorizontalToAnimation;
import com.easyandroidanimations.library.ShakeAnimation;
import com.hannesdorfmann.mosby.mvp.MvpActivity;
import com.tlab.wish.App;
import com.tlab.wish.CustomTypeFace;
import com.tlab.wish.R;
import com.tlab.wish.utils.DialogUtils;
import com.tlab.wish.utils.ViewUtils;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AuthActivity extends MvpActivity<AuthView, AuthPresenter> implements AuthView {

    public static final int AUTH_REQUET_CODE = 0x234;

    @Bind(R.id.auth_toolbar)
    Toolbar toolbar;

    @Bind(R.id.auth_progress)
    View progress;

    @Bind(R.id.auth_signup_layout)
    View signUpLayout;

    @Bind(R.id.auth_signup_title)
    TextView signUpTitle;

    @Bind(R.id.auth_signup_username)
    TextView signUpUsername;

    @Bind(R.id.auth_signup_pass1)
    EditText signUpPass1;

    @Bind(R.id.auth_signup_pass2)
    EditText signUpPass2;

    @Bind(R.id.auth_signup_signin)
    TextView signUpSignInBtn;

    @Bind(R.id.auth_signup_hint)
    TextView signUpHint;

    @Bind(R.id.auth_signup_btn)
    Button signUpBtn;

    @Bind(R.id.auth_signin_layout)
    View signInLayout;

    @Bind(R.id.auth_signin_username)
    EditText signInUsername;

    @Bind(R.id.auth_signin_password)
    EditText signInPass;

    @Bind(R.id.auth_signin_btn)
    Button signInBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authentication);
        ButterKnife.bind(this);

        initViews();
        ViewUtils.configureToolbar(this, toolbar, R.drawable.toolbar_close);

        presenter.onCreate();
    }

    private void initViews(){
        final Typeface ROBOTO_REGULAR = App.getInstance().getTypeface(CustomTypeFace.MyTypeFace.ROBOTO_REGULAR);
        final Typeface ROBOTO_BOLD = App.getInstance().getTypeface(CustomTypeFace.MyTypeFace.ROBOTO_BOLD);

        signUpTitle.setTypeface(ROBOTO_REGULAR);
        signUpUsername.setTypeface(ROBOTO_BOLD);
        signUpSignInBtn.setTypeface(ROBOTO_REGULAR);
        signUpHint.setTypeface(ROBOTO_REGULAR);
        signUpBtn.setTypeface(ROBOTO_REGULAR);
        signInUsername.setTypeface(ROBOTO_REGULAR);
        signInBtn.setTypeface(ROBOTO_REGULAR);

        SpannableString content = new SpannableString(signUpSignInBtn.getText());
        content.setSpan(new UnderlineSpan(), 0, content.length(), 0);
        signUpSignInBtn.setText(content);

    }

    @OnClick(R.id.auth_signup_signin)
    public void onSignUpSignInClick(){
        showSignInView();
    }

    @OnClick(R.id.auth_signin_btn)
    public void onSignInClick(){
        if(!App.getInstance().isOnline()){
            DialogUtils.showOfflineDialog(this);
            return;
        }

        presenter.onSignInClick(SignInInfo.newInstance(
                signInUsername.getText().toString(),
                signInPass.getText().toString()
        ));
    }

    @OnClick(R.id.auth_signup_btn)
    public void onSignUnClick(){
        if(!App.getInstance().isOnline()){
            DialogUtils.showOfflineDialog(this);
            return;
        }

        presenter.onSignUpClick(SignUpInfo.newInstance(
                signUpUsername.getText().toString(),
                signUpPass1.getText().toString(),
                signUpPass2.getText().toString()
        ));
    }

    @Override
    public void showLoading() {
        progress.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoading() {
        progress.setVisibility(View.GONE);
    }

    @Override
    public void showSignUpView(AuthInfo authInfo) {
        progress.setVisibility(View.GONE);
        signUpLayout.setVisibility(View.VISIBLE);

        signUpUsername.setText(authInfo.getUsername());
        signUpHint.setText(authInfo.getHint());
    }

    @Override
    public void showSignInView() {
        toolbar.setTitle(R.string.signin_title);

        new FlipHorizontalToAnimation(signUpLayout)
                .setFlipToView(signInLayout)
                .setInterpolator(new LinearInterpolator())
                .animate();
    }

    @Override
    public void showPasswordsDontMatchError() {
        signUpPass1.setText("");
        signUpPass2.setText("");

        new ShakeAnimation(signUpLayout)
                .setNumOfShakes(3)
                .setDuration(Animation.DURATION_SHORT)
                .animate();

        Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        v.vibrate(200);
    }

    @Override
    public void showAuthError(String message) {
        DialogUtils.showAlertDialog(this, message);
    }

    @Override
    public void showUnknownError(boolean finishActivity) {
        DialogUtils.showSomethingWentWrong(this, true);
    }

    @Override
    public void onLoginSuccess() {
        setResult(RESULT_OK);
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.onDestroy();
    }

    @NonNull
    @Override
    public AuthPresenter createPresenter() {
        return new AuthPresenter();
    }
}
