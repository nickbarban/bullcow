package com.nb.bullcow.views.game;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.PageTitle;
import com.nb.bullcow.views.MainLayout;
import com.vaadin.flow.router.RouteAlias;

@PageTitle("Game")
@Route(value = "game", layout = MainLayout.class)
@RouteAlias(value = "", layout = MainLayout.class)
public class GameView extends HorizontalLayout {

    private TextField name;
    private Button sayHello;

    public GameView() {
        setMargin(true);
        name = new TextField("Your name");
        sayHello = new Button("Say hello");
        add(name, sayHello);
        setVerticalComponentAlignment(Alignment.END, name, sayHello);
        sayHello.addClickListener(e -> {
            Notification.show("Hello " + name.getValue());
        });
    }

}
