package com.baoliang.goods.MainPage;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;

import com.baoliang.goods.R;



public class GetApInfo extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.apinfo_activity);
        Intent intent = getIntent();
        EditText acnum= findViewById(R.id.content3input_acnum);
        acnum.setText(intent.getStringExtra("acnum").toString());
        EditText boss=findViewById(R.id.content3input_boss);
        boss.setText(intent.getStringExtra("boss").toString());
        EditText phone=findViewById(R.id.content3input_phone);
        phone.setText(intent.getStringExtra("phone").toString());
        EditText goods =findViewById(R.id.content3input_goods);
        goods.setText(intent.getStringExtra("goods").toString());
        EditText start=findViewById(R.id.content3input_start);
        start.setText(intent.getStringExtra("start").toString());
        EditText destination = findViewById(R.id.content3input_destination);
        destination.setText(intent.getStringExtra("destination").toString());
        EditText weight=findViewById(R.id.content3input_weight);
        weight.setText(intent.getStringExtra("weight").toString());
        EditText receiver=findViewById(R.id.content3input_receiver);
        receiver.setText(intent.getStringExtra("receiver").toString());
        EditText receivephone=findViewById(R.id.content3input_receivephone);
        receivephone.setText(intent.getStringExtra("recephone").toString());

    }


    }


