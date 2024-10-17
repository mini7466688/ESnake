package com.egoism.ui.modifier.buttonAction;

import com.egoism.ui.modifier.Modifier;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class setLatticeShowButtonActionListenerImpl implements ActionListener {
    public Modifier modifier;

    public setLatticeShowButtonActionListenerImpl(Modifier modifier) {
        this.modifier = modifier;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Modifier.clickSetLatticeShowButtonCount++;
        if (Modifier.clickSetLatticeShowButtonCount % 2 == 0) {
            this.modifier.gameJFrame.latticeShow = true;
        } else {
            this.modifier.gameJFrame.latticeShow = false;
        }
    }
}
