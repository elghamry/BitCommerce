package sa.biotic.app.utils

import android.content.Context
import android.widget.TextView
import com.afollestad.materialdialogs.DialogBehavior
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.ModalDialog
import com.afollestad.materialdialogs.customview.customView
import com.afollestad.materialdialogs.customview.getCustomView
import com.airbnb.lottie.LottieAnimationView
import com.google.android.material.button.MaterialButton
import sa.biotic.app.R

object ErrorDialog {

    fun showCustomViewDialog(
        dialogBehavior: DialogBehavior = ModalDialog,
        context: Context,
        custView: Int,
        msg: String
    ) {
        val dialog = MaterialDialog(context, dialogBehavior).show {
            //            title(R.string.googleWifi)
            cornerRadius(16f)
                .noAutoDismiss()
//            cancelable(false)  // calls setCancelable on the underlying dialog
            cancelOnTouchOutside(false)  // calls setCanceledOnTouchOutside on the underlying dialog

            customView(
                custView,
                scrollable = false,
                horizontalPadding = true
            )
        }


        // Setup custom view content
        val customView = dialog.getCustomView()
        val lottieDone = customView.findViewById<LottieAnimationView>(R.id.lottie_done)
        val errorMsg = customView.findViewById<TextView>(R.id.tvErrorMsg)
        errorMsg.text = msg

        lottieDone.setAnimation("error.json")
//        binding.lottieConnection.setColorFilter(R.color.purple)

        lottieDone.playAnimation()
        lottieDone.loop(true)

        val okbtn: MaterialButton = customView.findViewById(R.id.ok_btn)
        okbtn.setOnClickListener {
            dialog.dismiss()

//                Navigation.findNavController(binding.root).popBackStack(R.id.profileFragment,false)
        }


    }

}