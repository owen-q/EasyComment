package io.owen.plugin.easycomment.popup;

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
import io.owen.plugin.easycomment.GitUtil;

import javax.swing.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * Created by owen_q on 15/02/2019.
 */
public class GitBranchPopup {
    private String selectedBranch = "";
    private JList jList = null;
    private Editor editor = null;
    private Project project = null;
    private boolean isExclude = false;

    public GitBranchPopup(Editor editor) {
        this.project = ProjectCoreUtil.theOnlyOpenProject();
        this.editor = editor;

        this.jList = new JList(GitUtil.getBranchList());
        this.jList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        this.jList.addKeyListener(new KeyListener() {
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
    }


    public void show(){
        JBPopup popup = JBPopupFactory.getInstance().createListPopupBuilder(jList).setTitle("Branches").createPopup();

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
        popup.showInBestPositionFor(this.editor);
    }

    private String refine(String dirtyBranchName) {
        String pattern = "refs/heads/";
        int idx = dirtyBranchName.indexOf(pattern);
        return dirtyBranchName.substring(idx + pattern.length());
    }

    private void write(String text){
        final Document document = this.editor.getDocument();
        VisualPosition visualPosition = this.editor.getCaretModel().getVisualPosition();
        int startPosition = this.editor.getSelectionModel().getSelectionStart();

        WriteCommandAction.runWriteCommandAction(this.project, () -> {
            document.insertString(startPosition, "[" + text + "]");
            VisualPosition newVisualPosition1 = new VisualPosition(visualPosition.line, visualPosition.column + text.length() + 2);
            this.editor.getCaretModel().moveToVisualPosition(newVisualPosition1);
        });
    }

}
