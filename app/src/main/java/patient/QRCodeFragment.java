package patient;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;

import com.example.clinic.MainActivity;
import com.example.clinic.R;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;

public class QRCodeFragment extends Fragment {

    ImageView qrCode;

    public QRCodeFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.qr_code, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState){
        super.onActivityCreated(savedInstanceState);
        MainActivity.toolbar.setTitle("QR Code");
        qrCode = getView().findViewById(R.id.patientQRCode);
        String input = String.valueOf(((MainActivity)getActivity()).getPatient_ID());

        // this is used to write data with specific format
        MultiFormatWriter writer = new MultiFormatWriter() ;


        try {
            // make bit matrix to include the data in QR format
            BitMatrix matrix = writer.encode(input, BarcodeFormat.QR_CODE,275,400);

            // Barcode encoder
            BarcodeEncoder encoder = new BarcodeEncoder();

            // make bitmap  to show as image
            Bitmap bitmap = encoder.createBitmap(matrix);
            qrCode.setImageBitmap(bitmap);

            //InputMethodManager manager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            //manager.hideSoftInputFromWindow(ed.getApplicationWindowToken(),0);

        } catch (WriterException e) {
            e.printStackTrace();
        }
    }
}