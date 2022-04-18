package ca.jrvs.apps.grep;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.log4j.BasicConfigurator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JavaGrepImp implements JavaGrep{

  private String regex;
  private String rootPath;
  private String outFile;

  final Logger logger = LoggerFactory.getLogger(JavaGrepImp.class);

  public static void main(String[] args) throws IllegalAccessException {

    if (args.length < 3){
      throw new IllegalAccessException("USAGE: JavaGrep regex rooPath outFile");
    }

    BasicConfigurator.configure();

    JavaGrepImp javaGrepImp = new JavaGrepImp();

    javaGrepImp.setRegex(args[0]);
    javaGrepImp.setRootPath(args[1]);
    javaGrepImp.setOutFile(args[2]);
    try {
      javaGrepImp.process();
    } catch (Exception e) {
      javaGrepImp.logger.error("Error: Unable to process", e);
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
    List<String> matchedLines = new ArrayList<>();
    for (File file : listFiles(getRootPath())){
      try {
        for (String line : readLines(file)){
          if (containsPattern(line)){
            matchedLines.add(line);
          }
        }
      } catch (Exception e){
        throw new IOException("File could not be read");
      }
    }
    try {
      writeToFile(matchedLines);
    } catch (IOException e) {
      throw e;
    }

  }

  @Override
  public List<File> listFiles(String rootDir) {
    File dir = new File(rootDir);
    List<File> resultFiles = new ArrayList<File>();
    File[] dirContents = dir.listFiles();
//    System.out.println(dir.isDirectory());
    for (File dirContent: dirContents) {
      if (dirContent.isDirectory()) {
        List<File> dirResults = listFiles(dirContent.toString());
        resultFiles.addAll(dirResults);
      } else {
        resultFiles.add(dirContent);
      }
    }
    return resultFiles;
  }

  @Override
  public List<String> readLines(File inputFile) throws IllegalArgumentException, IOException {
    List<String> lines = new ArrayList<String>();
    try (BufferedReader reader = new BufferedReader(new FileReader(inputFile))) {
      String line;
      while ((line = reader.readLine()) != null) {
        lines.add(line);
      }
    } catch (IOException e) {
      throw new IllegalArgumentException("input file DNE or is not a regular file");
    }
    return lines;
  }

  @Override
  public boolean containsPattern(String line) {
    Pattern pattern = Pattern.compile(getRegex());
    Matcher matcher = pattern.matcher(line);
    return matcher.find();
  }

  @Override
  public void writeToFile(List<String> lines) throws IOException {
    try (BufferedWriter writer = new BufferedWriter(new FileWriter(getOutFile(), true))) {
      for (String line : lines) {
        writer.write(line);
        writer.newLine();
      }
    } catch (IOException e) {
      throw new IOException("ERROR: Could not write to file.");
    }

  }

}
