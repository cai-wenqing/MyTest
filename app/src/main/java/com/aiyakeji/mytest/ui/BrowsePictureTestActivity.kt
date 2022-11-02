package com.aiyakeji.mytest.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.view.View
import android.widget.ImageView
import com.aiyakeji.mytest.R
import com.aiyakeji.mytest.widgets.BrowsePictureDialog
import com.bumptech.glide.Glide

/**
 * @author CWQ
 * @date 2020/2/26
 * 查看大图测试
 */
class BrowsePictureTestActivity : AppCompatActivity(), View.OnClickListener {

    private val imgs = arrayListOf(
            "https://f12.baidu.com/it/u=1500781322,3886438953&fm=173&app=49&f=JPEG?w=507&h=359&s=B68241A554669D1D35245DB203008010&access=215967316",
            "https://f10.baidu.com/it/u=1670567169,162960745&fm=173&app=49&f=JPEG?w=444&h=342&s=BF13E14C4508A55D5CCE5D970300308C&access=215967316",
            "https://f12.baidu.com/it/u=1858341053,3233729781&fm=173&app=49&f=JPEG?w=466&h=345&s=1503B85C8E01115737CA8A900300F09F&access=215967316",
            "https://f12.baidu.com/it/u=3288303476,1645217648&fm=173&app=49&f=JPEG?w=483&h=361&s=EAE22DC56830109C6A99899703001090&access=215967316")
    private lateinit var iv_pic1:ImageView
    private lateinit var iv_pic2:ImageView
    private lateinit var iv_pic3:ImageView
    private lateinit var iv_pic4:ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_browse_picture_test)
        iv_pic1 = findViewById(R.id.iv_pic1)
        iv_pic2 = findViewById(R.id.iv_pic2)
        iv_pic3 = findViewById(R.id.iv_pic3)
        iv_pic4 = findViewById(R.id.iv_pic4)

        iv_pic1.setOnClickListener(this)
        iv_pic2.setOnClickListener(this)
        iv_pic3.setOnClickListener(this)
        iv_pic4.setOnClickListener(this)
        Glide.with(this).load(imgs[0]).into(iv_pic1)
        Glide.with(this).load(imgs[1]).into(iv_pic2)
        Glide.with(this).load(imgs[2]).into(iv_pic3)
        Glide.with(this).load(imgs[3]).into(iv_pic4)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.iv_pic1 -> {
                //activity方式实现
                BrowsePictureActivity.start(this, imgs, 0)

                //dialogFragment方式实现
//                val dialog = BrowsePictureDialog.newInstance(imgs,0)
//                dialog.show(supportFragmentManager,null)
            }
            R.id.iv_pic2 -> BrowsePictureActivity.start(this, imgs, 1)
            R.id.iv_pic3 -> BrowsePictureActivity.start(this, imgs, 2)
            R.id.iv_pic4 -> BrowsePictureActivity.start(this, imgs, 3)
        }
    }
}