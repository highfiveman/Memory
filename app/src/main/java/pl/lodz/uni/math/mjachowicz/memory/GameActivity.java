package pl.lodz.uni.math.mjachowicz.memory;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;

public class GameActivity extends AppCompatActivity {

    private ArrayList<ImageButton> buttons;
    private ArrayList<Card> cards;
    private ArrayList<Bitmap> photos;

    private Card firstButton;
    private Card secondButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        firstButton=null;
        secondButton=null;

        Intent intent = getIntent();

        ArrayList<String> paths = intent.getStringArrayListExtra("paths");

        photos = new ArrayList<>();
        cards = new ArrayList<>();
        buttons = new ArrayList<>();

        for(String path:paths)
        {
            photos.add(BitmapFactory.decodeFile(path));
        }

        buttons.add((ImageButton)findViewById(R.id.card1));
        buttons.add((ImageButton)findViewById(R.id.card2));
        buttons.add((ImageButton)findViewById(R.id.card3));
        buttons.add((ImageButton)findViewById(R.id.card4));
        buttons.add((ImageButton)findViewById(R.id.card5));
        buttons.add((ImageButton)findViewById(R.id.card6));
        buttons.add((ImageButton)findViewById(R.id.card7));
        buttons.add((ImageButton)findViewById(R.id.card8));
        Collections.shuffle(buttons);

        for(int i=0;i<4;i++)
        {
            cards.add(new Card(i,photos.get(i),buttons.get(i*2)));
            cards.add(new Card(i,photos.get(i),buttons.get(i*2+1)));

        }
    }

    public void selectCardOnClick(View view)
    {
        ImageButton clickedButton = (ImageButton)view;
        int index = getIndexOfButton(clickedButton);

        Card clickedCard = cards.get(index);

        if(firstButton==null)
        {
            firstButton=clickedCard;
            firstButton.flipCard();
        }
        else if(firstButton==clickedCard&&secondButton==null)
        {
            firstButton.hideCard();
            firstButton=null;
            secondButton=null;
        }
        else if(secondButton==null)
        {
            secondButton=clickedCard;
            secondButton.flipCard();
        }
        else
        {
            if(firstButton.getId()==secondButton.getId())
            {
                firstButton.getImageButton().setVisibility(View.INVISIBLE);
                secondButton.getImageButton().setVisibility(View.INVISIBLE);
                if(checkIfWon())
                {
                    gameWin();
                }
            }
            firstButton.hideCard();
            secondButton.hideCard();
            firstButton=null;
            secondButton=null;
        }
    }

    private boolean checkIfWon()
    {
        for(ImageButton button:buttons)
        {
            if(button.getVisibility()==View.VISIBLE) return false;
        }
        return true;
    }

    private void gameWin()
    {
        findViewById(R.id.textView).setVisibility(View.VISIBLE);
    }

    private int getIndexOfButton(ImageButton button)
    {
        for(int i=0;i<buttons.size();i++)
        {
            if(button==buttons.get(i)) return i;
        }
        return -1;
    }


}
