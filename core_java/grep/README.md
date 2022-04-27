# Introduction
This project is a simplified version of the UNIX `grep` command implemented in Java. Its basic function is to scan a directory recursively and 
return any lines in any of the child files that match a regular expression. The entire project is managed using Apache Maven which organizes 
its dependencies and plugins. In this case that includes the logging package slf4j and the shade lifecycle plugin that generates the Uber JAR file
needed to run the application. There are two implementations of the grep functionality in this project: one that relies on pre-Java 8 JDK features and one
that leverages the power of Java JDK 8 Streams and Lambdas. Finally, the app is deployed on Docker hub using a Docker image built from the project code.

# Quick Start
```bash
outfile=grep_$(date +%F_%T).txt
srcfile="./data"
docker pull khoriwatson/grep
docker run -rm -v `pwd`/data:/data -v `pwd`/out:/out khoriwatson/grep [INSERT REGEX HERE] ${srcfile} ${outfile}
```
The user can change the names or locations of the source file/directory and the log file either using the variables or directly through in the `docker run...` command. The regex pattern must be one that does not include spaces or declares them explicitly using the `\s` character class rather than a simple typed space as that interrupts the reading of the command line arguments.

#Implemenation
## Pseudocode
There are two different implementations of the grep functionality in this project. The implementation without Streams and Lambdas is shown below.
```java
matchedLines = []
for (file in listAllDirectoryFilesRecursively(rootDir)){
  for (line in readLines(file)){
    if (containsPattern(line)){
      matchedLines.add(line)
    }
  }
}
writeToFile(matchedLines)  
```
The implementation that makes use of Streams and Lambdas is as follows
```java
//Stream of File objects
listAllFilesRecursively(rootDir)

//Creates a Stream of Streams of Strings and flattens it to a Stream of all Strings from the all files
.mapAndFlatten (file to readLines(file))

.filter(containsPattern(line))

 //Terminating operation that writes a single line to a file rather than a list of lines
.forEach(writeToFile(line))
```


## Performance Issue
One of the issues with the application surrounds memory usage and the viability of using the grep program on directories that contain many large files or on
large files themselves. Depending on the size of the heap space allocated by the JVM at runtime, and the amount of objects created during program execution,
there is a chance of running into a OutOfMemoryError. There are two ways to get around this error: the first is to use the `-Xmx` flag when configuring the JVM to specify
a maximum heap size greater the size of the files that are being put through the grep program, but this is easier said than done. Another solution is to use the Stream
and Lambda implementation of the grep program as Java 8 Streams operate on data being read in through a source rather than being stored in the heap. Using the Stream
and Lambda version, the program is able to process a file larger than 5 megabytes with a heap only 5 megabytes large.

# Test
The application was tested manually using some sample data in the form of a large text file that contains several of William Shakespeare's most famous plays. The project was
tested sequentially with the first test being of its ability to access files recursively within directories and also its ability to distinguish between directories
and regular files. This was done by nesting the sample file within several directories in the `data` directory and running the program with `data` as the `rootDir` and 
seeing if it could find the sample file. Testing to see if the program could read all the lines of a file and if it could match lines based on a regular expression was done
at the same time, using the same file and manually comparing the output lines that were sent to an ArrayList (rather than a file) to see if they matched the regular
expression. The ability for the program to match lines across several files was also tested by running the program with an additional, smaller sample file that matched
certain regex (regular expression) patterns. Finally, it was all put together in the `process()` method and tested as one program using the sample text files provided and comparing
the expected output with the text generated in the `outfile`.

# Deployment
A Docker image of this app built from a OpenJDK base image running on the lightweight Alpine Linux distribution. The Docker image contains the full uber jar file found in the Maven target folder and runs that as the entry point for the image. This way, the user can use the app by adding the desired command line arguments at the end of the `docker run...` command as opposed to invoke `java` explicitly.

# Improvement
1. One thing that I would like to improve with this project is the speed with which it actually parses the information and scans through the files. In particular, the 
implementation that utilizes the Java Stream API might be sped up with the use of parallel streams that would leverage multi-core processors to complete the stream operations
in parallel.
2. Another potential improvement is adding more of the grep features, like being able to match lines in a case insensitive way or tell the user how many matches there
are for a pattern withing a given file or overall. In addition, it would be nice to be able to tell which matched lines are from which file.
3. Another improvement could be more robust command line argument reading that is able to parse regex that includes literal spaces in the non-character class defined way (\s).
