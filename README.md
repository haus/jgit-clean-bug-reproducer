## Prerequisites

* Git
* Maven

## Usage

1. Clone the repo `git@github.com:haus/jgit-clean-bug-reproducer.git`
2. Run `mvn clean install`
3. Run `mvn exec:java`

After step 3, you should see some output that looks like the following...


    [INFO] Scanning for projects...
    [INFO]
    [INFO] ------------------------------------------------------------------------
    [INFO] Building jgit-clean-bug 1.0-SNAPSHOT
    [INFO] ------------------------------------------------------------------------
    [INFO]
    [INFO] --- exec-maven-plugin:1.4.0:java (default-cli) @ jgit-clean-bug ---
    SLF4J: Failed to load class "org.slf4j.impl.StaticLoggerBinder".
    SLF4J: Defaulting to no-operation (NOP) logger implementation
    SLF4J: See http://www.slf4j.org/codes.html#StaticLoggerBinder for further details.
    [WARNING]
    java.lang.reflect.InvocationTargetException
    	at sun.reflect.NativeMethodAccessorImpl.invoke0(Native Method)
    	at sun.reflect.NativeMethodAccessorImpl.invoke(NativeMethodAccessorImpl.java:62)
    	at sun.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:43)
    	at java.lang.reflect.Method.invoke(Method.java:498)
    	at org.codehaus.mojo.exec.ExecJavaMojo$1.run(ExecJavaMojo.java:293)
    	at java.lang.Thread.run(Thread.java:745)
    Caused by: org.eclipse.jgit.api.errors.JGitInternalException: Could not delete file /Users/matthaus/src/jgit-clean-bug/target/repos/outer-repo/inner-repo
    	at org.eclipse.jgit.api.CleanCommand.call(CleanCommand.java:138)
    	at jgitcleanbug.JgitCleanBug.cleanRepo(JgitCleanBug.java:28)
    	at jgitcleanbug.JgitCleanBug.main(JgitCleanBug.java:50)
    	... 6 more
    Caused by: java.io.IOException: Could not delete file /Users/matthaus/src/jgit-clean-bug/target/repos/outer-repo/inner-repo
    	at org.eclipse.jgit.util.FileUtils.delete(FileUtils.java:197)
    	at org.eclipse.jgit.util.FileUtils.delete(FileUtils.java:125)
    	at org.eclipse.jgit.api.CleanCommand.call(CleanCommand.java:125)
    	... 8 more
    [INFO] ------------------------------------------------------------------------
    [INFO] BUILD FAILURE
    [INFO] ------------------------------------------------------------------------
    [INFO] Total time: 0.696 s
    [INFO] Finished at: 2016-07-22T10:29:48-07:00
    [INFO] Final Memory: 11M/309M
    [INFO] ------------------------------------------------------------------------
    [ERROR] Failed to execute goal org.codehaus.mojo:exec-maven-plugin:1.4.0:java (default-cli) on project jgit-clean-bug: An exception occured while executing the Java class. null: InvocationTargetException: Could not delete file /Users/matthaus/src/jgit-clean-bug/target/repos/outer-repo/inner-repo -> [Help 1]
    [ERROR]
    [ERROR] To see the full stack trace of the errors, re-run Maven with the -e switch.
    [ERROR] Re-run Maven using the -X switch to enable full debug logging.
    [ERROR]
    [ERROR] For more information about the errors and possible solutions, please read the following articles:
    [ERROR] [Help 1] http://cwiki.apache.org/confluence/display/MAVEN/MojoExecutionException
