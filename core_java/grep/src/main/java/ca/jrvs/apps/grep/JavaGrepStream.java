package ca.jrvs.apps.grep;

import java.io.*;
import java.util.stream.Stream;

public interface JavaGrepStream {
  /**
   * Top level search workflow
   * @throws IOException
   */
  void process() throws IOException;

  /**
   * Traverse a given directory and return all teh files
   * @param rootDir input directory
   * @return files under the rootDir
   */
  Stream<File> listFiles(String rootDir) throws IOException;

  /**
   * Read a file and return all the lines
   *
   * @param inputFile file to be read
   * @return lines
   * @throws IllegalArgumentException if given inputFile is not a file
   */
  Stream<String> readLines(File inputFile) throws IllegalArgumentException, IOException;

  /**
   * Check if a line contains the regex pattern (passed by user)
   * @param line input string
   * @return true if there is a match
   */
  boolean containsPattern(String line);

  /**
   * Write lines to a file
   * @param lines matched lines
   * @throws IOException if write failed
   */
  void writeToFile(String lines) throws IOException;

  String getRootPath();

  void setRootPath(String rootPath);

  String getRegex();

  void setRegex(String regex);

  String getOutFile();

  void setOutFile(String outFile);

}

