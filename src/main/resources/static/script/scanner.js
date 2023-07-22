
// THIS SECTION FOR THE HEADER USER FRAGMENT #CREATED BY FAISAL# //
function openCamera() {
    // Check if the device is iOS
    if (/iPad|iPhone|iPod/.test(navigator.userAgent) && !window.MSStream) {
        // Display a message asking users to open the camera manually
        alert("To scan QR code, please open the Camera app or a QR code scanner on your device.");
    }
    // Check if the device is Android
    else if (/Android/.test(navigator.userAgent)) {
        // Redirect to the Android camera app
        window.location.href = "intent:#Intent;action=android.media.action.IMAGE_CAPTURE;end;";
    }
    // Check if the device is Windows
    else if (/Windows/.test(navigator.userAgent)) {
        // Redirect to the Windows camera app
        window.location.href = "microsoft.windows.camera:";
    }
    else {
        // Device not supported
        console.error('Camera app not supported on this device.');
    }
}
// THIS THE END OF THE SECTION FOR THE HEADER USER FRAGMENT #CREATED BY FAISAL# //


