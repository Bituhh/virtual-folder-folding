package uk.org.oliveira.fold;

import com.intellij.openapi.ui.Messages;
import com.intellij.ui.ToolbarDecorator;
import com.intellij.ui.components.JBList;

import javax.swing.*;
import java.util.*;

public class ListFactory {
    private final Set<String> model = new HashSet<>();
    private final JBList<String> list = new JBList<>();
    private final ToolbarDecorator decorator;
    private final JPanel panel;

    public ListFactory() {
        this.decorator = ToolbarDecorator.createDecorator(this.list);
        this.setupDecorator();

        this.panel = this.decorator.createPanel();
    }

    private void setupDecorator() {
        decorator.setAddAction(button -> {
            String input = Messages.showInputDialog("Which option would you like to add?", "Add Option", Messages.getQuestionIcon());
            this.model.add(input);
            this.list.setListData(this.getValues());
        }).setRemoveAction(button -> {
            String selectedValue = this.list.getSelectedValue();
            this.model.remove(selectedValue);
            this.list.setListData(this.getValues());
        }).disableUpDownActions();
    }

    public Set<String> getModel() {
        return this.model;
    }

    public String[] getValues() {
        String[] x = new String[this.model.size()];
        int i = 0;
        for (String str: this.model) {
            x[i++] = str;
        }
        return x;
    }

    public JBList<String> getList() {
        return list;
    }

    public ToolbarDecorator getDecorator() {
        return decorator;
    }

    public JPanel getPanel() {
        return panel;
    }

    public void setModel(String[] set) {
        this.model.clear();
        this.model.addAll(Arrays.asList(set));
        this.list.setListData(set);
    }
}
