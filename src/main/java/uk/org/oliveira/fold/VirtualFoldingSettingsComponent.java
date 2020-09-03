package uk.org.oliveira.fold;

import com.intellij.ui.components.*;
import com.intellij.util.ui.FormBuilder;

import javax.swing.*;

public class VirtualFoldingSettingsComponent {
    private final JPanel panel;
    private final JBTextField divider = new JBTextField();
    private final ListFactory componentTypes = new ListFactory();
    private final ListFactory extensionTypes = new ListFactory();
    private final ListFactory specTypes = new ListFactory();

    public VirtualFoldingSettingsComponent() {
        panel = FormBuilder.createFormBuilder()
                .addLabeledComponent("File name divider:", divider, false)
                .addComponent(new JBLabel("Component types"))
                .addComponent(componentTypes.getPanel())
                .addSeparator()
                .addComponent(new JBLabel("Extension types"))
                .addComponent(extensionTypes.getPanel())
                .addSeparator()
                .addComponent(new JBLabel("Spec types"))
                .addComponent(specTypes.getPanel())
                .getPanel();
    }

    public JPanel getPanel() {
        return panel;
    }

    public ListFactory getComponentTypes() {
        return componentTypes;
    }

    public ListFactory getExtensionTypes() {
        return extensionTypes;
    }

    public ListFactory getSpecTypes() {
        return specTypes;
    }

    public JBTextField getDivider() {
        return divider;
    }
}
