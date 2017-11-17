package com.samsung.muhammad.polisi.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.samsung.muhammad.polisi.R;
import com.samsung.muhammad.polisi.app.ServerManager;
import com.samsung.muhammad.polisi.data.ResponseData;
import com.samsung.muhammad.polisi.util.FileUtil;
import com.yalantis.ucrop.UCrop;

import java.io.File;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import pl.aprilapps.easyphotopicker.DefaultCallback;
import pl.aprilapps.easyphotopicker.EasyImage;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class SubmitCommentActivity extends AppCompatActivity {

    public static final String NEWS_ID = "news_id";

    public static final int PERMISSION_REQ_CODE = 10000;

    @BindView(R.id.progress_bar)
    ProgressBar progressBar;

    @BindView(R.id.et_name)
    EditText etName;
    @BindView(R.id.et_comment)
    EditText etComment;

    @BindView(R.id.iv_image)
    ImageView ivImage;

    private String newsId;

    private String imageFilePath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_submit_comment);
        ButterKnife.bind(this);

        newsId = getIntent().getStringExtra(NEWS_ID);

        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.CAMERA,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE,
                            Manifest.permission.READ_EXTERNAL_STORAGE},
                    PERMISSION_REQ_CODE);
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode) {
            case PERMISSION_REQ_CODE: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED
                        && grantResults[1] == PackageManager.PERMISSION_GRANTED
                        && grantResults[2] == PackageManager.PERMISSION_GRANTED
                        ) {

                } else {
                    finish();
                }
                break;
            }
        }

    }

    @OnClick(R.id.bt_select_image)
    public void selectImage() {
        EasyImage.openChooserWithGallery(this, "Upload gambar dari..", 0);
    }

    @OnClick(R.id.bt_send)
    public void sendComment() {
        String name = etName.getText().toString().trim();
        String comment = etComment.getText().toString().trim();

        if (TextUtils.isEmpty(name) || name.equals("")) {
            Toast.makeText(this, "Nama tidak boleh kosong", Toast.LENGTH_LONG).show();
        } else if (TextUtils.isEmpty(comment) || comment.equals("")) {
            Toast.makeText(this, "Komentar tidak boleh kosong", Toast.LENGTH_LONG).show();
        } else {

            RequestBody requestBody = null;

            if (imageFilePath != null) {
                File file = new File(imageFilePath);
                requestBody = RequestBody.create(MediaType.parse("image/jpeg"), file);
            }

            RequestBody newsIdBody = RequestBody.create(MediaType.parse("text/plain"), newsId);
            RequestBody nameBody = RequestBody.create(MediaType.parse("text/plain"), name);
            RequestBody commentBody = RequestBody.create(MediaType.parse("text/plain"), comment);

            Call<List<ResponseData>> call = ServerManager.getInstance().getService().sendComment(requestBody, newsIdBody, nameBody, commentBody);
            call.enqueue(new Callback<List<ResponseData>>() {
                @Override
                public void onResponse(Call<List<ResponseData>> call, Response<List<ResponseData>> response) {
                    if(response.body() == null){
                        Toast.makeText(SubmitCommentActivity.this, "Terjadi kesalahan, silahkan coba lagi", Toast.LENGTH_LONG).show();
                        return;
                    }

                    try {
                        if (response.body().get(0).getMessage().equalsIgnoreCase("berhasil")) {
                            Toast.makeText(SubmitCommentActivity.this, "Berhasil memberi komentar, terima kasih", Toast.LENGTH_LONG).show();
                            setResult(RESULT_OK);
                            finish();
                        } else {
                            Toast.makeText(SubmitCommentActivity.this, "Gagal memberi komentar, silahkan coba lagi", Toast.LENGTH_LONG).show();
                        }
                    } catch (Exception e) {
                        Toast.makeText(SubmitCommentActivity.this, "Terjadi kesalahan, silahkan coba lagi", Toast.LENGTH_LONG).show();
                        return;
                    }
                }

                @Override
                public void onFailure(Call<List<ResponseData>> call, Throwable t) {
                    Toast.makeText(SubmitCommentActivity.this, "Terjadi kesalahan, silahkan coba lagi", Toast.LENGTH_LONG).show();
                }
            });
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        EasyImage.handleActivityResult(requestCode, resultCode, data, this, new DefaultCallback() {

            @Override
            public void onImagePickerError(Exception e, EasyImage.ImageSource source, int type) {
                Toast.makeText(SubmitCommentActivity.this, "Image not found "+e.getMessage(), Toast.LENGTH_LONG).show();
            }

            @Override
            public void onImagesPicked(@NonNull List<File> imageFiles, EasyImage.ImageSource source, int type) {
                if (imageFiles != null && imageFiles.size() > 0) {
                    File cropFile = FileUtil.getInstance().createTempFile(SubmitCommentActivity.this);
                    if (cropFile.exists()) {
                        UCrop.of(Uri.fromFile(imageFiles.get(0)), Uri.fromFile(cropFile))
                                .withAspectRatio(5, 3)
                                .withMaxResultSize(500, 300)
                                .start(SubmitCommentActivity.this);
                    }
                }
            }

            @Override
            public void onCanceled(EasyImage.ImageSource source, int type) {
                if (source == EasyImage.ImageSource.CAMERA) {
                    File photoFile = EasyImage.lastlyTakenButCanceledPhoto(SubmitCommentActivity.this);
                    if (photoFile != null) photoFile.delete();
                }
            }

        });

        if (resultCode == RESULT_OK && requestCode == UCrop.REQUEST_CROP) {
            final Uri resultUri = UCrop.getOutput(data);
            imageFilePath = resultUri.getPath();
            Glide.with(SubmitCommentActivity.this)
                    .load(imageFilePath)
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .skipMemoryCache(true)
                    .crossFade()
                    .into(ivImage);
            ivImage.setVisibility(View.VISIBLE);

//            ImageUtil.getInstance().loadImage(SubmitCommentActivity.this, new File(imageFilePath), image);
        } else if (resultCode == UCrop.RESULT_ERROR) {
            final Throwable cropError = UCrop.getError(data);
        }
    }
}
