package kth.pe.englishchattingproject

import android.app.Service
import android.content.Intent
import android.media.MediaPlayer
import android.os.IBinder



class MusicService : Service() , MediaPlayer.OnPreparedListener{

    lateinit var mediaPlayer : MediaPlayer
    var trackIndex = 0
    var trackResourceIds: HashMap<String, Int>? = null

    override fun onBind(p0: Intent?): IBinder? {
        return null
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {

        trackIndex = 1
        // in case you only have two tracks
        trackResourceIds = HashMap()
        trackResourceIds?.put("1", R.raw.dialog_1_to_10)

        mediaPlayer = MediaPlayer();

        mediaPlayer.setOnPreparedListener {  this }
        mediaPlayer.prepareAsync()

        return super.onStartCommand(intent, flags, startId)
    }

    override fun onPrepared(p0: MediaPlayer?) {
        mediaPlayer.start()
    }

    override fun onDestroy() {
        mediaPlayer.stop()
        mediaPlayer.release()
        super.onDestroy()
    }

    fun getNextTrack(): Int {
        // in case you only have two tracks
        trackIndex = if (trackIndex > 1) 1 else trackIndex
        return trackResourceIds?.get("${trackIndex++}") ?: 0

    }

}