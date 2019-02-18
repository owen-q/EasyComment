package io.owen.plugin.gittemplate.macro;

import com.intellij.codeInsight.template.*;
import com.intellij.codeInsight.template.macro.MacroBase;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.roots.ProjectFileIndex;
import com.intellij.openapi.vfs.VirtualFile;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.storage.file.FileRepositoryBuilder;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.File;

/**
 * Created by owen_q on 14/02/2019.
 */
public class GitUserEmailMacro extends MacroBase {
    private static final String NAME = "gitBranchName";
    private static final String DESCRIPTION = "gitBranchName()";

    public GitUserEmailMacro(){
        super(NAME, DESCRIPTION);
    }

    @Nullable
    @Override
    protected Result calculateResult(@NotNull Expression[] params, ExpressionContext context, boolean quick) {
        return getResult(context);
    }

    @Override
    public Result calculateResult(@NotNull Expression[] params, ExpressionContext context) {
        return getResult(context);
    }

    @Override
    public Result calculateQuickResult(@NotNull Expression[] params, ExpressionContext context) {
        return getResult(context);
    }

    @Override
    public String getName() {
        return NAME;
    }

    @Override
    public String getPresentableName() {
        return NAME;
    }

    @NotNull
    @Override
    public String getDefaultValue() {
        return super.getDefaultValue();
    }


    @Override
    public boolean isAcceptableInContext(TemplateContextType context) {
        return context instanceof JavaCodeContextType;
    }

    private Result getResult(ExpressionContext context){
        String branchName = getCurrentBranchName(context.getProject());
        return new TextResult(branchName);
    }

    private String getCurrentBranchName(Project project) {
        String branchName = "";

        try {
            VirtualFile projectBaseDir = project.getWorkspaceFile();
            String projectPath = ProjectFileIndex.SERVICE.getInstance(project).getContentRootForFile(projectBaseDir).getPath();

            Repository existingRepo = new FileRepositoryBuilder()
                    .setGitDir(new File(projectPath + "/.git"))
                    .build();

            branchName = existingRepo.getBranch();

            if(branchName.equals("") || branchName == null)
                branchName = "Project is not initialized";
        }
        catch (Exception e) {
            branchName = "Project is not initialized";
        }

        return branchName;
    }

}
