package com.vaadinboot.bullcow.ui;

import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import lombok.Getter;
import lombok.extern.java.Log;

/**
 * @author Nick Barban.
 */
@Getter
@Log
public class GameLayout extends HorizontalLayout {

    private final LeftDeskLayout leftDeskLayout;
    private final RightDeskLayout rightDeskLayout;

    public GameLayout() {
        leftDeskLayout = new LeftDeskLayout();
        add(leftDeskLayout);
        rightDeskLayout = new RightDeskLayout();
        add(rightDeskLayout);
    }
}
