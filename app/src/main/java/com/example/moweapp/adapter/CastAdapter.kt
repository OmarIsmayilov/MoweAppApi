package com.example.moweapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.moweapp.databinding.CastItemBinding
import com.example.moweapp.model.Cast
import com.example.moweapp.utils.Constants
import com.example.moweapp.utils.Constants.IMAGE_BASE
import com.squareup.picasso.Picasso

class CastAdapter  : RecyclerView.Adapter<CastAdapter.viewHolder>()  {
    private val castList = arrayListOf<Cast>()

    inner class viewHolder(val binding : CastItemBinding)  :RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): viewHolder {
        val design = CastItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return viewHolder(design)
    }

    override fun getItemCount(): Int {
        return castList.size
    }

    override fun onBindViewHolder(holder: viewHolder, position: Int) {
        val cast = castList[position]
        setData(cast,holder)

        }

    fun updateList(newList : List<Cast>){
        castList.clear()
        castList.addAll(newList)
        notifyDataSetChanged()
    }

    }

    fun setData(cast: Cast,holder : CastAdapter.viewHolder) {

        with(holder.binding){

            tvCastName.text = cast.name
            tvCastCharacter.text = cast.character
            Picasso.get().load(IMAGE_BASE +cast.profilePath).into(ivCast)
            ivCast.setOnClickListener{
                if (lyExtended.visibility == View.GONE){
                    lyExtended.visibility = View.VISIBLE
                }else{
                    lyExtended.visibility = View.GONE
                }


            }
    }

}

