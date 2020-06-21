package com.example.irir1.myapplication.activities;
import com.example.irir1.myapplication.api.APIService;
import com.example.irir1.myapplication.api.APIUrl;
import com.example.irir1.myapplication.models.Result;
import com.example.irir1.myapplication.models.User;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.irir1.myapplication.helper.SharedPrefManager;
import com.example.irir1.myapplication.R;
import com.example.irir1.myapplication.models.registro;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    /////////variables function camara 1 y 2
    File photoFile = null;
    File photoFile1 = null;
    static final int CAPTURE_IMAGE_REQUEST = 1;
    static final int CAPTURE_IMAGE_REQUEST1 = 2;
    String mCurrentPhotoPath;
    ProgressDialog progressDialog;
    String lect_String,corte_String,idc_string,nombre_string,id_string,cod_string,id_cliente;
    String estado="3";

/////////////////////////////////////////////////
    TextView nombre,cod,id,idc, id_clientes;
    EditText corte,lect;
    Button enviar,camara2,camara;
    ImageView image1,image2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Cargando espere porfavor...");

        User user = SharedPrefManager.getInstance(this).getUser();

        nombre = (TextView) findViewById(R.id.textViewTitle1); //nombre del cliente
        cod = (TextView) findViewById(R.id.textViewTitle);//codigo id registrado tabla asignar (id_asignar)
        id = (TextView) findViewById(R.id.textViewRating);//codigo id del personal cuadrilla(id_cuadrilla)
        /*id_clientes = (TextView) findViewById(R.id.id_clientes);
        idc = (TextView) findViewById(R.id.id_c);
        corte = (EditText) findViewById(R.id.corte);
        lect = (EditText) findViewById(R.id.lect);
        camara =(Button)findViewById(R.id.camara);
        enviar =(Button)findViewById(R.id.enviar);
        camara2 =(Button)findViewById(R.id.camara2);

        image1 =(ImageView)findViewById(R.id.image1);
        image2 =(ImageView)findViewById(R.id.image2);*/

        id_clientes = (TextView) findViewById(R.id.textViewRating);//codigo extraido tabla de excel
        idc = (TextView) findViewById(R.id.textViewShortDesc);
        corte = (EditText) findViewById(R.id.signup_input_name1);
        lect = (EditText) findViewById(R.id.signup_input_name0);
        camara =(Button)findViewById(R.id.add_photo_button);
        enviar =(Button)findViewById(R.id.enviar);
        camara2 =(Button)findViewById(R.id.add_photo_button1);

        image1 =(ImageView)findViewById(R.id.textViewTitle13);
        image2 =(ImageView)findViewById(R.id.textViewTitle16);

        Intent mIntent = getIntent();
        Bundle mBundle = getIntent().getExtras();
        if (mBundle != null) {
            int inte = mIntent.getIntExtra("id", 0);
            String integer=Integer.toString(inte);
            id.setText(integer);
            int idclientes = mIntent.getIntExtra("id_clientes_intent", 0);
            String id_clientes_string=Integer.toString(idclientes);
            id_clientes.setText(id_clientes_string);

            nombre.setText(mBundle.getString("nom"));
            cod.setText(mBundle.getString("cod"));
        }
        String idcs=Integer.toString(SharedPrefManager.getInstance(this).getUser().getId());
        idc.setText(idcs);

        ////////////////////
        //////////////////Click CLICK

        enviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              //  text.setText(lect_String);
                uploadFile();

            }
        });

        camara.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lect_String = lect.getText().toString().trim();
                captureImage();
            }
        });
        camara2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                corte_String = corte.getText().toString().trim();
                id_string = id.getText().toString().trim();
                idc_string = idc.getText().toString().trim();
                nombre_string = nombre.getText().toString().trim();
                cod_string = cod.getText().toString().trim();
                id_cliente = id_clientes.getText().toString().trim();
                captureImage1();

            }
        });
    }

//////////////////////////////////////////////////////////////////////////////////////////////////////////
private void captureImage()
{

    if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
        ActivityCompat.requestPermissions(this, new String[] { Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE }, 0);
    }
    else
    {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            // Create the File where the photo should go
            try {

                photoFile = createImageFile();
                displayMessage(getBaseContext(),photoFile.getAbsolutePath());
                Log.i("Mayank",photoFile.getAbsolutePath());

                // Continue only if the File was successfully created
                if (photoFile != null) {
                    Uri photoURI = FileProvider.getUriForFile(this,
                            "com.example.irir1.myapplication.fileprovider",
                            photoFile);
                    takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                    startActivityForResult(takePictureIntent, CAPTURE_IMAGE_REQUEST);
                }
            } catch (Exception ex) {
                // Error occurred while creating the File
                displayMessage(getBaseContext(),ex.getMessage().toString());
            }


        }else
        {
            displayMessage(getBaseContext(),"Nullll");
        }
    }

}
    private void captureImage1()
    {

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[] { Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE }, 0);
        }
        else
        {
            Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
                // Create the File where the photo should go
                try {

                    photoFile1 = createImageFile();
                    displayMessage(getBaseContext(),photoFile1.getAbsolutePath());
                    Log.i("Mayank",photoFile1.getAbsolutePath());

                    // Continue only if the File was successfully created
                    if (photoFile1 != null) {
                        Uri photoURI = FileProvider.getUriForFile(this,
                                "com.example.irir1.myapplication.fileprovider",
                                photoFile1);
                        takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                        startActivityForResult(takePictureIntent, CAPTURE_IMAGE_REQUEST1);
                    }
                } catch (Exception ex) {
                    // Error occurred while creating the File
                    displayMessage(getBaseContext(),ex.getMessage().toString());
                }


            }else
            {
                displayMessage(getBaseContext(),"Nullll");
            }
        }

    }
///////////////////////////////////////////////////////////////////////////////////////////////////////////
    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        mCurrentPhotoPath = image.getAbsolutePath();

        return image;
    }

    private void displayMessage(Context context, String message)
    {
        Toast.makeText(context,message,Toast.LENGTH_LONG).show();
    }
    /////////////////////////////////////////////////////////////////////////////////////////////////////
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == CAPTURE_IMAGE_REQUEST && resultCode == RESULT_OK) {
            Bitmap myBitmap = BitmapFactory.decodeFile(photoFile.getAbsolutePath());

            image1.setImageBitmap(myBitmap);
        }
        else
        { if (requestCode == CAPTURE_IMAGE_REQUEST1 && resultCode == RESULT_OK) {

            Bitmap myBitmap1 = BitmapFactory.decodeFile(photoFile1.getAbsolutePath());

            image2.setImageBitmap(myBitmap1);
        }
        else
        {
            displayMessage(getBaseContext(),"Request cancelled or something went wrong.");
        }
            displayMessage(getBaseContext(),"Request cancelled or something went wrong.");
        }



    }


    // Uploading Image/Video
    private void uploadFile() {
        progressDialog.show();

        // Map is used to multipart the file using okhttp3.RequestBody
        //  File file = new File(mediaPath);

        // Parsing any Media type file
        RequestBody requestBody = RequestBody.create(MediaType.parse("*/*"), photoFile);
        MultipartBody.Part fileToUpload = MultipartBody.Part.createFormData("fotos1", photoFile.getName(), requestBody);
        RequestBody filename = RequestBody.create(MediaType.parse("text/plain"), photoFile.getName());

        RequestBody requestBody1 = RequestBody.create(MediaType.parse("*/*"), photoFile1);
        MultipartBody.Part fileToUpload1 = MultipartBody.Part.createFormData("fotos2", photoFile1.getName(), requestBody1);
        RequestBody filename1 = RequestBody.create(MediaType.parse("text/plain"), photoFile1.getName());

        RequestBody id_ = RequestBody.create(MediaType.parse("text/plain"), idc_string);
        RequestBody idc_ = RequestBody.create(MediaType.parse("text/plain"), id_string);

        RequestBody corte_ = RequestBody.create(MediaType.parse("text/plain"), corte_String);
        RequestBody lect_ = RequestBody.create(MediaType.parse("text/plain"), lect_String);
        RequestBody estado1 = RequestBody.create(MediaType.parse("text/plain"), estado);
        RequestBody id_clientes = RequestBody.create(MediaType.parse("text/plain"), id_cliente);


///////////////////////////////////////////////////////////////////////////////////////////////////////call retrofit uri
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(APIUrl.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
//////////////////////////////////////////////////////////////////////////////////////////////////////
//        Call<Result> call = getResponse.uploadFile(fileToUpload,fileToUpload1, filename,filename1,id_,id_,idc_,nombre_,cod_,corte_,lect_,estado1);
        APIService getResponse = retrofit.create(APIService.class);
        Call<Result> call = getResponse.uploadFile(fileToUpload,filename, fileToUpload1,filename1,id_,idc_,corte_,lect_,estado1,id_clientes);
        call.enqueue(new Callback<Result>() {
            @Override
            public void onResponse(Call<Result> call, Response<Result> response) {
                Result serverResponse = response.body();
                if (serverResponse != null) {
                    if (serverResponse.getSuccess()) {
                        Toast.makeText(getApplicationContext(), serverResponse.getMessage(),Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getApplicationContext(), serverResponse.getMessage(),Toast.LENGTH_SHORT).show();
                    }
                } else {
                    assert serverResponse != null;
                    Log.v("Response", serverResponse.toString());
                }
                progressDialog.dismiss();

            }

            @Override
            public void onFailure(Call<Result> call, Throwable t) {

            }
        });
    }




}
