package ca.jrvs.apps.grep;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.Path;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.apache.log4j.BasicConfigurator;

public class JavaGrepLambdaImp extends JavaGrepImp{

  public static void main(String[] args) throws IllegalArgumentException {
    if (args.length < 3){
      throw new IllegalArgumentException("USAGE: JavaGrep regex rooPath outFile");
    }

//    BasicConfigurator.configure();

    JavaGrepLambdaImp javaGrepLambdaImp = new JavaGrepLambdaImp();

    javaGrepLambdaImp.setRegex(args[0]);
    javaGrepLambdaImp.setRootPath(args[1]);
    javaGrepLambdaImp.setOutFile(args[2]);
    try {
      javaGrepLambdaImp.process();
    } catch (Exception e) {
      javaGrepLambdaImp.logger.error("Error: Unable to process", e);
    }

  }

  @Override
  public List<File> listFiles(String rootDir) {
    Path pathRoot = Paths.get(rootDir);
    try (Stream<Path> files = Files.walk(pathRoot)){
      return files.filter(file -> !Files.isDirectory(file))
          .map(Path :: toFile)
          .collect(Collectors.toList());
    } catch (Exception e) {
      System.out.println("Cannot read the file");
      return new ArrayList<>();
    }
  }

  @Override
  public List<String> readLines(File inputFile) throws IOException {
    Path path = inputFile.toPath();
    return Files.lines(path).collect(Collectors.toList());
  }

}
