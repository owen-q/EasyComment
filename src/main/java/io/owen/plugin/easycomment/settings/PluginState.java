package io.owen.plugin.easycomment.settings;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by owen_q on 20/02/2019.
 */
@Getter
@Setter
public class PluginState {
    private String format = "Enter comment formats";
    private String gitUserName = "";
    private String gitUserEmail = "";

    private final String COMMENT_TYPE_CONSTANT = "$comment$";
    private boolean isCommentTypeEnabled;

    private final String GIT_BRANCH_NAME_CONSTANT = "$git_branch$";
    private boolean isGitBranchNameEnabled;


    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public void isAdjustable(){

    }

    public void parse(){

    }

    public boolean isCommentTypeEnabled() {
        return isCommentTypeEnabled;
    }

    public boolean isGitBranchNameEnabled() {
        return isGitBranchNameEnabled;
    }

    public String warnning(){
        return "Unknown command: ";
    }

    // ---- Git


}
