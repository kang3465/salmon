package cn.ele.core.util.cmy;

/**
 * Created:         2001.10
 * Last Modified:   2001.10.12/2001.11.3
 * Description:
 *
 *
 */

/**
 * <p>
 * Title: TRS 内容协作平台（TRS WCM）
 * </p>
 * <p>
 * Description:
 * </p>
 * <p>
 * class FilesMan ―― WCM文件管理对象的定义和实现
 * <p>
 * 文件命名规则：
 * <p>
 * 格式： ff+yyyy,mm,dd+tt,ttt,ttt+rrrr.ext
 * <p>
 * index: 01 2345 67 89 01 234 567 8901
 * <p>
 * 其中：
 * <p>
 * <0>ff:文件目录的标识，2位
 * <p>
 * <1>yyyymmnn--创建日期, 8位，其中
 * <p>
 * yyyy--4位表示的年,mm--2位表示的月，dd--2位表示的日；
 * <p>
 * 如：011009 表示 2001-10-09。
 * <p>
 * <2>tt,ttt,ttt 为8位时间值(单位毫秒)。如：1992849 表示 08:33:12
 * <p>
 * <3>rrrr -- 4位随机数。
 * <p>
 * <4>.ext -- 扩展名。
 * <p>
 * 文件存储目录组织规则：Dir1+Dir2+Dir3
 * <p>
 * 如：[UploadTempPath]\N200110\N20011015\
 * <p>
 * [1]一级目录(Dir1)
 * <p>
 * <1>上传文件临时目录 （标识：U0）
 * <p>
 * <2>普通文件数据目录 （标识：N0）
 * <p>
 * <3>受保护文件数据目录 （标识：P0）
 * <p>
 * <4>系统用临时目录 （标识：ST）
 * <p>
 * <5>用户用临时目录 （标识：UT）
 * <p>
 * <6>受保护文件数据目录 （标识：P0）
 * <p>
 * <7>发布路径 (标识：B0)
 * <p>
 * <8>可通过HTTP协议访问的路径(标识：W0)
 * <p>
 * <9>其他：用户可扩展 (标识：2位)
 * <p>
 * [2]二级目录(Dir2)
 * <p>
 * 一级目录标识+年月，文件名的前8个字符，如："N0200110\"
 * <p>
 * [3]三级目录(Dir3)
 * <p>
 * 二级目录+日标识，文件名的前10个字符，如："N020011015\"*
 * <p>
 * Copyright: Copyright (c) 2001-2002 TRS信息技术有限公司
 * </p>
 * <p>
 * Company: TRS信息技术有限公司(www.trs.com.cn)
 * </p>
 *
 * @author TRS信息技术有限公司
 * @version 1.0
 */

import cn.ele.core.Exception.CMyException;
import cn.ele.core.Exception.ExceptionNumber;
import cn.ele.core.Exception.WCMException;
import cn.ele.core.message.I18NMessage;
//import com.trs.infra.support.config.PathConfig;
import org.apache.log4j.Logger;

import java.io.File;
import java.util.HashMap;
import java.util.TimeZone;

public class FilesMan extends Object {
    public static boolean DELETE_FILE_ON_MOVE = false;

    private static Logger s_logger = Logger.getLogger(FilesMan.class);

    private static FilesMan m_filesMan = new FilesMan();

    // 常量定义：关于文件名称
    /** 文件名称最小值 */
    public final static int FILENAME_MIN_LENGTH = 22; //

    /** 目录标识长度 */
    public final static int FILENAME_FLAG_LENGTH = 2; //

    /** 日期长度 */
    public final static int FILENAME_DATE_LENGTH = 8; //

    /** 时间值长度 */
    public final static int FILENAME_TIME_LENGTH = 8; //

    /** 随机数长度 */
    public final static int FILENAME_RANDOM_LENGTH = 4; //

    /** 当前时区时间差 */
    private final static int TIMEZONE_RAWOFFSET = TimeZone.getDefault()
            .getRawOffset();

    // 常量定义：文件标识类型，对应一级目录分类
    /** 文件标识类型：普通文件目录 */
    public final static String FLAG_NORMAL = "N0"; //

    /** 文件标识类型：受保护文件目录 */
    public final static String FLAG_PROTECTED = "P0"; //

    /** 文件标识类型：上传的临时文件目录 */
    public final static String FLAG_UPLOAD = "U0"; //

    /** 文件标识类型：系统用临时文件目录 */
    public final static String FLAG_SYSTEMTEMP = "ST"; //

    /** 文件标识类型：用户用临时文件目录 */
    public final static String FLAG_USERTEMP = "UT"; //

    /** 文件标识类型：模板文件目录 */
    public final static String FLAG_TEMPLATE = "TM"; //

    /** 文件标识类型：本地存放发布文件的路径 */
    public final static String FLAG_LOCALPUB = "LP"; //

    /** 文件标识类型：本地存放预览文件的路径 */
    public final static String FLAG_LOCALPREVIEW = "LV"; //

    /** 文件标识类型：可通过http协议访问的文件的路径 */
    public final static String FLAG_WEBFILE = "W0"; //

    /** 文件标识类型：文档导出导入相关资源文件的存放路径 */
    public final static String FLAG_DOCUMENTSOURCE = "DS";

    /** 文件标识类型：智能建站预设路径 */
    public final static String FLAG_SITEFROM = "SF";

    /** 文件标识类型：BigTable（稀疏数据）的存储路径 */
    public final static String FLAG_BIGTABLE = "BT";

    /** 文件标识类型：InfoView（自定义表单，借助MS InfoPath实现）的存储路径 */
    public final static String FLAG_INFOVIEW = "IV";

    /** 系统中XSL文件的存放路径 */
    public final static String FLAG_TRANSFORMER = "TF";

    /** 文件标识类型：HelpSearchIndex（帮助文件索引）的存储路径 */
    public final static String FLAG_HELPSEARCH = "HS";

    // 常量定义：目录类型 3类
    /** 目录类型：本地目录，用于Server( for Server ) */
    public final static int PATH_LOCAL = 0; //

    /** 目录类型：Http目录，用于Web( for Client ) */
    public final static int PATH_HTTP = 1; //

    /** 目录类型：Ftp目录，用于Ftp( for Client ) */
    public final static int PATH_FTP = 2; //

    private final HashMap m_hPathConfig; // 文件目录配置表

    // 构造函数
    /**
     * 构造函数
     *
//     * @param _app
     *            关联的Application
     */
    private FilesMan() {
        m_hPathConfig = new HashMap(11);
        // wenyh@2005-7-1 10:02:26 add comment:Test for DB
        // DBManager.getDBManager();
        // wenyh@2005-7-1 10:02:26 add comment:Test for DB
    }

    public static FilesMan getFilesMan() {
        if (m_filesMan.m_hPathConfig.isEmpty()) {
            loadFilesMan();
        }
        return m_filesMan;
    }

    // =========================================================================
    // 文件目录管理

    /**
     * TODO 装载系统有关目录配置信息
     *
     * @param _bMakeDirIfNotExists
     *            目录不存在时是否自动创建
     * @throws WCMException
     */
/*    private synchronized void loadPathConfigs(boolean _bMakeDirIfNotExists)
            throws WCMException {
        if (!m_filesMan.m_hPathConfig.isEmpty()) {
            return;
        }
        Configs configs = null;
        Config aConfig = null;

        // modified by hxj.为了安全行，从配置文件加载路径配置，而不从数据库加载，防止用户的修改
        ConfigServer server = ConfigServer.getServer();

        try {
            configs = new Configs();
            configs.open(new WCMFilter("", "CType=" + Config.TYPE_FILE_PATH, ""));
            for (int i = 0; i < configs.size(); i++) {
                aConfig = (Config) configs.getAt(i);
                if (aConfig == null)
                    continue;

                String sKey = aConfig.getConfigKey();
                String sPath = server.getInitProperty(sKey);
                if (CMyString.isEmpty(sPath)) {
                    sPath = aConfig.getValue();
                    server.setInitProperties(sKey, sPath);
                }

                try {
                    this.putPathConfig(sKey, sPath, _bMakeDirIfNotExists);
                    // this.putPathConfig(aConfig, _bMakeDirIfNotExists);
                    // //why@2002
                    // -04-24
                } catch (Exception ex) {
                    s_logger.error(
                            I18NMessage.get(FilesMan.class, "FilesMan.label1",
                                    "--->配置记录：WCMConfig Id=[")
                                    + aConfig.getId()
                                    + "] Key=["
                                    + aConfig.getConfigKey()
                                    + "]  Value=["
                                    + aConfig.getValue() + "] ）", ex);
                }// end try:处理完一个Config对象
            }// end for：处理完所有的目录配置
            configs.clear();
        } catch (Exception ex) {
            throw new WCMException(ExceptionNumber.ERR_WCMEXCEPTION,
                    I18NMessage.get(FilesMan.class, "FilesMan.label2",
                            "装载文件目录配置信息时失败(FilesMan.loadPathConfigs)"), ex);
        }
    }*/// END: loadPathConfigs( )

    /**
     * TODO 装载指定目录配置信息
     *
     * @param _config
     *            配置对象
     * @return 若装在成功，则返回true；否则，返回false
     * @throws WBEException
     */
/*
    public boolean putPathConfig(Config _config, boolean _bMakeDirIfNotExists)
            throws WCMException {
        if (_config == null || !_config.isValidInstance())
            return false;

        try {
            // 取关键字（亦即路径标识）
            String sKey = _config.getConfigKey().trim();
            if (sKey.length() != 2) { // 关键字必须是2位，作为路径标志
                throw new WCMException(ExceptionNumber.ERR_PARAM_INVALID,
                        I18NMessage.get(FilesMan.class, "FilesMan.label3",
                                "目录配置中关键字必须为2位(FilesMan.loadPathConfig)"));
            }

            // 分解配置信息
            PathConfig pathConfig = new PathConfig(_config.getValue());
            return this.putPathConfig(sKey, pathConfig, _bMakeDirIfNotExists);
        } catch (Exception ex) {
            throw new WCMException(ExceptionNumber.ERR_WCMEXCEPTION,
                    I18NMessage.get(FilesMan.class, "FilesMan.label4",
                            "装载配置信息失败(FilesMan.putPathConfig)"), ex);
        }
    }

    public boolean putPathConfig(String sKey, String sPath,
                                 boolean _bMakeDirIfNotExists) throws WCMException {
        if (CMyString.isEmpty(sKey) || CMyString.isEmpty(sPath))
            return false;

        try {
            // 取关键字（亦即路径标识）
            sKey = sKey.trim();
            if (sKey.length() != 2) { // 关键字必须是2位，作为路径标志
                throw new WCMException(ExceptionNumber.ERR_PARAM_INVALID,
                        I18NMessage.get(FilesMan.class, "FilesMan.label3",
                                "目录配置中关键字必须为2位(FilesMan.loadPathConfig)"));
            }

            // 分解配置信息
            PathConfig pathConfig = new PathConfig(sPath);
            return this.putPathConfig(sKey, pathConfig, _bMakeDirIfNotExists);
        } catch (Exception ex) {
            throw new WCMException(ExceptionNumber.ERR_WCMEXCEPTION,
                    I18NMessage.get(FilesMan.class, "FilesMan.label4",
                            "装载配置信息失败(FilesMan.putPathConfig)"), ex);
        }
    }
*/

    /**
     * TODO 载入指定目录配置信息
     *
     * @param _sPathFlag
     *            目录标志（和文件标志为同一标志，FLAG_NORMAL等常量）
     * @param _pathConfig
     *            目录配置对象
     * @param _bMakeDirIfNotExists
     *            目录不存在是否自动创建
     * @return
     * @throws Exception
     */
/*    public boolean putPathConfig(String _sPathFlag, PathConfig _pathConfig,
                                 boolean _bMakeDirIfNotExists) throws Exception {
        if (_pathConfig == null)
            return false;

        // 检查本地路径是否存在
        String sLocalPath = _pathConfig.getLocalPath();
        if (!CMyFile.fileExists(sLocalPath)) { // 目录不存在
            if (_bMakeDirIfNotExists) { // 创建目录
                CMyFile.makeDir(sLocalPath, true);
            } else {
                throw new WCMException(ExceptionNumber.ERR_WCMEXCEPTION,
                        I18NMessage.get(FilesMan.class, "FilesMan.label5",
                                "本地路径")
                                + sLocalPath
                                + I18NMessage.get(FilesMan.class,
                                "FilesMan.label6",
                                "不存在(FilesMan.loadPathConfig)"));
            }
        }// end if

        // 装入配置哈希表
        synchronized (m_hPathConfig) {
            this.m_hPathConfig.put(_sPathFlag.toUpperCase(), _pathConfig);
        }

        return true;
    }*/

    /**
     *  TODO 从目录配置项中删除指定配置项
     *
     * @param _sPathFlag
     *            目录标志（和文件标志为同一标志，FLAG_NORMAL等常量）
     * @return
     */
/*    public PathConfig removePathConfig(String _sPathFlag) {
        if (true) {// 不从数据库加载配置，而是从文件加载
            return null;
        }
        synchronized (m_hPathConfig) {
            return (PathConfig) this.m_hPathConfig.remove(_sPathFlag
                    .toUpperCase());
        }
    }*/

    /**
     * TODO 取指定目录标识的配置信息
     *
     * @param _sPathFlag
     *            目录标志（和文件标志为同一标志，FLAG_NORMAL等常量）
     * @return 指定目录标识的配置信息
     */
//    public PathConfig getPathConfig(String _sPathFlag) {
//        return (PathConfig) m_hPathConfig.get(_sPathFlag);
//    }

    /**
     * 获取指定目录标识的配置信息值
     *
     * @param _sPathFlag
     *            目录标志（和文件标志为同一标志，FLAG_NORMAL等常量）
     * @param _nPathType
     *            目录类型（定义在FilesMan中，PATH_LOCAL等常量）
     * @return 指定目录标识的配置信息值
     */
    public String getPathConfigValue(String _sPathFlag, int _nPathType) {
        String sPath=null;
        //TODO 加载配置
       /* PathConfig pathConfig = this.getPathConfig(_sPathFlag); // 目录配置信息
        if (pathConfig == null) {
            return null;
        }

        switch (_nPathType) {
            case PATH_LOCAL: {
                sPath = pathConfig.getLocalPath();
                // 对路径进行处理，如果有多个分隔符，这里要提化成一个（Windows系统下将4个替换成2个）
                sPath = getLocalFormatPath(sPath);
                break;
            }
            case PATH_HTTP: {
                sPath = pathConfig.getHttpPath();
                break;
            }
            case PATH_FTP: {
                sPath = pathConfig.getFtpPath();
                break;
            }
            default:
                return null;
        }*/// end case
        return sPath;
    }

    /**
     * 对路径进行处理，将多个路径分隔符替换为一个路径分隔符<BR>
     * 如果路径分隔符为“/”，则将所有的“//”替换为“/”; 如果路径分隔符为“\”，则将所有的“\\\\”替换为“\\”.
     *
     * @param sPath
     * @return
     */
    private static String getLocalFormatPath(String sPath) {
        // add by caohui@2012-11-26 下午3:01:42
        // 不兼容错误写法，会引发不支持网络路径
        if (true)
            return sPath;

        if (CMyString.isEmpty(sPath))
            return "";
        String source;
        String dest;
        if (File.separatorChar == '/') {
            source = "//";
            dest = "/";
        } else {
            source = "\\\\\\\\";
            dest = "\\\\";
        }
        return sPath.replaceAll(source, dest);
    }

    /**
     * 目录映射：根据文件名映射文件所在的路径
     * <p>
     * 文件所在路径
     * <p>
     * 说明：不检查文件路径是否真正存在
     *
     * @param _sFileName
     *            文件名
     * @param _nPathType
     *            目录类型
     * @return 根据文件名映射文件所在的路径
     * @throws WCMException
     */
    public String mapFilePath(String _sFileName, int _nPathType)
            throws WCMException {
        if (_sFileName == null) {
            throw new WCMException(ExceptionNumber.ERR_PARAM_INVALID,
                    I18NMessage.get(FilesMan.class, "FilesMan.label7",
                            "文件名为空(FilesMan.mapFilePath)"));
        }

        // 检查文件名长度是否符合最小文件长度
        _sFileName = _sFileName.trim();

        // add by liuhm@20140114 安全性问题处理，需要先判断再获取文件名
        if (_sFileName.indexOf("..") >= 0) {
            throw new WCMException(ExceptionNumber.ERR_PARAM_INVALID, "["
                    + _sFileName
                    + I18NMessage.get(FilesMan.class, "FilesMan.label8",
                    "]无效的文件格式(FilesMan.mapFilePath)"));
        }

        _sFileName = CMyFile.extractFileName(_sFileName);
        if (_sFileName.length() < FILENAME_MIN_LENGTH) {
            throw new WCMException(ExceptionNumber.ERR_PARAM_INVALID, "["
                    + _sFileName
                    + I18NMessage.get(FilesMan.class, "FilesMan.label8",
                    "]无效的文件格式(FilesMan.mapFilePath)"));
        }

        // TODO 取文件存储目录配置信息，并检查类型标识（前2位）是否正确
        /*PathConfig pathConfig = null; // 目录配置信息
        pathConfig = this.getPathConfig(_sFileName.substring(0, 2));
        if (pathConfig == null) {
            throw new WCMException(ExceptionNumber.ERR_PARAM_INVALID,
                    I18NMessage.get(FilesMan.class, "FilesMan.label9",
                            "_sFileName:[" + _sFileName
                                    + "]，文件格式不匹配：类型标识无效(FilesMan.mapFilePath)"));
        }*/

        // TODO 构造文件路径
        String sPath=null; // 文件路径
/*
        char chrPathSeparator = (_nPathType == PATH_LOCAL ? File.separatorChar
                : '/'); // 路径分割符
        switch (_nPathType) {
            case PATH_LOCAL: {
                sPath = pathConfig.getLocalPath();
                break;
            }
            case PATH_HTTP: {
                sPath = pathConfig.getHttpPath();
                break;
            }
            case PATH_FTP: {
                sPath = pathConfig.getFtpPath();
                break;
            }
            default:
                throw new WCMException(ExceptionNumber.ERR_PARAM_INVALID,
                        I18NMessage.get(FilesMan.class, "FilesMan.label10",
                                "无效的路径类别(FilesMan.mapFilePath)"));
        }// end case
        sPath += _sFileName.substring(0, 8) + chrPathSeparator
                + _sFileName.substring(0, 10) + chrPathSeparator;
*/

        return sPath; // 返回路径
    }

    // =========================================================================
    // TODO 构造有效文件名
    /**
     * 获取下一个可用的文件名称
     * <p>
     * 构造说明：
     * <p>
     * [1]构造文件名时，自动检测文件名是否重复。
     * <p>
     * [2]若重复，则在重复的文件名后再追加2位随机数。
     * <p>
     * 说明：为减少系统负担，一旦两次获取文件名失败，返回null.
     *
     * @param _sPathFlag
     *            文件类型标识（值由FLAG_NORMAL等常量定义）
     * @param _sFileExt
     *            文件扩展名（".ext"或"ext"形式）
     * @param _crTime
     *            创建时间（可省，默认值null，表示当前时间）
     * @param _bIncludePath
     *            返回值中是否包含路径（可省，默认值包含）
     * @return 若成功，返回得到的文件名；否则，返回null.
     * @throws WCMException
     */
    public synchronized String getNextFileName(String _sPathFlag,
                                               String _sFileExt, CMyDateTime _crTime, boolean _bIncludePath)
            throws WCMException {
        // 安全性检查：_sFileExt不能含有..信息
        // http://192.9.200.87:8989/browse/WCMVS-420
        /*if (_sFileExt.indexOf("..") >= 0) {
            throw new WCMException("非法文件后缀信息，不能获取相应的文件名");
        }
        _sFileExt = _sFileExt.replace("?", "");
        if (_sFileExt.length() > 8) {
            throw new WCMException("不是常规后缀信息，不能获取相应的文件名");
        }

        PathConfig pathConfig = null; // 目录配置信息

        String sDate, sTime, sRandom;
        long lTime;
        String sFilePath, sFileName, sFileExt; // 构造的文件名（含扩展名）
        int i;

        // 取目录配置信息，并检查参数_sPathFlag是否有效
        if (_sPathFlag == null) {
            throw new WCMException(ExceptionNumber.ERR_PARAM_INVALID,
                    I18NMessage.get(FilesMan.class, "FilesMan.label11",
                            "路径标识为空（FilesMan.getNextFileName）"));
        }
        _sPathFlag = _sPathFlag.trim().toUpperCase();
        pathConfig = (PathConfig) m_hPathConfig.get(_sPathFlag);
        if (pathConfig == null) {
            throw new WCMException(ExceptionNumber.ERR_PARAM_INVALID,
                    I18NMessage.get(FilesMan.class, "FilesMan.label12",
                            "无效的路径标识参数(FilesMan.getNextFileName)"));
        }

        // 处理文件的创建时间参数
        if ((_crTime == null) || _crTime.isNull()) {
            _crTime = CMyDateTime.now(); // 取当前时间
        }
        // 获取创建日期（8位:yyyMMdd）
        sDate = _crTime.toString("yyyyMMdd");

        // 获取文件存放路径（本地路径）
        sFilePath = pathConfig.getLocalPath() + _sPathFlag
                + sDate.substring(0, 6) + File.separatorChar + _sPathFlag
                + sDate.substring(0, 8) + File.separatorChar;
        // 检查路径是否存在
        if (!CMyFile.fileExists(sFilePath)) { // 路径不存在，则创建之
            try {
                CMyFile.makeDir(sFilePath, true); // 创建目录
            } catch (Exception ex) {
                throw new WCMException(ExceptionNumber.ERR_PARAM_INVALID,
                        I18NMessage.get(FilesMan.class, "FilesMan.label13",
                                "指定路径")
                                + sFilePath
                                + I18NMessage.get(FilesMan.class,
                                "FilesMan.label14",
                                "无法创建(FilesMan.getNextFileName)"));
            }// end try
        }// end if

        // 取创建时间（7位）
        // 注意：计算时间值时，必须将mydtNow.getTimeInMillis()值加上时区差。
        lTime = (_crTime.getTimeInMillis() + TIMEZONE_RAWOFFSET)
                % CMyDateTime.ADAY_MILLIS;
        sTime = CMyString.numberToStr(lTime, 8, '0');

        // 取4位随机数
        sRandom = CMyString.numberToStr(Math.round(Math.random() * 10000), 4,
                '0');

        // 处理扩展名
        sFileExt = _sFileExt.trim();
        // 检查第一个字符是否为'.'
        if ((sFileExt.length() > 0) && (sFileExt.charAt(0) != '.')) {
            sFileExt = "." + sFileExt;
        }

        // 构造文件名称
        // 在检查文件名重复时，最多允许构造两次。
        sFileName = _sPathFlag + sDate + sTime + sRandom;
        for (i = 0; i < 2; i++) {
            if (i > 0) { // 扩展随机数以解决重复问题，每次向后扩展两位
                sFileName += CMyString.numberToStr(
                        Math.round(Math.random() * 100), 2, '0');
            }
            // 检查文件名是否重复
            if (!CMyFile.fileExists(sFilePath + sFileName + sFileExt))
                return (_bIncludePath ? sFilePath : "") + sFileName + sFileExt; // 找到不重复的文件名
        }*/// end for
        return null; // 没有找到不重复的文件名
    }// END:getNextFileName

    /**
     * 获取下一个可用的文件名称
     *String _sPathFlag,String _sFileExt, CMyDateTime _crTime, boolean _bIncludePath
//     * @see getNextFileName( String _sPathFlag, String _sFileExt, CMyDateTime
//     *      _crTime, boolean _bIncludePath )
     */
    public synchronized String getNextFileName(String _sPathFlag,
                                               String _sFileExt) throws WCMException {
        return getNextFileName(_sPathFlag, _sFileExt, null, false);
    }

    /**
     * 获取下一个可用的文件名称
     *
//     * @see getNextFileName( String _sPathFlag, String _sFileExt, CMyDateTime
//     *      _crTime, boolean _bIncludePath )
     */
    public synchronized String getNextFileName(String _sPathFlag,
                                               String _sFileExt, CMyDateTime _crTime) throws WCMException {
        return getNextFileName(_sPathFlag, _sFileExt, _crTime, false);
    }

    /**
     * 获取下一个可用的文件名称
     *
//     * @see getNextFileName( String _sPathFlag, String _sFileExt, CMyDateTime
//     *      _crTime, boolean _bIncludePath )
     */
    public synchronized String getNextFilePathName(String _sPathFlag,
                                                   String _sFileExt) throws WCMException {
        return getNextFileName(_sPathFlag, _sFileExt, null, true);
    }

    /**
     * 获取下一个可用的文件名称
     *
//     * @see getNextFileName( String _sPathFlag, String _sFileExt, CMyDateTime
//     *      _crTime, boolean _bIncludePath )
     */
    public synchronized String getNextFilePathName(String _sPathFlag,
                                                   String _sFileExt, CMyDateTime _crTime) throws WCMException {
        return getNextFileName(_sPathFlag, _sFileExt, _crTime, true);
    }

    // ==========================================================================
    // 从文件名中提取文件创建时间
    // 文件格式：ff+yyyy,mm,dd+tt,ttt,ttt+rrrr.ext
    // index: 01 2345 67 89 01 234 567 8901

    /**
     * 提取日期值
     *
     * @param _sFileName
     *            文件名
     * @return 字符串，格式：yyyy-MM-dd
     * @throws WCMException
     *             若文件名格式不符，抛出异常
     */
    public static String extractFileCrDateValue(String _sFileName)
            throws WCMException {
        if (_sFileName.length() < FILENAME_MIN_LENGTH) {
            throw new WCMException(ExceptionNumber.ERR_PARAM_INVALID,
                    I18NMessage.get(FilesMan.class, "FilesMan.label15",
                            "无效的文件格式(FilesMan.extractFileCrDateValue)"));
        }

        String sDate;
        sDate = _sFileName.substring(2, 6) + "-" + _sFileName.substring(6, 8)
                + "-" + _sFileName.substring(8, 10);
        return sDate;
    }

    /**
     * 从文件名中提取创建时间值
     *
     * @param _sFileName
     *            文件名
     * @return long型
     * @throws WCMException
     *             若文件名格式不符，抛出异常
     */
    public static long extractFileCrTimeValue(String _sFileName)
            throws WCMException {
        if (_sFileName.length() < FILENAME_MIN_LENGTH) {
            throw new WCMException(ExceptionNumber.ERR_PARAM_INVALID,
                    I18NMessage.get(FilesMan.class, "FilesMan.label16",
                            "无效的文件格式(FilesMan.extractFileCrTimeValue)"));
        }

        long lTime;
        try {
            lTime = Integer.parseInt(_sFileName.substring(10, 18));
        } catch (Exception ex) {
            throw new WCMException(ExceptionNumber.ERR_PARAM_INVALID,
                    I18NMessage.get(FilesMan.class, "FilesMan.label16",
                            "无效的文件格式(FilesMan.extractFileCrTimeValue)"), ex);
        }// end try
        return lTime;
    }

    /**
     * 提取创建日期值
     *
     * @param _sFileName
     * @return 返回值：CMyDateTime
     * @throws WCMException
     *             异常：若文件名格式不符，抛出异常
     */
    public static CMyDateTime extractFileCrDate(String _sFileName)
            throws WCMException {
        CMyDateTime mydtCrDate;
        String sDate;

        // 取日期字符串值
        sDate = extractFileCrDateValue(_sFileName);

        // 转化为日期值
        try {
            mydtCrDate = new CMyDateTime();
            mydtCrDate.setDateWithString(sDate, CMyDateTime.FORMAT_DEFAULT);
        } catch (CMyException ex) {
            throw new WCMException(ExceptionNumber.ERR_PARAM_INVALID,
                    I18NMessage.get(FilesMan.class, "FilesMan.label17",
                            "无效的文件格式(FilesMan.extractFileCrDate)"), ex);
        }// end try
        return mydtCrDate;
    }

    /**
     * 提取文件创建的日期时间值
     *
     * @param _sFileName
     *            文件名
     * @return CMyDateTime 日期时间值
     * @throws WCMException
     *             异常：若文件名格式不符，抛出异常
     */
    public static CMyDateTime extractFileCrDateTime(String _sFileName)
            throws WCMException {
        CMyDateTime mydtCrDate = null; // 创建日期
        long lTime; // 时间值（毫秒数）

        mydtCrDate = extractFileCrDate(_sFileName); // 取日期值
        lTime = extractFileCrTimeValue(_sFileName); // 取时间值
        return new CMyDateTime(mydtCrDate.getTimeInMillis() + lTime);
    }// END:extractFileCrTime

    // =======================================================================
    // 扩展逻辑接口

    /**
     * 将指定文件复制到指定的目录
     *
     * @param _srcFilePathName
     *            原文件名（包含路径）
     * @param _dstPathFlag
     *            目标目录标识（必须是有效的目录标识）
     * @param _bReturnPath
     *            是否返回保存后的文件名
     * @return
     * @throws WCMException
     */
    public String copyFile(String _srcFilePathName, String _dstPathFlag,
                           boolean _bReturnPath) throws WCMException {
        try {
            String sFileExt = CMyFile.extractFileExt(_srcFilePathName); // 取原文件扩展名
            String sSaveFilePathName = getNextFilePathName(_dstPathFlag,
                    sFileExt);
            // 获得下一个可用的文件名
            CMyFile.copyFile(_srcFilePathName, sSaveFilePathName); // 复制文件
            return (_bReturnPath ? sSaveFilePathName : CMyFile
                    .extractFileName(sSaveFilePathName));
        } catch (Exception ex) {
            throw new WCMException(ExceptionNumber.ERR_WCMEXCEPTION,
                    I18NMessage.get(FilesMan.class, "FilesMan.label18",
                            "复制文件到指定目录失败（FilesMan.copyFile）"), ex);
        }
    }// END: copyFile()

    /**
     * 将指定文件移动到指定的目录
     *
     * @param _srcFilePathName
     *            原文件名（包含路径）
     * @param _dstPathFlag
     *            目标目录标识（必须是有效的目录标识）
     * @param _bReturnPath
     *            是否返回保存后的文件名
     * @return
     * @throws WCMException
     */
    public String moveFile(String _srcFilePathName, String _dstPathFlag,
                           boolean _bReturnPath) throws WCMException {
        try {
            String sSaveFile = copyFile(_srcFilePathName, _dstPathFlag,
                    _bReturnPath);
            // add by caohui@2016年3月20日 下午1:20:20
            // 规避前端多次请求，导致文件被删除
            if (DELETE_FILE_ON_MOVE)
                CMyFile.deleteFile(_srcFilePathName);
            return sSaveFile;
        } catch (Exception ex) {
            throw new WCMException(ExceptionNumber.ERR_WCMEXCEPTION,
                    I18NMessage.get(FilesMan.class, "FilesMan.label19",
                            "移动文件到指定目录失败（FilesMan.moveFile）"), ex);
        }
    }// END: moveFile()

    /**
     * 删除指定的文件
     *
     * @param _sFileName
     *            按照WCM文件命名规则构造的文件名
     * @return 删除成功时返回true；否则返回false
     * @throws WCMException
     */
    public boolean deleteFile(String _sFileName) throws WCMException {
        try {
            String sFilePathName;

            _sFileName = CMyFile.extractFileName(_sFileName.trim());
            sFilePathName = this.mapFilePath(_sFileName, PATH_LOCAL)
                    + _sFileName;

            return CMyFile.deleteFile(sFilePathName);

        } catch (Exception ex) {
            throw new WCMException(ExceptionNumber.ERR_WCMEXCEPTION,
                    I18NMessage.get(FilesMan.class, "FilesMan.label20",
                            "删除指定的文件失败（FilesMan.deleteFile）"), ex);
        }
    }// END: removeFile()

    // =======================================================================
    // 应用相关

    /**
     * <p>
     * 模板附件文件所在目录映射
     * <p>
     * 目录组织：文件保存在Template目录下，并按站点划分子目录
     * <p>
     * 命名规则： <Template Path>\site[站点ID]\
     *
     * @param _nSiteId
     *            站点ID
     * @param _nPathType
     *            路径类型（定义在FilesMan中）
     * @param _bCreateWhenNotExists
     *            路径不存在时是否自动产生
     * @return 模板附件文件所在目录
     * @throws WCMException
     *             路径类型参数有误或产生新的目录时
     * @deprecated to use getTemplateAppendixPath(WebSite.OBJ_TYPE, _nSiteId,
     *             _nPathType, _bCreateWhenNotExists) instead please.
     */
    public String getTempAppendixFilePath(int _nSiteId, int _nPathType,
                                          boolean _bCreateWhenNotExists) throws WCMException {
        return getTemplateAppendixPath(103, _nSiteId, _nPathType,
                _bCreateWhenNotExists);
    }

    /**
     * Returns the template appendix file path of the sepcified root. <BR>
     * Path format rule: <BR>
     * [1]to keep the compatibility with WCM5.1 and before versions, if the root
     * is a website (Type=103), returns "site[Root Id]\" or "site[Root Id]/"
     * like "site2\" or "site2/"; <BR>
     * [2]otherwise, returns "root[Root Type in Hex mode]\[Root Id]\" or
     * "root[Root Type in Hex mode]\[Root Id]\", like "root10A\12\" or
     * "root10A/12/". <BR>
     *
     * @param _nRootType
     *            root type, like WebSite.OBJ_TYPE
     * @param _nRootId
     *            root id.
     * @param _nPathType
     *            path type, like PATH_LOCAL, PATH_HTTP, etc.
     * @param _bCreateWhenNotExists
     *            <code>true</code>, to create the path if it is a local
     *            directory.
     * @return the template appendix file path of the sepcified root.
     * @throws WCMException
     *             if failed to find the config or create path.
     */
    public String getTemplateAppendixPath(int _nRootType, int _nRootId,
                                          int _nPathType, boolean _bCreateWhenNotExists) throws WCMException {
        String sPath = this.getPathConfigValue(FLAG_TEMPLATE, _nPathType);
        if (sPath == null) {
            throw new WCMException(ExceptionNumber.ERR_WCMEXCEPTION,
                    "Path config for " + FLAG_TEMPLATE + " missing!");
        }

        char chrSpearator = (_nPathType == PATH_LOCAL ? File.separatorChar
                : '/');
        if (_nRootType == 103) {// The magic num. for not to import the WebSite.
            sPath += "site";
        } else {
            sPath += "root" + Integer.toHexString(_nRootType) + chrSpearator;
        }

        sPath += _nRootId + "" + chrSpearator;
        if (_nPathType == PATH_LOCAL && _bCreateWhenNotExists) {
            try {
                CMyFile.makeDir(sPath, true);
            } catch (Exception ex) {
                throw new WCMException(ExceptionNumber.ERR_WCMEXCEPTION,
                        "Failed to create template appendixes path:" + sPath,
                        ex);
            }
        }

        return sPath;
    }

    /**
     * 根据文件名，返回文件所在路径的Flag标识，如FilesMan.Flag_Protected
     *
     * @param sFileName
     * @return
     * @throws WCMException
     */
    public String getFileFlag(String sFileName) {

        String sFlag = "";
        String sFileHeader = "";

        // 校验参数
        if (sFileName == null)
            sFlag = "";

        // 根据头返回类型
        sFileHeader = sFileName.substring(0, 2);

        if (sFileHeader.equals(FLAG_PROTECTED))
            sFlag = FLAG_PROTECTED;

        if (sFileHeader.equals(FLAG_NORMAL))
            sFlag = FLAG_NORMAL;

        if (sFileHeader.equals(FLAG_UPLOAD))
            sFlag = FLAG_UPLOAD;

        if (sFileHeader.equals(FLAG_SYSTEMTEMP))
            sFlag = FLAG_SYSTEMTEMP;

        if (sFileHeader.equals(FLAG_USERTEMP))
            sFlag = FLAG_USERTEMP;

        if (sFileHeader.equals(FLAG_TEMPLATE))
            sFlag = FLAG_TEMPLATE;

        if (sFileHeader.equals(FLAG_LOCALPUB))
            sFlag = FLAG_LOCALPUB;

        if (sFileHeader.equals(FLAG_WEBFILE))
            sFlag = FLAG_WEBFILE;

        return sFlag;
    }

    // =======================================================================
    // 对象接口测试

    /**
     * 将指定文件移动到指定的目录
     *
     * @param _srcFileName
     *            原文件名（不包含路径），WCM标识中有的
     * @param _dstPathFlag
     *            目标目录标识（必须是有效的目录标识）
     * @param _bReturnPath
     *            是否返回保存后的文件名
     * @return
     * @throws WCMException
     */
    public String moveWCMFile(String _srcFileName, String _dstPathFlag,
                              boolean _bReturnPath) throws WCMException {

        // 校验参数
        if (_srcFileName == null) {
            throw new WCMException(ExceptionNumber.ERR_PARAM_INVALID,
                    I18NMessage.get(FilesMan.class, "FilesMan.label21",
                            "源文件名为空！"));
        }

        // 构造路径名，以便调用接口移动
        String sPathName = mapFilePath(_srcFileName, PATH_LOCAL);

        try {
            String sMoveFile = moveFile(sPathName + _srcFileName, _dstPathFlag,
                    _bReturnPath);
            return sMoveFile;
        } // enf of try
        catch (Exception ex) {
            throw new WCMException(ExceptionNumber.ERR_WCMEXCEPTION,
                    I18NMessage.get(FilesMan.class, "FilesMan.label19",
                            "移动文件到指定目录失败（FilesMan.moveFile）"), ex);
        }

    }// END: moveWCMFile()

    /**
     * 将指定WCM文件复制到目标目录
     *
     * @param _srcFileName
     *            ：WCM原始文件，不含路径
     * @param _dstPathFlag
     * @return：复制后的WCM文件名
     * @throws WCMException
     */
    public String copyWCMFile(String _srcFileName, String _dstPathFlag)
            throws WCMException {

        try {
            String sFileName = mapFilePath(_srcFileName, FilesMan.PATH_LOCAL)
                    + _srcFileName;
            // 复制文件
            return copyFile(sFileName, _dstPathFlag, false);
        } catch (Exception ex) {
            throw new WCMException(ExceptionNumber.ERR_WCMEXCEPTION,
                    I18NMessage.get(FilesMan.class, "FilesMan.label22",
                            "复制WCM文件到指定目录失败"), ex);
        }
    }// END: copyFile()

    private static void loadFilesMan() {
        try {
//            m_filesMan.loadPathConfigs(true); // TODO 装载目录配置信息
            s_logger.info(I18NMessage.get(FilesMan.class, "FilesMan.label23",
                    "装载目录配置信息完成！"));
        } catch (Exception ex) {
            s_logger.error(I18NMessage.get(FilesMan.class, "FilesMan.label24",
                    "装载目录配置信息失败"), ex);
        }
    }

    public static boolean isValidFile(String _sFileName, String _sPathFlag) {
        // HTTP产生的图片
        int nLastPos = _sFileName.lastIndexOf('/');
        if (nLastPos >= 0) {
            // 1.Extract File Name
            String sFileName = _sFileName.substring(nLastPos + 1);

            // 如果是WEB地址的文件需要校验文件是否存在
            if (_sFileName.trim().toUpperCase().indexOf("HTTP") == 0) {
                try {
                    if (!CMyFile.fileExists(FilesMan.getFilesMan().mapFilePath(
                            sFileName, FilesMan.PATH_LOCAL)
                            + sFileName)) {
                        s_logger.error(I18NMessage.get(FilesMan.class,
                                "FilesMan.label25", "文件[")
                                + sFileName
                                + I18NMessage.get(FilesMan.class,
                                "FilesMan.label26", "]不存在！自动从[")
                                + _sFileName
                                + I18NMessage.get(FilesMan.class,
                                "FilesMan.label27", "]下载！"));
                        return false;
                    }
                    // ge modify by gfc @2008-2-20 上午10:24:52
                    // 屏蔽不必要的文件格式校验的error信息
                    // }catch (Exception e) {
                    // s_logger.error("文件["+sFileName+I18NMessage.get(FilesMan.
                    // class, "FilesMan.label28", "]不是有效的文件!"));
                    // return false;
                    // //e.printStackTrace();
                    // }
                } catch (WCMException e) {
                    // 已知的、从FilesMan.mapFilePath抛出的异常，直接返回不输出信息
                    return false;
                } catch (Exception e) {
                    s_logger.warn(I18NMessage.get(FilesMan.class,
                            "FilesMan.label29", "校验文件[")
                            + sFileName
                            + I18NMessage.get(FilesMan.class,
                            "FilesMan.label30", "]格式时出现未捕获的异常：")
                            + e.getMessage());
                    return false;
                }
            }

            // 2.Validate File Name
            boolean bValidFile = isValidFile(sFileName, _sPathFlag);
            if (!bValidFile)
                return false;

            // 3.Validate HttpPath
            try {
                String sPath = FilesMan.getFilesMan().mapFilePath(sFileName,
                        FilesMan.PATH_HTTP);
                return _sFileName.indexOf(sPath) >= 0;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        if (_sFileName == null
                || _sFileName.length() < FilesMan.FILENAME_MIN_LENGTH)
            return false;
        if (_sFileName.indexOf(_sPathFlag) != 0)
            return false;
        return true;
    }

    /**
     * 根据FilesMan的规则，判断文件对应的实际文件物理是否存在 <BR>
     * 不抛出异常，只返回true/false <BR>
     *
     * @param _sFileName
     *            不含路径名，符合FilesMan规则的文件名
     * @return true表示文件存在，false表示不存在
     */
    public boolean fileExists(String _sFileName) {
        // 01. 校验参数
        if (_sFileName == null) {
            return false;
        }

        // 02. 构造文件的全文件名（路径+文件名）
        try {
            String sFullName = mapFilePath(_sFileName, FilesMan.PATH_LOCAL)
                    + _sFileName;
            if (CMyFile.fileExists(sFullName))
                return true;
        } catch (WCMException ex) {
            s_logger.warn(I18NMessage.get(FilesMan.class, "FilesMan.label31",
                    "无法映射文件的本地路径，可能是正常逻辑，程序继续运行。"));
            return false;
        }

        return false;
    } // END of fileExists

    /**
     * TODO 刷新目录配置缓冲
     *
     * @param _config
     *            指定的目录配置
     * @return
     * @throws Exception
     */
/*    public boolean refreshPathConfig(Config _config) throws Exception {
        if (true) {// 不从数据库加载配置，而是从文件加载
            return true;
        }
        String value = _config.getNewPropertyAsString("CVALUE");
        if (value == null) {
            return this.putPathConfig(_config, true);
        }// 新建

        // else config value changed
        return this.putPathConfig(_config.getConfigKey(),
                new PathConfig(value), true);
    }*/
}