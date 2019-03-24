package io.owen.plugin.easycomment.core;

import io.owen.plugin.easycomment.popup.DynamicWritable;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by owen_q on 23/03/2019.
 */

@Getter
@Setter
@Builder
public class Job {
    private DynamicWritable writableAction;
    private ActionState actionState = ActionState.WAIT;
    private int initialIndex = -1;


    public void start(){
        this.actionState = ActionState.WRITING;
    }

    public void done(){
        this.actionState = ActionState.DONE;
    }

    public void cancel(){
        this.actionState = ActionState.CANCEL;
    }


}
