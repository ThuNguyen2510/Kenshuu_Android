package com.example.kenshuu.adapters

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import com.example.kenshuu.R
import com.example.kenshuu.model.DtUser
import okhttp3.internal.toLongOrDefault
import org.w3c.dom.Text

class UserAdapter(var context: Context, var listUser: List<DtUser>) : BaseAdapter() {

    class ViewHolder(row : View){
        var tvStt: TextView
        var tvUserId: TextView
        var tvFullName: TextView
        var tvAuthorityName: TextView
        init{
            tvStt = row.findViewById(R.id.tvStt) as TextView
            tvUserId = row.findViewById(R.id.tvStt) as TextView
            tvFullName = row.findViewById(R.id.tvFullName) as TextView
            tvAuthorityName = row.findViewById(R.id.tvAuthorityName) as TextView
        }
    }
    override fun getCount(): Int {
        return listUser.size
    }

    override fun getItem(position: Int): Any {
        return listUser.get(position)
    }

    override fun getItemId(position: Int): Long {
        return  position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        var view : View?
        var viewholder : ViewHolder
        if(convertView==null){
            var layoutinflater : LayoutInflater = LayoutInflater.from(context)
            view =  layoutinflater.inflate(R.layout.user_record,null)
            viewholder = ViewHolder(view)
            view.tag = viewholder
        }else{
            // run lan 2
            view = convertView
            viewholder= convertView.tag as ViewHolder
        }
        var user : DtUser = getItem(position) as DtUser
        viewholder.tvStt.text=position.toString()
        viewholder.tvUserId.text= user.userId
        viewholder.tvFullName.text= user.familyName +" "+user.firstName
        viewholder.tvAuthorityName.text= user.role?.authorityName
        return view as View
    }
}