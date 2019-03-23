package io.owen.plugin.easycomment.popup;

import com.intellij.openapi.editor.Editor;

/**
 * Created by owen_q on 23/03/2019.
 */
public interface DynamicWritable {

    String getKey();

    String getVariable();

    int getVariableLength();

    Editor write(Editor editor, String text);

    void show(Editor editor);
}
