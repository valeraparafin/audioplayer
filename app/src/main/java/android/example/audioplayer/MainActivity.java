package android.example.audioplayer;

import androidx.appcompat.app.AppCompatActivity;

import android.media.AudioManager;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;


public class MainActivity extends AppCompatActivity {

    SeekBar volumeSeekBar;
    PlayerProgressSeekBar progressBar;
    AudioManager audioManager;
    TextView spentTextView;
    TextView endTextView;

    ImageView playPauseButton;

    MediaPlayerController mpc;

    ImageView btnPlay;
    ImageView btnNext;
    ImageView btnPrev;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        spentTextView = findViewById(R.id.spentTextView);
        endTextView = findViewById(R.id.endTextView);
        progressBar = findViewById(R.id.progressBar);
        volumeSeekBar = findViewById(R.id.seekBar);
        playPauseButton = findViewById(R.id.buttonPlay);

        mpc = new MediaPlayerController(getApplicationContext(), spentTextView, endTextView, progressBar, volumeSeekBar, playPauseButton);
        // TODO: костыль
        mpc.setSource(R.raw.stuff);
    }

    public void playButton(View view) {
            btnPlay = findViewById(R.id.buttonPlay);
            if (mpc.isPlaying()) {
                btnPlay.setImageResource(R.drawable.play_btn);
                mpc.pause();
            }
            else {
                btnPlay.setImageResource(R.drawable.pause_btn);
                mpc.play();
            }
    }

    public void prevButton(View view) {
       btnPrev = findViewById(R.id.buttonPrev);
        mpc.setSource(R.raw.ukulele);
        mpc.play();
    }

    public void nextButton(View view) {
        btnNext = findViewById(R.id.buttonNext);
        mpc.setSource(R.raw.ukulele);
        mpc.play();
        if (mpc.isPlaying()) {
            btnPlay.setImageResource(R.drawable.pause_btn);
        }
    }

}
