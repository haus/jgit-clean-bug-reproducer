package jgitcleanbug;

import java.io.File;
import java.io.IOException;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.InitCommand;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.lib.RepositoryBuilder;

public class JgitCleanBug {
    public static void initializeRepo(File dir) throws GitAPIException {
        InitCommand initRepoCommand = new InitCommand();
        initRepoCommand.setDirectory(dir);
        initRepoCommand.call();
    }

    public static Repository getRepository(File dir) throws IOException {
        RepositoryBuilder curRepo = new RepositoryBuilder();
        curRepo.setWorkTree(dir);
        return curRepo.build();
    }

    public static void cleanRepo(File dir) throws IOException, GitAPIException {
        Repository repo = getRepository(dir);
        Git git = Git.wrap(repo);
        git.clean().setCleanDirectories(true).call();
    }

    public static void main(String[] args) throws Exception {
        File currentDir = new File(".").getCanonicalFile();
        File baseDir = new File(currentDir + "/target/repos");

        File outerRepoDir = new File(baseDir + "/" + "outer-repo");

        outerRepoDir.mkdirs();

        // Set up outer repo
        initializeRepo(outerRepoDir);

        File innerRepoDir = new File(outerRepoDir + "/" + "inner-repo");
        innerRepoDir.mkdirs();

        // Add an inner repo
        initializeRepo(innerRepoDir);

        // Try to clean the outer repo
        // Watch it fail with a JGitInternalException
        cleanRepo(outerRepoDir);
    }
}
