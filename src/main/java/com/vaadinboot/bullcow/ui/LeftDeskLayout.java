package com.vaadinboot.bullcow.ui;

import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.java.Log;

/**
 * @author Nick Barban.
 */
@Getter
@Setter
@Log
public class LeftDeskLayout extends VerticalLayout {

    private CheckRow lastRow;

    public void addRow(CheckRow row) {
        lastRow = row;
        add(lastRow);
    }
}
