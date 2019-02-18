package io.owen.plugin.easycomment;

import org.junit.Test;

/**
 * Created by owen_q on 18/02/2019.
 */
public class TestBranchRefine {


    @Test
    public void testBranchName() {
        // Given:
        String givenBranchName = "refs/heads/a";

        // When:

        String refinedBranhName = refine(givenBranchName);

        // Then:

        System.out.println(refinedBranhName);


    }

    public String refine(String dirtyBranchName) {
        String pattern = "refs/heads/";
        int idx = dirtyBranchName.indexOf(pattern);
        return dirtyBranchName.substring(idx + pattern.length());
    }
}
