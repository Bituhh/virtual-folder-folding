package uk.org.oliveira.fold;

import com.intellij.ide.projectView.ViewSettings;
import com.intellij.ide.util.treeView.AbstractTreeNode;
import com.intellij.psi.PsiDirectory;
import com.intellij.psi.PsiFile;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TreeStructureProvider implements com.intellij.ide.projectView.TreeStructureProvider {

    @Override
    public @NotNull Collection<AbstractTreeNode<?>> modify(@NotNull AbstractTreeNode<?> parent,
                                                           @NotNull Collection<AbstractTreeNode<?>> children,
                                                           ViewSettings viewSettings) {
        if (!(parent.getValue() instanceof PsiDirectory)) {
            return children;
        }

        List<AbstractTreeNode<?>> ret = new ArrayList<>();
        Map<String, ComponentFileGroup> map = new HashMap<>();
        for (AbstractTreeNode<?> child : children) {
            if (!(child.getValue() instanceof PsiFile)) {
                ret.add(child);
                continue;
            }

            PsiFile psiFile = (PsiFile) child.getValue();
            String filename = psiFile.getName();
            Matcher m = getPattern().matcher(filename);
            if (!m.find()) {
                ret.add(child);
                continue;
            }

            String prefix = m.group(1);
            String componentType = m.group(2);
            String specType = m.group(4);
            String extensionType = m.group(5);

            VirtualFoldingState state = VirtualFoldingState.getInstance();
            final String groupKey = String.format("%s" + state.divider + "%s", prefix, componentType);

            ComponentFileGroup group = map.get(groupKey);
            if (group == null) {
                group = new ComponentFileGroup(child.getProject(), viewSettings, psiFile, groupKey, componentType);
                map.put(groupKey, group);
                ret.add(group);
            }
            String childExtension = specType != null ? specType + state.divider + extensionType : extensionType;
            group.addChild(child, childExtension);
        }

        // Undo grouping that has only child
        for (int i = 0, len = ret.size(); i < len; i++) {
            AbstractTreeNode<?> added = ret.get(i);
            if (added instanceof ComponentFileGroup) {
                ComponentFileGroup g = (ComponentFileGroup) added;
                if (g.getChildrenCount() <= 1) {
                    ret.set(i, g.getOriginalFirstChild());
                }
            }
        }

        return ret;
    }

    @Nullable
    @Override
    public Object getData(@NotNull Collection<AbstractTreeNode<?>> selected, @NotNull String dataName) {
        return null;
    }

    public Pattern getPattern() {
        VirtualFoldingState state = VirtualFoldingState.getInstance();
        //noinspection StringBufferReplaceableByString
        return Pattern.compile(
                new StringBuilder().append("(.*)\\").append(state.divider)
                        .append("(").append(String.join("|", state.componentTypes))
                        .append(")\\").append(state.divider).append("((")
                        .append(String.join("|", state.specTypes))
                        .append(")\\").append(state.divider).append(")?(")
                        .append(String.join("|", state.extensionTypes))
                        .append(")").toString());
    }

}
