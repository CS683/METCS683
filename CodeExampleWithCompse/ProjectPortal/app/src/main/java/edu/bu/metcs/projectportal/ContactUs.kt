package edu.bu.metcs.projectportal

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.content.ContextCompat
import edu.bu.metcs.projectportal.ui.theme.ProjectPortalTheme


@Composable
fun ContactUs() {

    val context = LocalContext.current

    Column (horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxWidth()
         ){
        Spacer(modifier = Modifier.weight(1f))
        Text(text = "BU MET Computer Science Department")
        Row ( modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Spacer(modifier = Modifier.weight(1f))
            Text(text = " 6173585683")
            CallUsIcon(context, "6173585683")
            Spacer(modifier = Modifier.weight(1f))
        }

        Text ("1010 Commonwealth Ave, 3rd Floor")
        Spacer(modifier = Modifier.weight(1f))

    }
}


@Composable
@Preview
private fun ContactUsPreview() {
    ProjectPortalTheme {
        Surface(modifier = Modifier.fillMaxWidth()) {
          ContactUs()
        }
    }
}

@Composable
fun CallUsIcon(context: Context, phoneNum: String) {

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            context.startActivity(
                Intent(
                    Intent.ACTION_CALL,
                    Uri.parse("tel: $phoneNum"))
            )
        }
    }

    IconButton(onClick = {
        if (ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.CALL_PHONE,
            ) ==
            PackageManager.PERMISSION_GRANTED
        ) {
            // make phone call through intent
            context.startActivity(
                Intent(
                    Intent.ACTION_CALL,
                    Uri.parse("tel: $phoneNum")
                )
            )
        } else {
            launcher.launch(Manifest.permission.CALL_PHONE) // Request permission if not granted
        }
    }){
        Icon(
            imageVector = Icons.Filled.Phone,
            contentDescription = "phone",
        )
    }

}




