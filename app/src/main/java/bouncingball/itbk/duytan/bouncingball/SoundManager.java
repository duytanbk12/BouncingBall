package bouncingball.itbk.duytan.bouncingball;

import android.app.Activity;
import android.content.Context;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Build;

public class SoundManager {
    private static final int MAX_STREAMS = 5;
    private static final int streamType = AudioManager.STREAM_MUSIC;

    private SoundPool soundPool;
    private AudioManager audioManager;

    private boolean loaded;

    private int soundIdHit;
    private int soundIdHitl;
    private float volume;

    public SoundManager(Activity main) {

        audioManager = (AudioManager) main.getSystemService(Context.AUDIO_SERVICE);
        float currentVolumeIndex = (float) audioManager.getStreamVolume(streamType);
        float maxVolumeIndex = (float) audioManager.getStreamMaxVolume(streamType);
        this.volume = currentVolumeIndex / maxVolumeIndex;

        if (Build.VERSION.SDK_INT >= 21) {
            AudioAttributes audioAttrib = new AudioAttributes.Builder()
                    .setUsage(AudioAttributes.USAGE_GAME)
                    .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                    .build();

            SoundPool.Builder builder = new SoundPool.Builder();
            builder.setAudioAttributes(audioAttrib).setMaxStreams(MAX_STREAMS);

            this.soundPool = builder.build();
        } else {
            this.soundPool = new SoundPool(MAX_STREAMS, AudioManager.STREAM_MUSIC, 0);
        }
        this.soundPool.setOnLoadCompleteListener(new SoundPool.OnLoadCompleteListener() {
            @Override
            public void onLoadComplete(SoundPool soundPool, int sampleId, int status) {
                loaded = true;
            }
        });

        this.soundIdHit = this.soundPool.load(main, R.raw.hitbat, 1);
        this.soundIdHitl = this.soundPool.load(main, R.raw.hitbatl, 1);

    }

    public void playHit() {
        if (loaded) {
            float leftVolumn = volume;
            float rightVolumn = volume;
            int streamId = this.soundPool.play(this.soundIdHit, leftVolumn, rightVolumn, 0, 0, 1f);
        }
    }
    public void playHitl() {
        if (loaded) {
            float leftVolumn = volume;
            float rightVolumn = volume;
            int streamId = this.soundPool.play(this.soundIdHitl, leftVolumn, rightVolumn, 0, 0, 1f);
        }
    }
}

