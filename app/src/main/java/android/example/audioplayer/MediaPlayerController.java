package android.example.audioplayer;

import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import java.io.IOException;

public class MediaPlayerController {

    final int PROGRESS_BAR_REFRESH_MSEC = 500;
    final int CURRENT_TIME_REFRESH_MSEC = 1000;

    Context ctx;
    MediaPlayer mp;

    TextView currentTimeTextView;
    TextView totalTimeTextView;

    PlayerProgressSeekBar progressBar;
    SeekBar volumeBar;

    ImageView playPauseButton;

    AudioManager audioManager;

    public MediaPlayerController(
            Context ctx,
            TextView currentTimeTextView,
            TextView totalTimeTextView,
            PlayerProgressSeekBar progressBar,
            SeekBar volumeBar,
            final ImageView playPauseButton){
        this.ctx = ctx;

        this.mp = new MediaPlayer();
        this.audioManager = (AudioManager)ctx.getSystemService(Context.AUDIO_SERVICE);

        this.currentTimeTextView = currentTimeTextView;
        this.totalTimeTextView = totalTimeTextView;
    // bad try ****************************************************************
     /*   this.progressBar = progressBar;
        this.progressBar.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(mp.isPlaying()){
                    SeekBar sb = (SeekBar)v;
                    mp.seekTo(sb.getProgress());
                }
                return false;
            }
        });
        this.progressBar.setMediaPlayer(this.mp);
        */
        this.progressBar = progressBar;
        this.progressBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener()
        {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

                if (fromUser) {
                    mp.seekTo(progress);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        this.volumeBar = volumeBar;

        this.volumeBar.setMax(audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC));
        this.volumeBar.setProgress(audioManager.getStreamVolume(AudioManager.STREAM_MUSIC));
        this.volumeBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                    audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, progress,0);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        this.playPauseButton = playPauseButton;
        this.mp.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                playPauseButton.setImageResource(R.drawable.play_btn);
              
            }
        });
    }

    public void setSource(int resid) {
        if (mp.isPlaying()) {
            mp.reset();
        }
        Uri mediaPath = Uri.parse("android.resource://" + ctx.getPackageName() + "/" + resid);
        try {
            mp.setDataSource(ctx.getApplicationContext(), mediaPath);
            mp.prepare();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void play() {
        mp.start();
        progressBar.setMax(mp.getDuration());
        progressBar.postDelayed(mUpdateProgress, PROGRESS_BAR_REFRESH_MSEC);

        totalTimeTextView.setText(milliSecondsToTimer(mp.getDuration()));

        currentTimeTextView.setText(milliSecondsToTimer(mp.getCurrentPosition()));
        currentTimeTextView.postDelayed(mUpdateCurrentPosition, CURRENT_TIME_REFRESH_MSEC);
    }

    public void pause() {
        mp.pause();
        progressBar.post(mUpdateProgress);
        currentTimeTextView.post(mUpdateCurrentPosition);
    }

    public boolean isPlaying() {
        return mp.isPlaying();
    }

    private Runnable mUpdateCurrentPosition = new Runnable() {
        public void run() {
            if (mp.isPlaying()) {
                currentTimeTextView.setText(milliSecondsToTimer(mp.getCurrentPosition()));
                currentTimeTextView.postDelayed(this, CURRENT_TIME_REFRESH_MSEC);
            } else {
                currentTimeTextView.removeCallbacks(this);
            }
        }
    };

    private Runnable mUpdateProgress = new Runnable() {
        public void run() {
            if (mp.isPlaying()) {
                progressBar.setProgress(mp.getCurrentPosition());
                progressBar.postDelayed(this, PROGRESS_BAR_REFRESH_MSEC);
            } else {
                progressBar.removeCallbacks(this);
            }
        }
    };

    //Here is function to convert milliseconds to timer
   public String milliSecondsToTimer(long milliseconds) {
        String finalTimerString = "";
        String secondsString = "";
        // Convert total duration into time
        int hours = (int) (milliseconds / (1000 * 60 * 60));
        int minutes = (int) (milliseconds % (1000 * 60 * 60)) / (1000 * 60);
        int seconds = (int) ((milliseconds % (1000 * 60 * 60)) % (1000 * 60) / 1000);
        // Add hours if there
        if (hours > 0) {
            finalTimerString = hours + ":";
        }
        // Prepending 0 to seconds if it is one digit
        if (seconds < 10) {
            secondsString = "0" + seconds;
        }   else {
            secondsString = "" + seconds;
        }
        finalTimerString = finalTimerString + minutes + ":" + secondsString;
        return finalTimerString;
    }

}
