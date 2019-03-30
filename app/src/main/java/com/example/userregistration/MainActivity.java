package com.example.userregistration;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {

    //Define the components
    EditText username, password, phoneNumber;
    RadioGroup genderRG;
    Button signInBtn, resetBtn;

    //accepted data type
    String nameStr, pwdStr, phoneStr, genderStr;
    boolean btnFlag = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //initialize components
        username = findViewById(R.id.txtName);
        password = findViewById(R.id.txtPwd);
        phoneNumber = findViewById(R.id.txtPhone);
        genderRG = findViewById(R.id.rgGender);
        signInBtn = findViewById(R.id.btnSignIn);
        resetBtn = findViewById(R.id.btnReset);
        Log.i("tag", "initializing...");

        //when click sign in button
        signInBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                //get values
                getValues();

                //check whether button is selected or not
                for (int i = 0; i < genderRG.getChildCount(); i++) {
                    RadioButton selectedBtn = (RadioButton) genderRG.getChildAt(i);
                    if (selectedBtn.isChecked()) {
                        genderStr = selectedBtn.getText().toString();
                        Log.i("tag", genderStr);
                        btnFlag = true;
                    }
                }

                //if the values are not complete or illegal, show the warning
                if (btnFlag == false || checkValues() == false) {
                    Toast.makeText(MainActivity.this,
                            "incomplete or illegal information !",
                            Toast.LENGTH_LONG).show();
                } else {
                    showNotification();
                }
            }
        });

        //when click reset button
        resetBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                username.setText("");
                password.setText("");
                phoneNumber.setText("");
                genderRG.clearCheck();
                Log.i("debug", "clear message..");
            }
        });
    }

    public void getValues() {
        nameStr = username.getText().toString().trim();
        Log.i("tag", nameStr);
        pwdStr = password.getText().toString().trim();
        Log.i("tag", pwdStr);
        phoneStr = phoneNumber.getText().toString().trim();
        Log.i("tag", phoneStr);
    }

    public boolean checkValues() {
        //values are empty or phone number is illegal
        if (nameStr == "" || pwdStr == "" || phoneStr == "" || checkPhone(phoneStr) == false)
            return false;
        return true;
    }

    public static boolean checkPhone(String mobileNums) {
        /**
         * 判断字符串是否符合手机号码格式
         * 移动号段: 134,135,136,137,138,139,147,150,151,152,157,158,159,170,178,182,183,184,187,188
         * 联通号段: 130,131,132,145,155,156,170,171,175,176,185,186
         * 电信号段: 133,149,153,170,173,177,180,181,189
         * @param str
         * @return the detected string
         */
        String telRegex = "^((13[0-9])|(14[5,7,9])|(15[^4])|(18[0-9])|(17[0,1,3,5,6,7,8]))\\d{8}$";
        // "[1]"代表下一位为数字可以是几，"[0-9]"代表可以为0-9中的一个，"[5,7,9]"表示可以是5,7,9中的任意一位,[^4]表示除4以外的任何一个,
        // d{9}"代表后面是可以是0～9的数字，有9位。
        if (TextUtils.isEmpty(mobileNums)) {
            return false;
        } else
            return mobileNums.matches(telRegex);
    }

    public void showNotification() {
        Toast.makeText(MainActivity.this,
                "log in successfully! info : " + nameStr + "    " + genderStr + "    " + phoneStr,
                Toast.LENGTH_LONG).show();
    }

}
