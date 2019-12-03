package android.example.audioplayer;

import android.content.Context;
import android.media.MediaPlayer;
import android.util.AttributeSet;
import android.widget.SeekBar;

public class PlayerProgressSeekBar extends SeekBar {

    MediaPlayer mp;

    public PlayerProgressSeekBar(Context context) {
        super(context);
    }

    public PlayerProgressSeekBar(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public PlayerProgressSeekBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public PlayerProgressSeekBar(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public void setMediaPlayer(MediaPlayer mp) {
        this.mp = mp;
    }

    @Override
    public boolean performClick() {
        if(mp.isPlaying()){
            mp.seekTo(this.getProgress());
        }
        return false;
    }
}
