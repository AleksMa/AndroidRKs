package com.bmstu.iu9.swimrunners.androidrk1.fragments

import android.app.Activity
import android.app.Dialog
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.widget.RadioButton
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import com.bmstu.iu9.swimrunners.androidrk1.R
import com.bmstu.iu9.swimrunners.androidrk1.databinding.CryptoPickerBinding

class CryptoPickerFragment : DialogFragment() {
    companion object {
        const val EXTRA_CRYPTO_TYPE = "com.bmstu.iu9.swimrunners.androidrk1.fragments.cryptotype"
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val binding = CryptoPickerBinding.inflate(layoutInflater, null, false)

        return AlertDialog.Builder(requireContext())
            .setView(binding.root)
            .setTitle(getString(R.string.choose_crypto_type))
            .setPositiveButton(getString(R.string.ok)) { _: DialogInterface, _: Int ->
                val checked = binding.root.findViewById<RadioButton>(
                    binding.cryptoTypeRadio.checkedRadioButtonId
                )

                sendResult(checked.text.toString())
            }
            .create()
    }

    private fun sendResult(type: String) {
        if (targetFragment == null) {
            return
        }

        val intent = Intent()
        intent.putExtra(EXTRA_CRYPTO_TYPE, type)

        targetFragment!!.onActivityResult(targetRequestCode, Activity.RESULT_OK, intent)
    }
}
