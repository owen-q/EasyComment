package io.owen.plugin.easycomment.action;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.DataKeys;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.project.Project;
import io.owen.plugin.easycomment.popup.CommentPopup;
import io.owen.plugin.easycomment.popup.GitBranchPopup;
import io.owen.plugin.easycomment.settings.PluginStateManager;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.util.Arrays;
import java.util.List;

/**
 * Created by owen_q on 15/02/2019.
 */
public class EasyCommentActionWrapper extends AnAction {
    private List<String> commentCommands = Arrays.asList("TODO", "FIXME", "XXX", "CHECKME", "DOCME", "TESTME", "PENDING");
    private JList jList = null;
    private Project project = null;
    private Editor editor = null;
    private boolean isEscape = false;
    private String selectedCommand = "";
    private PluginStateManager stateManager = PluginStateManager.getInstance();

    public EasyCommentActionWrapper() {

    }

    @Override
    public void beforeActionPerformedUpdate(@NotNull AnActionEvent e) {
        this.editor = e.getData(DataKeys.EDITOR);
    }

    @Override
    public void actionPerformed(AnActionEvent e) {
//        if(this.stateManager.isCommentTypeEnabled()) {
        System.out.println("ㅡㅡ");
            Editor editor = e.getData(DataKeys.EDITOR);
            CommentPopup commentPopup = new CommentPopup(editor);
            commentPopup.show();

            GitBranchPopup gitBranchPopup = new GitBranchPopup(editor);
            gitBranchPopup.show();

            // TODO(owen.qqq): route next input
//        }
    }

    public String convert(String command) {
        String input = "";
        input = "// " + command.toUpperCase() + " ";

        return input;
    }
}
