package com.vaadinboot.bullcow.ui;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * @author Nick Barban.
 */

public class RightDeskLayout extends VerticalLayout {

    public RightDeskLayout() {
        List<String> letters = IntStream.rangeClosed('A', 'Z')
                .mapToObj(ch -> String.valueOf((char) ch))
                .collect(Collectors.toList());

        int numOfRows = letters.size() / 4 + 1;
        List<List<String>> table = IntStream.range(0, numOfRows)
                .mapToObj(i -> {
                    if (i * 4 + 4 > letters.size()) {
                        return letters.subList(i * 4, letters.size());
                    } else {
                        return letters.subList(i * 4, i * 4 + 4);
                    }
                })
                .collect(Collectors.toList());
        table.forEach(line -> {
            HorizontalLayout layout = new HorizontalLayout();
            layout.setAlignItems(Alignment.START);
            layout.setAlignSelf(Alignment.END);
            line.forEach(letter -> {
                Button button = new Button(letter);
                button.setWidth("30px");
                button.setHeight("30px");
                button.addClickListener(event -> {
                    if (button.getIcon() == null) {
                        button.setIcon(VaadinIcon.BULLSEYE.create());
                    } else if (button.isIconAfterText()) {
                        button.setIconAfterText(false);
                        button.setIcon(null);
                    } else {
                        button.setIcon(VaadinIcon.PLUS.create());
                        button.setIconAfterText(true);
                    }
                });
                layout.add(button);
            });
            add(layout);
        });
    }
}
