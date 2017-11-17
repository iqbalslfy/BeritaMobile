package com.samsung.muhammad.polisi.app;

import com.samsung.muhammad.polisi.data.Comment;
import com.samsung.muhammad.polisi.data.ResponseData;

import java.util.List;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

/**
 * Created by dody on 10/10/17.
 */

public interface ServerAPI {


    @POST("getListKomentar.php")
    Call<List<Comment>> getNewsComments(@Query("id") String id);

    @Multipart
    @POST("insertKomentar.php")
    Call<List<ResponseData>> sendComment(@Part("foto\"; filename=\"pic.jpg") RequestBody file,
                                   @Part("id_berita") RequestBody newsId,
                                   @Part("nama") RequestBody name,
                                   @Part("komentar") RequestBody comment);


}
