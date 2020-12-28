package com.app.worldheritagetraveler.ui.places

import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import com.app.worldheritagetraveler.R
import com.app.worldheritagetraveler.data.models.FilterOptions
import com.app.worldheritagetraveler.databinding.DialogFilterBinding


/**
World Heritage Traveler
Created by Catalin on 8/29/2020
 **/
class FilterDialog(
    private val mListener: FilterListener,
    private val mFilterOptions: FilterOptions
) : DialogFragment() {

    companion object {
        const val TAG = "FilterDialog"
    }

    private lateinit var mBinding: DialogFilterBinding
    private lateinit var countries: Array<String>

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        mBinding = DataBindingUtil.inflate(
            requireActivity().layoutInflater,
            R.layout.dialog_filter, null, false
        )
        countries = requireContext().resources.getStringArray(R.array.countries)
        val adapter = ArrayAdapter(
            requireContext(),
            R.layout.country_spinner_item, countries
        )
        mBinding.filterCountrySpinner.adapter = adapter
        mBinding.filterCountrySpinner.setSelection(countries.indexOf(mFilterOptions.country))
        mBinding.filterFavoriteSwitch.isChecked = mFilterOptions.favorite
        mBinding.filterVisitedSwitch.isChecked = mFilterOptions.visited
        return AlertDialog.Builder(requireContext(), R.style.AppTheme_Dialog)
            .setTitle(R.string.filter)
            .setPositiveButton(
                R.string.apply,
                (DialogInterface.OnClickListener { d, _ ->
                    mFilterOptions.favorite = mBinding.filterFavoriteSwitch.isChecked
                    mFilterOptions.visited = mBinding.filterVisitedSwitch.isChecked
                    mFilterOptions.country =
                        countries[mBinding.filterCountrySpinner.selectedItemPosition]
                    mListener.onApply(mFilterOptions)
                    d.dismiss()
                })
            )
            .setNegativeButton(
                R.string.cancel,
                (DialogInterface.OnClickListener { d, _ ->
                    mListener.onCancel()
                    d.dismiss()
                })
            )
            .setView(mBinding.root)
            .create()
    }

    override fun onStart() {
        super.onStart()
        dialog?.window?.setLayout(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
    }

}