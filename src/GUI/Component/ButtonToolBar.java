/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package GUI.Component;

import com.formdev.flatlaf.extras.FlatSVGIcon;
import com.formdev.flatlaf.fonts.roboto.FlatRobotoFont;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension; // Add this import statement
import javax.swing.JButton;
import javax.swing.border.EmptyBorder;

/**
 *
 * @author Tran Nhat Sinh
 */
public class ButtonToolBar extends JButton {
    String permisson;

    public ButtonToolBar(String text, String icon, String permisson) {
        this.permisson = permisson;
        this.setFont(new java.awt.Font(FlatRobotoFont.FAMILY, 1, 14));
        this.setForeground(new Color(1, 88, 155));
        this.setIcon(new FlatSVGIcon("./icon/" + icon));
        this.setText(text);
        this.setFocusable(false);
        this.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        this.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        this.setCursor(new Cursor(Cursor.HAND_CURSOR));
        this.putClientProperty("JButton.buttonType", "toolBarButton");
        int padding = 12;
        this.setBorder(new EmptyBorder(padding, padding, padding, padding));
    }

    public String getPermisson() {
        return this.permisson;
    }

}
