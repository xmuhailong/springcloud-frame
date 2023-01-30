package com.zzl.core.base.utils;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

/**
 * @author zhaozhonglong
 * @version 1.0.0
 * @date 2019/10/9 下午8:11
 */
public class MD5SaltUtils {

    private static final Integer SALT_LENGTH = 12;


    /**
     * @description 根据原密码，获得加密后的16进制形式口令
     * @param [password]
     * @return java.lang.String
     * @author zhaozhonglong
     * @date  2019/10/9 20:16:44
     */
    public static String getEncryptedPwd(String password)
            throws NoSuchAlgorithmException, UnsupportedEncodingException {
        byte[] salt = getSalt();

        return doGetEncryptedPwd(password, salt);
    }

    /**
     * @description 根据原密码和盐，获得加密后的16进制形式口令
     * @param [password, salt]
     * @return java.lang.String
     * @author zhaozhonglong
     * @date  2019/10/9 20:18:26
     */
    public static String getEncryptedPwd(String password, byte[] salt)
            throws NoSuchAlgorithmException, UnsupportedEncodingException {
        return doGetEncryptedPwd(password, salt);
    }

    /**
     * @description 获得加密后的16进制形式口令
     * @param [password, salt]
     * @return java.lang.String
     * @author zhaozhonglong
     * @date  2019/10/9 20:21:04
     */
    public static String doGetEncryptedPwd(String password, byte[] salt)
            throws NoSuchAlgorithmException, UnsupportedEncodingException {
        //声明加密后的口令数组变量
        byte[] pwd = null;

        //声明消息摘要对象
        MessageDigest md = null;

        //创建消息摘要
        md = MessageDigest.getInstance("MD5");

        //将盐数据传入消息摘要对象
        md.update(salt);

        //将口令的数据传给消息摘要对象
        md.update(password.getBytes("UTF-8"));

        //获得消息摘要的字节数组
        byte[] digest = md.digest();

        //因为要在口令的字节数组中存放盐，所以加上盐的字节长度
        pwd = new byte[digest.length + SALT_LENGTH];

        //将盐的字节拷贝到生成的加密口令字节数组的前12个字节，以便在验证口令时取出盐
        System.arraycopy(salt, 0, pwd, 0, SALT_LENGTH);

        //将消息摘要拷贝到加密口令字节数组从第13个字节开始的字节
        System.arraycopy(digest, 0, pwd, SALT_LENGTH, digest.length);

        //将字节数组格式加密后的口令转化为16进制字符串格式的口令
        return CommonStringUtils.byteToHexString(pwd);
    }

    /**
     * @description 获取盐
     * @param []
     * @return byte[]
     * @author zhaozhonglong
     * @date  2019/10/9 20:16:14
     */
    public static byte[] getSalt() {
        //随机数生成器
        SecureRandom random = new SecureRandom();

        //声明盐数组变量   12
        byte[] salt = new byte[SALT_LENGTH];

        //将随机数放入盐变量中
        random.nextBytes(salt);

        return salt;
    }

}
