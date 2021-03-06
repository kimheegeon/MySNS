package com.example.gmlrj.mysns;

import android.app.AlertDialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

public class EditActivity extends AppCompatActivity {

    private Uri imageUri;
    private ImageView iv_image;
    private Bitmap image_bitmap;
    private static final int PICK_FROM_CAMERA = 1;
    private static final int PICK_FROM_GALLERY = 2;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_edit, menu);
        return true;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        final EditText et_title = (EditText) findViewById(R.id.et_title);
        iv_image = (ImageView)findViewById(R.id.iv_photo);
        final EditText et_text = (EditText) findViewById(R.id.et_text);

        Button bt_submit = (Button) findViewById(R.id.bt_submit);
        ImageButton bt_photo = (ImageButton) findViewById(R.id.bt_photo);

        bt_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String title = et_title.getText().toString();
                String text = et_text.getText().toString();

                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success = jsonResponse.getBoolean("success");
                            if (success) {
                                ProfileFragment fragment = new ProfileFragment();
                                Bundle bundle = new Bundle();
                                bundle.putString("title",String.valueOf(et_title.toString()));
                                fragment.setArguments(bundle);
                                finish();
                            } else {
                                android.app.AlertDialog.Builder builer = new android.app.AlertDialog.Builder(EditActivity.this);
                                builer.setMessage("글쓰기에 실패하였습니다.")
                                        .setNegativeButton("다시 시도", null)
                                        .create()
                                        .show();
                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                };
                EditRequest editRequest = new EditRequest(title, text, responseListener);
                RequestQueue queue = Volley.newRequestQueue(EditActivity.this);
                queue.add(editRequest);
            }
        });
        bt_photo.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                SelectPic(view);
            }
        });
    }

    public void SelectPic(View view) {
        final CharSequence[] items = {"카메라", "앨범"};
        android.app.AlertDialog.Builder alertDialogBuilder = new android.app.AlertDialog.Builder(
                this);

        // 제목셋팅
        alertDialogBuilder.setTitle("선택 목록 대화 상자");
        alertDialogBuilder.setItems(items,
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,
                                        int id) {
                        switch (id) {
                            case 0:
                                int permissionCheck = ContextCompat.checkSelfPermission(EditActivity.this, android.Manifest.permission.CAMERA);
                                if (permissionCheck == getPackageManager().PERMISSION_DENIED) {
                                    ActivityCompat.requestPermissions(EditActivity.this, new String[]{android.Manifest.permission.CAMERA}, 0);
                                }

                                File photo = new File(Environment.getExternalStorageDirectory(),".camera.jpg");
                                imageUri = Uri.fromFile(photo);

                                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                                //    intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
                                startActivityForResult(intent, 0);
                                break;

                            case 1:
                                // 앨범 호출
                                intent = new Intent(Intent.ACTION_PICK);
                                intent.setData(android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                                intent.setType(android.provider.MediaStore.Images.Media.CONTENT_TYPE);
                                startActivityForResult(Intent.createChooser(intent, "Select Picture"), 1);
                                break;
                        }
                        dialog.dismiss();
                    }
                });
        // 다이얼로그 생성
        android.app.AlertDialog alertDialog = alertDialogBuilder.create();

        // 다이얼로그 보여주기
        alertDialog.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {


            switch (requestCode) {

                case 0:
                    if(resultCode!=0){

                        if(requestCode==1&&!data.equals(null)){

                            try{
                                image_bitmap = (Bitmap)data.getExtras().get("data");


                                String imagePath = imageUri.getPath();
                                ExifInterface exif = new ExifInterface(imagePath);
                                int exifOrientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);
                                int exifDegree = exifOrientationToDegrees(exifOrientation);
                                image_bitmap = rotate(image_bitmap, exifDegree);


                                iv_image.setImageBitmap(image_bitmap);
                                iv_image.setScaleType(ImageView.ScaleType.FIT_XY);
                            } catch(Exception e){
                                return;
                            }
                        }
                    }

                case 1:
                    try {

                        //이미지 데이터를 비트맵으로 받아온다.
                        image_bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), data.getData());

                    } catch (FileNotFoundException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    } catch (IOException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;

                default:
                    break;
            }
            //배치해놓은 ImageView에 set
            iv_image.setImageBitmap(image_bitmap);
        }
    }
    public int exifOrientationToDegrees(int exifOrientation)
    {
        if(exifOrientation == ExifInterface.ORIENTATION_ROTATE_90)
        {
            return 90;
        }
        else if(exifOrientation == ExifInterface.ORIENTATION_ROTATE_180)
        {
            return 180;
        }
        else if(exifOrientation == ExifInterface.ORIENTATION_ROTATE_270)
        {
            return 270;
        }
        return 0;
    }

    public Bitmap rotate(Bitmap bitmap, int degrees)
    {
        if(degrees != 0 && bitmap != null)
        {
            Matrix m = new Matrix();
            m.setRotate(degrees, (float) bitmap.getWidth() / 2,
                    (float) bitmap.getHeight() / 2);
            try
            {
                Bitmap converted = Bitmap.createBitmap(bitmap, 0, 0,
                        bitmap.getWidth(), bitmap.getHeight(), m, true);
                if(bitmap != converted)
                {
                    bitmap.recycle();
                    bitmap = converted;
                }
            }
            catch(OutOfMemoryError ex)
            {
                // 메모리가 부족하여 회전을 시키지 못할 경우 그냥 원본을 반환합니다.
            }
        }
        return bitmap;
    }
}