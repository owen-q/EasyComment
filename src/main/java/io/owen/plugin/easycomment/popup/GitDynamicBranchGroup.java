package io.owen.plugin.easycomment.popup;

import com.intellij.openapi.actionSystem.ActionGroup;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.roots.ProjectFileIndex;
import com.intellij.openapi.vfs.VirtualFile;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.storage.file.FileRepositoryBuilder;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.util.List;

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

    private AnAction[] getBranchList(Project project){
        try{
            VirtualFile projectBaseDir = project.getWorkspaceFile();
            String projectPath = ProjectFileIndex.SERVICE.getInstance(project).getContentRootForFile(projectBaseDir).getPath();

            Repository existingRepo = new FileRepositoryBuilder()
                    .setGitDir(new File(projectPath + "/.git"))
                    .build();

            Git git = new Git(existingRepo);

            List<org.eclipse.jgit.lib.Ref> refList = git.branchList().call();

            AnAction[] result = new AnAction[refList.size()];

            for(int i=0; i<refList.size(); i++){
                result[i] = new GitBranchAction(refList.get(i).getName());
            }

            return result;
        }
        catch (Exception e){
            e.printStackTrace();
        }

        return new GitBranchAction[]{new GitBranchAction("aaaaa")};
    }
}
