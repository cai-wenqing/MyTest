package com.aiyakeji.mytest.ui.fragments

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.aiyakeji.mytest.R
import com.aiyakeji.mytest.databinding.FragmentCameraHostBinding

private var PERMISSIONS_REQUIRED = arrayOf(Manifest.permission.CAMERA)

class CameraXHostFragment : Fragment() {

    private lateinit var binding: FragmentCameraHostBinding
    private var jumpAction: (() -> Unit)? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCameraHostBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // add the storage access permission request for Android 9 and below.
        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.P) {
            val permissionList = PERMISSIONS_REQUIRED.toMutableList()
            permissionList.add(Manifest.permission.WRITE_EXTERNAL_STORAGE)
            PERMISSIONS_REQUIRED = permissionList.toTypedArray()
        }

        initView()
    }

    private fun initView() {
        binding.tvPhoto.setOnClickListener {
            jumpWithPermission {
                Navigation.findNavController(requireActivity(), R.id.fragment_container)
                    .navigate(R.id.action_host_to_photo)
            }
        }
        binding.tvVideo.setOnClickListener {
            jumpWithPermission {
                Navigation.findNavController(requireActivity(), R.id.fragment_container)
                    .navigate(R.id.action_host_to_video)
            }
        }
    }


    private fun jumpWithPermission(action: (() -> Unit)) {
        if (PERMISSIONS_REQUIRED.all {
                ContextCompat.checkSelfPermission(
                    requireContext(),
                    it
                ) == PackageManager.PERMISSION_GRANTED
            }) {
            action.invoke()
        } else {
            jumpAction = action
            permissionLauncher.launch(PERMISSIONS_REQUIRED)
        }
    }

    private val permissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { permissions ->
            var permissionGranted = true
            permissions.entries.forEach {
                if (it.key in PERMISSIONS_REQUIRED && !it.value) {
                    permissionGranted = false
                }
            }
            if (permissionGranted) {
                jumpAction?.invoke()
            } else {
                Toast.makeText(requireContext(), "请先授权！", Toast.LENGTH_SHORT).show()
            }
        }
}