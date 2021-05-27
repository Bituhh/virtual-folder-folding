package uk.org.oliveira.fold;

import com.intellij.openapi.options.Configurable;
import com.intellij.openapi.options.ConfigurationException;
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.util.Arrays;

public class VirtualFoldingSettingsConfigurable implements Configurable {
    private VirtualFoldingSettingsComponent settingsComponent;

    @Nls(capitalization = Nls.Capitalization.Title)
    @Override
    public String getDisplayName() {
        return "Virtual Folder Folding";
    }

    @Nullable
    @Override
    public JComponent createComponent() {
        settingsComponent = new VirtualFoldingSettingsComponent();
        return settingsComponent.getPanel();
    }

    @Override
    public boolean isModified() {
        VirtualFoldingState state = VirtualFoldingState.getInstance();
        if (!Arrays.equals(settingsComponent.getComponentTypes().getValues(), state.componentTypes) ||
                !Arrays.equals(settingsComponent.getExtensionTypes().getValues(), state.extensionTypes) ||
                !Arrays.equals(settingsComponent.getSpecTypes().getValues(), state.specTypes) ||
                settingsComponent.getDivider().getText().equals(state.divider)) {
            state.componentTypes = settingsComponent.getComponentTypes().getValues();
            state.extensionTypes = settingsComponent.getExtensionTypes().getValues();
            state.specTypes = settingsComponent.getSpecTypes().getValues();
           return true;
        }
        return false;
    }

    @Override
    public void apply() throws ConfigurationException {
        VirtualFoldingState state = VirtualFoldingState.getInstance();
        state.componentTypes = settingsComponent.getComponentTypes().getValues();
        state.extensionTypes = settingsComponent.getExtensionTypes().getValues();
        state.specTypes = settingsComponent.getSpecTypes().getValues();
    }

    @Override
    public void reset() {
        VirtualFoldingState state = VirtualFoldingState.getInstance();
        settingsComponent.getComponentTypes().setModel(state.componentTypes);
        settingsComponent.getExtensionTypes().setModel(state.extensionTypes);
        settingsComponent.getSpecTypes().setModel(state.specTypes);
        settingsComponent.getDivider().setText(state.divider);
    }

    @Override
    public void disposeUIResources() {
        settingsComponent = null;
    }
}
