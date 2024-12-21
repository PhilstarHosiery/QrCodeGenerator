package com.philstar.app.qrcodegenerator;

import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class QrErrorCorrectionLevels {

    public record ErrCorLevel(String description, ErrorCorrectionLevel level) {
        @Override
        public String toString() {
            return description;
        }
    }


    /*
        Level L (Low)	    7% of data bytes can be restored.
        Level M (Medium)	15% of data bytes can be restored.
        Level Q (Quartile)	25% of data bytes can be restored.
        Level H (High)	    30% of data bytes can be restored.
     */
    public static ErrCorLevel L = new ErrCorLevel("Level L - 7%", ErrorCorrectionLevel.L);
    public static ErrCorLevel M = new ErrCorLevel("Level M - 15%", ErrorCorrectionLevel.M);
    public static ErrCorLevel Q = new ErrCorLevel("Level Q - 25%", ErrorCorrectionLevel.Q);
    public static ErrCorLevel H = new ErrCorLevel("Level H - 30%", ErrorCorrectionLevel.H);


    public static ObservableList<ErrCorLevel> getErrorCorrectionLevels() {
        ObservableList<ErrCorLevel> levels = FXCollections.observableArrayList();
        levels.addAll(L, M, Q, H);
        return levels;
    }

}
