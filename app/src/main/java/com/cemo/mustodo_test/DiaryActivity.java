package com.cemo.mustodo_test;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.cemo.mustodo_test.api.RetrofitClient;
import com.cemo.mustodo_test.api.diary.DiaryRequest;
import com.cemo.mustodo_test.api.diary.DiaryServiceInterface;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DiaryActivity extends AppCompatActivity {

    public static final int SELECT_ALBUM = 1;
    private ImageView diaryImage;
    private ImageView backArrow2;
    private Button uploadBtn;
    private Button openBtn;
    private Button diaryCreateBtn;

    private EditText diaryTitle;
    private EditText diaryContent;

    private TextView sheetTitle;
    static BottomSheetDialog bottomSheetDialog;
    private ArrayList<String> openTextList = new ArrayList<String>();

    private DiaryServiceInterface diaryService;

    private Uri selectedImageUri;

    String userNick, userEmail, mode, selColor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diary);

        userNick = getIntent().getStringExtra("userNick");
        userEmail = getIntent().getStringExtra("userEmail");
        mode = getIntent().getStringExtra("mode");

        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        getWindow().setStatusBarColor(Color.parseColor("#ffffff"));

        diaryService = RetrofitClient.getClient().create(DiaryServiceInterface.class);

        diaryTitle = findViewById(R.id.wr_diary_title);
        diaryContent = findViewById(R.id.wr_diary_contents);
        initBackArrow();
        initUploadBtn();
        initOpenBtn();

        diaryCreateBtn = findViewById(R.id.diaryCreateBtn);
        diaryCreateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String title = diaryTitle.getText().toString();
                String content = diaryContent.getText().toString();

                if(title.isEmpty()){
                    Toast.makeText(getApplicationContext(), "제목을 작성해주세요!", Toast.LENGTH_SHORT).show();
                }else if(content.isEmpty()){
                    Toast.makeText(getApplicationContext(), "일기를 작성해주세요!", Toast.LENGTH_SHORT).show();
                }else{
                    boolean isOpen = openBtn.getText().toString().equals("전체 공개");
                    DiaryRequest body = new DiaryRequest(title, content, isOpen);

                    diaryService.createDiary(userNick, body).enqueue(new Callback<Object>() {
                        @Override
                        public void onResponse(Call<Object> call, Response<Object> response) {
                            if(response.isSuccessful()) {
                                Toast.makeText(getApplicationContext(), "생성 성공", Toast.LENGTH_SHORT).show();

                                Intent intent = new Intent(DiaryActivity.this, MainActivity.class);

                                intent.putExtra("userNick", userNick);
                                intent.putExtra("email", userEmail);
                                intent.putExtra("mode", mode);
                                startActivity(intent);

                                finish();
                            } else {
                                Toast.makeText(getApplicationContext(), "생성 실패", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<Object> call, Throwable t) {

                        }
                    });
                }
            }
        });
    }

    private void initUploadBtn() {
        diaryImage = findViewById(R.id.diaryImage);
        uploadBtn = findViewById(R.id.uploadImageBtn);
        uploadBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_PICK);

                intent.setType(MediaStore.Images.Media.CONTENT_TYPE);
                startActivityForResult(intent, SELECT_ALBUM);
            }
        });
    }

    private void initOpenBtn() {
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.bottom_sheet, null, false);
        bottomSheetDialog = new BottomSheetDialog(this);

        bottomSheetDialog.setContentView(view);

        sheetTitle = view.findViewById(R.id.sheet_title);
        LinearLayout bottom_sheet_btn_group = view.findViewById(R.id.bottom_sheet_btn_group);
        LinearLayout bottom_sheet_btn = view.findViewById(R.id.bottom_sheet_btn);

        openBtn = findViewById(R.id.sel_open_btn);
        openBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sheetTitle.setText("공개 설정");

                bottomSheetDialog.setContentView(view);

                openTextList.clear();

                openTextList.add("나만 보기");
                openTextList.add("전체 공개");

                final String[] selecteText1 = new String[1];

                ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(),android.R.layout.simple_list_item_single_choice, openTextList);

                ListView lvSheet = view.findViewById(R.id.bt_sheed_lv);
                lvSheet.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
                lvSheet.setAdapter(adapter);

                lvSheet.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    // 콜백매개변수는 순서대로 어댑터뷰, 해당 아이템의 뷰, 클릭한 순번, 항목의 아이디
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        selecteText1[0] = openTextList.get(i).toString();
                    }
                });

                Button btnOK, btnClose;

                bottom_sheet_btn.setVisibility(View.GONE);
                bottom_sheet_btn_group.setVisibility(View.VISIBLE);

                btnOK = view.findViewById(R.id.btnOk);
                btnOK.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        openBtn.setText(selecteText1[0]);

                        bottomSheetDialog.dismiss();
                    }
                });

                btnClose = view.findViewById(R.id.btnCancel);
                btnClose.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        bottomSheetDialog.dismiss();
                    }
                });


                bottomSheetDialog.show();
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {

            // compare the resultCode with the
            // SELECT_PICTURE constant
            if (requestCode == SELECT_ALBUM) {
                // Get the url of the image from data
                selectedImageUri = data.getData();
                if (null != selectedImageUri) {
                    // update the preview image in the layout
                    diaryImage.setImageURI(selectedImageUri);
                }
            }
        }
    }



    private void initBackArrow() {
        backArrow2 = findViewById(R.id.backArrow2);

        backArrow2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DiaryActivity.this, MainActivity.class);

                startActivity(intent);

                finish();
            }
        });
    }
}