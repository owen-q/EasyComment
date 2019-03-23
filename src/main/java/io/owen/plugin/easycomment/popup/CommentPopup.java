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
import io.owen.plugin.easycomment.core.ActionState;
import io.owen.plugin.easycomment.core.CommentEventDispatcher;

import javax.swing.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Arrays;
import java.util.List;

/**
 * Created by owen_q on 01/03/2019.
 */
public class CommentPopup implements DynamicWritable{
    private final String KEY = "comment_popup";
    private final String VARIABLE = "$comment$";
    private List<String> commentCommands = Arrays.asList("TODO", "FIXME", "XXX", "CHECKME", "DOCME", "TESTME", "PENDING");
    private JList jList;
    private Editor editor;
    private boolean isExclude = false;
    private String selectedComment = "";

    public CommentPopup() {
        initCommentList();
    }

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
            if(source.getSelectedValue() != null){
                this.selectedComment = source.getSelectedValue().toString();
            }
        });
    }

    @Override
    public String getKey() {
        return KEY;
    }

    @Override
    public String getVariable() {
        return VARIABLE;
    }

    @Override
    public int getVariableLength() {
        return VARIABLE.length();
    }

    @Override
    public void show(Editor editor){
        this.editor = editor;

        JBPopup popup = JBPopupFactory.getInstance().createListPopupBuilder(jList).setTitle("Comments").createPopup();
        popup.addListener(new JBPopupListener() {
            @Override
            public void beforeShown(LightweightWindowEvent event) {

            }

            @Override
            public void onClosed(LightweightWindowEvent event) {
                // write...
                if(!isExclude) {
                    write(selectedComment);
                    CommentEventDispatcher.getInstance().propagate(KEY, ActionState.DONE);
                }
                else {
                    CommentEventDispatcher.getInstance().propagate(KEY, ActionState.CANCEL);

                }

                jList.setSelectedIndex(0);
                isExclude = false;
            }
        });

        popup.showInBestPositionFor(this.editor);
    }

    @Deprecated
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

    @Override
    public Editor write(Editor editor, String text) {

        final Document document = editor.getDocument();
        VisualPosition visualPosition = editor.getCaretModel().getVisualPosition();
        int startPosition = editor.getSelectionModel().getSelectionStart();

        WriteCommandAction.runWriteCommandAction(ProjectCoreUtil.theOnlyOpenProject(), () -> {
            document.insertString(startPosition, text);
            VisualPosition newVisualPosition1 = new VisualPosition(visualPosition.line, visualPosition.column + text.length());
            editor.getCaretModel().moveToVisualPosition(newVisualPosition1);
        });

        return editor;
    }

    private void write(String text){
        final Document document = this.editor.getDocument();
        VisualPosition visualPosition = this.editor.getCaretModel().getVisualPosition();
        int startPosition = this.editor.getSelectionModel().getSelectionStart();

        WriteCommandAction.runWriteCommandAction(ProjectCoreUtil.theOnlyOpenProject(), () -> {
            document.insertString(startPosition, text);
            VisualPosition newVisualPosition1 = new VisualPosition(visualPosition.line, visualPosition.column + text.length());
            this.editor.getCaretModel().moveToVisualPosition(newVisualPosition1);
        });
    }

}
