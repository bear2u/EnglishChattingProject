package kth.pe.englishchattingproject

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    val mItems = ArrayList<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mItems.addAll(resources.getStringArray(R.array.array_english))

        initRecyclerView()
    }

    private fun initRecyclerView(){
        LinearLayoutManager(this).run {
            val listAdapter = ListAdapter()
            main_recycler_view.layoutManager = this
            main_recycler_view.adapter = listAdapter
            DividerItemDecoration(this@MainActivity , this.orientation).let {
                main_recycler_view.addItemDecoration(it)
            }
        }

    }

    inner class ListAdapter : RecyclerView.Adapter<ListAdapter.ViewHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ListAdapter.ViewHolder{
            val layoutInflater = LayoutInflater.from( this@MainActivity )
            return ViewHolder(layoutInflater.inflate(android.R.layout.simple_list_item_1 , parent, false))
        }

        override fun onBindViewHolder(holder: ListAdapter.ViewHolder, position: Int) {
            with(mItems[position]){
                holder.bindItem(this)
            }

            holder.itemView.setOnClickListener {
                val intent = Intent(this@MainActivity , ChatActivity::class.java)
                intent.putExtra("pos" , position)
                startActivity(intent)
            }
        }

        override fun getItemCount(): Int  = mItems.size

        inner class ViewHolder(private val view : View) : RecyclerView.ViewHolder(view) {
            fun bindItem(item : String) {
                val txtItemView = view.findViewById<TextView>(android.R.id.text1)
                txtItemView?.text = item
            }
        }
    }
}
