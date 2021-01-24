package com.dome.mp.server.utils.sign;


import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

/**
 * <p>简单aes</p>
 *
 * @author TanXin
 * @date 2020/12/3 10:23
 */
public final class AES {

    private AES() {
    }

    // 算法填充模式
    private static final String CIPHER_INSTANCE = "AES/ECB/PKCS5Padding";
    // 算法
    private static final String ALGORITHM = "AES";
    // 随机算法
    private static final String RANDOM_ALGORITHM = "SHA1PRNG";
    //key大小
    private static final int KEY_SIZE = 128;

    /**
     * <p>AES简单加密<p>
     *
     * @param seed:    种子字符串（盐值字符串）
     * @param content: 加密内容
     * @author TanXin
     * @date 2020/12/3 10:49
     * @return java.lang.String
     */
    public static String encrypt(String seed, String content) {
        try {
            //1.构造密钥生成器，指定为AES算法,不区分大小写
            KeyGenerator keygen = KeyGenerator.getInstance(ALGORITHM);
            //2.根据种子字符串初始化密钥生成器
            SecureRandom random = SecureRandom.getInstance(RANDOM_ALGORITHM);
            random.setSeed(seed.getBytes(StandardCharsets.UTF_8));
            keygen.init(KEY_SIZE, random);
            //3.产生原始对称密钥
            SecretKey originalKey = keygen.generateKey();
            //4.获得原始对称密钥的字节数组
            byte[] raw = originalKey.getEncoded();
            //5.根据字节数组生成AES密钥
            SecretKey key = new SecretKeySpec(raw, ALGORITHM);
            //6.根据指定算法AES自成密码器
            Cipher cipher = Cipher.getInstance(CIPHER_INSTANCE);
            //7.初始化密码器，第一个参数为加密(Encrypt_mode)或者解密解密(Decrypt_mode)操作，第二个参数为使用的KEY
            cipher.init(Cipher.ENCRYPT_MODE, key);
            //8.获取加密内容的字节数组(这里要设置为utf-8)不然内容中如果有中文和英文混合中文就会解密为乱码
            byte[] byteEncode = content.getBytes(StandardCharsets.UTF_8);
            //9.根据密码器的初始化方式--加密：将数据加密 并返回
            return Base64Utils.encodeBase64(cipher.doFinal(byteEncode));
        } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | IllegalBlockSizeException | BadPaddingException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * <p>AES解密<p>
     *
     * @param seed:    种子字符串（盐值字符串）
     * @param content: 解密内容
     * @author TanXin
     * @date 2020/12/3 10:55
     * @return java.lang.String
     */
    public static String decrypt(String seed, String content) {
        try {
            //1.构造密钥生成器，指定为AES算法,不区分大小写
            KeyGenerator keygen = KeyGenerator.getInstance(AES.ALGORITHM);
            //2.根据种子字符串初始化密钥生成器
            SecureRandom random = SecureRandom.getInstance(RANDOM_ALGORITHM);
            random.setSeed(seed.getBytes(StandardCharsets.UTF_8));
            keygen.init(KEY_SIZE, random);
            //3.产生原始对称密钥
            SecretKey originalKey = keygen.generateKey();
            //4.获得原始对称密钥的字节数组
            byte[] raw = originalKey.getEncoded();
            //5.根据字节数组生成AES密钥
            SecretKey key = new SecretKeySpec(raw, AES.ALGORITHM);
            //6.根据指定算法AES自成密码器
            Cipher cipher = Cipher.getInstance(CIPHER_INSTANCE);
            //7.初始化密码器，第一个参数为加密(Encrypt_mode)或者解密(Decrypt_mode)操作，第二个参数为使用的KEY
            cipher.init(Cipher.DECRYPT_MODE, key);
            //8.将加密并编码后的内容解码成字节数组
            byte[] byteContent = Base64Utils.decodeBase64(content);
            // 解密返回
            return new String(cipher.doFinal(byteContent), StandardCharsets.UTF_8);
        } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | IllegalBlockSizeException | BadPaddingException e) {
            e.printStackTrace();
        }
        return null;
    }

//    public static void main(String[] args) {
//        Scanner scanner = new Scanner(System.in);
//        /*
//         * 加密
//         */
//        System.out.println("使用AES对称加密，请输入加密的规则");
//        String seed = scanner.next();
//        System.out.println("请输入要加密的内容:");
//        String content = scanner.next();
//        String encrypt = AES.encrypt(seed, content);
//        System.out.println("根据输入的种子" + seed + "加密后的密文是:" + encrypt);
//
//        /*
//         * 解密
//         *
//         */
//        System.out.println("使用AES对称解密，请输入加密的种子：(须与加密相同)");
//        seed = scanner.next();
//        System.out.println("请输入要解密的内容（密文）:");
//        content = scanner.next();
//        String decrypt = AES.decrypt(seed, content);
//        System.out.println("根据输入的种子" + seed + "解密后的明文是:" + decrypt);
//    }
}
