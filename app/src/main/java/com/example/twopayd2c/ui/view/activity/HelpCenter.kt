package com.example.twopayd2c.activity

import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Bundle
import android.view.ViewGroup
import android.view.Window.FEATURE_NO_TITLE
import android.view.WindowManager
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.media3.common.MediaItem
import androidx.media3.common.util.UnstableApi
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.ui.PlayerView
import com.example.twopayd2c.R
import com.example.twopayd2c.adapter.HelpTopicsAdapter
import com.example.twopayd2c.adapter.VideoAdapter
import com.example.twopayd2c.data.model.HelpPointsModel
import com.example.twopayd2c.data.model.VideoItemModel
import com.example.twopayd2c.databinding.ActivityHelpCenterBinding
import com.example.twopayd2c.ui.view.activity.TransactionHistory


@UnstableApi
class HelpCenter : AppCompatActivity(), VideoAdapter.OnItemClickListener {
    lateinit var binding: ActivityHelpCenterBinding
    lateinit var videoAdapter: VideoAdapter
    lateinit var helpTopicsAdapter: HelpTopicsAdapter
    var helpPointsList: MutableList<HelpPointsModel> = mutableListOf()
    lateinit var dialog : Dialog

    lateinit var exoPlayer : ExoPlayer

    val videoList = listOf(
        VideoItemModel(R.raw.demovideo,"Your money, protected with bank-grade security and instant transactions."),
        VideoItemModel(R.raw.demovideo,"Experience lightning-fast payments with zero hassle."),
        VideoItemModel(R.raw.demovideo,"Fast. Secure. Seamless payments—anytime, anywhere.")
    )


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityHelpCenterBinding.inflate(layoutInflater)
        val secondaryBlue = ContextCompat.getColor(this, R.color.white)
        binding.main.setBackgroundColor(secondaryBlue)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { view, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            val imeInsets = insets.getInsets(WindowInsetsCompat.Type.ime())
            val bottomPadding = Math.max(systemBars.bottom, imeInsets.bottom)

            // APPLY TOP PADDING: This prevents the overlap with the status bar
            view.setPadding(
                systemBars.left,
                systemBars.top,   // This fixes the overlap
                systemBars.right,
                bottomPadding
            )
            insets
        }

        // Create player
        exoPlayer = ExoPlayer.Builder(this).build()
        loadVideoInView()
        setHelpToipcs()
        setOnClickListner()

    }

    fun setOnClickListner(){
        binding.backicon.setOnClickListener {
            val intent = Intent(this, DashBoard::class.java)
            intent.putExtra("NAVIGATION_TARGET", "HOME")
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
            startActivity(intent)
            finish()
        }

        binding.myticketHelplayout.setOnClickListener {
            startActivity(Intent(this@HelpCenter, com.example.twopayd2c.ui.view.activity.MyTickets::class.java))
        }

        binding.transactionHelplayout.setOnClickListener {
            startActivity(Intent(this@HelpCenter, TransactionHistory::class.java))
        }

    }

    fun loadVideoInView(){
        videoAdapter = VideoAdapter(this, videoList,this)
        binding.showVideoinlist.adapter = videoAdapter
        binding.showVideoinlist.setHasFixedSize(true)

    /*  val snapHelper = PagerSnapHelper()
        snapHelper.attachToRecyclerView(binding.showVideoinlist)

        binding.showVideoinlist.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)

                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    val layoutManager = recyclerView.layoutManager as LinearLayoutManager

                    // Get the range of visible items
                    val firstVisible = layoutManager.findFirstVisibleItemPosition()
                    val lastVisible = layoutManager.findLastVisibleItemPosition()

                    // Get the position currently playing in the adapter
                    val playingPos = videoAdapter.getCurrentPlayingPosition()

                    // If the playing video is no longer visible, stop it
                    if (playingPos != RecyclerView.NO_POSITION && (playingPos < firstVisible || playingPos > lastVisible)) {
                        videoAdapter.stopCurrentVideo()
                    }
                }
            }
        })*/


    }


    fun setHelpToipcs(){
        helpPointsList.clear()
        helpPointsList.add(HelpPointsModel(resources.getString(R.string.topup_your_wallet),resources.getString(R.string.easily_add_money)))
        helpPointsList.add(HelpPointsModel(resources.getString(R.string.raistickettitle),resources.getString(R.string.raisetickettxt)))
        helpPointsList.add(HelpPointsModel(resources.getString(R.string.promocode),resources.getString(R.string.offerpromocode)))
        helpPointsList.add(HelpPointsModel(resources.getString(R.string.qrcodetitle),resources.getString(R.string.uniqueqrcode)))
        helpPointsList.add(HelpPointsModel(resources.getString(R.string.travelbooking),resources.getString(R.string.travelpolicy)))
        helpPointsList.add(HelpPointsModel(resources.getString(R.string.transactiontitle),resources.getString(R.string.transactionhistory)))

        helpTopicsAdapter= HelpTopicsAdapter(this@HelpCenter,helpPointsList)
        binding.helptopics.adapter = helpTopicsAdapter
        helpTopicsAdapter.notifyDataSetChanged()
    }


    fun openDialogForVideoFullScreen(uri: Uri){
        dialog = Dialog(this@HelpCenter, android.R.style.Theme_Black_NoTitleBar_Fullscreen)
        dialog.requestWindowFeature(FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.fullscreen_video_layout)

        dialog.window?.apply {
            setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
            setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)
        }

        dialog.setCanceledOnTouchOutside(false)

        var playerView = dialog.findViewById<PlayerView>(R.id.player_screen)
        var cancel = dialog.findViewById<ImageView>(R.id.cancel)
        playerView.requestFocus()

        playerView.player = exoPlayer

        // MediaItem
        val mediaItem = MediaItem.fromUri(uri)

        // Prepare & play
        exoPlayer.setMediaItem(mediaItem)
        exoPlayer.playWhenReady = true
        exoPlayer.prepare()


        cancel.setOnClickListener {
            dialog.dismiss()
        }

        dialog.show()
    }


    override fun onDestroy() {
        super.onDestroy()
        exoPlayer.release()
    }

    override fun onItemClick(position: Int,uri: Uri) {
        openDialogForVideoFullScreen(uri)
    }


}