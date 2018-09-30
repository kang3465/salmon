/*
 *	ImageMagickCmd.java
 *	History				Who				What
 *	2009-7-26 		 	wenyh			Created.
 */
package cn.ele.core.util.cmy.photo;

import cn.ele.core.Exception.WCMException;
import cn.ele.core.constants.ImageLibConstants;
import cn.ele.core.util.cmy.CMyFile;
import cn.ele.core.util.cmy.CMyString;
import cn.ele.core.util.cmy.FilesMan;
import org.apache.log4j.Logger;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Map;

/**
 * Title: TRS 内容协作平台（TRS WCM）<BR>
 * Description: <BR>
 * TODO <BR>
 * Copyright: Copyright (c) 2004-2009 北京拓尔思信息技术股份有限公司<BR>
 * Company: 北京拓尔思信息技术股份有限公司(www.trs.com.cn)<BR>
 * 
 * @author wenyh
 * @version 1.0
 * 
 */

public final class ImageMagickCmd {
	private static Logger logger = Logger.getLogger(ImageMagickCmd.class);

	private final static int SUCCESS = 0;

	private static int NOT_SCALESIZE = -1;

	private static String CONVERT_QUALITY;

	private static String CONVERT_STRIP;

	private static String CMD_CONVERT;

	private static String CMD_COMPOSITE;

	private static String CMD_IDENTIFY;

	private final static String[] GRAVITIES = { "northwest", "center",
			"southeast", "north", "northeast", "west", "east", "southwest",
			"south" };

	public static void setCmds(Map cmds) {
		String sConvertQuality = (String) cmds.get("CONVERT_QUALITY");
		String sConvertStrip = (String) cmds.get("CONVERT_STRIP");

		CONVERT_QUALITY = CMyString.showEmpty(sConvertQuality, "100");
		CONVERT_STRIP = CMyString.showEmpty(sConvertStrip, "");
		CMD_CONVERT = (String) cmds.get("CMD_CONVERT");
		CMD_COMPOSITE = (String) cmds.get("CMD_COMPOSITE");
		CMD_IDENTIFY = (String) cmds.get("CMD_IDENTIFY");
	}

	public static int scale(ImageObj _originalImage, ImageObj _dstImage)
			throws Exception {
		int nWidth = _dstImage.width;
		if (nWidth <= 0) {
			nWidth = _originalImage.width * _dstImage.height
					/ _originalImage.height;
		}
		if (nWidth == 0) {
			nWidth = 1;
		}

		int nHeight = _dstImage.height;
		if (nHeight <= 0) {
			nHeight = _originalImage.height * _dstImage.width
					/ _originalImage.width;
		}
		if (nHeight == 0) {
			nHeight = 1;
		}
		String[] cmd = new String[7];
		cmd[0] = CMD_CONVERT;
		cmd[1] = "-scale";
		StringBuffer buff = new StringBuffer(16);
		buff.append(nWidth).append('x').append(nHeight).append('!');
		cmd[2] = buff.toString();
		buff.setLength(0);
		buff = null;

		cmd[3] = "-quality";
		cmd[4] = CONVERT_QUALITY;// "70";
		cmd[5] = _originalImage.filename;
		cmd[6] = _dstImage.filename;

		// 当配置文件中配置了CONVERT_STRIP=-strip参数后，需要对指令数组cmd[]进行重组,在第6位插入-strip
		if (!CMyString.isEmpty(CONVERT_STRIP)) {
			String[] newCmd = new String[8];
			System.arraycopy(cmd, 0, newCmd, 0, 5);
			newCmd[5] = CONVERT_STRIP;
			System.arraycopy(cmd, 5, newCmd, 6, 2);
			cmd = newCmd;
		}
		return exec(cmd);

	}

	static int execSimple(String[] cmd) throws Exception {
		Process proc = Runtime.getRuntime().exec(cmd);
		return proc.waitFor();
	}

	public static int addWaterkmark(String watermarkfile, String filename,
                                    int[] gravity) throws Exception {
		if (isSelfDefinePosition(gravity, ImageLibConstants.MARKPOS_SELF_DEFIND)) {
			addWaterMarkInDefindePosition(watermarkfile, filename, gravity);
		} else {
			addWaterMarkInPosition(watermarkfile, filename, gravity);
		}
		return 0;
	}

	private static void addWaterMarkInDefindePosition(String watermarkfile,
                                                      String filename, int[] gravity) throws Exception {
		// 支持自定义水印位置
		ImageObj img = new ImageObj(filename);
		img.setFilename(filename);
		Float temp = ((float) gravity[2] / (float) gravity[3])
				* img.width;
		int opX = temp.intValue();
		temp = ((float) gravity[1] / (float) gravity[4]) * img.height;
		int opY = temp.intValue();

		String[] cmd = new String[8];
		cmd[0] = CMD_COMPOSITE;
		cmd[1] = "-compose";
		cmd[2] = "atop";
		cmd[3] = "-geometry";
		StringBuffer buff = new StringBuffer(16);
		buff.append('+').append(opX).append('+').append(opY);
		cmd[4] = buff.toString();
		buff.setLength(0);
		cmd[5] = watermarkfile;
		cmd[6] = filename;
		cmd[7] = filename;
		exec(cmd);
	}

	private static void addWaterMarkInPosition(String watermarkfile,
                                               String filename, int[] gravity) throws Exception {
		String[] cmd = new String[8];
		cmd[0] = CMD_COMPOSITE;
		cmd[1] = "-compose";
		cmd[2] = "atop";
		cmd[3] = "-gravity";
		// cmd[3] = "-geometry";
		cmd[5] = watermarkfile;
		cmd[6] = filename;
		cmd[7] = filename;
		for (int i = 0, len = gravity.length; i < len; i++) {
			if (gravity[i] > 3) {
				cmd[4] = GRAVITIES[gravity[i] - 2];
			} else {
				cmd[4] = GRAVITIES[gravity[i] - 1];
			}
			exec(cmd);
		}

	}

	private static boolean isSelfDefinePosition(int[] _pPosition, int _nPosition) {
		if (_pPosition == null || _pPosition.length < 5) {
            return false;
        }

		if (_pPosition[0] == _nPosition) {
            return true;
        }

		return false;
	}
/*

	public static void roateImg(ImageObj srcimg, String degree)
			throws Exception {
		FilesMan fileman = FilesMan.getFilesMan();
		String filename = fileman.getNextFileName(FilesMan.FLAG_SYSTEMTEMP,
				CMyFile.extractFileExt(srcimg.filename), CMyDateTime.now(),
				true);
		String[] cmd = new String[5];
		cmd[0] = CMD_CONVERT;
		cmd[1] = "-rotate";
		cmd[2] = degree;
		cmd[3] = srcimg.filename;
		cmd[4] = filename;
		exec(cmd);
		srcimg.setFilename(filename);
	}

	public static void borderImg(ImageObj srcimg, String color, int width,
                                 int height) throws Exception {
		FilesMan fileman = FilesMan.getFilesMan();
		String filename = fileman.getNextFileName(FilesMan.FLAG_SYSTEMTEMP,
				CMyFile.extractFileExt(srcimg.filename), CMyDateTime.now(),
				true);
		String[] cmd = new String[7];
		cmd[0] = CMD_CONVERT;
		cmd[1] = "-border";
		StringBuffer buff = new StringBuffer(16);
		buff.append(width).append('x').append(height).append('!');
		cmd[2] = buff.toString();
		cmd[3] = "-bordercolor";
		cmd[4] = color;
		cmd[5] = srcimg.filename;
		cmd[6] = filename;
		exec(cmd);
		srcimg.setFilename(filename);
	}

	public static void raiseImg(ImageObj srcimg, int width, int height,
			boolean zRaised) throws Exception {
		FilesMan fileman = FilesMan.getFilesMan();
		String filename = fileman.getNextFileName(FilesMan.FLAG_SYSTEMTEMP,
				CMyFile.extractFileExt(srcimg.filename), CMyDateTime.now(),
				true);
		String[] cmd = new String[5];
		cmd[0] = CMD_CONVERT;
		if (zRaised) {
			cmd[1] = "-raise";
		} else {
			cmd[1] = "+raise";
		}
		StringBuffer buff = new StringBuffer(16);
		buff.append(width).append('x').append(height).append('!');
		cmd[2] = buff.toString();
		cmd[3] = srcimg.filename;
		cmd[4] = filename;
		exec(cmd);
		srcimg.setFilename(filename);
	}

	*/
/**
//	 * @param srcimg
//	 * @param bmp2type
	 *//*

	public static void convertBmp(ImageObj srcimg, String bmp2type)
			throws Exception {
		String[] cmd = new String[3];
		cmd[0] = CMD_CONVERT;
		cmd[1] = srcimg.filename;
		cmd[2] = cmd[1].substring(0, cmd[1].lastIndexOf('.') + 1) + bmp2type;
		exec(cmd);
		srcimg.filename = cmd[2];
	}

	public static void crop(ImageObj srcimg, int x, int y, int width, int height)
			throws Exception {
		FilesMan fileman = FilesMan.getFilesMan();
		String filename = fileman.getNextFileName(FilesMan.FLAG_SYSTEMTEMP,
				CMyFile.extractFileExt(srcimg.filename), CMyDateTime.now(),
				true);

		String[] cmd = new String[5];
		cmd[0] = CMD_CONVERT;
		cmd[1] = srcimg.filename;
		cmd[2] = "-crop";
		cmd[3] = width + "x" + height + "+" + x + "+" + y;
		cmd[4] = filename;
		exec(cmd);
		srcimg.setFilename(filename);
	}
*/

	static int exec(String[] cmd) throws Exception {
		if (logger.isDebugEnabled()) {
			StringBuffer sb = new StringBuffer();
			for (int i = 0; i < cmd.length; i++) {
				sb.append(" " + cmd[i]);
			}
			logger.debug("CMD命令： " + sb.toString());
		}
		return exec(cmd, null);
	}

	static int exec(String[] cmd, String[] result) throws Exception {
		if(logger.isDebugEnabled()){
			StringBuffer sb = new StringBuffer();
			for (int i = 0; i < cmd.length; i++) {
				sb.append(" " + cmd[i]);
			}
			logger.debug("cmd:" + sb.toString());
			logger.error("who call me", new Exception("exec cmd"));
			
		}
		
		final ByteArrayOutputStream baos = new ByteArrayOutputStream();
		Process proc = Runtime.getRuntime().exec(cmd);
		final InputStream pis = proc.getInputStream();
		final InputStream per = proc.getErrorStream();
		try {
			Thread t = new Thread() {
				public void run() {
					try {
						int a = 0;
						while ((a = per.read()) != -1) {
							baos.write(a);
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			};
			t.start();

			int a = 0;
			while ((a = pis.read()) != -1) {
				baos.write(a);
			}

			t.join();
			int exitCode = proc.waitFor();
			String msg = new String(baos.toByteArray());
			if (exitCode != SUCCESS) {
				throw new Exception("process fail:" + exitCode + ",msg=" + msg);
			}
			if (result != null && result.length > 0) {
				result[0] = msg.trim();
			}
			return SUCCESS;
		} finally {
			if (pis != null) {
				try {
					pis.close();
				} catch (Exception ex) {
				}
			}
			if (per != null) {
				try {
					per.close();
				} catch (Exception ex) {
				}
			}
		}
	}

	public static int[] getImageSize(ImageObj img) throws Exception {
		String[] cmd = { CMD_IDENTIFY, "-format", "%wx%h", img.filename };
		String[] holder = new String[1];
		exec(cmd, holder);
		String[] wh = holder[0].split("x");
		int[] result = new int[2];
		result[0] = Integer.parseInt(wh[0]);
		result[1] = Integer.parseInt(wh[1]);
		return result;
	}

	/**
	 * 转换图片
	 * 
	 * @param _sSrcFile
	 *            要转换的图片.符合WCMData文件名规则(或全路径).
	 * @param scaleLen
	 *            转换的目标尺寸.
	 * @param scaleWidth
	 *            是否固定以图片的宽为准则等比例处理.
	 * @return 处理成功,则返回新文件名.否则返回原文件名.
	 * @throws Exception
	 *             处理过程中出现错误.
	 */
	public static String scale(String _sSrcFile, String _sDstFile,
                               int scaleLen, boolean scaleWidth) throws Exception {
		if (scaleLen <= 0) {
			throw new Exception("Illeagal options: target scalelen is zero.");
		}
		String[] cmd = new String[7];
		cmd[0] = CMD_CONVERT;
		cmd[1] = "-scale";
		StringBuffer buff = new StringBuffer(16);
		ImageObj originalImage = new ImageObj();
		originalImage.setFilename(_sSrcFile);
		boolean enlargePic = false;
		if (scaleWidth || originalImage.width >= originalImage.height) {
			buff.append(scaleLen).append('x');
			enlargePic = (scaleLen >= originalImage.width);
		} else {
			enlargePic = (scaleLen >= originalImage.height);
			buff.append('x').append(scaleLen);
		}
		// 原始尺寸小于转换尺寸
		if (enlargePic) {
			return _sSrcFile;
		}
		cmd[2] = buff.toString();
		buff.setLength(0);
		buff = null;

		cmd[3] = "-quality";
		cmd[4] = CONVERT_QUALITY;// "100";
		cmd[5] = originalImage.filename;
		// String result = makeFileName(originalImage.filename, scaleLen);
		cmd[6] = _sDstFile;

		// 当配置文件中配置了CONVERT_STRIP=-strip参数后，需要对指令数组cmd[]进行重组,在第6位插入-strip
		if (!CMyString.isEmpty(CONVERT_STRIP)) {
			String[] newCmd = new String[8];
			System.arraycopy(cmd, 0, newCmd, 0, 5);
			newCmd[5] = CONVERT_STRIP;
			System.arraycopy(cmd, 5, newCmd, 6, 2);
			cmd = newCmd;
		}

		if (exec(cmd) == SUCCESS) {
			return _sDstFile;
		}
		return _sSrcFile;
	}

	protected static String makeFileName(String filename, int scaleLen) {
		int dotIndex = filename.lastIndexOf('.');
		StringBuffer buff = new StringBuffer(filename.length()
				+ String.valueOf(scaleLen).length() + 1);
		buff.append(filename.substring(0, dotIndex));
		buff.append('_');
		buff.append(scaleLen);
		buff.append(filename.substring(dotIndex));

		return buff.toString();
	}

	/**
	 * 转换图片
	 * 
	 * @param _sSrcFile
	 *            要转换的图片.符合WCMData文件名规则(或全路径).
	 * @param _sDstFile
	 *            生成新图片的名称，要求全路径
//	 * @param nQuality
	 *            转换的质量比.
//	 * @param scaleSizeWidth
	 *            转换的目标尺寸宽度.
	 * @return 处理成功,则返回新文件名.否则返回原文件名.
	 * @throws Exception
	 *             处理过程中出现错误.
	 */
	public static String convert(String _sSrcFile, String _sDstFile,
                                 int _nQuality, int _nScaleSizeWidth) throws Exception {

        if (logger.isDebugEnabled()) {
            logger.debug("Convert [" + _sSrcFile + "] to [" + _sDstFile + "]");
        }
	    //TODO
	    /*if (CMD_CONVERT == null) {
	        ImageLibConfigImpl oImageLibConfigImpl = new ImageLibConfigImpl();
	        oImageLibConfigImpl.reloadConfig();
        }*/
	    
		ImageObj originalImage = new ImageObj();
		originalImage.setFilename(_sSrcFile);

		// 如果宽度<=100需要做特殊处理，以便提升压缩比率
		// +profile "*", -colors 100,-quality 80
		if (_nScaleSizeWidth <= 100) {
			int nColors = 100;
			boolean bRemoveExIF = true;
			_nQuality = 70;
			return convert(_sSrcFile, _sDstFile, _nQuality, _nScaleSizeWidth,
					bRemoveExIF, nColors);
		}

		// 1.如果需要图片质量的压缩
		String[] cmd = new String[5];
		if (_nQuality > 0 && _nQuality < 100) {
			cmd[0] = CMD_CONVERT;
			cmd[1] = "-quality";
			cmd[2] = String.valueOf(_nQuality);
			cmd[3] = originalImage.filename;
			cmd[4] = _sDstFile;
			// 1.1 同时还需要图片尺寸的压缩
			if (_nScaleSizeWidth > 0 && originalImage.width > _nScaleSizeWidth) {
				String[] newCmd = new String[7];
				System.arraycopy(cmd, 0, newCmd, 0, 1);
				newCmd[1] = "-scale";
				newCmd[2] = _nScaleSizeWidth + "x";
				System.arraycopy(cmd, 1, newCmd, 3, 4);
				cmd = newCmd;
			}
		} else {
			// 2.如果仅需要尺寸的压缩
			if (_nScaleSizeWidth > 0 && originalImage.width > _nScaleSizeWidth) {
				cmd = new String[5];
				cmd[0] = CMD_CONVERT;
				cmd[1] = "-scale";
				cmd[2] = _nScaleSizeWidth + "x";
				cmd[3] = originalImage.filename;
				cmd[4] = _sDstFile;
			} else {
				return _sSrcFile;
			}
		}
		// 3.执行
		if (exec(cmd) == SUCCESS) {
			return _sDstFile;
		}
		return _sSrcFile;
	}

	public static String convert(String _sSrcFile, String _sDstFile,
                                 int _nQuality, int _nScaleSizeWidth, int _nDensity)
			throws Exception {
		// 1 构造图片处理命令
		// 1.1 转换命令
		ArrayList<String> arCMD = new ArrayList<String>(5);
		arCMD.add(CMD_CONVERT);

		// 1.2 图片宽度
		// 1.2.1 获取原始图片的宽度和高度
		ImageObj originalImage = new ImageObj();
		originalImage.setFilename(_sSrcFile);
		// 1.2.2 符合压缩条件，添加命令
		if (_nScaleSizeWidth > 0 && originalImage.width > _nScaleSizeWidth) {
			arCMD.add("-scale");
			arCMD.add(_nScaleSizeWidth + "x");
		}

		// 1.3 图片质量
		if (_nQuality > 0 && _nQuality < 100) {
			arCMD.add("-quality");
			arCMD.add(String.valueOf(_nQuality));
		}

		// 1.4 DPI
		if (_nDensity > 0) {
			arCMD.add("-density");
			arCMD.add(String.valueOf(_nDensity));
		}

		// 2 判断是否要处理
		if (arCMD.size() <= 1) {
			CMyFile.copyFile(_sSrcFile, _sDstFile);
			return _sDstFile;
		}

		// 3 转换
		arCMD.add(_sSrcFile);
		arCMD.add(_sDstFile);
		String[] pCMD = new String[0];
		if (exec(arCMD.toArray(pCMD)) == SUCCESS) {
			return _sDstFile;
		}
		return _sSrcFile;
	}

	public static String convert(String _sSrcFile, String _sDstFile,
                                 int _nQuality, int _nScaleSizeWidth, boolean _bRemoveExIF,
                                 int _nColor) throws Exception {
		// 1 构造图片处理命令
		// 1.1 转换命令
		ArrayList<String> arCMD = new ArrayList<String>(5);
		arCMD.add(CMD_CONVERT);
		
		logger.info("CMD_CONVERT:" + CMD_CONVERT);

		// 1.2 图片宽度
		// 1.2.1 获取原始图片的宽度和高度
		ImageObj originalImage = new ImageObj();
		originalImage.setFilename(_sSrcFile);
		// 1.2.2 符合压缩条件，添加命令
		if (_nScaleSizeWidth > 0 && originalImage.width > _nScaleSizeWidth) {
			arCMD.add("-scale");
			arCMD.add(_nScaleSizeWidth + "x");
		}

		// 1.3 图片质量
		if (_nQuality > 0 && _nQuality < 100) {
			arCMD.add("-quality");
			arCMD.add(String.valueOf(_nQuality));
		}

		// 1.4 颜色
		if (_nColor > 0) {
			arCMD.add("-colors");
			arCMD.add(String.valueOf(_nColor));
		}

		// 1.5 去掉EXIF信息
		if (_bRemoveExIF) {
			arCMD.add("+profile");
			arCMD.add("'*'");
		}

		// 2 判断是否要处理
		if (arCMD.size() <= 1) {
			CMyFile.copyFile(_sSrcFile, _sDstFile);
			return _sDstFile;
		}

		// 3 转换
		arCMD.add(_sSrcFile);
		arCMD.add(_sDstFile);
		String[] pCMD = new String[0];
		if (exec(arCMD.toArray(pCMD)) == SUCCESS) {
			return _sDstFile;
		}
		return _sSrcFile;
	}

	// 改变图片后缀
	public static String convert(String _sSrcFile, String _sDstFile)
			throws Exception {
		return convert(_sSrcFile, _sDstFile, Integer.parseInt(CONVERT_QUALITY),
				NOT_SCALESIZE);
	}

	// 改变图片后缀和质量压缩比
	public static String convertQuality(String _sSrcFile, String _sDstFile,
                                        int _nQuality) throws Exception {
		return convert(_sSrcFile, _sDstFile, _nQuality, NOT_SCALESIZE);
	}

	public static class ImageObj {
		public String filename;

		public int width;

		public int height;

		public ImageObj() {

		}

		public ImageObj(String fn) throws WCMException {
			File file = new File(fn);
			if (!file.exists()) {
				FilesMan fileman = FilesMan.getFilesMan();
				fn = fileman.mapFilePath(fn, FilesMan.PATH_LOCAL) + fn;
				file = new File(fn);
			}

			this.filename = file.getAbsolutePath();
		}

		public ImageObj(String fn, int width, int heigth) {
			this.filename = fn;
			this.width = width;
			this.height = heigth;
		}

		/**
		 * @param fn
		 */
		public void setFilename(String fn) throws Exception {
			File file = new File(fn);
			if (!file.exists()) {
				FilesMan fileman = FilesMan.getFilesMan();
				fn = fileman.mapFilePath(fn, FilesMan.PATH_LOCAL) + fn;
				file = new File(fn);
			}

			this.filename = file.getAbsolutePath();

			try {
				// use imagemagick command first.
				int[] wh = getImageSize(this);
				this.width = wh[0];
				this.height = wh[1];
			} catch (Exception ex) {
				logger.debug("尝试使用图片库获取图片尺寸出现异常", ex);
				// second try: ImageIO
				try {
					BufferedImage img = ImageIO.read(file);
					this.width = img.getWidth();
					this.height = img.getHeight();
					// return;
				} catch (Throwable ex2) {
					logger.debug("尝试使用jdk接口获取图片尺寸出现异常", ex2);
					// last try.
					try {
						Image img = Toolkit.getDefaultToolkit().getImage(
								this.filename);
						Component component = new Component() {
						};
						MediaTracker tracker = new MediaTracker(component);
						tracker.addImage(img, 1);
						tracker.waitForID(1, 0);
						this.width = img.getWidth(null);
						this.height = img.getHeight(null);
					} catch (Throwable ex3) {
						logger.debug("尝试使用MediaTracker接口处理出现异常", ex3);
						throw new Exception("处理图片失败，请确认图片库是否设置正确，或者当前jdk配置有问题"
								+ "  请进一步通过查看debug日志来分析错误");
					}
				}
			}
		}

		public String toString() {
			StringBuffer buff = new StringBuffer(64);
			buff.append(this.filename);
			buff.append(":width=").append(this.width);
			buff.append(",height=").append(this.height);
			return buff.toString();
		}
	}
}
