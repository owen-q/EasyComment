package io.owen.plugin.easycomment.popup;

import com.intellij.openapi.command.WriteCommandAction;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.editor.VisualPosition;
import com.intellij.openapi.project.ProjectCoreUtil;
import com.intellij.openapi.ui.popup.JBPopup;
import com.intellij.openapi.ui.popup.JBPopupFactory;
import com.intellij.openapi.ui.popup.JBPopupListener;
import com.intellij.openapi.ui.popup.LightweightWindowEvent;

import javax.swing.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Arrays;
import java.util.List;

/**
 * Created by owen_q on 01/03/2019.
 */
public class CommentPopup {
    private List<String> commentCommands = Arrays.asList("TODO", "FIXME", "XXX", "CHECKME", "DOCME", "TESTME", "PENDING");
    private JList jList;
    private Editor editor;
    private boolean isExclude = false;
    private String selectedComment = "";

    public CommentPopup(Editor editor) {
        this.editor = editor;
        initCommentList();
    }

    private void initCommentList(){
        this.jList = new JList(commentCommands.toArray(new String[0]));
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
            this.selectedComment = source.getSelectedValue().toString();
        });
    }

    public void show(){
        JBPopup popup = JBPopupFactory.getInstance().createListPopupBuilder(jList).setTitle("Comments").createPopup();
        popup.addListener(new JBPopupListener() {
            @Override
            public void beforeShown(LightweightWindowEvent event) {

            }

            @Override
            public void onClosed(LightweightWindowEvent event) {
                // write...
                if(!isExclude)
                    write(selectedComment);

                isExclude = false;
            }
        });

        popup.showInBestPositionFor(this.editor);
    }

    private void write(String text){
        final Document document = this.editor.getDocument();
        VisualPosition visualPosition = this.editor.getCaretModel().getVisualPosition();
        int startPosition = this.editor.getSelectionModel().getSelectionStart();

        WriteCommandAction.runWriteCommandAction(ProjectCoreUtil.theOnlyOpenProject(), () -> {
            document.insertString(startPosition, "[" + text + "]");
            VisualPosition newVisualPosition1 = new VisualPosition(visualPosition.line, visualPosition.column + text.length() + 2);
            this.editor.getCaretModel().moveToVisualPosition(newVisualPosition1);
        });
    }

}
