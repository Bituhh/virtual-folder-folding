package uk.org.oliveira.fold;

import com.intellij.openapi.components.PersistentStateComponent;
import com.intellij.openapi.components.ServiceManager;
import com.intellij.openapi.components.State;
import com.intellij.openapi.components.Storage;
import com.intellij.util.xmlb.XmlSerializerUtil;
import org.jetbrains.annotations.NotNull;

import java.util.HashSet;
import java.util.Set;

@State(
        name = "uk.org.oliveira.fold.VirtualFoldingState",
        storages = {@Storage("VirtualFoldingPlugin.xml")}
)
public final class VirtualFoldingState implements PersistentStateComponent<VirtualFoldingState> {
    public String divider = ".";
    public String[] componentTypes = {"component", "service", "pipe", "guard", "directive", "reducers", "actions", "effects", "selectors"};
    public String[] extensionTypes = {"ts", "js", "tsx", "java", "py", "cpp", "c", "cs", "css", "scss", "html", "xml"};
    public String[] specTypes = {"spec", "test"};

    @Override
    public VirtualFoldingState getState() {
        return this;
    }

    @Override
    public void loadState(@NotNull VirtualFoldingState state) {
        XmlSerializerUtil.copyBean(state, this);
    }

    public static VirtualFoldingState getInstance() {
        return ServiceManager.getService(VirtualFoldingState.class);
    }
}
