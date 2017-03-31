package com.handshake.pritz.ftest;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import static android.R.attr.name;
import static android.R.id.message;

public class MainActivity extends AppCompatActivity {

    EditText mes;
    Button send;
    ListView list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mes = (EditText) findViewById(R.id.mes);
        send = (Button) findViewById(R.id.send);
        list = (ListView) findViewById(R.id.list);
        // Write a message to the database
       final FirebaseDatabase database = FirebaseDatabase.getInstance();
       final DatabaseReference myRef = database.getReference("name");


        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(),Main2Activity.class);
                startActivity(intent);
                Map<String,Object> values = new HashMap<>();



                values.put("name", "puf");

                values.put("message", message);

                Chat chat = new Chat("Robin", mes.getText().toString());

                myRef.push().setValue(chat);



                mes.setText("");

            }
        });

        final List<Chat> listmes = new LinkedList<>();
        final ArrayAdapter<Chat> adapter = new ArrayAdapter<Chat>
                (this, android.R.layout.two_line_list_item, listmes) {
            @Override
            public View getView(int pos, View view, ViewGroup parent) {
                if (view == null) {
                    view = getLayoutInflater().inflate(android.R.layout.two_line_list_item, parent, false);
                }
                Chat chat = listmes.get(pos);
                ((TextView) view.findViewById(android.R.id.text1)).setText(chat.getName());
                ((TextView) view.findViewById(android.R.id.text2)).setText(chat.getMess());
                return view;
            }
        };
        list.setAdapter(adapter);




        myRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Chat chat=dataSnapshot.getValue(Chat.class);
                listmes.add(chat);
                adapter.notifyDataSetChanged();

            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(MainActivity.this, "HELP", Toast.LENGTH_SHORT).show();

            }
        });
    }
}

