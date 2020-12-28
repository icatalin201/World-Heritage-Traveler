package com.app.worldheritagetraveler.ui.places

import android.content.Intent
import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.worldheritagetraveler.R
import com.app.worldheritagetraveler.data.models.FilterOptions
import com.app.worldheritagetraveler.data.models.Place
import com.app.worldheritagetraveler.data.models.SortOptions
import com.app.worldheritagetraveler.databinding.FragmentPlacesBinding
import com.app.worldheritagetraveler.ui.place.PlaceActivity
import org.koin.android.ext.android.inject

/**
World Heritage Traveler
Created by Catalin on 8/27/2020
 **/
class PlacesFragment : Fragment(), PlaceActionListener {

    private lateinit var mAdapter: PlacesAdapter
    private lateinit var mBinding: FragmentPlacesBinding
    private val mViewModel: PlacesViewModel by inject()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_places,
            container, false
        )
        setHasOptionsMenu(true)
        mAdapter = PlacesAdapter(this, requireContext())
        mBinding.placesRecyclerView.adapter = mAdapter
        val isTablet = requireContext().resources.getBoolean(R.bool.is_tablet)
        if (isTablet) {
            mBinding.placesRecyclerView.layoutManager = GridLayoutManager(requireContext(), 2)
        } else {
            mBinding.placesRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        }
        mViewModel.mPlaceList.observe(
            viewLifecycleOwner,
            { mAdapter.submitList(it.toMutableList()) })
        return mBinding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_filter, menu)
        inflater.inflate(R.menu.menu_sort, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.filter) {
            FilterDialog(object : FilterListener {
                override fun onApply(filterOptions: FilterOptions) {
                    mViewModel.applyFilter(filterOptions)
                }

                override fun onCancel() {

                }
            }, mViewModel.mFilterOptions)
                .show(childFragmentManager, FilterDialog.TAG)
        } else if (item.itemId == R.id.sort) {
            val alertDialog = AlertDialog
                .Builder(requireContext(), R.style.AppTheme_Dialog)
                .setTitle(R.string.sort)
                .setItems(
                    R.array.sort_options
                ) { dialog, position ->
                    mViewModel.applySort(SortOptions.ofPosition(position))
                    dialog.dismiss()
                }
                .create()
            alertDialog.show()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun open(place: Place) {
        val intent = Intent(requireContext(), PlaceActivity::class.java)
        intent.putExtra(PlaceActivity.PLACE, place.id)
        intent.putExtra(PlaceActivity.COUNTRY, place.country)
        startActivity(intent)
    }

}