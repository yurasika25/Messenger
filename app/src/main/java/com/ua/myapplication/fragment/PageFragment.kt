package com.ua.myapplication.fragment

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.core.app.NotificationCompat
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import com.ua.myapplication.R
import com.ua.myapplication.`interface`.OnPlusMinusCallback
import com.ua.myapplication.constants.Constants.ARGUMENT_PAGE_NUMBER
import com.ua.myapplication.constants.Constants.channelId
import com.ua.myapplication.constants.Constants.description
import com.ua.myapplication.main.KEY_PAGE
import com.ua.myapplication.main.MainActivity
import kotlinx.android.synthetic.main.fragment_page.*

class PageFragment : Fragment(R.layout.fragment_page) {

    private var pageNumber = -1
    private lateinit var callback: OnPlusMinusCallback
    private lateinit var notification: NotificationManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        pageNumber = requireArguments().getInt(ARGUMENT_PAGE_NUMBER)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpUI()
        visibilityFun()
    }

    private fun visibilityFun() {
        if (tvPage.text.toString().toInt() > 1)
            minusBT.visibility = View.VISIBLE
        else
            minusBT.visibility = View.INVISIBLE
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
            notification.cancel(pageNumber)

        }

        notification =
            requireActivity().getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        create_new.setOnClickListener {
            val intent = Intent(requireActivity(), MainActivity::class.java)
                .putExtra(KEY_PAGE, pageNumber)
            val pendingIntent = PendingIntent.getActivity(
                requireContext(),
                pageNumber, intent, PendingIntent.FLAG_UPDATE_CURRENT
            )
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                val notificationChannel = NotificationChannel(
                    channelId, description,
                    NotificationManager.IMPORTANCE_HIGH
                )
                notification.createNotificationChannel(notificationChannel)
            }

            val builder = NotificationCompat.Builder(requireContext(), channelId)
                .setSmallIcon(R.drawable.ic_notification_small)
                .setContentTitle(context?.getString(R.string.notification_title))
                .setContentText("Notification $pageNumber")
                .setColor(
                    ResourcesCompat.getColor(
                        resources,
                        R.color.color_text_notification,
                        null
                    )
                )
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setAutoCancel(true)
                .setLargeIcon(
                    BitmapFactory.decodeResource(
                        this.resources, R.drawable.ic_foreground
                    )
                )
                .setContentIntent(pendingIntent)
            notification.notify(pageNumber, builder.build())
        }
    }

    companion object {

        fun newInstance(page: Int): PageFragment {
            val pageFragment = PageFragment()
            val arguments = Bundle()
            arguments.putInt(ARGUMENT_PAGE_NUMBER, page)
            pageFragment.arguments = arguments
            return pageFragment
        }
    }
}
