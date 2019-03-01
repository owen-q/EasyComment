package io.owen.plugin.easycomment.settings;

import com.intellij.openapi.components.PersistentStateComponent;
import com.intellij.openapi.components.ServiceManager;
import com.intellij.openapi.components.State;
import com.intellij.openapi.components.Storage;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.project.ProjectCoreUtil;
import io.owen.plugin.easycomment.GitUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Created by owen_q on 20/02/2019.
 */

@State(name = "PluginStateManager", storages = @Storage("ec-plugin.xml"))
public class PluginStateManager implements PersistentStateComponent<PluginState>{
    private PluginState pluginState = new PluginState();

    @Nullable
    @Override
    public PluginState getState() {
        return this.pluginState;
    }

    @Override
    public void loadState(@NotNull PluginState state) {
        this.pluginState = state;
    }

    @Nullable
    public static PluginStateManager getInstance(Project project){
        PluginStateManager manager = ServiceManager.getService(PluginStateManager.class);
        return manager;
    }

    @Nullable
    public static PluginStateManager getInstance(){
        return getInstance(ProjectCoreUtil.theOnlyOpenProject());
    }

    // get, setter facade

    public void setFormat(String format){
        this.pluginState.setFormat(format);
    }

    public String getFormat(){
        return this.pluginState.getFormat();
    }

    public boolean isCommentTypeEnabled(){
        return this.pluginState.isCommentTypeEnabled();
    }

    public boolean isGitBranchNameEnabled(){
        return this.pluginState.isGitBranchNameEnabled();
    }

    // TODO(owen.qqq):
    public void loadGitconfig(){
        String userName = GitUtil.getUserNmae();
        String userEmail = GitUtil.getUserEmail();
        this.pluginState.setGitUserEmail(userEmail);
        this.pluginState.setGitUserName(userName);
    }



}
