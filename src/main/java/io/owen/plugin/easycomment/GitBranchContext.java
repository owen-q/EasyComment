package io.owen.plugin.easycomment;

import com.intellij.codeInsight.template.TemplateContextType;
import com.intellij.psi.PsiFile;
import org.jetbrains.annotations.NotNull;

/**
 * Created by owen_q on 14/02/2019.
 */
public class GitBranchContext extends TemplateContextType {
//    private Logger logger = LoggerFactory.getLogger(GitBranchContext.class);

    protected GitBranchContext(){
        super("GIT_BRANCH_TEMPLATE", "GitBranchTemplate");
    }

    public GitBranchContext(@NotNull String id, @NotNull String presentableName) {
        super(id, presentableName);
    }

    @Override
    public boolean isInContext(@NotNull PsiFile file, int offset) {
        return file.getName().endsWith(".java");
    }



}
