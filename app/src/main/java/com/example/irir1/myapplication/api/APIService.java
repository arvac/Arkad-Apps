package com.example.irir1.myapplication.api;


import com.example.irir1.myapplication.models.Messages;
import com.example.irir1.myapplication.models.Result;


import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;

/**
 * Created by Belal on 14/04/17.
 */

public interface APIService {

    @FormUrlEncoded
    @POST("register")
    Call<Result> createUser(
            @Field("name") String name,
            @Field("email") String email,
            @Field("password") String password,
            @Field("gender") String gender);


    @FormUrlEncoded
    @POST("login")
    Call<Result> userLogin(
            @Field("email") String email,
            @Field("password") String password
    );
    //getting messages
    @GET("planificados/{id}")
    Call<Messages> getMessages(@Path("id") int id);
    //imagenes
    @Multipart
    @POST("regis")
        // Call<ServerResponse> uploadFile(@Part MultipartBody.Part file, @Part("file") RequestBody name);
    Call<Result> uploadFile(@Part MultipartBody.Part foto1, @Part("fotos1") RequestBody name1,@Part MultipartBody.Part foto2, @Part("fotos2") RequestBody name2,
                            @Part("id_c") RequestBody id_c,@Part("id_a") RequestBody id_a,@Part("corte") RequestBody corte,@Part("lectura") RequestBody lectura
    ,@Part("estado") RequestBody estado ,@Part("id_clientes") RequestBody id_clientes );

}
