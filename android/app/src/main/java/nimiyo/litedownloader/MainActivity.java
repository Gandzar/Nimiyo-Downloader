package nimiyo.litedownloader;

import android.os.Build;
import android.os.Bundle;
import com.getcapacitor.BridgeActivity;

public class MainActivity extends BridgeActivity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        registerPlugin(MediaSaverPlugin.class);
        super.onCreate(savedInstanceState);

        // Auto request notification permission on Android 13+ (API 33+)
        if (Build.VERSION.SDK_INT >= 33) {
            if (checkSelfPermission(android.Manifest.permission.POST_NOTIFICATIONS) != android.content.pm.PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{android.Manifest.permission.POST_NOTIFICATIONS}, 101);
            }
        }
    }

    @Override
    public void onBackPressed() {
        if (bridge != null && bridge.getWebView() != null) {
            bridge.getWebView().evaluateJavascript(
                "(function() { if (typeof window.handleAppBackButton === 'function') { return window.handleAppBackButton(); } return false; })()",
                (result) -> {
                    if ("false".equals(result) || "null".equals(result) || result == null) {
                        runOnUiThread(() -> super.onBackPressed());
                    }
                }
            );
        } else {
            super.onBackPressed();
        }
    }
}
