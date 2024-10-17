package com.egoism.ui.modifier.buttonAction;

import com.egoism.ui.modifier.Modifier;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class closeButtonActionListenerImpl implements ActionListener {
    public Modifier modifier;

    public closeButtonActionListenerImpl(Modifier modifier) {
        this.modifier = modifier;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        this.modifier.dispose();
    }
}
