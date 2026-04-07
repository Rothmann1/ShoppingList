package com.example.shoppinglist;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

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
        welcomeMsg.setText("ברוכים הבאים לאיזישופ!");
        welcomeMsg.setTextSize(18);
        welcomeMsg.setPadding(0, 0, 0, 20);
        mainLayout.addView(welcomeMsg, 0);

        shoppingList = new ArrayList<>();
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, shoppingList);
        listView.setAdapter(adapter);

        loadData();

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = editName.getText().toString().trim();
                String amountStr = editAmount.getText().toString().trim();

                if (name.isEmpty()) {
                    Toast.makeText(MainActivity.this, "נא להזין שם", Toast.LENGTH_SHORT).show();
                    return;
                }

                int amountToAdd = amountStr.isEmpty() ? 1 : Integer.parseInt(amountStr);
                int existingIndex = -1;

                for (int i = 0; i < shoppingList.size(); i++) {
                    if (shoppingList.get(i).toLowerCase().startsWith(name.toLowerCase() + " (")) {
                        existingIndex = i;
                        break;
                    }
                }

                if (existingIndex != -1) {
                    String existingText = shoppingList.get(existingIndex);
                    int startIndex = existingText.lastIndexOf("(") + 1;
                    int endIndex = existingText.lastIndexOf(")");
                    int currentAmount = Integer.parseInt(existingText.substring(startIndex, endIndex));

                    int newTotal = currentAmount + amountToAdd;
                    shoppingList.set(existingIndex, name + " (" + newTotal + ")");
                } else {
                    shoppingList.add(name + " (" + amountToAdd + ")");
                }

                adapter.notifyDataSetChanged();
                saveData();

                editName.setText("");
                editAmount.setText("");
            }
        });

        btnSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SettingsActivity.class);
                intent.putExtra("listSize", shoppingList.size());
                startActivity(intent);
            }
        });
    }

    private void saveData() {
        SharedPreferences sharedPreferences = getSharedPreferences("EasyShopPrefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        StringBuilder sb = new StringBuilder();
        for (String s : shoppingList) {
            sb.append(s).append(",");
        }
        editor.putString("shopping_list", sb.toString());
        editor.apply();
    }

    private void loadData() {
        SharedPreferences sharedPreferences = getSharedPreferences("EasyShopPrefs", MODE_PRIVATE);
        String savedString = sharedPreferences.getString("shopping_list", "");
        if (!savedString.isEmpty()) {
            String[] items = savedString.split(",");
            shoppingList.clear();
            for (String item : items) {
                if (!item.isEmpty()) {
                    shoppingList.add(item);
                }
            }
            adapter.notifyDataSetChanged();
        }
    }
}