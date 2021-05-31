package com.example.kenshuu.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.example.kenshuu.R
import com.example.kenshuu.model.Count

class CountAdapter(var context: Context, var listCount: List<Count>) : BaseAdapter() {
    class ViewHolder(row: View) {
        var tvAuthorityName: TextView
        var tvMale: TextView
        var tvFemale: TextView
        var tvNullGender: TextView
        var tvUnder19: TextView
        var tvOver20: TextView
        var tvNullAge: TextView

        init {
            tvAuthorityName = row.findViewById(R.id.tvAuthorityName) as TextView
            tvMale = row.findViewById(R.id.tvMale) as TextView
            tvFemale = row.findViewById(R.id.tvFemale) as TextView
            tvNullGender = row.findViewById(R.id.tvNullGender) as TextView
            tvUnder19 = row.findViewById(R.id.tvUnder19) as TextView
            tvOver20 = row.findViewById(R.id.tvOver20) as TextView
            tvNullAge = row.findViewById(R.id.tvNullAge) as TextView
        }
    }

    override fun getCount(): Int {
        return listCount.size
    }

    override fun getItem(position: Int): Any {
        return listCount.get(position)
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        var view: View?
        var viewholder: CountAdapter.ViewHolder
        if (convertView == null) {
            var layoutinflater: LayoutInflater = LayoutInflater.from(context)
            view = layoutinflater.inflate(R.layout.count_record, null)
            viewholder = CountAdapter.ViewHolder(view)
            view.tag = viewholder
        } else {
            view = convertView
            viewholder = convertView.tag as CountAdapter.ViewHolder
        }
        var count: Count = getItem(position) as Count
        viewholder.tvAuthorityName.text = count.authorityName
        viewholder.tvMale.text = count.male.toString()
        viewholder.tvFemale.text = count.female.toString()
        viewholder.tvNullGender.text = count.nullGender.toString()
        viewholder.tvUnder19.text = count.under19.toString()
        viewholder.tvOver20.text = count.over20.toString()
        viewholder.tvNullAge.text = count.nullAge.toString()
        return view as View
    }
}