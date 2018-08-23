package com.example.manikesh.akkismessager;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

public class MenusActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        TextView textView2=(TextView)findViewById(R.id.textView2);
        registerForContextMenu(textView2);

        final ImageButton imageButton=(ImageButton)findViewById(R.id.imageButton);

        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popup = new PopupMenu(MenusActivity.this, imageButton);
                popup.getMenuInflater().inflate(R.menu.context_menu, popup.getMenu());

                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    public boolean onMenuItemClick(MenuItem item) {
                        Toast.makeText(MenusActivity.this,"You Clicked : " + item.getTitle(),Toast.LENGTH_SHORT).show();
                        return true;
                    }
                });

                popup.show();
            }
        });

    }

    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.options_menu, menu);

        return true;
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v,
                                    ContextMenu.ContextMenuInfo menuInfo) {

        if(v.getId()==R.id.textView2) {

            MenuInflater inflater = getMenuInflater();
            inflater.inflate(R.menu.context_menu, menu);
        }

    }

    public boolean onOptionsItemSelected(MenuItem item){

        TextView optionsMenuItem=(TextView)findViewById(R.id.textView);

        optionsMenuItem.setText(item.getTitle() + " Selected");

        return true;

    }



    public boolean onContextItemSelected(MenuItem item) {

        TextView contextMenuItem=(TextView)findViewById(R.id.textView);
        String text = item.getTitle() + " from context menu selected !!";
        contextMenuItem.setText(text);

        return true;
    }

}
