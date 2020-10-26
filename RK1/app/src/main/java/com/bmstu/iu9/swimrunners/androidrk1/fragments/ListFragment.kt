package com.bmstu.iu9.swimrunners.androidrk1.fragments

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bmstu.iu9.swimrunners.androidrk1.databinding.FragmentListBinding
import com.bmstu.iu9.swimrunners.androidrk1.databinding.ListItemBinding
import com.bmstu.iu9.swimrunners.androidrk1.models.DayTrades
import com.bmstu.iu9.swimrunners.androidrk1.viewModels.RestCoinViewModel

class ListFragment : Fragment() {
    companion object {
        const val REQUEST_CRYPTO_TYPE = 1
        const val DIALOG_CRYPTO_TAG = "CryptoType"
        var currentCryptoType: String = "BTC"
    }

    private var _binding: FragmentListBinding? = null
    private val binding get() = _binding!!

    private val vm: RestCoinViewModel by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        vm.loadTimeseries(currentCryptoType)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentListBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.tradesList.layoutManager = LinearLayoutManager(context)
        binding.tradesList.adapter = TradesAdapter()

        val tradesAdapter = binding.tradesList.adapter as TradesAdapter

        vm.error.observe(viewLifecycleOwner, { e ->
            run {
                binding.error.visibility = View.VISIBLE
                binding.error.text = e
            }
        })

        vm.timeseries.observe(viewLifecycleOwner, { timeseries ->
            run {
                binding.error.visibility = View.GONE
                binding.error.text = ""
                tradesAdapter.timeseries = timeseries
            }
        })

        binding.cryptoPickBtn.text = currentCryptoType
        binding.cryptoPickBtn.setOnClickListener {
            val dialog = CryptoPickerFragment()
            dialog.setTargetFragment(this, REQUEST_CRYPTO_TYPE)
            dialog.show(parentFragmentManager, DIALOG_CRYPTO_TAG)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (resultCode != Activity.RESULT_OK || data == null) {
            return
        }

        if (requestCode == REQUEST_CRYPTO_TYPE) {
            val type = data.getStringExtra(CryptoPickerFragment.EXTRA_CRYPTO_TYPE)!!
            currentCryptoType = type
            binding.cryptoPickBtn.text = currentCryptoType
            vm.loadTimeseries(currentCryptoType)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private class TradesHolder(binding: ListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        private val root = binding.root
        private val dateTextView = binding.date
        private val priceHighTextView = binding.priceHigh

        fun bind(dayTrade: DayTrades) {
            dateTextView.text = dayTrade.date
            priceHighTextView.text = dayTrade.priceHigh.toString()

            // TODO: Add navigation
//            root.setOnClickListener { v ->
//                val action = ListFragmentDirections.actionListFragmentToArtistFragment(artist.id)
//                v.findNavController().navigate(action)
//            }
        }
    }

    private class TradesAdapter : RecyclerView.Adapter<TradesHolder>() {
        var timeseries: List<DayTrades> = arrayListOf()
            set(value) {
                field = value
                this.notifyDataSetChanged()
            }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TradesHolder {
            val inflater = LayoutInflater.from(parent.context)
            val binding = ListItemBinding.inflate(inflater, parent, false)

            return TradesHolder(binding)
        }

        override fun onBindViewHolder(holder: TradesHolder, position: Int) {
            holder.bind(timeseries[position])
        }

        override fun getItemCount(): Int {
            return timeseries.size
        }
    }
}
