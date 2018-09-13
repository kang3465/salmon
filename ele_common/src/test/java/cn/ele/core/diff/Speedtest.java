// Copyright 2010 Google Inc. All Rights Reserved.

/**
 * Diff Speed Test
 *
 * Compile from diff-match-patch/java with:
 * javac -d classes src/name/fraser/neil/plaintext/DiffMatchPatch.java tests/name/fraser/neil/plaintext/Speedtest.java
 * Execute with:
 * java -classpath classes name/fraser/neil/plaintext/Speedtest
 *
 * @author fraser@google.com (Neil Fraser)
 */

package cn.ele.core.diff;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;

public class Speedtest {

  public static void main(String args[]) throws IOException {
      String trace = "";
    String text1 = readFile("D:\\ideaworkspace\\8.6\\element_parent\\ele_common\\src\\test\\java\\cn\\ele\\core\\diff\\Speedtest1.txt");
    String text2 = readFile("D:\\ideaworkspace\\8.6\\element_parent\\ele_common\\src\\test\\java\\cn\\ele\\core\\diff\\Speedtest2.txt");

    DiffMatchPatch dmp = new DiffMatchPatch();
    dmp.Diff_Timeout = 0;

    // Execute one reverse diff as a warmup.
    dmp.diff_main(text2, text1, false);

    long start_time = System.nanoTime();
      LinkedList<DiffMatchPatch.Diff> diffs = dmp.diff_main(text1, text2, false);
      dmp.diff_cleanupSemantic(diffs);
      for (DiffMatchPatch.Diff diff : diffs) {

          if (diff.operation.equals(DiffMatchPatch.Operation.DELETE)){
              trace+="删除:<del style=\"background:#ffe6e6;\">"+diff.text+"</del> <br/>";
          }else if (diff.operation.equals(DiffMatchPatch.Operation.EQUAL)){
              trace+=""+diff.text+"";
          }else if (diff.operation.equals(DiffMatchPatch.Operation.INSERT)){
              trace+="新增:<ins style=\"background:#e6ffe6;\">"+diff.text+"</ins>  <br/>";
          }else{

          }
      }
      System.out.println(trace);
      long end_time = System.nanoTime();
    System.out.printf("Elapsed time: %f\n", ((end_time - start_time) / 1000000000.0));
  }

  private static String readFile(String filename) throws IOException {
    // Read a file from disk and return the text contents.
    StringBuilder sb = new StringBuilder();
    FileReader input = new FileReader(filename);
    BufferedReader bufRead = new BufferedReader(input);
    try {
      String line = bufRead.readLine();
      while (line != null) {
        sb.append(line).append('\n');
        line = bufRead.readLine();
      }
    } finally {
      bufRead.close();
      input.close();
    }
    return sb.toString();
  }
}
