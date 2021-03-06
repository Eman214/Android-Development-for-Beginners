package com.example.android.miwok;

import androidx.appcompat.app.AppCompatActivity;
import androidx.media.AudioAttributesCompat;
import androidx.media.AudioFocusRequestCompat;
import androidx.media.AudioManagerCompat;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

public class FamilyActivity extends AppCompatActivity {

    //Handles the playing of all audio files
    private MediaPlayer mMediaPlayer;
    AudioManager.OnAudioFocusChangeListener mAudioFocusChangeListener =
            new AudioManager.OnAudioFocusChangeListener() {
                public void onAudioFocusChange(int focusChange) {
                    if (focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT ||
                            focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK) {
                        // Pause playback because your Audio Focus was temporarily stolen, but will be back soon.
                        mMediaPlayer.pause();
                        mMediaPlayer.seekTo(0);
                    } else if (focusChange == AudioManager.AUDIOFOCUS_LOSS) {
                        // Stop playback, because you lost the Audio Focus.
                        // Release media resources
                        mMediaPlayer.release();
                    } else if (focusChange == AudioManager.AUDIOFOCUS_GAIN) {
                        // Audio focus has been regained, resume playback
                        mMediaPlayer.start();
                    }
                }
            };
    //Handles audio focus while playing audio files
    private AudioManager mAudioManager;
    private AudioFocusRequestCompat mAudioFocusRequest;

    /**
     * This listener gets triggered when the {@link MediaPlayer} has completed
     * playing the audio file.
     */
    private MediaPlayer.OnCompletionListener mCompletionListener = new MediaPlayer.OnCompletionListener() {
        @Override
        public void onCompletion(MediaPlayer mMediaPlayer) {
            //releases audion in media players
            releaseMediaPlayer();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.word_list);

        //Setup the {@link AudioManager} to request audio focus
        mAudioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);

        AudioAttributesCompat audioAttributes = new AudioAttributesCompat.Builder()
                .setContentType(AudioAttributesCompat.CONTENT_TYPE_SPEECH)
                .setUsage(AudioAttributesCompat.USAGE_MEDIA)
                .build();

        mAudioFocusRequest = new AudioFocusRequestCompat
                .Builder(AudioManagerCompat.AUDIOFOCUS_GAIN_TRANSIENT)
                .setAudioAttributes(audioAttributes)
                .setOnAudioFocusChangeListener(mAudioFocusChangeListener)
                .build();

        //Creates a list  of words
        final ArrayList<Word> words = new ArrayList<>();

        //Assign values to objects in arraylist.
        words.add(new Word("father", "әpә", R.drawable.family_father, R.raw.family_father));
        words.add(new Word("mother", "әṭa", R.drawable.family_mother, R.raw.family_mother));
        words.add(new Word("son", "angsi", R.drawable.family_son, R.raw.family_son));
        words.add(new Word("daughter", "tune", R.drawable.family_daughter, R.raw.family_daughter));
        words.add(new Word("older brother", "taachi", R.drawable.family_older_brother, R.raw.family_older_brother));
        words.add(new Word("younger brother","chalitti", R.drawable.family_younger_brother, R.raw.family_younger_brother));
        words.add(new Word("older sister", "teṭe", R.drawable.family_older_sister, R.raw.family_older_sister));
        words.add(new Word("younger sister", "kolliti", R.drawable.family_younger_sister, R.raw.family_younger_sister));
        words.add(new Word("grandmother", "ama", R.drawable.family_grandmother, R.raw.family_grandmother));
        words.add(new Word("grandfather", "paapa", R.drawable.family_grandfather, R.raw.family_grandfather));


        // Create an {@link ArrayAdapter}, whose data source is a list of Strings. The
        // adapter knows how to create layouts for each item in the list.
        WordAdapter adapter = new WordAdapter(this, words, R.color.category_family);

        // Get a reference to the ListView, and attach the adapter.
        ListView listView = findViewById(R.id.list);
        listView.setAdapter(adapter);

        //Sets on click listener for each item in the array list
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //Release media player it it currently exists
                releaseMediaPlayer();

                //gets the word object at the given position clicked byt the user
                Word word = words.get(position);

                // Request audio focus for playback
                int result = AudioManagerCompat.requestAudioFocus(mAudioManager, mAudioFocusRequest);


                if (result == AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {
                    //Successfully gain audio focus
                    //Sets up the media player for the audio resource associated with the current word object
                    mMediaPlayer = MediaPlayer.create(FamilyActivity.this, word.getAudioResourceId());

                    //Start playback
                    mMediaPlayer.start();

                    //Checks if the audio has finished and then releases it
                    mMediaPlayer.setOnCompletionListener(mCompletionListener);
                }
            }
        });
    }

    @Override
    protected void onStop() {
        super.onStop();
        //When the activity is stopped, releases the audio resources in the media player
        releaseMediaPlayer();
    }


    /**
     * Clean up the media player by releasing its resources.
     */
    private void releaseMediaPlayer() {
        // If the media player is not null, then it may be currently playing a sound.
        if (mMediaPlayer != null) {
            // Regardless of the current state of the media player, release its resources
            // because we no longer need it.
            mMediaPlayer.release();

            // Set the media player back to null. For our code, we've decided that
            // setting the media player to null is an easy way to tell that the media player
            // is not configured to play an audio file at the moment.
            mMediaPlayer = null;
            AudioManagerCompat.abandonAudioFocusRequest(mAudioManager, mAudioFocusRequest);
        }

    }
}