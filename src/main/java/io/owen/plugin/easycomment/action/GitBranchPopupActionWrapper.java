package io.owen.plugin.easycomment.action;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.DataKeys;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.project.ProjectCoreUtil;
import io.owen.plugin.easycomment.popup.GitBranchPopup;
import org.jetbrains.annotations.NotNull;

/**
 * Created by owen_q on 15/02/2019.
 */
public class GitBranchPopupActionWrapper extends AnAction {
    private String selectedBranch = "";
    private Editor editor = null;
    private Project project;
    private boolean isExclude = false;

    public GitBranchPopupActionWrapper() {
        this.project = ProjectCoreUtil.theOnlyOpenProject();
    }

    @Override
    public void beforeActionPerformedUpdate(@NotNull AnActionEvent e) {
        this.editor = e.getData(DataKeys.EDITOR);
    }

    @Override
    public void actionPerformed(AnActionEvent e) {
        Editor editor = e.getData(DataKeys.EDITOR);
        GitBranchPopup gitBranchPopup = new GitBranchPopup(editor);
        gitBranchPopup.show();
    }

}
