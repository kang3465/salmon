/*
 * 	History				Who				What
 *  2010-08-14			wenyh			修改copyFile,目标与源相同时不执行操作.
 *  2011-09-14          wenyh           修改readFile:处理文本,忽略首行可能出现的BOM头.
 */

/**
 * Created:         2001.10
 * Last Modified:   2006.01.16
 * Description:
 *      class CMyFile ―― 文件操作通用函数的定义和实现
 * Update Log:
 * 		2006.04.13	FuChengrui
 * 		增加了方法extractMainFileName()用以抽取主文件名，
 * 		增加了方法excludeFileExt()用以去除文件名中扩展名部分，
 * 		2006.01.16	FuChengrui
 * 		修改了函数makeDir(String,boolean)的行为定义，如果是因为目标目录已经存在，
 * 		导致的调用创建目录的API失败，则仍然返回<code>true</code>，尽管目录并不是
 * 		该函数创建的。
 */

package cn.ele.core.util.cmy;

import cn.ele.core.Exception.CMyException;
import cn.ele.core.Exception.ExceptionNumber;
import cn.ele.core.Exception.WCMException;
import cn.ele.core.message.I18NMessage;

import java.io.*;
import java.net.URL;
import java.net.URLDecoder;
import java.sql.Timestamp;

/**
 * Title: TRS 内容协作平台（TRS WCM） Description: class CMyFile ―― 文件操作通用函数的定义和实现
 * Copyright: Copyright (c) 2001-2002 TRS信息技术有限公司 Company:
 * TRS信息技术有限公司(www.trs.com.cn)
 * 
 * @author TRS信息技术有限公司
 * @version 1.0
 */
public class CMyFile {

	// caohui@2004-07-19 定义日志输出对象
	private static org.apache.log4j.Logger m_oLogger = org.apache.log4j.Logger
			.getLogger(CMyFile.class);

	/**
	 * 为屏蔽存储的差异，而提供中间层接口，真正的实现可能是本地存储、smb协议的远程存储或oss存储
	 */
	/*****************************************************************************************/
	/**
	 * 
	 */

	/*****************************************************************************************/

	/**
	 * 构造函数
	 */
	public CMyFile() {

	}

	// ==================================================================
	// 文件名称分解的几个工具函数

	/**
	 * 检查指定文件是否存在
	 * 
	 * @param pathfilename
	 *            文件名称(含路径）
	 * @return 若存在，则返回true；否则，返回false
	 */
	public static boolean fileExists(String pathfilename) {
		File file = new File(pathfilename);
		return file.exists();
	}

	/**
	 * 检查指定文件的路径是否存在
	 * 
	 * @param pathfilename
	 *            文件名称(含路径）
	 * @return 若存在，则返回true；否则，返回false
	 */
	public static boolean pathExists(String pathfilename) {
		String sPath = extractFilePath(pathfilename);
		return fileExists(sPath);
	}

	/**
	 * 从文件的完整路径名（路径+文件名）中提取文件名(包含扩展名) <br>
	 * 如：d:\path\file.ext --> file.ext
	 * 
	 * @param filePathName
	 * @return
	 */
	public static String extractFileName(String filePathName) {
		return extractFileName(filePathName, File.separator);
	}

	/**
	 * 从文件的完整路径名（路径+文件名）中提取文件名(包含扩展名) <br>
	 * 如：d:\path\file.ext --> file.ext
	 * 
	 * @param filePathName
	 *            全文件路径名
	 * @param fileSeparator
	 *            文件分隔符
	 * @return
	 */
	public static String extractFileName(String filePathName,
                                         String fileSeparator) {
		int nPos = -1;
		if (fileSeparator == null) {
			nPos = filePathName.lastIndexOf(File.separatorChar);
			if (nPos < 0) {
				nPos = filePathName
						.lastIndexOf(File.separatorChar == '/' ? '\\' : '/');
			}
		} else {
			nPos = filePathName.lastIndexOf(fileSeparator);
		}

		if (nPos < 0) {
			return filePathName;
		}

		return filePathName.substring(nPos + 1);
	}

	// caohui@020513
	/**
	 * 从EB路径地址中提取: 文件名
	 * 
	 * @param filePathName
	 * @return
	 */
	public static String extractHttpFileName(String filePathName) {
		int nPos = filePathName.lastIndexOf("/");
		return filePathName.substring(nPos + 1);
	}

	/**
	 * 从文件的完整路径名（路径+文件名）中提取:主文件名（不包括路径和扩展名）
	 * 
	 * @param filePathName
	 * @return
	 */
	public static String extractMainFileName(String filePathName) {
		String sFileMame = extractFileName(filePathName);
		int nPos = sFileMame.lastIndexOf('.');
		if (nPos > 0) {
			return sFileMame.substring(0, nPos);
		}
		return sFileMame;
	}

	/**
	 * 排除文件的扩展名,只保留路径(如果存在)和主文件名
	 * 
	 * @param sFileMame
	 * @return
	 */
	public static String excludeFileExt(String sFileMame) {
		int nPos = sFileMame.lastIndexOf('.');
		if (nPos > 0) {
			return sFileMame.substring(0, nPos);
		}
		return sFileMame;
	}

	/**
	 * 从文件的完整路径名（路径+文件名）中提取: 文件扩展名
	 * 
	 * @param filePathName
	 * @return
	 */
	public static String extractFileExt(String filePathName) {
		// 适应新锐乱码文件的逻辑
		if (filePathName.endsWith("7png")) {
			// System.err.println(filePathName + " is worse file name!!!!!");
			return "png";
		}
		if (filePathName.endsWith("7jpg")) {
			return "jpg";
		}
		// 过滤掉 ? 后面的参数
		// 过滤掉 ? 后面的参数
		int nParamPos = filePathName.lastIndexOf('?');
		if (nParamPos >= 0) {
			filePathName = filePathName.substring(0, nParamPos);
		}

		int nPos = filePathName.lastIndexOf('.');
		return (nPos >= 0 ? filePathName.substring(nPos + 1) : "");
	}

	/**
	 * 从文件的完整路径名（路径+文件名）中提取 路径（包括：Drive+Directroy )
	 * 
	 * @param filePathName
	 * @return
	 */
	public static String extractFilePath(String filePathName) {
		int nPos = filePathName.lastIndexOf('/');
		if (nPos < 0) {
			nPos = filePathName.lastIndexOf('\\');
		}

		// 过滤掉 ? 后面的参数
		int nParamPos = filePathName.lastIndexOf('?');
		if (nParamPos >= 0) {
			filePathName = filePathName.substring(0, nParamPos);
		}

		return (nPos >= 0 ? filePathName.substring(0, nPos + 1) : "");
	}

	/**
	 * 将文件/路径名称转化为绝对路径名
	 * 
	 * @param filePathName
	 *            文件名或路径名
	 * @return
	 */
	public static String toAbsolutePathName(String filePathName) {
		File file = new File(filePathName);
		return file.getAbsolutePath();
	}

	/**
	 * 从文件的完整路径名（路径+文件名）中提取文件所在驱动器 <br>
	 * 注意：区分两种类型的文件名表示 <br>
	 * [1] d:\path\filename.ext --> return "d:" <br>
	 * [2] \\host\shareDrive\shareDir\filename.ext --> return
	 * "\\host\shareDrive"
	 * 
	 * @param filePathName
	 * @return
	 */
	public static String extractFileDrive(String filePathName) {
		int nPos;
		int nLen = filePathName.length();

		// 检查是否为 "d:\path\filename.ext" 形式
		if ((nLen > 2) && (filePathName.charAt(1) == ':')) {
			return filePathName.substring(0, 2);
		}

		// 检查是否为 "\\host\shareDrive\shareDir\filename.ext" 形式
		if ((nLen <= 2) || (filePathName.charAt(0) != File.separatorChar)
				|| (filePathName.charAt(1) != File.separatorChar)) {
			return "";
		}
		nPos = filePathName.indexOf(File.separatorChar, 2);
		if (nPos >= 0)
			nPos = filePathName.indexOf(File.separatorChar, nPos + 1);
		return (nPos >= 0 ? filePathName.substring(0, nPos)
				: filePathName);

	}// END:extractFileDrive

	/**
	 * 删除指定的文件
	 * 
	 * @param filePathName
	 *            指定的文件名
	 * @return
	 */
    public static boolean deleteFile(String filePathName) {
        if (m_oLogger.isDebugEnabled()) {
            m_oLogger.debug(
                    "who call me? ： deleteFile ： "
                            + CMyString.showEmpty(filePathName),
                    new Throwable());
        }
        File file = new File(filePathName);
        return file.exists() ? file.delete() : false;
    }

	// =======================================================================
	// 目录操作函数

	/**
	 * 创建目录
	 * 
	 * @param _sDir
	 *            目录名称
	 * @param _bCreateParentDir
	 *            如果父目录不存在，是否创建父目录
	 * @return
	 */
	public static boolean makeDir(String _sDir, boolean _bCreateParentDir) {
		boolean zResult = false;
		File file = new File(_sDir);
		if (_bCreateParentDir) {
			zResult = file.mkdirs(); // 如果父目录不存在，则创建所有必需的父目录
		} else {
			zResult = file.mkdir(); // 如果父目录不存在，不做处理
		}
		if (!zResult) {
			zResult = file.exists();
		}
		return zResult;
	}

	/**
	 * 删除指定的目录下所有的文件 注意：若文件或目录正在使用，删除操作将失败。
	 * 
	 * @param _sDir
	 *            目录名
	 * @param _bDeleteChildren
	 *            是否删除其子目录或子文件（可省略，默认不删除）
	 * @return <code>true</code> if the directory exists and has been deleted
	 *         successfully.
	 * @deprecated to use deleteDir(String _sPath) or deleteDir(File _path)
	 *             instead.
	 */
	public static boolean deleteDir(String _sDir, boolean _bDeleteChildren) {
		File file = new File(_sDir);
		if (!file.exists()) {
			return false;
		}

		if (_bDeleteChildren) { // 删除子目录及其中文件
			File[] files = file.listFiles(); // 取目录中文件和子目录列表
			File aFile;
			for (int i = 0; i < files.length; i++) {
				aFile = files[i];
				if (aFile.isDirectory()) {
					deleteDir(aFile);
				} else {
					aFile.delete();
				}
			}// end for
		}// end if
		return file.delete(); // 删除该目录
	}// END:deleteDir

	/**
	 * Deletes a file path, and all the files and subdirectories in this path
	 * will also be deleted.
	 * 
	 * @param _sPath
	 *            the specified path.
	 * @return <code>true</code> if the path exists and has been deleted
	 *         successfully; <code>false</code> othewise.
	 */
	public static boolean deleteDir(String _sPath) {
		File path = new File(_sPath);
		return deleteDir(path);
	}

	/**
	 * Deletes a file path, and all the files and subdirectories in this path
	 * will also be deleted.
	 * 
	 * @param _path
	 *            the specified path.
	 * @return <code>true</code> if the path exists and has been deleted
	 *         successfully; <code>false</code> othewise.
	 */
	public static boolean deleteDir(File _path) {
		// 1. to check whether the path exists
		if (!_path.exists()) {
			return false;
		}

		// 2. to delete the files in the path
		if (_path.isDirectory()) {
			// if _path is not a dir,files=null
			File[] files = _path.listFiles();
			File aFile;
			for (int i = 0; i < files.length; i++) {
				aFile = files[i];
				if (aFile.isDirectory()) {
					deleteDir(aFile);
				} else {
					aFile.delete();
				}
			}// endfor
		}

		// 3. to delete the path self
		return _path.delete();
	}

	/**
	 * 获取某一路径下的特殊文件
	 * 
	 * @param dir
	 *            路径名称
	 * @param extendName
	 *            文件扩展名，"."可以包含也可以不包含
	 * @return
	 */
	public static File[] listFiles(String dir, String extendName) {
		File fDir = new File(dir);
		if (extendName.charAt(0) != '.') {
			extendName = "." + extendName;
		}
		File[] Files = fDir.listFiles(new CMyFilenameFilter(extendName));
		return Files;
	}

	/**
	 * 获取某一路径下的子文件夹
	 * 
	 * @param _dir
	 *            路径名称
	 * @return 子文件夹对象数组
	 */
	public static File[] listSubDirectories(String _dir) {
		File fDir = new File(_dir);
		FileFilter fileFilter = new FileFilter() {
			public boolean accept(File file) {
				return file.isDirectory();
			}
		};

		File[] files = fDir.listFiles(fileFilter);
		return files;
	}

	// =======================================================================
	// 文件读写操作函数

	// 读取文件的内容，返回字符串类型的文件内容
	/**
	 * 读取文件的内容，返回字符串类型的文件内容
	 * 
	 * @param _sFileName
	 *            文件名
	 * @return
	 * @throws CMyException
	 */
	public static String readFile(String _sFileName) throws CMyException {
		// FileReader fileReader = null;
		//
		// StringBuffer buffContent = null;
		// String sLine;
		//
		// //caohui@2004-07-19 增加异常是对于资源的释放
		// FileInputStream fis = null;
		// BufferedReader buffReader = null;
		//
		// try {
		// //[frankwater|2002.10.23]增加读取文件的字符编码CMyString.FILE_WRITING_ENCODING
		// fis = new FileInputStream(_sFileName);
		// buffReader = new BufferedReader(new InputStreamReader(fis,
		// CMyString.FILE_WRITING_ENCODING));
		// //[frankwater|2002.10.23]依次读取文件中的内容
		// while ((sLine = buffReader.readLine()) != null) {
		// if (buffContent == null) {
		// buffContent = new StringBuffer();
		// } else {
		// buffContent.append("\n");
		// }
		// buffContent.append(sLine);
		// }//end while
		// //[frankwater|2002.10.23]关闭打开的字符流和文件流
		//
		// //[frankwater|2002.10.23]返回文件的内容
		// return (buffContent == null ? "" : buffContent.toString());
		// } catch (FileNotFoundException ex) {
		// throw new CMyException(ExceptionNumber.ERR_FILE_NOTFOUND,
		// "要读取得文件没有找到(CMyFile.readFile)", ex);
		// } catch (IOException ex) {
		// throw new CMyException(ExceptionNumber.ERR_FILEOP_READ,
		// "读文件时错误(CMyFile.readFile)", ex);
		// } finally {
		// //增加异常时资源的释放
		// try {
		// if (fileReader != null)
		// fileReader.close();
		// if (buffReader != null)
		// buffReader.close();
		// if (fis != null)
		// fis.close();
		// } catch (Exception ex) {
		// }
		//
		// }//end try

		// wenyh@2005-5-10 15:41:27 add comment:修改
		return readFile(_sFileName, CMyString.FILE_WRITING_ENCODING);
	}// END: readFile()

	// wenyh@2005-5-10 15:32:45 add comment:添加接口,指定字符编码读文件

	/**
	 * 读取文件的内容，返回字符串类型的文件内容
	 * 
	 * @param _sFileName
	 *            文件名
	 * @param _sEncoding
	 *            以指定的字符编码读取文件内容,默认为"UTF-8"
	 * @return
	 * @throws CMyException
	 */
	public static String readFile(String _sFileName, String _sEncoding)
			throws CMyException {
		FileReader fileReader = null;

		StringBuffer buffContent = null;
		String sLine;

		// caohui@2004-07-19 增加异常是对于资源的释放
		FileInputStream fis = null;
		BufferedReader buffReader = null;
		if (_sEncoding == null) {
			_sEncoding = "UTF-8";
		}

		try {
			// [frankwater|2002.10.23]增加读取文件的字符编码CMyString.FILE_WRITING_ENCODING
			fis = new FileInputStream(_sFileName);
			buffReader = new BufferedReader(new InputStreamReader(fis,
					_sEncoding));
			// [frankwater|2002.10.23]依次读取文件中的内容
			boolean zFirstLine = "UTF-8".equalsIgnoreCase(_sEncoding);
			while ((sLine = buffReader.readLine()) != null) {
				if (buffContent == null) {
					buffContent = new StringBuffer();
				} else {
					buffContent.append("\n");
				}
				if (zFirstLine) {
					sLine = removeBomHeaderIfExists(sLine);
					zFirstLine = false;
				}
				buffContent.append(sLine);
			}// end while
				// [frankwater|2002.10.23]关闭打开的字符流和文件流

			// [frankwater|2002.10.23]返回文件的内容
			return (buffContent == null ? "" : buffContent.toString());
		} catch (FileNotFoundException ex) {
			throw new CMyException(ExceptionNumber.ERR_FILE_NOTFOUND,
					I18NMessage.get(CMyFile.class, "CMyFile.label1",
							"要读取得文件没有找到(CMyFile.readFile)"), ex);
		} catch (IOException ex) {
			throw new CMyException(ExceptionNumber.ERR_FILEOP_READ,
					I18NMessage.get(CMyFile.class, "CMyFile.label2",
							"读文件时错误(CMyFile.readFile)"), ex);
		} finally {
			// 增加异常时资源的释放
			try {
				if (fileReader != null) {
					fileReader.close();
				}
				if (buffReader != null) {
					buffReader.close();
				}
				if (fis != null) {
					fis.close();
				}
			} catch (Exception ex) {
			}

		}// end try
	}

	/**
	 * 移除字符串中的BOM前缀
	 * 
	 * @param _sLine
	 *            需要处理的字符串
	 * @return 移除BOM后的字符串.
	 */
	private static String removeBomHeaderIfExists(String _sLine) {
		if (_sLine == null) {
			return null;
		}
		String line = _sLine;
		if (line.length() > 0) {
			char ch = line.charAt(0);
			// 使用while是因为用一些工具看到过某些文件前几个字节都是0xfffe.
			// 0xfeff,0xfffe是字节序的不同处理.JVM中,一般是0xfeff
			while ((ch == 0xfeff || ch == 0xfffe)) {
				line = line.substring(1);
				if (line.length() == 0) {
					break;
				}
				ch = line.charAt(0);
			}
		}
		return line;
	}

	public static byte[] readBytesFromFile(String _sFileName)
			throws CMyException {
		FileInputStream fis = null;
		ByteArrayOutputStream bos = null;
		try {
			fis = new FileInputStream(_sFileName);
			byte[] buffer = new byte[1024];
			bos = new ByteArrayOutputStream(2048);
			int nLen = 0;
			while ((nLen = fis.read(buffer)) > 0) {
				bos.write(buffer, 0, nLen);
			}
			return bos.toByteArray();
		} catch (Exception e) {
			throw new CMyException("读取文件[" + _sFileName + "]失败！");
		} finally {
			if (bos != null) {
				try {
					bos.close();
				} catch (Exception e) {
				}
			}
			if (fis != null) {
				try {
					fis.close();
				} catch (Exception e) {
				}
			}
		}
	}

	// 以指定内容_sFileContent生成新的文件_sFileName
	/**
	 * 以指定内容_sFileContent生成新的文件_sFileName
	 * 
	 * @param _sFileName
	 *            文件名
	 * @param _sFileContent
	 *            指定的内容
	 * @return
	 * @throws CMyException
	 */
	public static boolean writeFile(String _sFileName, String _sFileContent)
			throws CMyException {
		return writeFile(_sFileName, _sFileContent,
				CMyString.FILE_WRITING_ENCODING);
	}// END: writeFile()

	/**
	 * 以指定内容_sFileContent生成新的文件_sFileName
	 * 
	 * @param _sFileName
	 *            文件名
	 * @param _sFileContent
	 *            指定的内容
	 * @return
	 * @throws CMyException
	 */
	public static boolean writeFile(String _sFileName, String _sFileContent,
                                    String _encoding) throws CMyException {
		return writeFile(_sFileName, _sFileContent, _encoding, false);
	}// END: writeFile()

	public static boolean writeFile(String _sFileName, String _sFileContent,
                                    String _sFileEncoding, boolean _bWriteUnicodeFlag)
			throws CMyException {
		/*
		 * 只在写出的文件大小比原文件小的时候才认为是错误,需要重新分发.如果出现异常,则还是直接抛出异常
		 */
		boolean zOk = writeFile0(_sFileName, _sFileContent, _sFileEncoding,
				_bWriteUnicodeFlag);
		int nMaxCount = 30;
		int nCount = 1;
		while (!zOk) {
			try {
				Thread.sleep(100);
			} catch (Exception ex) {
			}

			File file = new File(_sFileName);
			m_oLogger.warn("Try again write [" + _sFileContent.length()
					+ "] to [" + _sFileName + "][" + file.length()
					+ "], already try [" + nCount + "] times!");
			zOk = writeFile0(_sFileName, _sFileContent, _sFileEncoding,
					_bWriteUnicodeFlag);
			nCount++;
			if (nCount > nMaxCount) {
				break;
			}
		}

		return zOk;
	}

	private static boolean writeFile0(String _sFileName, String _sFileContent,
                                      String _sFileEncoding, boolean _bWriteUnicodeFlag)
			throws CMyException {
		int nSrcLen = 0;// _sFileContent.length();

		// 1.创建目录
		String sPath = extractFilePath(_sFileName);
		if (m_oLogger.isDebugEnabled()) {
			m_oLogger.debug(sPath);
		}
		if (!CMyFile.pathExists(sPath)) {
			boolean bCreatPath = CMyFile.makeDir(sPath, true);
			if (!bCreatPath) {
				throw new CMyException("创建目录[" + sPath
						+ "]失败！有可能是当前用户没有权限或者目录被锁定，请联系系统管理员排查!");
			}
		}
		String sFileEncoding = CMyString.showNull(_sFileEncoding,
				CMyString.FILE_WRITING_ENCODING);

		boolean bRet = false;
		// caohui@加入异常的处理
		FileOutputStream fos = null;
		Writer outWriter = null;
		try {
			fos = new FileOutputStream(_sFileName);

			if (_bWriteUnicodeFlag) {
				fos.write(0xFEFF);
			}
			byte[] pContent = _sFileContent.getBytes(_sFileEncoding);// new
																		// String(_sFileContent.getBytes(),
																		// sFileEncoding).getBytes();
			nSrcLen = pContent.length;
			fos.write(pContent);

			// outWriter = new OutputStreamWriter(fos, sFileEncoding); // 指定编码方式
			// if (_bWriteUnicodeFlag)
			// outWriter.write(0xFEFF);
			// outWriter.write(_sFileContent); // 写操作

			bRet = true;
		} catch (Exception ex) {
			m_oLogger.error(
					I18NMessage.get(CMyFile.class, "CMyFile.label5", "写文件[")
							+ _sFileName
							+ I18NMessage.get(CMyFile.class, "CMyFile.label6",
									"]发生异常"), ex);
			throw new CMyException(ExceptionNumber.ERR_FILEOP_WRITE,
					I18NMessage.get(CMyFile.class, "CMyFile.label7",
							"写文件错误(CMyFile.writeFile)"), ex);
		} finally {
			if (outWriter != null) {
				try {
					outWriter.flush();
				} catch (Exception ex) {
				}

				try {
					outWriter.close();
				} catch (Exception ex) {
				}
			}

			if (fos != null) {
				try {
					fos.flush();
				} catch (Exception ex) {
				}

				try {
					fos.close();
				} catch (Exception ex) {
				}
			}
		}

		File newFile = new File(_sFileName);
		if (bRet && newFile.exists()) {
			return newFile.length() >= nSrcLen;
		} else {
			return false;
		}
	}// END: writeFile()

	// 把指定的内容_sAddContent追加到文件_sFileName中
	/**
	 * 把指定的内容_sAddContent追加到文件_sFileName中
	 * 
	 * @param _sFileName
	 *            文件名
	 * @param _sAddContent
	 *            追加的内容
	 * @return
	 * @throws CMyException
	 */
	public static boolean appendFile(String _sFileName, String _sAddContent)
			throws CMyException {
		boolean bResult = false;
		// caohui@2004-07-19 释放资源
		RandomAccessFile raf = null;
		try {
			raf = new RandomAccessFile(_sFileName, "rw");
			raf.seek(raf.length());
			raf.writeBytes(_sAddContent);
			bResult = true;
		} catch (Exception ex) {
			throw new CMyException(ExceptionNumber.ERR_FILEOP_FAIL,
					I18NMessage.get(CMyFile.class, "CMyFile.label8",
							"向文件追加内容时发生异常(CMyFile.appendFile)"), ex);
		} finally {
			// caohui@2004-07-19 释放资源
			try {
				if (raf != null) {
					raf.close();
				}
			} catch (Exception ex) {
			}
		}
		return bResult;
	}// END: appendFile()

	/**
	 * 移动文件
	 * 
	 * @param _sSrcFile
	 *            待移动的文件
	 * @param _sDstFile
	 *            目标文件
	 * @return
	 * @throws CMyException
	 */
	public static boolean moveFile(String _sSrcFile, String _sDstFile)
			throws CMyException {
		return moveFile(_sSrcFile, _sDstFile, true);
	}

	/**
	 * 移动文件
	 * 
	 * @param _sSrcFile
	 *            待移动的文件
	 * @param _sDstFile
	 *            目标文件
	 * @param _bMakeDirIfNotExists
	 *            若目标路径不存在，是否创建;可缺省,默认值为true.
	 * @return
	 * @throws CMyException
	 */
	public static boolean moveFile(String _sSrcFile, String _sDstFile,
                                   boolean _bMakeDirIfNotExists) throws CMyException {
		// 1.拷贝
		copyFile(_sSrcFile, _sDstFile, _bMakeDirIfNotExists);
		// 2.删除
		deleteFile(_sSrcFile);
		return false;
	}

	/**
	 * 复制文件
	 * 
	 * @param _sSrcFile
	 *            源文件（必须包含路径）
	 * @param _sDstFile
	 *            目标文件（必须包含路径）
//	 * @param _bMakeDirIfNotExists
	 *            若目标路径不存在，是否创建;可缺省,默认值为true.
	 * @return 若文件复制成功，则返回true；否则，返回false.
	 * @throws CMyException
	 *             源文件不存在,或目标文件所在目录不存在,或文件复制失败,会抛出异常.
	 */
	public static boolean copyFile(String _sSrcFile, String _sDstFile)
			throws CMyException {
		return copyFile(_sSrcFile, _sDstFile, true);
	}

	public static boolean copyFile(String _sSrcFile, String _sDstFile,
                                   boolean _bMakeDirIfNotExists) throws CMyException {
		return copyFile(_sSrcFile, _sDstFile, _bMakeDirIfNotExists, false);
	}// END: copyFile()

	public static boolean copyFile(String _sSrcFile, String _sDstFile,
                                   boolean _bMakeDirIfNotExists, boolean preserveFileDate)
			throws CMyException {
		/*
		 * 只在写出的文件大小比原文件小的时候才认为是错误,需要重新分发.如果出现异常,则还是直接抛出异常
		 */
		boolean zOk = copyFile0(_sSrcFile, _sDstFile, _bMakeDirIfNotExists,
				preserveFileDate);
		int nMaxCount = 30;
		int nCount = 1;
		while (!zOk) {
			try {
				Thread.sleep(100);
			} catch (Exception ex) {
			}

			m_oLogger.warn("Try again copy [" + _sSrcFile + "] to ["
					+ _sDstFile + "], already try [" + nCount + "] times!");
			zOk = copyFile0(_sSrcFile, _sDstFile, _bMakeDirIfNotExists,
					preserveFileDate);
			nCount++;
			if (nCount > nMaxCount) {
				break;
			}
		}

		return zOk;
	}

	private static boolean copyFile0(String _sSrcFile, String _sDstFile,
                                     boolean _bMakeDirIfNotExists, boolean preserveFileDate)
			throws CMyException {
		File srcFile = new File(_sSrcFile);
		if (srcFile.isDirectory() || !srcFile.exists()) {
			throw new CMyException("无效的文件[" + _sSrcFile + "]！");
		}
		long lSrcStart = srcFile.length();

		// 需要兼容图片在转码时出现0KB的情况
		int nPos = _sSrcFile.lastIndexOf('.');
		String sFileExt = null;
		if (nPos > 0 && nPos < (_sSrcFile.length() - 1)) {
			sFileExt = _sSrcFile.substring(nPos + 1);
		}
		if (lSrcStart == 0 && sFileExt != null
				&& ("jpg".equalsIgnoreCase(sFileExt))) {
			m_oLogger.warn("Copy 0KB File [" + _sSrcFile + "] to [" + _sDstFile
					+ "]........");
			return false;
		}

		FileInputStream fis = null;
		FileOutputStream fos = null;
		try {
			// wenyh@2010-8-14 : 目标与源是同一个文件的话,不再执行拷贝.否则可能会将文件破坏.
			File dstFile = new File(_sDstFile);
			if (srcFile.equals(dstFile)) {
				return true;// 目标与源是同一个文件,直接返回.
			}
			fis = new FileInputStream(srcFile); // 若文件不存在,会抛出异常

			// why@2003-09-27 如果目录不存在，则创建目录
			try {
				fos = new FileOutputStream(dstFile);
			} catch (FileNotFoundException ex) {
				if (_bMakeDirIfNotExists) { // 自动创建目录
					if (!CMyFile.makeDir(CMyFile.extractFilePath(_sDstFile),
							true)) {
						throw new CMyException(ExceptionNumber.ERR_FILEOP_FAIL,
								I18NMessage.get(CMyFile.class,
										"CMyFile.label9", "为目标文件[")
										+ _sDstFile
										+ I18NMessage.get(CMyFile.class,
												"CMyFile.label10", "]创建目录失败！"));
					}
					fos = new FileOutputStream(_sDstFile);
				} else {
					throw new CMyException(ExceptionNumber.ERR_FILEOP_FAIL,
							I18NMessage.get(CMyFile.class, "CMyFile.label11",
									"指定目标文件[")
									+ _sDstFile
									+ I18NMessage.get(CMyFile.class,
											"CMyFile.label12", "]所在目录不存在！"), ex);
				}
			}// end try

			byte buffer[] = new byte[4096];
			int nLen;
			// while ((bytes = fis.read(buffer, 0, 4096)) > 0) {
			while ((nLen = fis.read(buffer)) > 0) {
				fos.write(buffer, 0, nLen);
			}

		} catch (FileNotFoundException ex) {
			throw new CMyException(ExceptionNumber.ERR_FILE_NOTFOUND,
					I18NMessage.get(CMyFile.class, "CMyFile.label13",
							"要复制的原文件没有发现(CMyFile.copyFile)"), ex);
		} catch (IOException ex) {
			throw new CMyException(ExceptionNumber.ERR_FILEOP_FAIL,
					I18NMessage.get(CMyFile.class, "CMyFile.label14",
							"复制文件时发生异常(CMyFile.copyFile)"), ex);
		} finally {
			if (fos != null) {
				try {
					fos.flush();
				} catch (Exception ex) {
				}

				try {
					fos.close();
				} catch (Exception ex) {
				}
			}
			if (fis != null) {
				try {
					fis.close();
				} catch (Exception ex) {
				}
			}
		}// end try
		if (preserveFileDate) {
			new File(_sDstFile).setLastModified(new File(_sSrcFile)
					.lastModified());
		}

		File newFile = new File(_sDstFile);
		if (newFile.exists()) {
			srcFile = new File(_sSrcFile);
			long lSrc = srcFile.length(), lDst = newFile.length();

			if (lDst == 0 && sFileExt != null
					&& ("jpg".equalsIgnoreCase(sFileExt))) {
				m_oLogger.warn("Copy 0KB File[" + _sSrcFile + "][" + lSrc
						+ "][" + lSrcStart + "] to [" + _sDstFile + "][" + lDst
						+ "]!");
				return false;
			}

			boolean bResult = (lDst >= lSrc);
			if (!bResult) {
				m_oLogger.warn("Copy File[" + _sSrcFile + "][" + lSrc + "]["
						+ lSrcStart + "] to [" + _sDstFile + "][" + lDst + "]");
			}

			return bResult;
		} else {
			return false;
		}
	}// END: copyFile()

	/**
	 * 拷贝文件夹到指定的文件夹
	 * 
	 * @param _fromFileDir
	 *            源目录
	 * @param _toFileDir
	 *            目标目录
	 * @param _bIncludeCurrDir
	 *            是否包含源目录的自身目录
	 * @throws CMyException
	 */
	public static void copyFileDir(String _fromFileDir, String _toFileDir,
                                   boolean _bIncludeCurrDir) throws CMyException {

		// 1.参数的校验
		if (CMyString.isEmpty(_fromFileDir) || CMyString.isEmpty(_toFileDir)) {
			return;
		}
		File fFromFile = new File(_fromFileDir);
		File fToFile = new File(_toFileDir);

		if (fFromFile.isFile() || fToFile.isFile()) {
			throw new CMyException("源目录和目标目录都不能是文件！");
		}

		File[] files = fFromFile.listFiles();
		if (files == null || files.length == 0) {
			return;
		}

		// 2.路径的提前处理
		_fromFileDir = CMyString
				.setStrEndWith(_fromFileDir, File.separatorChar);
		_toFileDir = CMyString.setStrEndWith(_toFileDir, File.separatorChar);

		// 3.判断目标文件目录是否存在
		if (_bIncludeCurrDir) {
			File parentFile = new File(_toFileDir + fFromFile.getName());
			if (!parentFile.exists()) {
				parentFile.mkdir();
			}
			_toFileDir = _toFileDir + fFromFile.getName();
			_toFileDir = _toFileDir + File.separatorChar;
		}

		// 4.拷贝文件
		for (int i = 0; i < files.length; i++) {
			File aFile = files[i];
			if (aFile == null) {
				continue;
			}
			if ("Thumbs.db".equals(aFile.getName())) {
				continue;
			}

			if (!aFile.isFile()) {
				String sToFileDirPath = _toFileDir + aFile.getName();
				if (!CMyFile.fileExists(sToFileDirPath)) {
					makeDir(sToFileDirPath, true);
				}
				copyFileDir(_fromFileDir + aFile.getName(), sToFileDirPath,
						false);
			} else {
				try {
					copyFile(_fromFileDir + aFile.getName(),
							_toFileDir + aFile.getName(), true);
				} catch (Exception e) {
					// TODO 某个文件没复制成功，是否抛出异常需要进一步考虑,在此先将信息输出到后台
					System.out.println(e.getStackTrace());
				}
			}
		}
	}

	/**
	 * map the resource related path to full real path
	 * 
	 * @param _resource
	 *            related path of resource
	 * @return the full real path
	 * @throws Exception
	 *             if encounter errors
	 */
	public static String mapResouceFullPath(String _resource)
			throws WCMException {
		// URL url = CMyFile.class.getResource(_resource);
		URL url = Thread.currentThread().getContextClassLoader()
				.getResource(_resource);
		if (url == null) {
			throw new WCMException(ExceptionNumber.ERR_FILE_NOTFOUND,
					I18NMessage.get(CMyFile.class, "CMyFile.label15", "文件[")
							+ _resource
							+ I18NMessage.get(CMyFile.class, "CMyFile.label16",
									"]没有找到！"));
		}

		// else
		String sPath = null;
		try {
			sPath = url.getFile();
			if (sPath.indexOf('%') >= 0) {
				// ge modify by gfc @2007-8-23 13:19:30 加上enc参数，以免调用时抛空指针异常
				// sPath = URLDecoder.decode(url.getFile(), null);
				String enc = System.getProperty("file.encoding", "GBK");
				sPath = URLDecoder.decode(url.getFile(), enc);

			}
		} catch (Exception ex) {
			throw new WCMException(ExceptionNumber.ERR_FILE_NOTFOUND,
					I18NMessage.get(CMyFile.class, "CMyFile.label15", "文件[")
							+ url.getFile()
							+ I18NMessage.get(CMyFile.class, "CMyFile.label17",
									"]转换失败！"), ex);
		}
		return sPath;
	}

	public static String mapResouceFullPath(String _resource, Class _currClass)
			throws WCMException {
		URL url = _currClass.getResource(_resource);
		if (url == null) {
			throw new WCMException(ExceptionNumber.ERR_FILE_NOTFOUND,
					I18NMessage.get(CMyFile.class, "CMyFile.label15", "文件[")
							+ _resource
							+ I18NMessage.get(CMyFile.class, "CMyFile.label16",
									"]没有找到！"));
		}

		String sPath = null;
		try {
			sPath = url.getFile();
			if (sPath.indexOf('%') >= 0) {
				// ge modify by gfc @2007-8-23 13:19:30 加上enc参数，以免调用时抛空指针异常
				// sPath = URLDecoder.decode(url.getFile(), null);
				String enc = System.getProperty("file.encoding", "GBK");
				sPath = URLDecoder.decode(url.getFile(), enc);

			}
		} catch (Exception ex) {
			throw new WCMException(ExceptionNumber.ERR_FILE_NOTFOUND,
					I18NMessage.get(CMyFile.class, "CMyFile.label15", "文件[")
							+ url.getFile()
							+ I18NMessage.get(CMyFile.class, "CMyFile.label17",
									"]转换失败！"), ex);
		}
		return sPath;
	}

	/**
	 * 判断图片大小的有效性
	 * 
	 * @param sFileAbsPath
	 *            图片绝对路径
	 * @param _minWidth
	 *            图片的最小宽度
	 * @param _minHeight
	 *            图片的最小高度
	 * @return
	 */
	public static boolean validImageFile(String sFileAbsPath, int _minWidth,
                                         int _minHeight) {
		if (!fileExists(sFileAbsPath)) {
			return false;
		}

		// TODO 文件大小尺寸的限制
		/*ImageObj originalImage = new ImageObj();

		try {
			originalImage.setFilename(sFileAbsPath);
		} catch (Exception e) {
			m_oLogger.error("读取文件[" + sFileAbsPath + "]出现异常！", e);
			return false;
		}
		if (originalImage.width < _minWidth) {
			return false;
		}
		if (originalImage.height < _minHeight) {
			return false;
		}*/
		return true;

		// 文件大小尺寸的限制
		// File imageFile = new File(sFileAbsPath);
		// BufferedImage srcFile;
		// try {
		// srcFile = ImageIO.read(imageFile);
		// } catch (IOException e) {
		// if (m_oLogger.isDebugEnabled()) {
		// m_oLogger.error("读取文件[" + sFileAbsPath + "]出现异常！", e);
		// }
		// return false;
		// }
		//
		// if (srcFile == null) {
		// return false;
		// }
		//
		// int nSrcWidth = srcFile.getWidth();
		// if (nSrcWidth < _minWidth) {
		// return false;
		// }
		// int nSrcHeight = srcFile.getHeight();
		// if (nSrcHeight < _minHeight) {
		// return false;
		// }
		// return true;
	}

	// ==============================================================
	// 测试

	// public static void main(String args[]) {
	// try {
	// CMyFile.writeFile("c:\\test.txt", I18NMessage.get(CMyFile.class,
	// "CMyFile.label18", "中国人test"), "UTF-16LE", true);
	//
	// String sSrcFile = "";
	// // String sDstFile = "";
	// long lStartTime, lEndTime;
	// // 测试文件的复制：
	// sSrcFile = "d:\\temp\\InfoRadar.pdf";
	// // sDstFile = "d:\\temp\\sub\\InfoRadar_old.pdf";
	// lStartTime = System.currentTimeMillis();
	// // CMyFile.copyFile(sSrcFile, sDstFile);
	// lEndTime = System.currentTimeMillis();
	// System.out.println(I18NMessage.get(CMyFile.class,
	// "CMyFile.label19", "==============所用时间：")
	// + (lEndTime - lStartTime) + "ms ==============");
	//
	// sSrcFile = "d:\\write_test.html";
	// String sContent = CMyFile.readFile(sSrcFile);
	// lStartTime = System.currentTimeMillis();
	// CMyFile.writeFile(sSrcFile + ".new", sContent);
	// lEndTime = System.currentTimeMillis();
	// System.out.println(I18NMessage.get(CMyFile.class,
	// "CMyFile.label19", "==============所用时间：")
	// + (lEndTime - lStartTime) + "ms ==============");
	//
	// /*
	// * //CMyFile.deleteDir("d:\\trswcm\\wcm\\doc\\temp\\",2); CMyFile wf
	// * = new CMyFile(); String sFilePathName[] =
	// * {"d:\\CMyFileOut.txt","CMyFileOut.txt"
	// * ,"d:\\test\\CMyFileOut","\\\\wanghaiyang\\share\\test.txt"}; int
	// * i;
	// *
	// * //测试有关文件、目录检查、创建、删除等操作 String sPath = "d:\\test2\\test21\\";
	// * String sSubPath = sPath + "test211\\"; boolean bRet;
	// * System.out.println( sPath + "=" + CMyFile.fileExists(sPath) );
	// *
	// * bRet = CMyFile.makeDir(sPath,true); System.out.println("Create
	// * dir["+sPath+"]=" +bRet ); System.out.println( sPath + "=" +
	// * CMyFile.fileExists(sPath) );
	// *
	// * bRet = CMyFile.makeDir(sSubPath,true); System.out.println("Create
	// * dir["+sSubPath+"]=" +bRet ); System.out.println( sSubPath+ "=" +
	// * CMyFile.fileExists(sSubPath) );
	// *
	// * bRet = CMyFile.deleteDir( sPath, true );
	// * System.out.println("Delete dir=" + bRet ); System.out.println(
	// * sPath + CMyFile.fileExists(sPath) );
	// *
	// * //测试有关文件名提取等函数 for( i=0; i <sFilePathName.length; i++ ){
	// * System.out.println("FilePathName=["+sFilePathName[i]+"]");
	// * System.out.println(" File
	// * found="+CMyFile.fileExists(sFilePathName[i]) );
	// * System.out.println(" FileName=[" +
	// * CMyFile.extractFileName(sFilePathName[i]) + "]");
	// * System.out.println(" FileExt=[" +
	// * CMyFile.extractFileExt(sFilePathName[i]) + "]");
	// * System.out.println(" FilePath=[" +
	// * CMyFile.extractFilePath(sFilePathName[i]) + "]");
	// * System.out.println(" FileAbsolutePathName=[" +
	// * CMyFile.toAbsolutePathName(sFilePathName[i]) + "]");
	// * System.out.println(" FileDrive=[" +
	// * CMyFile.extractFileDrive(sFilePathName[i]) + "]"); }//end for
	// *
	// *
	// * //把strContent写入到文件strFilename中 String strContent = "This is a
	// * test file."; wf.writeFile("d:\\CMyFileOut.txt", strContent);
	// * //要打开文件，当前目录下必须有此文件， 例如：template.html System.out.println(
	// * wf.readFile("template.html") );
	// */
	// } catch (CMyException ex) {
	// ex.printStackTrace(System.out);
	// }// end try
	// }

	/**
	 * 返回一个临时的文件名(通常用作目录)
	 * 
	 * @return 临时文件名
	 */
	public static String getTempFileName() {
		String sTime = new Timestamp(System.currentTimeMillis()).toString();
		sTime = sTime.substring(0, 19).replace('-', '.').replace(' ', '_')
				.replace(':', '.');

		StringBuffer sb = new StringBuffer(256);

		sb.append("__deleted_");
		sb.append(sTime);
		sb.append("__");

		return sb.toString();
	}
}