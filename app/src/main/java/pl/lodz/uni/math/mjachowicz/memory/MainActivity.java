package pl.lodz.uni.math.mjachowicz.memory;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    static final int REQUEST_TAKE_PHOTO = 1;
    static final int REQUEST_IMAGE_CAPTURE = 1;

    private ArrayList<String> paths;
    private ImageButton currentButton;
    private ArrayList<Boolean> clickedImageButtons;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        paths = new ArrayList<>(Arrays.asList("","","",""));
        clickedImageButtons = new ArrayList<>(Arrays.asList(false,false,false,false));
    }

    private File createImageFile(int buttonIndex) throws IOException {
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
        paths.set(buttonIndex,image.getAbsolutePath());
        return image;
    }

    private void dispatchTakePictureIntent(int buttonIndex) {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile(buttonIndex);
            } catch (IOException ex) {
                // Error occurred while creating the File
            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(this,
                        "com.example.memory.fileprovider",
                        photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO);
            }
        }
    }

    public void takePhotoOnClick(View view)
    {
        ImageButton clickedButton = (ImageButton)view;
        int buttonIndex = Integer.parseInt(clickedButton.getTag().toString())-1;
        if(!clickedImageButtons.get(buttonIndex))
        {
            currentButton = clickedButton;

            clickedImageButtons.set(buttonIndex,true);
            dispatchTakePictureIntent(buttonIndex);
        }
    }

    public void startGameOnClick(View view)
    {
        Intent intent = new Intent(this,GameActivity.class);
        intent.putExtra("paths",paths);
        startActivity(intent);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {

            int buttonIndex = Integer.parseInt(currentButton.getTag().toString())-1;
            Bitmap bitmap = BitmapFactory.decodeFile(paths.get(buttonIndex));
            Matrix matrix = new Matrix();
            matrix.postRotate(90);
            bitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(),bitmap.getHeight(), matrix, true);
            currentButton.setScaleType(ImageButton.ScaleType.FIT_XY);
            currentButton.setImageBitmap(bitmap);
        }
    }

}


