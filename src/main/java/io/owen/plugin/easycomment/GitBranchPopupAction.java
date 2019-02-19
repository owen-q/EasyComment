package io.owen.plugin.easycomment;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.DataKeys;
import com.intellij.openapi.command.WriteCommandAction;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.editor.VisualPosition;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.project.ProjectCoreUtil;
import com.intellij.openapi.ui.popup.JBPopup;
import com.intellij.openapi.ui.popup.JBPopupFactory;
import com.intellij.openapi.ui.popup.JBPopupListener;
import com.intellij.openapi.ui.popup.LightweightWindowEvent;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * Created by owen_q on 15/02/2019.
 */
public class GitBranchPopupAction extends AnAction {
    private String selectedBranch = "";
    private Editor editor = null;
    private Project project;
    private boolean isExclude = false;

    public GitBranchPopupAction() {
        this.project = ProjectCoreUtil.theOnlyOpenProject();
    }

    @Override
    public void beforeActionPerformedUpdate(@NotNull AnActionEvent e) {
        this.editor = e.getData(DataKeys.EDITOR);
    }

    @Override
    public void actionPerformed(AnActionEvent e) {
        JList jList = new JList(GitUtil.getBranchList());
        jList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);


        jList.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {

            }

            @Override
            public void keyPressed(KeyEvent e) {
                if(e.getKeyCode() == KeyEvent.VK_ESCAPE){
                    isExclude = true;
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {

            }
        });

        jList.addListSelectionListener(e1 -> {

            JList source = (JList)e1.getSource();
            this.selectedBranch = source.getSelectedValue().toString();
        });

        JBPopup popup = JBPopupFactory.getInstance().createListPopupBuilder(jList).createPopup();
        popup.addListener(new JBPopupListener() {
            @Override
            public void beforeShown(LightweightWindowEvent event) {

            }

            @Override
            public void onClosed(LightweightWindowEvent event) {
                // write...
                if(!isExclude)
                    write(selectedBranch);

                isExclude = false;
            }
        });
        popup.showInBestPositionFor(e.getData(DataKeys.EDITOR));
    }

    public void write(String text){
        final Document document = this.editor.getDocument();
        VisualPosition visualPosition = this.editor.getCaretModel().getVisualPosition();
        int startPosition = this.editor.getSelectionModel().getSelectionStart();

        WriteCommandAction.runWriteCommandAction(this.project, () -> {
            document.insertString(startPosition, text);
            VisualPosition newVisualPosition1 = new VisualPosition(visualPosition.line, visualPosition.column + text.length());
            this.editor.getCaretModel().moveToVisualPosition(newVisualPosition1);
        });
    }
}
