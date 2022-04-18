package ca.jrvs.apps.grep;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;
import org.apache.log4j.BasicConfigurator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JavaGrepStreamImp implements JavaGrepStream{

  private String regex;
  private String rootPath;
  private String outFile;

  final Logger logger = LoggerFactory.getLogger(JavaGrepImp.class);

  public static void main(String[] args) throws IllegalAccessException {

    if (args.length < 3){
      throw new IllegalAccessException("USAGE: JavaGrep regex rooPath outFile");
    }

    BasicConfigurator.configure();

    JavaGrepStreamImp javaGrepStreamImp = new JavaGrepStreamImp();

    javaGrepStreamImp.setRegex(args[0]);
    javaGrepStreamImp.setRootPath(args[1]);
    javaGrepStreamImp.setOutFile(args[2]);
    try {
      javaGrepStreamImp.process();
    } catch (Exception e) {
      javaGrepStreamImp.logger.error("Error: Unable to process", e);
    }

    //TO DO: the rest of this lol
  }

  @Override
  public String getRegex() {
    return regex;
  }

  @Override
  public void setRegex(String regex) {
    this.regex = regex;
  }

  @Override
  public String getRootPath() {
    return rootPath;
  }

  @Override
  public void setRootPath(String rootPath) {
    this.rootPath = rootPath;
  }

  @Override
  public String getOutFile() {
    return outFile;
  }

  @Override
  public void setOutFile(String outFile) {
    this.outFile = outFile;
  }

  @Override
  public void process() throws IOException {
    Stream<File> listFiles = listFiles(getRootPath());
    listFiles.flatMap(file -> {
      try {
        return readLines(file);
      } catch (IOException e) {
        e.printStackTrace();
        return null;
      }
    }).filter(line -> containsPattern(line))
        .forEach(line -> {
          try {
            writeToFile(line);
          } catch (IOException e) {
            e.printStackTrace();
          }
        });
  }

  @Override
  public Stream<File> listFiles(String rootDir) throws IOException{
    try {
      Path path = Paths.get(rootDir);
      return Files.walk(path)
          .filter(file -> Files.isRegularFile(file))
          .map(file -> file.toFile());
    } catch (IOException e) {
      throw e;
    }
  }

  @Override
  public Stream<String> readLines(File inputFile) throws IllegalArgumentException, IOException {
    Path path = inputFile.toPath();
    return Files.lines(path);
  }

  @Override
  public boolean containsPattern(String line) {
    Pattern pattern = Pattern.compile(getRegex());
    Matcher matcher = pattern.matcher(line);
    return matcher.find();
  }

  @Override
  public void writeToFile(String line) throws IOException {
    try (BufferedWriter writer = new BufferedWriter(new FileWriter(getOutFile(), true))) {
        writer.write(line);
        writer.newLine();
    } catch (IOException e) {
      throw e;
    }
  }
}
