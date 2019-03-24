package io.owen.plugin.easycomment.util;

import com.intellij.openapi.command.WriteCommandAction;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.editor.VisualPosition;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.project.ProjectCoreUtil;

/**
 * Created by owen_q on 25/02/2019.
 */
public class CommentWriteUtil {

    public static void write(String format, String currentCommand, String text, Editor targetEditor){
        Project project = ProjectCoreUtil.theOnlyOpenProject();
        final Document document = targetEditor.getDocument();
        VisualPosition visualPosition = targetEditor.getCaretModel().getVisualPosition();
        int startPosition = targetEditor.getSelectionModel().getSelectionStart();

        WriteCommandAction.runWriteCommandAction(project, () -> {
            document.insertString(startPosition, text);
            VisualPosition newVisualPosition1 = new VisualPosition(visualPosition.line, visualPosition.column + text.length());
            targetEditor.getCaretModel().moveToVisualPosition(newVisualPosition1);
        });

    }
}
