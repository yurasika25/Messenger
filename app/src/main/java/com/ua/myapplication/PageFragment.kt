package com.ua.myapplication

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.core.app.NotificationCompat
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_page.*

class PageFragment : Fragment(R.layout.fragment_page) {

    private var pageNumber = -1
    private lateinit var callback: OnPlusMinusCallback

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        pageNumber = requireArguments().getInt(ARGUMENT_PAGE_NUMBER)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpUI()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnPlusMinusCallback) {
            callback = context
        } else {
            throw IllegalArgumentException("$context must implement OnPlusMinusCallback")
        }
    }

    private fun setUpUI() {
        tvPage.text = "$pageNumber"
        plusBT.setOnClickListener {
            callback.onPlus()
        }
        minusBT.setOnClickListener {
            callback.onMinus()
        }
//        val notificationManager = requireActivity().getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        createBT.setOnClickListener {
//            val intent = Intent(requireActivity(), MainActivity::class.java)
//            val pendingIntent = PendingIntent.getActivity(requireContext(),
//                    0, intent, PendingIntent.FLAG_UPDATE_CURRENT)
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//                val notificationChannel = NotificationChannel(channelId, description,
//                        NotificationManager.IMPORTANCE_HIGH)
//                notificationChannel.enableLights(true)
//                notificationChannel.lightColor = Color.GREEN
//                notificationChannel.enableVibration(false)
//                notificationManager.createNotificationChannel(notificationChannel)
//            }
//            val builder = NotificationCompat.Builder(requireContext(), channelId)
//                    .setSmallIcon(R.drawable.notifications)
//                    .setLargeIcon(BitmapFactory.decodeResource(this.resources, R.drawable.notifications))
//                    .setContentIntent(pendingIntent)
//            notificationManager.notify(pageNumber, builder.build())
        }
    }

    companion object {
        private const val ARGUMENT_PAGE_NUMBER = "arg_page_number"
        private const val channelId = "i.apps.notifications"
        private const val description = "Test notification"

        fun newInstance(page: Int): PageFragment {
            val pageFragment = PageFragment()
            val arguments = Bundle()
            arguments.putInt(ARGUMENT_PAGE_NUMBER, page)
            pageFragment.arguments = arguments
            return pageFragment
        }
    }
}

interface OnPlusMinusCallback {
    fun onPlus()
    fun onMinus()
}