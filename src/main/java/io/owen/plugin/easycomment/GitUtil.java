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
import java.util.stream.Collectors;

/**
 * Created by owen_q on 15/02/2019.
 */
public class GitUtil {
    private static Project currentProject;
    private static String projectAbsolutePath;
    private static final String GIT_BRANCH_PREFIX = "refs/heads/";

    private static String refine(String dirtyBranchName) {
        int idx = dirtyBranchName.indexOf(GIT_BRANCH_PREFIX);
        return dirtyBranchName.substring(idx + GIT_BRANCH_PREFIX.length());
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
            List<Ref> refList = git.branchList().call();

            return refList.stream().map(Ref::getName).map(GitUtil::refine).collect(Collectors.toList()).toArray(new String[0]);

            /*
            for(int i=0; i<refList.size(); i++){
                result[i] = (refList.get(i).getName());
            }

            return result;
            */

        }
        catch (Exception e){
            return new String[]{"error"};
        }
    }

    private static Repository getCurrentRepository(){
        try{
            Project currentProject = ProjectCoreUtil.theOnlyOpenProject();
            VirtualFile projectBaseDir = currentProject.getWorkspaceFile();
            String projectPath = ProjectFileIndex.SERVICE.getInstance(currentProject).getContentRootForFile(projectBaseDir).getPath();

            Repository existingRepo = new FileRepositoryBuilder()
                    .setGitDir(new File(projectPath + "/.git"))
                    .build();

            return existingRepo;
        }
        catch (Exception e){
            return null;
        }
    }

    public static String getUserName(){
        Repository currentRepository = getCurrentRepository();
        return currentRepository.getConfig().getString("user", null, "name");
    }

    public static String getUserEmail(){
        Repository currentRepository = getCurrentRepository();
        return currentRepository.getConfig().getString("user", null, "email");
    }

}
