package cn.ele.core.zip;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;
import java.util.zip.*;

public class ZipUtil {
    /**
     * 将zipEntries字符串数组下的文件压缩成文件为zipFileName的zip文件
     * @param zipFileName
     * @param zipEntries
     */
    public static void zip(String zipFileName, String[] zipEntries) {

        try (ZipOutputStream zos = new ZipOutputStream(new BufferedOutputStream(
                new FileOutputStream(zipFileName)))) {

            // Set the compression level to best compression
            zos.setLevel(Deflater.BEST_COMPRESSION);

            for (int i = 0; i < zipEntries.length; i++) {
                File entryFile = new File(zipEntries[i]);
                if (!entryFile.exists()) {
                    System.out.println("The entry file  " + entryFile.getAbsolutePath()
                            + "  does  not  exist");
                    System.out.println("Aborted   processing.");
                    return;
                }
                ZipEntry ze = new ZipEntry(zipEntries[i]);
                zos.putNextEntry(ze);
                addEntryContent(zos, zipEntries[i]);
                zos.closeEntry();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void addEntryContent(ZipOutputStream zos, String entryFileName)
            throws IOException, FileNotFoundException {
        BufferedInputStream bis = new BufferedInputStream(new FileInputStream(
                entryFileName));

        byte[] buffer = new byte[1024];
        int count = -1;
        while ((count = bis.read(buffer)) != -1) {
            zos.write(buffer, 0, count);
        }
        bis.close();
    }

    /**
     * 列出zipFileName zip文件中文件的列表
     * @param zipFileName
     * @return
     * @throws Exception
     */
    public static List<String> listFileName(String zipFileName) throws Exception {
        ZipFile zf = new ZipFile(zipFileName);
        List<String> result = new ArrayList<>();
        Stream<? extends ZipEntry> entryStream = zf.stream();
        entryStream.forEach(entry -> {
            try {
                // Get the input stream for the current zip entry
                InputStream is = zf.getInputStream(entry);
                result.add(entry.getName());
            } catch (IOException e) {
                e.printStackTrace();
            }

        });
        return result;
    }

    /**
     *
     * @param zipFileName
     * @param unzipdir
     */
    public static void unzip(String zipFileName, String unzipdir) {
        try (ZipInputStream zis = new ZipInputStream(new BufferedInputStream(
                new FileInputStream(zipFileName)))) {

            ZipEntry entry = null;
            while ((entry = zis.getNextEntry()) != null) {
                System.out.println(entry.getName());
                // Extract teh entry"s contents
                if (entry.getName().endsWith("/")){
                    continue;
                }
                extractEntryContent(zis, entry, unzipdir);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void extractEntryContent(ZipInputStream zis, ZipEntry entry,
                                           String unzipdir) throws IOException, FileNotFoundException {

        String entryFileName = entry.getName();
        String entryPath = unzipdir + File.separator + entryFileName;

        createFile(entryPath);

        BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(
                entryPath));

        byte[] buffer = new byte[1024];
        int count = -1;
        while ((count = zis.read(buffer)) != -1) {
            bos.write(buffer, 0, count);
        }

        bos.close();
    }

    public static void createFile(String filePath) throws IOException {
        File file = new File(filePath);
        File parent = file.getParentFile();

        if (!parent.exists()) {

            parent.mkdirs();
        }

        file.createNewFile();
    }

    public static void main(String[] args) throws Exception {
//        List<String> strings1 = listFileName("F:\\黑马课程资料\\day10\\day10\\day10.zip");
        List<String> strings = listFileName("day10.zip");

        System.out.println(strings);
        for (String fileName:strings
             ) {
            System.out.println(fileName);
        }
//        unzip("F:\\黑马课程资料\\day10\\day10\\day10.zip","extracted");
//        unzip("ziptest.zip","extracted");
    }
}
