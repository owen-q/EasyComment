package io.owen.plugin.easycomment;

import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.storage.file.FileRepositoryBuilder;
import org.junit.Test;

import java.io.File;

/**
 * Created by owen_q on 01/03/2019.
 */
public class TestJGit {
    @Test
    public void testJgitRepository() {
        try {
            Repository existingRepo = new FileRepositoryBuilder()
                    .setGitDir(new File("/Users/owen/work/owen-toy/easy-comment" + "/.git"))
                    .build();


            existingRepo.getConfig().

            String userName = "";
            String userEmail = "";


            userName = existingRepo.getConfig().getString("user", null, "name");
            userEmail = existingRepo.getConfig().getString("user", null, "email");

            System.out.println(userName);
            System.out.println(userEmail);
        }
        catch(Exception e){

        }

    }
}
