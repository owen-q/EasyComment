package io.owen.plugin.easycomment;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.DataKeys;
import com.intellij.openapi.actionSystem.ShortcutSet;
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
import io.owen.plugin.easycomment.settings.PluginStateManager;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Arrays;
import java.util.List;

/**
 * Created by owen_q on 15/02/2019.
 */
public class CommentPopupAction extends AnAction {
    private List<String> commentCommands = Arrays.asList("TODO", "FIXME", "XXX", "CHECKME", "DOCME", "TESTME", "PENDING");
    private JList jList = null;
    private Project project = null;
    private Editor editor = null;
    private boolean isEscape = false;
    private String selectedCommand = "";
    private PluginStateManager stateManager = PluginStateManager.getInstance();

    public CommentPopupAction() {
        String[] contents = commentCommands.toArray(new String[0]);

        this.project = ProjectCoreUtil.theOnlyOpenProject();
        this.jList = new JList(contents);

        this.jList.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {

            }

            @Override
            public void keyPressed(KeyEvent e) {
                if(e.getKeyCode() == KeyEvent.VK_ESCAPE){
                    isEscape = true;
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {

            }
        });

        jList.addListSelectionListener(e -> {
            if(!e.getValueIsAdjusting()) {
                int idx = e.getFirstIndex();
                selectedCommand = commentCommands.get(idx);
            }
        });
    }

    @Override
    protected void setShortcutSet(ShortcutSet shortcutSet) {
        // TODO(owen.qqq): Set default shortcuts
//        super.setShortcutSet(shortcutSet);
    }

    @Override
    public void beforeActionPerformedUpdate(@NotNull AnActionEvent e) {
        this.editor = e.getData(DataKeys.EDITOR);
    }

    @Override
    public void actionPerformed(AnActionEvent e) {
        if(this.stateManager.isCommentTypeEnabled()) {
            JBPopup popup = JBPopupFactory.getInstance().createListPopupBuilder(jList).setTitle("Comment types").createPopup();
            popup.addListener(new JBPopupListener() {
                @Override
                public void beforeShown(LightweightWindowEvent event) {

                }

                @Override
                public void onClosed(LightweightWindowEvent event) {
                    if(!isEscape) {
                        write(convert(selectedCommand));
//                        CommentWriteUtil.write(stateManager.getFormat(), "$comment$", convert(selectedCommand), editor);

                        //TODO: Refactoring ...
                        if(stateManager.isGitBranchNameEnabled()) {
                            GitBranchPopup gitBranchPopup = new GitBranchPopup(editor);
                            gitBranchPopup.show();
                        }

                    }
                    isEscape = false;
                }
            });
            popup.showInBestPositionFor(e.getData(DataKeys.EDITOR));
        }


    }

    public String convert(String command) {
        String input = "";
        input = "// " + command.toUpperCase() + " ";

        return input;
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
