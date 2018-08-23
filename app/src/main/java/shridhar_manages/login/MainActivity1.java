package shridhar_manages.login;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity1 extends Activity {
    Button btnSignIn, btnSignUp;
    LoginDataBaseAdapter loginDataBaseAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main1);

        loginDataBaseAdapter = new LoginDataBaseAdapter(this);
        loginDataBaseAdapter = loginDataBaseAdapter.open();

        btnSignIn = (Button) findViewById(R.id.buttonSignIN);
        btnSignUp = (Button) findViewById(R.id.buttonSignUP);
    }

    public void signIn(View V) {
        final Dialog dialog = new Dialog(MainActivity1.this);
        dialog.setContentView(R.layout.activity_login);
        dialog.setTitle("Login");
        final EditText editTextUserName = (EditText) dialog
                .findViewById(R.id.editTextUserNameToLogin);
        final EditText editTextPassword = (EditText) dialog
                .findViewById(R.id.editTextPasswordToLogin);

        Button btnSignIn = (Button) dialog.findViewById(R.id.buttonSignIn);

        btnSignIn.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {

                String userName = editTextUserName.getText().toString();
                String password = editTextPassword.getText().toString();
                String storedPassword = loginDataBaseAdapter
                        .getSinlgeEntry(userName);

                if (password.equals(storedPassword)) {
                    Toast.makeText(MainActivity1.this,
                            "Congrats: Login Successfull", Toast.LENGTH_LONG)
                            .show();
                    dialog.dismiss();
                    welcome(v);
                } else {
                    Toast.makeText(MainActivity1.this,
                            "User Name or Password does not match",
                            Toast.LENGTH_LONG).show();
                }
            }
        });

        dialog.show();
    }
    public void signUp(View v){
        final Dialog dialog= new Dialog(MainActivity1.this);
        dialog.setContentView(R.layout.activity_signup);
        dialog.setTitle("SignUp");
        final EditText editTextUserName = (EditText) dialog.findViewById(R.id.editTextUserName);
        final EditText editTextPassword = (EditText) dialog.findViewById(R.id.editTextPassword);
        final EditText editTextConfirmPassword = (EditText) dialog.findViewById(R.id.editTextConfirmPassword);

        Button btnCreateAccount = (Button) dialog.findViewById(R.id.buttonCreateAccount);
        btnCreateAccount.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {

                String expression = "^(?=.*[A-Za-z])(?=.*\\\\d)(?=.*[$@$!%*#?&])[A-Za-z\\\\d$@$!%*#?&]{8,}$";
                String userName = editTextUserName.getText().toString();
                String password = editTextPassword.getText().toString();
                String confirmPassword = editTextConfirmPassword.getText()
                        .toString();
                if (userName.equals("") || password.equals("")
                        || confirmPassword.equals("")) {

                    Toast.makeText(MainActivity1.this, "Field Vaccant",
                            Toast.LENGTH_LONG).show();
                    return;
                }
                if(userName.length()<3){
                    Toast.makeText(MainActivity1.this,
                            " UserName should be of min 3 char ",
                            Toast.LENGTH_LONG).show();
                    return;
                }
                if(password.length()<8){
                    Toast.makeText(MainActivity1.this,
                            " Password should be of min 8 char ",
                            Toast.LENGTH_LONG).show();
                    return;
                }
                if (!password.equals(confirmPassword)) {
                    Toast.makeText(MainActivity1.this,
                            "Password does not match", Toast.LENGTH_LONG)
                            .show();
                    return;
                } else {

                    loginDataBaseAdapter.insertEntry(userName, password);
                    Toast.makeText(MainActivity1.this,
                            "Account Successfully Created ", Toast.LENGTH_LONG)
                            .show();

                }
            }
        });
        dialog.show();
    }
    public void welcome(View v){

        Intent intent1= new Intent(MainActivity1.this,Main2Activity.class);
        startActivity(intent1);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        loginDataBaseAdapter.close();
    }
}