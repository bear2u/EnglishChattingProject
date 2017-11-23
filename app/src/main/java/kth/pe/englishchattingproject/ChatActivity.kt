package kth.pe.englishchattingproject

import android.content.Intent
import android.media.MediaPlayer
import android.os.Bundle
import android.os.Handler
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_chat.*

class ChatActivity : AppCompatActivity() {
    val mItems = ArrayList<ChatItems>()
    var mpPlayer = MediaPlayer()
    var mpPlayerStates = States.STOP

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)

        intent.getIntExtra("pos" , 0).let {
            var source = 0
            when (it) {
                0 -> source = R.raw.dialog_1_to_10
            }

            Log.d("KTH" , "ok run")
            mpPlayer = MediaPlayer.create( this@ChatActivity , source)


        }


        mItems.add(ChatItems( 1 , 1 , 1))
        initRecyclerView()
    }

    private fun initRecyclerView(){
        LinearLayoutManager(this).run {
            val listAdapter = ListAdapter()
            messagesContainer.layoutManager = this
            messagesContainer.adapter = listAdapter
        }
    }

    override fun onDestroy() {
        stopPlaying()
        mpPlayer.release()
        super.onDestroy()
    }

    fun stopPlaying(){
        if(mpPlayerStates == States.PLAY){
            mpPlayer.stop()
            mpPlayerStates = States.STOP
        }
    }

    inner class ListAdapter : RecyclerView.Adapter<ListAdapter.ViewHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ListAdapter.ViewHolder{
            val layoutInflater = LayoutInflater.from( this@ChatActivity )
            return ViewHolder(layoutInflater.inflate( R.layout.item_bubble , parent, false))
        }

        override fun onBindViewHolder(holder: ListAdapter.ViewHolder, position: Int) {
            with(mItems[position]){
                holder.bindItem(this)
            }

            holder.itemView.setOnClickListener {
                if(mpPlayerStates == States.PLAY){
                    mpPlayer.pause()
                    mpPlayerStates = States.PAUSE

                    return@setOnClickListener
                }

                mpPlayer.isLooping = false
                mpPlayer.seekTo(2000)
                mpPlayer.start()
                mpPlayerStates = States.PLAY


            }
        }

        override fun getItemCount(): Int  = mItems.size

        inner class ViewHolder(private val view : View) : RecyclerView.ViewHolder(view) {
            fun bindItem(item : ChatItems) {
                val txtItemView = view.findViewById<TextView>( R.id.txtMessage)
                txtItemView?.text = item.start_pos.toString()
            }
        }
    }
}
