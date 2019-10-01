package com.skullmind.io.camera

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import butterknife.BindView
import butterknife.ButterKnife
import com.skullmind.io.R

val EXTRA_CAMERA: String = "EXTRA_CAMERA"

fun newIntentToCamera(context: AppCompatActivity, cameraName:String): Intent {
    val intent: Intent = Intent(context, CameraActivity::class.java)
    intent.putExtra(EXTRA_CAMERA,cameraName)
    return intent
}
class CameraActivity :AppCompatActivity(){

    @BindView(R.id.tv_camera_name)
    lateinit var view:TextView

    lateinit var model: CameraViewModel

    var observer: Observer<String> = Observer{
        view.setText(it)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_camera)
        ButterKnife.bind(this)
        val name = intent.getStringExtra(EXTRA_CAMERA)
        model = ViewModelProviders.of(this).get(CameraViewModel::class.java)
        model.name.observe(this,observer)
        model.name.value = name
    }
}