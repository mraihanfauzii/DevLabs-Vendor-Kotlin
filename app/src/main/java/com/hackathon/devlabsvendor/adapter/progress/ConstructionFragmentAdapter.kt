package com.hackathon.devlabsvendor.adapter.progress

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.hackathon.devlabsvendor.ui.main.construction.SedangDikerjakanFragment
import com.hackathon.devlabsvendor.ui.main.construction.SelesaiFragment

class ConstructionFragmentAdapter(fm: FragmentManager, lifecycle: Lifecycle, data: Bundle) :
    FragmentStateAdapter(fm, lifecycle) {
    private var fragmentBundle: Bundle

    init {
        fragmentBundle = data
    }

    override fun getItemCount(): Int = 3

    override fun createFragment(position: Int): Fragment {
        var fragment = Fragment()
        when (position) {
            0 -> fragment = SedangDikerjakanFragment()
            1 -> fragment = SelesaiFragment()
        }
        fragment.arguments = this.fragmentBundle
        return fragment
    }
}