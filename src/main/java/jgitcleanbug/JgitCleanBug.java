package jgitcleanbug;

import java.io.File;
import java.io.IOException;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.InitCommand;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.lib.PersonIdent;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.lib.RepositoryBuilder;

public class JgitCleanBug {
    static PersonIdent committer = new PersonIdent("Jill Adams", "jill@foo.bar");

    public static void initializeRepo(File dir) throws GitAPIException {
        InitCommand initRepoCommand = new InitCommand();
        initRepoCommand.setDirectory(dir);
        initRepoCommand.call();
    }

    public static void initializeRepo(File gitDir, File workDir) throws GitAPIException {
        InitCommand initRepoCommand = new InitCommand();
        initRepoCommand.setDirectory(gitDir);
        initRepoCommand.setBare(true);
        initRepoCommand.call();
    }

    public static Repository getRepository(File dir) throws IOException {
        RepositoryBuilder curRepo = new RepositoryBuilder();
        curRepo.setWorkTree(dir);
        return curRepo.build();
    }

    public static Repository getRepository(File gitDir, File workDir) throws IOException {
        RepositoryBuilder curRepo = new RepositoryBuilder();
        curRepo.setGitDir(gitDir);
        curRepo.setWorkTree(workDir);
        return curRepo.build();
    }

    public static void commitRepo(File gitDir, File workDir) throws IOException, GitAPIException {
        File file = new File(workDir + "/" + "a-file");
        file.createNewFile();
        Repository repo = getRepository(gitDir, workDir);
        Git git = Git.wrap(repo);
        git.add().addFilepattern("./*").call();
        git.commit().setMessage("Commiting file " + file.getPath() + " in repo " + workDir.getPath()).setAll(true).setAuthor(committer).setCommitter(committer).call();
    }

    public static void commitRepo(File dir) throws IOException, GitAPIException {
        File file = new File(dir + "/" + "a-file");
        file.createNewFile();
        Repository repo = getRepository(dir);
        Git git = Git.wrap(repo);
        git.add().addFilepattern("./*").call();
        git.commit().setMessage("Commiting file " + file.getPath() + " in repo " + dir.getPath()).setAll(true).setAuthor(committer).setCommitter(committer).call();
    }

    public static void cleanRepo(File gitDir, File workDir) throws IOException, GitAPIException {
        Repository repo = getRepository(gitDir, workDir);
        Git git = Git.wrap(repo);
        git.clean().setCleanDirectories(true).call();
    }

    public static void main(String[] args) throws Exception {
        File baseDir = new File(".").getCanonicalFile();
        File outerRepoGitDir = new File(baseDir + "/" + "outer-repo.git");
        File outerRepoWorkDir = new File(baseDir + "/" + "outer-repo");

        outerRepoGitDir.mkdirs();
        outerRepoWorkDir.mkdirs();

        // Set up outer repo
        initializeRepo(outerRepoGitDir, outerRepoWorkDir);

        // Make a commit
        commitRepo(outerRepoGitDir, outerRepoWorkDir);

        File innerRepoDir = new File(outerRepoWorkDir + "/" + "inner-repo");
        innerRepoDir.mkdirs();

        // Add an inner repo
        initializeRepo(innerRepoDir);
        commitRepo(innerRepoDir);

        // Try to clean the outer repo
        try {
            cleanRepo(outerRepoGitDir, outerRepoWorkDir);
        } catch (GitAPIException e) {
            System.out.println("Something bad happened during the clean");
            System.out.println(e.getMessage());
        }


        // Watch it fail
    }
}
