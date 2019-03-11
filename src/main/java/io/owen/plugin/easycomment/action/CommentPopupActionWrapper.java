package io.owen.plugin.easycomment.action;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.DataKeys;
import io.owen.plugin.easycomment.popup.CommentPopup;
import org.jetbrains.annotations.NotNull;

/**
 * Created by owen_q on 11/03/2019.
 */
public class CommentPopupActionWrapper extends AnAction {
    private com.intellij.openapi.editor.Editor editor = null;


    @Override
    public void beforeActionPerformedUpdate(@NotNull AnActionEvent e) {
        this.editor = e.getData(DataKeys.EDITOR);
    }

    @Override
    public void actionPerformed(AnActionEvent e) {
        CommentPopup commentPopup = new CommentPopup(editor);
        commentPopup.show();
    }
}
