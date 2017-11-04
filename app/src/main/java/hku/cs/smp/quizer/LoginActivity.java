package hku.cs.smp.quizer;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import hku.cs.smp.quizer.quiz.QuizActivity;

import java.util.regex.Pattern;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends AppCompatActivity {

    /**
     * Id to identity READ_CONTACTS permission request.
     */
    private static final int REQUEST_READ_CONTACTS = 0;

    /**
     * Keep track of the login task to ensure we can cancel it if requested.
     */

    // UI references.
    private EditText nameView;
    private EditText universityNumberView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        // Set up the login form.
        nameView = (EditText) findViewById(R.id.name);

        universityNumberView = (EditText) findViewById(R.id.university_number);
        universityNumberView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == R.id.start_quiz || id == EditorInfo.IME_NULL) {
                    startQuiz();
                    return true;
                }
                return false;
            }
        });

        Button startQuiz = (Button) findViewById(R.id.button_start_quiz);
        startQuiz.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                startQuiz();
            }
        });

    }

    /**
     * Attempts to sign in or register the account specified by the login form.
     * If there are form errors (invalid email, missing fields, etc.), the
     * errors are presented and no actual login attempt is made.
     */
    private void startQuiz() {

        // Reset errors.
        nameView.setError(null);
        universityNumberView.setError(null);

        // Store values at the time of the login attempt.
        String name = nameView.getText().toString();
        String universityNumber = universityNumberView.getText().toString();

        boolean cancel = false;
        View focusView = null;

        // Check for a valid password, if the user entered one.
        if (TextUtils.isEmpty(universityNumber) || !isNumberValid(universityNumber)) {
            universityNumberView.setError(getString(R.string.error_invalid_number));
            focusView = universityNumberView;
            cancel = true;
        }

        // Check for a valid email address.
        if (TextUtils.isEmpty(name)) {
            nameView.setError(getString(R.string.error_field_required));
            focusView = nameView;
            cancel = true;
        } else if (!isNameValid(name)) {
            nameView.setError(getString(R.string.error_invalid_name));
            focusView = nameView;
            cancel = true;
        }

        if (cancel) {
            focusView.requestFocus();
        } else {
            startActivity(new Intent(LoginActivity.this, QuizActivity.class));
            finish();
        }
    }

    private boolean isNameValid(String name) {
        //TODO: Replace this with your own logic
        Pattern pattern = Pattern.compile("^[a-zA-Z0-9\\s]+$");
        return pattern.matcher(name).matches();
    }

    private boolean isNumberValid(String number) {
        return TextUtils.isDigitsOnly(number);
    }


}

