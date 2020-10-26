package com.bmstu.iu9.swimrunners.androidrk1.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.navArgs
import com.bmstu.iu9.swimrunners.androidrk1.R
import com.bmstu.iu9.swimrunners.androidrk1.databinding.FragmentExtraInfoBinding
import com.bmstu.iu9.swimrunners.androidrk1.viewModels.RestCoinViewModel

class ExtraInfoFragment : Fragment() {
    private var _binding: FragmentExtraInfoBinding? = null
    private val binding get() = _binding!!

    private val args: ExtraInfoFragmentArgs by navArgs()
    private val vm: RestCoinViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentExtraInfoBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        vm.timeseries.observe(viewLifecycleOwner, { timeseries ->
            run {
                binding.extraInfoTitle.text = timeseries[args.index].date
                binding.extraInfoMax.text =
                    getString(R.string.extra_info_max_title) + timeseries[args.index].priceHigh
                binding.extraInfoMin.text =
                    getString(R.string.extra_info_min_title) + timeseries[args.index].priceLow
                binding.extraInfoTrades.text =
                    getString(R.string.extra_info_trades_title) + timeseries[args.index].tradesCount.toString()
            }
        })
    }
}