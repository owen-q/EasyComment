package io.owen.plugin.easycomment.core;

import com.intellij.openapi.command.WriteCommandAction;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.editor.VisualPosition;
import com.intellij.openapi.project.ProjectCoreUtil;
import io.owen.plugin.easycomment.popup.CommentPopup;
import io.owen.plugin.easycomment.popup.DynamicWritable;
import io.owen.plugin.easycomment.popup.GitBranchPopup;
import io.owen.plugin.easycomment.settings.PluginStateManager;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by owen_q on 23/03/2019.
 */
public class CommentEventDispatcher {
    private Logger logger = LoggerFactory.getLogger(CommentEventDispatcher.class);
    private CommentFormat commentFormat = null;
    private String format = "";

    private final int NOT_USED = -1;
    private boolean commentEnabled = false;
    private int commentStartPos = NOT_USED;

    private boolean branchEnabled = false;
    private int branchStartPos = NOT_USED;

//    private List<Job> jobList = new ArrayList<>();
    private List<String> jobOrderList = new ArrayList<>();
    private Map<String, Job> jobMap = new HashMap<>();
    private int formatIdx = 0;

    private Editor editor = null;

    private CommentEventDispatcher(){
        init();
    }

    public static CommentEventDispatcher getInstance()  {
        return Holder.INSTANCE;
    }

    // todo: Config 바뀌었을때 읽어들이기
    public void reloadCommentFormat(){
        init();
    }

    private void init(){
        this.format = PluginStateManager.getInstance().getFormat();
        parse();
        makeJob();
    }

    private void parse(){
        String predefinedCommentVariable = "$comment$";
        String predefinedBranchVariable = "$branch$";

        if(this.format.contains(predefinedBranchVariable)) {
            this.branchStartPos = this.format.indexOf(predefinedBranchVariable);
            this.branchEnabled = true;
        }

        if(this.format.contains(predefinedCommentVariable)) {
            this.commentStartPos = this.format.indexOf(predefinedCommentVariable);
            this.commentEnabled = true;
        }
    }


    private void makeJob(){
        if(this.commentEnabled) {
            DynamicWritable writable = new CommentPopup();
            Job job = Job.builder().actionState(ActionState.WAIT).writableAction(writable).initialIndex(commentStartPos).build();
            jobMap.put(writable.getKey(), job);
        }

        if(this.branchEnabled) {
            DynamicWritable writable = new GitBranchPopup();
            Job job = Job.builder().actionState(ActionState.WAIT).writableAction(new GitBranchPopup()).initialIndex(branchStartPos).build();
            jobMap.put(writable.getKey(), job);
        }

        this.jobOrderList = jobMap.values().stream()
                .sorted(JobComparator::compare)
                .map(job -> job.getWritableAction().getKey())
                .collect(Collectors.toList());
    }

    public void propagate(String key, ActionState state){
        jobMap.get(key).setActionState(state);

        if(state == ActionState.DONE || state == ActionState.CANCEL){
            // start other actions

            int currentJobIdx = jobOrderList.indexOf(key);
            if(isLastJob(currentJobIdx))
                end(this.editor);
            else {
                String nextActionKey = jobOrderList.get(currentJobIdx+1);
                start(nextActionKey, this.editor);
            }
        }
    }

    private boolean isLastJob(int currentJobIdx) {
        return currentJobIdx + 1 == jobOrderList.size();
    }

    public void start(String key, Editor editor){
        Job currentJob = null;
        this.editor = editor;

        if(StringUtils.isEmpty(key)){
            // start first things
            String firstJobKey = jobOrderList.get(0);
            currentJob = jobMap.get(firstJobKey);
        }
        else {
            // start target actions
            currentJob = jobMap.get(key);
        }

        currentJob.start();
        writeSupplement(editor, this.formatIdx, currentJob.getInitialIndex());
        currentJob.getWritableAction().show(editor);
        this.formatIdx = currentJob.getInitialIndex() + currentJob.getWritableAction().getVariableLength();
    }

    public void end(Editor editor){
        writeSupplement(editor, this.formatIdx, this.format.length());
    }

    private void writeSupplement(Editor editor, int startIdx, int endIdx){
        final Document document = editor.getDocument();
        VisualPosition visualPosition = editor.getCaretModel().getVisualPosition();
        int startPosition = editor.getSelectionModel().getSelectionStart();
        String text = format.substring(startIdx, endIdx);

        WriteCommandAction.runWriteCommandAction(ProjectCoreUtil.theOnlyOpenProject(), () -> {
            document.insertString(startPosition, text );
            VisualPosition newVisualPosition1 = new VisualPosition(visualPosition.line, visualPosition.column + text.length());
            editor.getCaretModel().moveToVisualPosition(newVisualPosition1);
        });
    }

    public void clear(){
        this.formatIdx = 0;
    }

    private static class Holder {
        private static CommentEventDispatcher INSTANCE = new CommentEventDispatcher();
    }
}
