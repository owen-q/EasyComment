package io.owen.plugin.easycomment.popup;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.command.WriteCommandAction;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.project.Project;
import org.jetbrains.annotations.Nullable;

/**
 * Created by owen_q on 15/02/2019.
 */
public class GitBranchAction extends AnAction {
    private final GitDynamicBranchGroup actionGroup = new GitDynamicBranchGroup();

    public GitBranchAction(){
    }

    public GitBranchAction(@Nullable String text) {
        super(text);
    }

    @Override
    public void actionPerformed(AnActionEvent e) {
        final Editor editor = e.getData(CommonDataKeys.EDITOR);
        final Document document = editor.getDocument();

        int startPosition = editor.getSelectionModel().getSelectionStart();

        WriteCommandAction.runWriteCommandAction(e.getProject(), () -> {
            document.insertString(startPosition, "ohhhhhhhhh");
        });

    }




    @Override
    public void update(AnActionEvent e) {
        Project project = e.getProject();
        e.getPresentation().setEnabledAndVisible(project != null);
    }
}
