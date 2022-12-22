package com.example.edufutureapp.activities;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.example.edufutureapp.R;
import androidx.cardview.widget.CardView;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;


public class PageNavigation extends AppCompatActivity implements View.OnClickListener{

    private CardView Courses, Quiz, Room;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_page_navigation);

        Courses = (CardView) findViewById(R.id.cardview_courses);
        Quiz = (CardView) findViewById(R.id.cardview_quiz);
        Room = (CardView) findViewById(R.id.cardview_room);

        Courses.setOnClickListener((View.OnClickListener) this);
        Quiz.setOnClickListener((View.OnClickListener) this);
        Room.setOnClickListener((View.OnClickListener) this);
    }

    @Override
    public void onClick(View v) {

        Intent i;
        switch (v.getId()) {
            case R.id.cardview_room:
                i = new Intent(this, EduRoomActivity.class);
                startActivity(i);
                break;

        }

    }

}