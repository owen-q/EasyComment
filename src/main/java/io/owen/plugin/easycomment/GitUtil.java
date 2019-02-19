package io.owen.plugin.easycomment;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.project.ProjectCoreUtil;
import com.intellij.openapi.roots.ProjectFileIndex;
import com.intellij.openapi.vfs.VirtualFile;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.storage.file.FileRepositoryBuilder;

import java.io.File;
import java.util.List;

/**
 * Created by owen_q on 15/02/2019.
 */
public class GitUtil {
    private static Project currentProject;
    private static String projectAbsolutePath;

    static {
//        VirtualFile projectBaseDir = .getWorkspaceFile();
//        String projectPath = ProjectFileIndex.SERVICE.getInstance(project).getContentRootForFile(projectBaseDir).getPath();
    }

    public static String[] getBranchList(){
        try{
            Project currentProject = ProjectCoreUtil.theOnlyOpenProject();
            VirtualFile projectBaseDir = currentProject.getWorkspaceFile();
            String projectPath = ProjectFileIndex.SERVICE.getInstance(currentProject).getContentRootForFile(projectBaseDir).getPath();

            Repository existingRepo = new FileRepositoryBuilder()
                    .setGitDir(new File(projectPath + "/.git"))
                    .build();

            Git git = new Git(existingRepo);
//            git.log().call().forEach(revCommit -> revCommit.au);

            List<Ref> refList = git.branchList().call();

            String[] result = new String[refList.size()];

            for(int i=0; i<refList.size(); i++){
                result[i] = (refList.get(i).getName());
            }

            return result;
        }
        catch (Exception e){
            return new String[]{"error"};
        }
    }

}
