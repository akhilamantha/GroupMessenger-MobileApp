package com.example.manikesh.akkismessager;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {

    private EditText editMessage;
    private DatabaseReference mdDtabase;
    //private Button backButton;

    private RecyclerView mMessageList;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private FirebaseUser mCurrentUser;
    private DatabaseReference mDatabaseUsers;

    private Button backButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//initiate all the controlers in the way you do always  y calling them with id of xml definitions
        editMessage = (EditText)findViewById(R.id.editMessageE);
        mdDtabase = FirebaseDatabase.getInstance().getReference().child("Message");

        mMessageList = (RecyclerView) findViewById(R.id.messageRec);
       backButton = (Button) findViewById(R.id.backbuttonid);

       // layout manager has the controlers of all the requied


        mMessageList.setHasFixedSize(true);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setStackFromEnd(true);
        mMessageList.setLayoutManager(linearLayoutManager);
        mAuth = FirebaseAuth.getInstance();
// create a firebase instance because it needs to be called as the firebase object.
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if(firebaseAuth.getCurrentUser() == null){
                    startActivity(new Intent(MainActivity.this,RegisterActivity.class));
                }
// the above definitions are important because
            }
        };
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mAuth.signOut();
                finish();
                startActivity(new Intent(MainActivity.this,loginActivity.class));
            }
        });
// if the back button is clicked it is redirected to the login page where your credentials are again given by the user
    }

    public  void sendmessageclick(View view){

        mCurrentUser = mAuth.getCurrentUser();
        mDatabaseUsers = FirebaseDatabase.getInstance().getReference().child("Users").child(mCurrentUser.getUid());

        final String messageValue = editMessage.getText().toString().trim();

        if(!TextUtils.isEmpty(messageValue)){
            final DatabaseReference newPost = mdDtabase.push();
            mDatabaseUsers.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    newPost.child("content").setValue(messageValue);
                    newPost.child("username").setValue(dataSnapshot.child("name").getValue()).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
// send is important whwre your username and content the message is generated here your cardview is generated accordingly..

                        }
                    });


                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });

            mMessageList.scrollToPosition(mMessageList.getAdapter().getItemCount());

// mesage list has your crad view and recycler view details and that happends only when the authentications steps are done.
            //all the required methods for appropriate fuctions should be called correcly and validated
            // this page has all the messaeg details including the name and the conetnt.



        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);

        FirebaseRecyclerAdapter <Message,MessageviewHolder> FBRA = new FirebaseRecyclerAdapter<Message, MessageviewHolder>(

            Message.class,
                R.layout.singlemessagelayout,
                MessageviewHolder.class,
                mdDtabase
        ) {
            @Override
            protected void populateViewHolder(MessageviewHolder viewHolder, Message model, int position) {
               viewHolder.setContent(model.getContent());
               viewHolder.setUsername(model.getUsername());

            }
        };
        mMessageList.setAdapter(FBRA);
    }

    public static class  MessageviewHolder extends RecyclerView.ViewHolder{

// your message view is nothing but your card view put in the recycler view  with all the details
        View mView;
        public MessageviewHolder(View itemView) {
            super(itemView);
            mView = itemView;
        }
        public  void setContent(String content){
            TextView message_content = (TextView) mView.findViewById(R.id.messageText);
            message_content.setText(content);
        }
        // your Mesage java class all the getters and setters

        public void setUsername(String username){
            TextView username_content = (TextView) mView.findViewById(R.id.usernameText);
            username_content.setText(username);
        }




    }


}
