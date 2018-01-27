package com.baoliang.goods.Login;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.SpannedString;
import android.text.style.AbsoluteSizeSpan;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.baoliang.goods.R;
import com.baoliang.goods.Register.Register;
import com.baoliang.goods.Tools.DisplayUtils;

public class Login extends AppCompatActivity implements View.OnClickListener{

    private EditText input_Name;
    private EditText input_Pass;
    private Button   loginBtn;
    private TextView bar;
    private Button   registerBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        if(getSupportActionBar()!=null)
        {
            getSupportActionBar().hide();
        }

        setInput();
    }



    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.loginbtn)
        {

            setActionForLogin();
        }

    }

        protected void setActionForLogin()
    {

        Intent intent=new Intent(Login.this,Register.class);
        startActivity(intent);
    }

    protected void setActionForRegister()
    {

    }




    /**
     * 设置输入框与标签
     */
    protected void setInput()
    {

        AbsoluteSizeSpan abspan=new AbsoluteSizeSpan(10,true);



        this.bar=(TextView)findViewById(R.id.bar);
        this.bar.setLines(25);
        this.bar.setBackgroundColor(Color.BLUE);

        //姓名输入框
        SpannableString namestr =  new SpannableString("请输入电话");
        this.input_Name = (EditText)findViewById(R.id.nameinput);
        namestr.setSpan(abspan, 0, namestr.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        this.input_Name.setHintTextColor(Color.GRAY);
        input_Name.setHint(new SpannedString(namestr));


        //密码输入框
        SpannableString passstr=  new SpannableString("请输入密码");
        this.input_Pass = (EditText)findViewById(R.id.inputpass);
        passstr.setSpan(abspan, 0, passstr.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        this.input_Pass.setHintTextColor(Color.GRAY);
        input_Pass.setHint(new SpannedString(passstr));



        //设置按钮
        this.loginBtn= (Button)findViewById(R.id.loginbtn);
        RelativeLayout.LayoutParams btnlayout = (RelativeLayout.LayoutParams) this.loginBtn.getLayoutParams();
        Context cxt= getApplicationContext();
        int sreenW=DisplayUtils.getSreenWidth(cxt);
        float btnw= (float) (sreenW*0.4);
        float btnmarginleft= (float) ((sreenW-btnw)*0.5);
        btnlayout.setMargins((int)btnmarginleft,100,(int)btnmarginleft,0);
        this.loginBtn.setLayoutParams(btnlayout);
        this.loginBtn.setOnClickListener(this);


        this.registerBtn=(Button)findViewById(R.id.register);
        RelativeLayout.LayoutParams reglayout=(RelativeLayout.LayoutParams)this.registerBtn.getLayoutParams();
        reglayout.setMargins((int)btnmarginleft,100,(int)btnmarginleft,0);
        this.registerBtn.setLayoutParams(reglayout);
    }




    /**
     * 点击空白区域隐藏键盘.
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            if (Login.this.getCurrentFocus() != null) {
                if (Login.this.getCurrentFocus().getWindowToken() != null) {
                    imm.hideSoftInputFromWindow(Login.this.getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                }
            }
        }
        return super.onTouchEvent(event);
    }


    /**隐藏键盘 */
    protected void hideInputKeyboard(View v) {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
    }

    /**弹起键盘 */
    protected void showInputKeyboard(View v) {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(v, 0);
    }

}
