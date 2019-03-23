package io.owen.plugin.easycomment.macro;

import com.intellij.codeInsight.template.*;
import com.intellij.codeInsight.template.macro.MacroBase;
import io.owen.plugin.easycomment.GitUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Created by owen_q on 14/02/2019.
 */
public class GitUserEmailMacro extends MacroBase {
    private static final String NAME = "gitUserEmail";
    private static final String DESCRIPTION = "gitUserEmail()";

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
        return new TextResult(GitUtil.getUserEmail());
    }

}
