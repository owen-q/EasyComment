package io.owen.plugin.easycomment.popup;

import com.intellij.openapi.actionSystem.ActionGroup;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Created by owen_q on 15/02/2019.
 */
public class GitDynamicBranchGroup extends ActionGroup {
//
//    @NotNull
//    @Override
//    public AnAction[] getChildren(@Nullable AnActionEvent e) {
//        if(e==null)
//            return new AnAction[0];
//        else
//            return getBranchList(e.getProject());
//    }
//
//

    @Override
    public boolean isPopup() {
        return true;
    }

    @NotNull
    @Override
    public AnAction[] getChildren(@Nullable AnActionEvent e) {
        return new AnAction[]{new GitBranchAction("Show Git Branches")};
    }
}
