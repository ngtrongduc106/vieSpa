/**
 * Author: ng-hoangnam
 * Date: 31/01/2023
 * Description:
 */

package com.viespa.utils;

import javafx.scene.control.Alert;

public class AlertUtil {
    public static void showError(String msg) {
        Alert alert = new Alert(Alert.AlertType.ERROR, msg);
        alert.showAndWait();
    }

    public static void showSuccess(String msg) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION, msg);
        alert.showAndWait();
    }
}
