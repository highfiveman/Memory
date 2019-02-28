package pl.lodz.uni.math.mjachowicz.memory;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.view.MenuItem;
import android.widget.ImageButton;

public class Card {
    private boolean showingPhoto=false;
    private int id;
    private Bitmap photo;
    private Bitmap defaultBitmap;

    private ImageButton imageButton;

    public Card(int id,Bitmap photo,ImageButton imageButton)
    {
        this.id=id;
        this.photo=photo;
        this.defaultBitmap=((BitmapDrawable)imageButton.getDrawable()).getBitmap();
        this.imageButton=imageButton;
        imageButton.setScaleType(ImageButton.ScaleType.FIT_XY);
    }

    public void flipCard()
    {
        if(showingPhoto)
        {
            hideCard();
        }
        else
        {
            showingPhoto=true;
            imageButton.setImageBitmap(photo);
        }
    }

    public void hideCard()
    {
        showingPhoto=false;
        imageButton.setImageBitmap(defaultBitmap);
    }

    public int getId()
    {
      return id;
    }

    public ImageButton getImageButton()
    {
        return imageButton;
    }
}
