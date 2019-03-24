package io.owen.plugin.easycomment.core;

/**
 * Created by owen_q on 23/03/2019.
 */
public interface JobComparator {

    static int compare(Job j1, Job j2){
        if(j1.getInitialIndex() < j2.getInitialIndex())
            return -1;
        else
            return 1;
    }
}
