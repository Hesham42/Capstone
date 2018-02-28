package com.example.hesham.moves;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.percent.PercentLayoutHelper;
import android.support.percent.PercentRelativeLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatTextView;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hesham.moves.Utilities.InputValidation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignInAndSignUp extends AppCompatActivity {
    private static final String TAG = "Guinness";
    //---------------chatLogin----------------------------
    private DatabaseReference mDatabase;
    private FirebaseAuth mAuth;
    private DatabaseReference mUserDatabase;
//------------------------------------------------
//    -------------------------------------
//

    //    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;


//  ----------------------------------------


    private TextView tvSignupInvoker;
    private TextView tvSigninInvoker;

    private LinearLayout llSignup;
    private LinearLayout llSignin;

    private Button btnSignup;
    private Button btnSignin;
    RelativeLayout relativeLayout;

    private TextInputLayout textInputLayoutEmail;
    private TextInputLayout textInputLayoutPassword;

    private TextInputEditText textInputEditTextEmail;
    private TextInputEditText textInputEditTextPassword;
    //    -------------------------------------------------------------------
    private TextInputLayout textInputLayoutEmailR;
    private TextInputLayout textInputLayoutPasswordR;
    private TextInputLayout textInputLayoutConfirmPasswordR;

    private TextInputEditText textInputEditTextEmailR;
    private TextInputEditText textInputEditTextPasswordR;
    private TextInputEditText textInputEditTextConfirmPasswordR;
    private TextInputLayout textInputLayoutName;
    private TextInputEditText textInputEditTextName;
    //---------------------------------------------------

    private AppCompatTextView textViewLinkRegister;
    private ProgressDialog mRegProgress;


    private InputValidation inputValidation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in_out);
        mAuth = FirebaseAuth.getInstance();
        mRegProgress = new ProgressDialog(this);
        mUserDatabase = FirebaseDatabase.getInstance().getReference().child("Users");


        initViews();
        initListeners();

    }


    private void initViews() {
        relativeLayout = findViewById(R.id.action_main);
        inputValidation = new InputValidation(this);
        tvSigninInvoker = (TextView) findViewById(R.id.tvSigninInvoker);
        tvSignupInvoker = (TextView) findViewById(R.id.tvSignupInvoker);

        btnSignin = (Button) findViewById(R.id.btnSignin);
        btnSignup = (Button) findViewById(R.id.btnSignup);

        llSignin = (LinearLayout) findViewById(R.id.llSignin);
        llSignup = (LinearLayout) findViewById(R.id.llSignup);
//-------------------------------SIgnIn------------------------------------------
        textInputLayoutEmail = findViewById(R.id.TextInputLayoutEmail);
        textInputLayoutPassword = findViewById(R.id.TextInputLayoutPassword);

        textInputEditTextEmail = findViewById(R.id.TextInputEditTextEmail);
        textInputEditTextPassword = findViewById(R.id.TextInputEditTextPassword);
//_________________________________SignUp___________________________________________

        textInputLayoutName = (TextInputLayout) findViewById(R.id.textInputLayoutName);
        textInputEditTextName = (TextInputEditText) findViewById(R.id.textInputEditTextName);

        textInputLayoutEmailR = findViewById(R.id.TextInputLayoutEmailR);
        textInputEditTextEmailR = findViewById(R.id.TextInputEditTextEmailR);

        textInputLayoutPasswordR = findViewById(R.id.TextInputLayoutPasswordR);
        textInputEditTextPasswordR = findViewById(R.id.textInputEditTextPasswrodR);

        textInputLayoutConfirmPasswordR = findViewById(R.id.textInputLayoutConfirmPasswordR);
        textInputEditTextConfirmPasswordR = findViewById(R.id.textInputEditTextConfirmPasswordR);
//________________________________________________________________________


    }

    private void initListeners() {
        tvSigninInvoker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showSigninForm();
            }
        });

        tvSignupInvoker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showSignupForm();
            }
        });
        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Animation clockwise = AnimationUtils.loadAnimation(getApplicationContext(),
                        R.anim.rotate_right_to_left);
                btnSignup.startAnimation(clockwise);
                postDataToSQLite();

            }
        });

        btnSignin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                verifyFromSQLite();

            }
        });
    }

    private void postDataToSQLite() {
        if (!inputValidation.isInputEditTextFilled(textInputEditTextName, textInputLayoutName, getString(R.string.error_message_name))) {
            return;
        }
        if (!inputValidation.isInputEditTextFilled(textInputEditTextEmailR, textInputLayoutEmailR, getString(R.string.error_message_email))) {
            return;
        }
        if (!inputValidation.isInputEditTextEmail(textInputEditTextEmailR, textInputLayoutEmailR, getString(R.string.error_message_email))) {
            return;
        }

        if (!inputValidation.isInputEditTextFilled(textInputEditTextPasswordR, textInputLayoutPasswordR,
                getString(R.string.error_message_password))) {
            return;
        }
        if (!inputValidation.isInputEditTextFilledPasswordLength(textInputEditTextPasswordR,
                textInputLayoutPasswordR,
                getString(R.string.error_message_password))) {
            return;
        }
        if (!inputValidation.isInputEditTextMatches(textInputEditTextPasswordR, textInputEditTextConfirmPasswordR,
                textInputLayoutConfirmPasswordR, getString(R.string.error_password_match))) {
            return;
        }

        mRegProgress.setTitle("Registering User");
        mRegProgress.setMessage("Please wait while we create your account !");
        mRegProgress.setCanceledOnTouchOutside(false);
        mRegProgress.show();

        register_user(
                textInputEditTextName.getText().toString().trim(),
                textInputEditTextEmailR.getText().toString().trim(),
                textInputEditTextPasswordR.getText().toString().trim());
        emptyInputEditTextR();


    }

    private void emptyInputEditTextR() {
        textInputEditTextName.setText(null);
        textInputEditTextEmailR.setText(null);
        textInputEditTextPasswordR.setText(null);
        textInputEditTextConfirmPasswordR.setText(null);
    }

    private void verifyFromSQLite() {
        if (!inputValidation.isInputEditTextFilled(textInputEditTextEmail,
                textInputLayoutEmail, getString(R.string.error_message_email))) {
            return;
        }
        if (!inputValidation.isInputEditTextEmail(textInputEditTextEmail, textInputLayoutEmail, getString(R.string.error_message_email))) {
            return;
        }
        if (!inputValidation.isInputEditTextFilled(textInputEditTextPassword, textInputLayoutPassword, getString(R.string.error_message_email))) {
            return;
        }

        mRegProgress.setTitle("Logging In");
        mRegProgress.setMessage("Please wait while we check your credentials.");
        mRegProgress.setCanceledOnTouchOutside(false);
        mRegProgress.show();

        loginUser(
                textInputEditTextEmail.getText().toString().trim(),
                textInputEditTextPassword.getText().toString().trim());
        emptyInputEditText();
    }

    /**
     * This method is to empty all input edit text
     */
    private void emptyInputEditText() {
        textInputEditTextEmail.setText(null);
        textInputEditTextPassword.setText(null);
    }


    private void showSignupForm() {
        PercentRelativeLayout.LayoutParams paramsLogin = (PercentRelativeLayout.LayoutParams) llSignin.getLayoutParams();
        PercentLayoutHelper.PercentLayoutInfo infoLogin = paramsLogin.getPercentLayoutInfo();
        infoLogin.widthPercent = 0.15f;
        llSignin.requestLayout();


        PercentRelativeLayout.LayoutParams paramsSignup = (PercentRelativeLayout.LayoutParams) llSignup.getLayoutParams();
        PercentLayoutHelper.PercentLayoutInfo infoSignup = paramsSignup.getPercentLayoutInfo();
        infoSignup.widthPercent = 0.85f;
        llSignup.requestLayout();

        tvSignupInvoker.setVisibility(View.GONE);
        tvSigninInvoker.setVisibility(View.VISIBLE);
        Animation translate = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.translate_right_to_left);
        llSignup.startAnimation(translate);

        Animation clockwise = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.rotate_right_to_left);
        btnSignup.startAnimation(clockwise);

    }

    private void showSigninForm() {
        PercentRelativeLayout.LayoutParams paramsLogin = (PercentRelativeLayout.LayoutParams) llSignin.getLayoutParams();
        PercentLayoutHelper.PercentLayoutInfo infoLogin = paramsLogin.getPercentLayoutInfo();
        infoLogin.widthPercent = 0.85f;
        llSignin.requestLayout();


        PercentRelativeLayout.LayoutParams paramsSignup = (PercentRelativeLayout.LayoutParams) llSignup.getLayoutParams();
        PercentLayoutHelper.PercentLayoutInfo infoSignup = paramsSignup.getPercentLayoutInfo();
        infoSignup.widthPercent = 0.15f;
        llSignup.requestLayout();

        Animation translate = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.translate_left_to_right);
        llSignin.startAnimation(translate);

        tvSignupInvoker.setVisibility(View.VISIBLE);
        tvSigninInvoker.setVisibility(View.GONE);
        Animation clockwise = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.rotate_left_to_right);
        btnSignin.startAnimation(clockwise);
    }


    private void register_user(final String display_name, String email, String password) {

        String myemail = email;
        String pass = password;
        String myname = display_name;
        mAuth.createUserWithEmailAndPassword(myemail, pass)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {

                            Log.d(TAG, "createUserWithEmail:success");
                            Toast.makeText(SignInAndSignUp.this, R.string.success_message, Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(SignInAndSignUp.this, MainActivity.class);
                            startActivity(intent);


                        } else {

                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            Toast.makeText(SignInAndSignUp.this, R.string.error_email_exists,
                                    Toast.LENGTH_SHORT).show();

                        }

                    }
                });
    }

    private void loginUser(String email, String password) {
        String emaitext = email;
        String passwordtext = password;
        mAuth.signInWithEmailAndPassword(emaitext, passwordtext)
                .addOnCompleteListener(SignInAndSignUp.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {

                            Log.d(TAG, "createUserWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            Toast.makeText(SignInAndSignUp.this, R.string.success_message, Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(SignInAndSignUp.this, MainActivity.class);
                            startActivity(intent);

                        } else {

                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            Toast.makeText(SignInAndSignUp.this, R.string.error_email_exists,
                                    Toast.LENGTH_SHORT).show();

                        }
                    }
                });

    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
    }

}
