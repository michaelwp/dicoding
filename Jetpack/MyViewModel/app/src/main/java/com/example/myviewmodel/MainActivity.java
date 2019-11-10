package com.example.myviewmodel;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

public class MainActivity extends AppCompatActivity {

    private EditText edtWidth, edtheight, edtLength;
    private TextView tvResult;
    private MainViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        viewModel = ViewModelProviders.of(this).get(MainViewModel.class);

        edtWidth = findViewById(R.id.edt_width);
        edtheight = findViewById(R.id.edt_height);
        edtLength = findViewById(R.id.edt_length);
        tvResult = findViewById(R.id.tv_result);

        displayResult();

        findViewById(R.id.btn_calculate).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String width = edtWidth.getText().toString();
                String height = edtheight.getText().toString();
                String length = edtLength.getText().toString();

                String errMsg = "Wajib di isi !";

                if (width.isEmpty()) {
                    edtWidth.setError(errMsg);
                    return;
                }

                if (height.isEmpty()) {
                    edtheight.setError(errMsg);
                    return;
                }

                if (length.isEmpty()) {
                    edtLength.setError(errMsg);
                    return;
                }

                viewModel.calculate(width, height, length);
                displayResult();
            }
        });
    }

    private void displayResult() {
        tvResult.setText(String.valueOf(viewModel.result));
    }
}
