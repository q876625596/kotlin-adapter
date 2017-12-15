package com.wuhenzhizao.adapter.example.ui

import android.widget.CheckBox
import android.widget.TextView
import android.widget.Toast
import com.google.gson.Gson
import com.wuhenzhizao.adapter.AbsListViewAdapter
import com.wuhenzhizao.adapter.ListViewAdapter
import com.wuhenzhizao.adapter.example.R
import com.wuhenzhizao.adapter.example.bean.Province
import com.wuhenzhizao.adapter.example.bean.ProvinceList
import com.wuhenzhizao.adapter.example.databinding.FragmentSingleTypeListViewBinding

/**
 * Created by liufei on 2017/12/13.
 */
class SingleTypeListViewFragment : BaseFragment<FragmentSingleTypeListViewBinding>() {
    private lateinit var adapter: AbsListViewAdapter<Province, *>

    override fun getContentViewId(): Int = R.layout.fragment_single_type_list_view

    override fun initViews() {
        val json = getString(R.string.provinces)
        val list = Gson().fromJson<ProvinceList>(json, ProvinceList::class.java)

        adapter = ListViewAdapter(context, list.provinceList)
                .match<Province>(R.layout.item_single_type_list_view)
                .viewHolderCreateInterceptor {

                }
                .viewHolderBindInterceptor { position, item, viewHolder ->
                    viewHolder.get<TextView>(R.id.tv).text = item.name
                    viewHolder.get<CheckBox>(R.id.cb).isChecked = item.checked
                }
                .clickInterceptor { position, item, vh ->
                    Toast.makeText(context, "position $position, ${item.name} clicked", Toast.LENGTH_SHORT).show()
                    adapter.items.forEachIndexed { index, province ->
                        province.checked = (index == position)
                    }
                    adapter.notifyDataSetChanged()
                }
        binding.lv.adapter = adapter
    }
}