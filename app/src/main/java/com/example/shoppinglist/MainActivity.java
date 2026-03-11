package com.example.shoppinglist;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private EditText editName, editAmount;
    private ListView listView;
    private Button btnAdd, btnSettings;

    private ArrayList<String> shoppingList;
    private ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editName = findViewById(R.id.edit_item_name);
        editAmount = findViewById(R.id.edit_item_amount);
        listView = findViewById(R.id.list_view_shopping);
        btnAdd = findViewById(R.id.btn_add_item);
        btnSettings = findViewById(R.id.btn_go_to_settings);

        LinearLayout mainLayout = findViewById(R.id.main_layout);
        TextView welcomeMsg = new TextView(this);
        welcomeMsg.setText("Welcome!");
        welcomeMsg.setTextSize(18);
        welcomeMsg.setPadding(0, 0, 0, 20);
        mainLayout.addView(welcomeMsg, 0);

        shoppingList = new ArrayList<>();
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, shoppingList);
        listView.setAdapter(adapter);
    }
}