package com.example.gmlrj.mysns;

import android.graphics.Bitmap;
import android.net.Uri;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.widget.ImageView;

public class EditActivity extends AppCompatActivity {

    private Uri imageUri;
    private ImageView iv_image;
    private Bitmap image_bitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        iv_image = (ImageView) this.findViewById(R.id.ImageView);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_edit, menu);
        return true;
    }

//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        switch (item.getItemId()) {
//            case R.id.action_submit:
//                Toast.makeText(this, "submit", Toast.LENGTH_SHORT).show();
//                return true;
//            case R.id.home:
//                NavUtils.navigateUpFromSameTask(this);
//                public class EditActivity extends AppCompatActivity {
//                return super.onOptionsItemSelected(item);
//            }
//        }
//
//        public void SelectPic(View view) {
//            final CharSequence[] items = {"카메라", "앨범"};
//            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
//                    this);
//
//            // 제목셋팅
//            alertDialogBuilder.setTitle("선택 목록 대화 상자");
//            alertDialogBuilder.setItems(items,
//                    new DialogInterface.OnClickListener() {
//                        public void onClick(DialogInterface dialog,
//                                            int id) {
//                            switch (id) {
//                                case 0:
//
//                                    int permissionCheck = ContextCompat.checkSelfPermission(EditActivity.this, android.Manifest.permission.CAMERA);
//                                    if (permissionCheck == getPackageManager().PERMISSION_DENIED) {
//                                        ActivityCompat.requestPermissions(EditActivity.this, new String[]{android.Manifest.permission.CAMERA}, 0);
//                                    }
//
//                                    File photo = new File(Environment.getExternalStorageDirectory(),".camera.jpg");
//                                    imageUri = Uri.fromFile(photo);
//
//                                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//                                    //    intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
//                                    startActivityForResult(intent, 0);
//
//                                    break;
//
//
//                                case 1:
//                                    // 앨범 호출
//                                    intent = new Intent(Intent.ACTION_PICK);
//                                    intent.setData(android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
//                                    intent.setType(android.provider.MediaStore.Images.Media.CONTENT_TYPE);
//                                    startActivityForResult(Intent.createChooser(intent, "Select Picture"), 1);
//
//
//                                    break;
//
//                            }
//                            dialog.dismiss();
//                        }
//                    });
//
//            // 다이얼로그 생성
//            AlertDialog alertDialog = alertDialogBuilder.create();
//
//            // 다이얼로그 보여주기
//            alertDialog.show();
//
//
//        }
//
//
//        @Override
//
//        protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//            super.onActivityResult(requestCode, resultCode, data);
//            if (resultCode == RESULT_OK) {
//
//                switch (requestCode) {
//
//                    case 0:
//                        if(resultCode!=0){
//
//                            if(requestCode==1&&!data.equals(null)){
//
//                                try{
//                                    image_bitmap = (Bitmap)data.getExtras().get("data");
//
//
//                                    String imagePath = imageUri.getPath();
//                                    ExifInterface exif = new ExifInterface(imagePath);
//                                    int exifOrientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);
//                                    int exifDegree = exifOrientationToDegrees(exifOrientation);
//                                    image_bitmap = rotate(image_bitmap, exifDegree);
//
//
//                                    iv_image.setImageBitmap(image_bitmap);
//                                    iv_image.setScaleType(ImageView.ScaleType.FIT_XY);
//                                } catch(Exception e){
//                                    return;
//                                }
//                            }
//                        }
//
//                    case 1:
//                        try {
//
//                            //이미지 데이터를 비트맵으로 받아온다.
//                            image_bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), data.getData());
//
//                        } catch (FileNotFoundException e) {
//                            // TODO Auto-generated catch block
//                            e.printStackTrace();
//                        } catch (IOException e) {
//                            // TODO Auto-generated catch block
//                            e.printStackTrace();
//                        } catch (Exception e) {
//                            e.printStackTrace();
//                        }
//                        break;
//
//                    default:
//                        break;
//                }
//                //배치해놓은 ImageView에 set
//                iv_image.setImageBitmap(image_bitmap);
//
//
//            }
//        }
//        public int exifOrientationToDegrees(int exifOrientation)
//        {
//            if(exifOrientation == ExifInterface.ORIENTATION_ROTATE_90)
//            {
//                return 90;
//            }
//            else if(exifOrientation == ExifInterface.ORIENTATION_ROTATE_180)
//            {
//                return 180;
//            }
//            else if(exifOrientation == ExifInterface.ORIENTATION_ROTATE_270)
//            {
//                return 270;
//            }
//            return 0;
//        }
//
//        public Bitmap rotate(Bitmap bitmap, int degrees)
//        {
//            if(degrees != 0 && bitmap != null)
//            {
//                Matrix m = new Matrix();
//                m.setRotate(degrees, (float) bitmap.getWidth() / 2,
//                        (float) bitmap.getHeight() / 2);
//
//                try
//                {
//                    Bitmap converted = Bitmap.createBitmap(bitmap, 0, 0,
//                            bitmap.getWidth(), bitmap.getHeight(), m, true);
//                    if(bitmap != converted)
//                    {
//                        bitmap.recycle();
//                        bitmap = converted;
//                    }
//                }
//                catch(OutOfMemoryError ex)
//                {
//                    // 메모리가 부족하여 회전을 시키지 못할 경우 그냥 원본을 반환합니다.
//                }
//            }
//            return bitmap;
//        }
    }
