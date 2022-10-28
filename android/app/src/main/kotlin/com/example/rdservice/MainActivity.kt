package com.example.rdservice

import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.Intent
import android.content.pm.PackageManager
import android.util.Log
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.core.app.ActivityCompat
import com.example.rdservice.CommonUtil.Companion.getPIDOptions
import io.flutter.embedding.android.FlutterActivity
import io.flutter.embedding.engine.FlutterEngine
import io.flutter.plugin.common.MethodChannel
import io.flutter.plugins.GeneratedPluginRegistrant
import org.simpleframework.xml.Serializer
import org.simpleframework.xml.core.Persister

class MainActivity : FlutterActivity() {
    private val CHANNEL_NAME = "channel"
    private lateinit var methodChannel: MethodChannel
    private var pidData: PidData? = null
    lateinit var serializer: Serializer

    override fun configureFlutterEngine(flutterEngine: FlutterEngine) {
        super.configureFlutterEngine(flutterEngine)
        GeneratedPluginRegistrant.registerWith(flutterEngine)

        MethodChannel(
            flutterEngine.dartExecutor.binaryMessenger,
            CHANNEL_NAME
        ).setMethodCallHandler { call, result ->
            if (call.method == "mantraDevice") {

                if (!CommonUtil.appInstalledOrNot(
                        packageManager,
                        "com.mantra.rdservice"
                    ) || !CommonUtil.appInstalledOrNot(
                        packageManager,
                        "com.mantra.clientmanagement"
                    )
                ) {
                    try {

                        val pidOption: String? = getPIDOptions("MANTRA")
//                            val pidOption: String? = "<PidOptions ver= '1.0'>\n" +
//                                    "   <Opts env='P' fCount='1' fType='0' format='0' iCount='0' otp='' pCount='0' pidVer='2.0' posh='UNKNOWN' timeout='10000'/>\n" +
//                                    "</PidOptions>"
                        print("#####pidoptions${pidOption}")
                        if (pidOption != null) {
                            val intent = Intent()
                            intent.action = "in.gov.uidai.rdservice.fp.CAPTURE"
                            intent.`package` = "com.mantra.rdservice"
                            intent.putExtra("PID_OPTIONS", pidOption)
                            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                            startActivityForResult(intent, 1001, null)
                        }
                    } catch (e: Exception) {
                        //Crashlytics.logException(e)
                    }

//                        var sharingIntent : Intent = Intent(Intent.ACTION_VIEW)
//                        sharingIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
//                        sharingIntent.data = Uri.parse("market://details?id=com.mantra.clientmanagement")
//
//                        context.startActivity(sharingIntent)

//                        startActivity(
//                            context, Intent(
//                                Intent.ACTION_VIEW,
//                                Uri.parse("https://play.google.com/store/apps/details?id=" + "com.mantra.clientmanagement")
//                            ), null
//                        )

//                        startActivity(
//                            context.applicationContext,
//                            Intent(
//                                Intent.ACTION_VIEW,
//                                Uri.parse("market://details?id=\" + \"com.mantra.clientmanagement")
//                            ),
//                            null
//                        )

                } else {
                    try {
                        val pidOption: String? = getPIDOptions("MANTRA")
//                            val pidOption: String? = "<PidOptions ver= '1.0'>\n" +
//                                    "   <Opts env='P' fCount='1' fType='0' format='0' iCount='0' otp='' pCount='0' pidVer='2.0' posh='UNKNOWN' timeout='10000'/>\n" +
//                                    "</PidOptions>"

                        // <Opts env="P" fCount="0" fType="0" format="0" iCount="0" otp="" pCount="0" pidVer="2.0" posh="UNKNOWN" timeout="10000"/>//
                        print("#####pidoptions${pidOption}")
                        if (pidOption != null) {
                            val intent = Intent()
                            intent.action = "in.gov.uidai.rdservice.fp.CAPTURE"
                            intent.`package` = "com.mantra.rdservice"
                            intent.putExtra("PID_OPTIONS", pidOption)
                            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                            startActivityForResult(intent, 1001, null)
                        }
                    } catch (e: Exception) {
                        Log.e("Error", e.toString());
                    }

                }

            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        try {
            Toast.makeText(context,"resultCode${requestCode.toString()}",Toast.LENGTH_SHORT).show()
          //  when (requestCode) {
                if(requestCode == 1001){
                    Toast.makeText(context,"data${data!!.getStringExtra("PID_DATA")}",Toast.LENGTH_SHORT).show()
                    val result: String = data!!.getStringExtra("PID_DATA")!!
                    Toast.makeText(context,"Result${result}",Toast.LENGTH_SHORT).show()
                    val serializer: Serializer = Persister()
                    if (result != null) {

                        pidData = serializer.read(PidData::class.java, result)
                        Toast.makeText(context,"pidData${pidData.toString()}",Toast.LENGTH_SHORT).show()
                        if (pidData != null) {
                            if (pidData?._Resp?.errCode.equals(
                                    "0",
                                    ignoreCase = true
                                )
                            ) {

                                var RBL_POPUP = ""


//                                if (moduleSelected == 0) {
//                                    RBL_POPUP = pref.getString(Constants.AEPS_CW_AGGRE, "")!!
//                                } else if (moduleSelected == 2) {
//                                    RBL_POPUP = pref.getString(Constants.AEPS_BE_AGGRE, "")!!
//                                } else if (moduleSelected == 4) {
//                                    RBL_POPUP = pref.getString(Constants.AEPS_MS_AGGRE, "")!!
//                                } else if (moduleSelected == 6) {
//                                    RBL_POPUP = pref.getString(Constants.AEPS_CD_AGGRE, "")!!
//                                }
                                //if (requestCode == 1001 &&
                                  //  pref.getString(Constants.FFactor, "")
                                    if (requestCode == 1001 && RBL_POPUP.equals("RBL", ignoreCase = true)
                                   // && pref.getString(Constants.RBL_POPUP_VISIBILITY, "")
                                     //   .equals("Y", ignoreCase = true)
                                ) {
                                        Toast.makeText(context,"#######${pidData!!._Skey.ci}",Toast.LENGTH_SHORT).show()
                                        Toast.makeText(context,"#######${pidData!!._Skey.value}",Toast.LENGTH_SHORT).show()
                                        Toast.makeText(context,"#######${pidData!!._DeviceInfo.mc}",Toast.LENGTH_SHORT).show()
                                        Toast.makeText(context,"#######${pidData!!._Skey.ci}",Toast.LENGTH_SHORT).show()
                                   // rblLoginPopup()
//                                    Toast.makeText(
//                                        context,
//                                        context.getString(R.string.default_password),
//                                        Toast.LENGTH_LONG
//                                    ).show()
                                } else {
                                    Toast.makeText(context,"#######${pidData!!._Skey.ci}",Toast.LENGTH_SHORT).show()
                                    Toast.makeText(context,"#######${pidData!!._Skey.value}",Toast.LENGTH_SHORT).show()
                                    Toast.makeText(context,"#######${pidData!!._DeviceInfo.mc}",Toast.LENGTH_SHORT).show()
                                    Toast.makeText(context,"#######${pidData!!._Skey.ci}",Toast.LENGTH_SHORT).show()
                                   // getParamsforAmount(pidData)
                                }
                            } else {
                                Toast.makeText(
                                    context,
                                    pidData?._Resp?.errInfo,
                                    Toast.LENGTH_LONG
                                ).show()
                            }
                        }

                    }
                }
          //  }
        } catch (e: Exception) {

        }
    }

}
