Git Basics:
Git is a series of individual commands, we are not going to need to use many of them.  There are many ways to interact with a Git repository.  I will focus on using the git command line to show how we can manage the branching and merging of distibuted checkins.  You can use tools like Tortoise Git that will give you a GUI to interact with Git, there are Git plugins for Eclipse, or you can download git for your given operating system.  The examples shown here will be executed on OSX, which requires the Command Line Tools from the XCode application to be installed.  On Linux, say Ubuntu, you will need to install Git via Apt: sudo apt-get install git

For a summary of some of the common commands that will be used in a traditional Git workflow, type the command line: git help everyday

This will open a textual representation of commands and their descriptions.  The workflow we will use is summarized in the first example.  Starting with the text "Create a topic branch and develop".

Remember that when you clone a repository to your system, you are working on a local copy of the repository.  Once you have fully integrated your changes, have tested the code, and are confident it is ready to be deployed to the Robot permanently, you will push the changes from your local repository to the remote repository (Github).  That way, anyone that updates their repository with the latest changes from the remote side will receive your changes.

That is a very high level summary, more specifically, the concepts we are going to use primarily are:

Clone - Clone a repository into a new directory
Add -  Add newly created files to the source control file index
Diff - Show the differences between your current file, basically the changes you introduced to a given file.
Commit - Commit your changes you've made to the robot to your local repository
Push - Push the locally committed changes from your repository, to the remote repository
Status - Show the summary of the changes that you have currently made
Merge - Merge the changes from one local branch to another

To get a copy of the remote repository, copy the URL from the Github website.  If you can't see the URL, click the big green button that says clone or download, and copy the URL it shows you.

The URL for the robot repository is:
https://github.com/Lambda-Corps/2017Steamworks.git


This Readme will be separated into workflows.  Some of it will be repetitive, but repetition is the key to mastery of a subject. 
The text that is preceded by the '>' character, indicates that this is a command that will be typed on the command line.  The tab-indented lines below it will be the output.  For example, the below text will show a command entered to see the file listing of a directory and its output.

	> ls -al
			total 0
			drwxr-xr-x   3 scott  staff   102 Jan 26 22:18 .
			drwxr-xr-x+ 46 scott  staff  1564 Jan 26 17:42 ..

For ANY git command that exists, there is a corresponding help command.  You simply type:
	> git help <COMMAND>   // where <COMMAND> represents any git command
	> git help status   //  A text window shows the complete help text of what the command does


BEFORE PROCEEDING READ THIS IMPORTANT POINT, THEN READ IT AGAIN, THEN ONE MORE TIME TO PREVENT THOSE WEIRD ERRORS YOU WILL INEVITABLY HAVE
Any git commands that change the file system, like checkout, branch, etc will NOT be reflected in Eclipse until you refresh the project (either hit F5, or right click and refresh the project)

For more detail information on all of these concepts, feel free to browse the online book git-scm.com

There are great examples of commands, workflows, and the git basics.  Any questions that come up from reading there or Stack Overflow feel free to ask me.

1)  GET A COPY OF THE LATEST SOURCE CODE OF THE ROBOT
	This is a command you would want to run from you Eclipse work space directory.  Assuming the Eclipse workspace is in the user's home directory, in the directory named 'workspace':
	> cd
	> cd workspace
	> git clone https://github.com/Lambda-Corps/2017Steamworks.git
			Cloning into '2017Steamworks'...
			remote: Counting objects: 72, done.
			remote: Compressing objects: 100% (61/61), done.
			remote: Total 72 (delta 30), reused 0 (delta 0), pack-reused 0
			Unpacking objects: 100% (72/72), done.

	To show there is a new directory named 2017Steamworks with the source code:
	> ls -al
			total 8
			drwxr-xr-x   6 scott  staff   204 Jan 26 22:28 .
			drwxr-xr-x+ 46 scott  staff  1564 Jan 26 17:42 ..
			drwxr-xr-x   9 scott  staff   306 Jan 26 22:28 2017Steamworks

	I could now go into Eclipse, import an existing project and start the process of building the robot and running code.

	To show the filesystem results of the command at the time of the writing:
	> cd 2017Steamworks
	> ls -al
			total 32
			drwxr-xr-x   9 scott  staff  306 Jan 26 22:28 .
			drwxr-xr-x   6 scott  staff  204 Jan 26 22:28 ..
			drwxr-xr-x  12 scott  staff  408 Jan 26 22:28 .git
			-rw-r--r--   1 scott  staff  189 Jan 26 22:28 .gitignore
			-rw-r--r--   1 scott  staff   16 Jan 26 22:28 README.md
			drwxr-xr-x   3 scott  staff  102 Jan 26 22:28 bin
			-rw-r--r--   1 scott  staff  164 Jan 26 22:28 build.properties
			-rw-r--r--   1 scott  staff  999 Jan 26 22:28 build.xml
			drwxr-xr-x   3 scott  staff  102 Jan 26 22:28 src



2)  CREATE A NEW TOPIC BRANCH (a branch that implements a new robot feature) IN THE LOCAL REPOSITORY
	For these instructions to work, I assume you are at a command line sitting at a prompt inside the 2017Steamworks repository directory.  The 'pwd' command will show you your current working directory.
	> pwd
			/Users/scott/robotics/2017Steamworks


	There are two steps to creating a local feature (topic) branch.  Create the branch, then checkout the branch for edits.  You can have any number of branches you would like, for example if you have been assigned two robot tasks, you can have two different branches each working toward the tasks.  Let's assume I have been assigned the two tasks, 1) Create Tank Drive controls and 2) Create arcade drive controls.  

	These two tasks will both be affecting the drive train subsystem, so we need to be careful in how we develop these competing feature branches.  First things first, let's figure out what branches we have available to us.
	> git status
			On branch master
			Your branch is up-to-date with 'origin/master'.
			nothing to commit, working tree clean

	You can see from the output a few things
		- The current checked out branch is named 'master'.
		- There are no remote changes that exist that need to be incorporated locally
		- At the moment, there are no changes on the local repository that are different from the remote repository

	To get the list of current branches:
	> git branch --list
			* master

	There is currently only one branch available, named 'master'

	Now lets create a new feature branch for making our tank drive.
	> git branch tankdrive
	> git branch --list
			* master
			  tankdrive

	Notice the newly created branch named 'tankdrive', and the asterisk next to 'master'.  The asterisk indicates the currently checked out branch.  To begin work on the tank drive branch, we need to checkout the new branch.
	> git checkout tankdrive
			Switched to branch 'tankdrive'
	> git status
			On branch tankdrive
			nothing to commit, working tree clean

	Now on the tankdrive branch, we can open eclipse, refresh the project and begin work on our newly created branch.  The assumption of our work in eclipse will be limited to creating the new Subsytem class and adding the RobotMap entries for the electronics, not adding associated commands or anything like that.

	After making those changes to the RobotMap and adding the subsytem, we will be ready to commit them.
	>  git status
			On branch tankdrive
			Changes not staged for commit:
			  (use "git add <file>..." to update what will be committed)
			  (use "git checkout -- <file>..." to discard changes in working directory)

				modified:   src/org/usfirst/frc/team1895/robot/RobotMap.java

			Untracked files:
			  (use "git add <file>..." to include in what will be committed)

				src/org/usfirst/frc/team1895/robot/subsystems/TankDriveSubsystem.java

			no changes added to commit (use "git add" and/or "git commit -a")

	From the output, the important part is that there are two categories of modifications.  
		1) Changes not staged for commit
			These are files that are tracking changes, but have not yet been added to the index
		2) Untracked files
			These are new files that are not in the source file index with tracking changes.  These are typically files that have been newly created since the last commit.

		The solution to both files is the same, they need to be added to the file index through the git add command.

		> git add src/org/usfirst/frc/team1895/robot/RobotMap.java 
		> git add src/org/usfirst/frc/team1895/robot/subsystems/TankDriveSubsystem.java 
		> git status
				On branch tankdrive
				Changes to be committed:
				  (use "git reset HEAD <file>..." to unstage)

					modified:   src/org/usfirst/frc/team1895/robot/RobotMap.java
					new file:   src/org/usfirst/frc/team1895/robot/subsystems/TankDriveSubsystem.java

		Notice the output has changed to include the changes that WOULD be committed if we were to execute the git commit command, so let's do that now.  After you execute the commit command, you will be given a text editor where you type a short message that talks about the changes you just made.  Optionally, you can pass the -m argument to git commit, followed by a sentence enclosed in quotation marks.  (e.g. git commit -m "Added new tankdrive subsystem")
		> git commit 
				[tankdrive 5a8868a] Added a new tankdrive subsystem.
				 2 files changed, 3 insertions(+)
				 create mode 100644 src/org/usfirst/frc/team1895/robot/subsystems/TankDriveSubsystem.java

		The output tells you the summary of the commit, with how many files, how many affected source lines, as well as the commit message.  To see the history you can use the git log command:
		> git log
				commit 5a8868ab6a7994f8da761150de02ce60ff23c2c7
				Author: Scott P <lospugs@gmail.com>
				Date:   Thu Jan 26 23:17:05 2017 -0500

				    Added a new tankdrive subsystem.

				commit e8bab319e25d3a83d1c6377cb827006ff3c65858
				Author: Ethan Passmore <ethan.passmore@gmail.com>
				Date:   Thu Jan 26 17:44:34 2017 -0500

				    Initial Commit
				    
				    Framework from Maddy

				commit a632254533955b441a3bfdcdc8120f181acd2c89
				Author: Ethan Passmore <ethan.passmore@gmail.com>
				Date:   Tue Jan 24 08:18:21 2017 -0500

				    Initial commit

		In the log output you can see the history of the commits (in descending order), the latest on top.  You can see that this particular branch of the repository has two commits that were
		added by Ethan, and one from me (Scott).  Each message has a hash value (the long string after the word 'commit'), an author and a timestamp.


		Now that we have created our branch, written the code, tested it in the simulator as well as on a live robot, we are ready to push it in to the release (master) branch of the robot.  Before doing that, we need to verify that other people haven't pushed changes from the remote side that we aren't accounting for yet.  So the first step is to checkout the master branch again, and check for updates.
		> git checkout master
				Switched to branch 'master'
				Your branch is up-to-date with 'origin/master'.
		> git pull 
				Already up-to-date.

		The output here states there are no remote changes that we need to worry about.  We are safe to incorporate the feature (topic) branch into the master. 
		> git merge tankdrive
				Updating e8bab31..5a8868a
				Fast-forward
				 src/org/usfirst/frc/team1895/robot/RobotMap.java                      | 2 ++
				 src/org/usfirst/frc/team1895/robot/subsystems/TankDriveSubsystem.java | 1 +
				 2 files changed, 3 insertions(+)
				 create mode 100644 src/org/usfirst/frc/team1895/robot/subsystems/TankDriveSubsystem.java

		To see the results of where we are now:
		> git status
				On branch master
				Your branch is ahead of 'origin/master' by 1 commit.
				  (use "git push" to publish your local commits)
				nothing to commit, working tree clean

		Notice that git is reporting to us that our LOCAL copy of the repository branch, has one more commit added to it than the REMOTE copy of the repository.  The message tells us that in order to get the two to sync up we should push the changes through the push command.  Since we want to be EXTRA careful and not screw up the remote repository, we are going to simulate the push command to verify that it will work successfully.  
		> git push --dry-run
				Username for 'https://github.com': lospugs
				Password for 'https://lospugs@github.com': 
				To https://github.com/Lambda-Corps/2017Steamworks.git
				   e8bab31..5a8868a  master -> master

		This command will ask you for your github credentials, will log into our team repository, and report to you the results of the commit command IF WE WERE TO EXECUTE IT.  If there are any error messages here, we need to work those out BEFORE pushing.  If you need help with this, contact me if Google doesn't help resolve the issue.

		If there are no error messages (like in my example), then feel free and push the changes to the remote side.



