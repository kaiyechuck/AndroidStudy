package com.example.myquiz;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.BatchUpdateException;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private static final int REQUEST_CODE_CHEAT=0;

    private Button mCheatButton;
    private Button mTrueButton;
    private Button mFalseButton;
    private Button mNextButton;
    private Button mPrevButton;
    private TextView mQuestionTextView;
    private boolean mIsCheater;


    private Question[] mQuestionsBank = new Question[]{
            new Question(R.string.question_australia,true),
            new Question(R.string.question_2,true),
            new Question(R.string.question_3,true),
            new Question(R.string.question_4,true),
            new Question(R.string.question_5,true),
            new Question(R.string.question_6,false),
    };
    private int mCurrentIndex=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG,"onCreate(Bundle) called");
        setContentView(R.layout.activity_main);

        mQuestionTextView =(TextView) findViewById(R.id.question_text_view);
        updatequestion();
        mQuestionTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCurrentIndex= (mCurrentIndex+1)% mQuestionsBank.length;
                updatequestion();
            }
        });



        mTrueButton=(Button) findViewById(R.id.true_button);
        mTrueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkanswer(true);
            }
        });
        mFalseButton=(Button) findViewById(R.id.false_button);
        mFalseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkanswer(false);
            }
        });

        mNextButton = (Button) findViewById(R.id.next_button);
        mNextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCurrentIndex= (mCurrentIndex+1)% mQuestionsBank.length;
                mIsCheater=false;
                updatequestion();
            }
        });

       // mPrevButton = (Button) findViewById(R.id.prev_button);
//        mPrevButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                mCurrentIndex= mCurrentIndex-1;
//                if(mCurrentIndex<0){
//                    mCurrentIndex=mQuestionsBank.length-1;
//                }
//                updatequestion();
//            }
//        });

        mCheatButton =(Button) findViewById(R.id.cheat_button);
        mCheatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean answerIsTrue = mQuestionsBank[mCurrentIndex].isAnswerTrue();
                Intent intent=CheatActivity.newIntent(MainActivity.this, answerIsTrue);
                startActivityForResult(intent, REQUEST_CODE_CHEAT);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        if(resultCode!= Activity.RESULT_OK){
            return;
        }

        if(resultCode==REQUEST_CODE_CHEAT){
            if(data==null){
                return;
            }
            mIsCheater=CheatActivity.wasAnswerShown(data);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, "onStart() called");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "onResume() called");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "onPause() called");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, "onStop() called");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy() called");
    }

    private void updatequestion(){
        int question = mQuestionsBank[mCurrentIndex].getTextResId();
        mQuestionTextView.setText(question);
    }

    private void checkanswer(boolean userPressedTrue){
        boolean ansIsTrue = mQuestionsBank[mCurrentIndex].isAnswerTrue();

        int messageToastId = 0;
        if(mIsCheater){
            messageToastId=R.string.cheat_toast;
        }else{
        if(userPressedTrue==ansIsTrue){
            messageToastId = R.string.correct_toast;
        }else{
            messageToastId=R.string.incorrect_toast;
        }}

        Toast.makeText(this, messageToastId, Toast.LENGTH_SHORT).show();
    }


}



