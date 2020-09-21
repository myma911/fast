//package cn.aaron911.file.util;
//
//import org.springframework.web.multipart.MultipartFile;
//
//import cn.aaron911.file.entity.VirtualFile;
//import cn.aaron911.file.exception.GlobalFileException;
//
//import javax.imageio.ImageIO;
//import java.awt.*;
//import java.io.*;
//
///**
// * 操作图片工具类
// *
// * @version 1.0
// */
//public class ImageUtil {
//
//    /**
//     * 获取图片信息
//     *
//     * @param file
//     * @throws IOException
//     */
//    public static VirtualFile getInfo(File file) {
//        if (null == file) {
//            return new VirtualFile();
//        }
//        try {
//            return getInfo(new FileInputStream(file))
//                    .setSize(file.length())
//                    .setOriginalFileName(file.getName())
//                    .setSuffix(FileUtil.getSuffix(file.getName()));
//        } catch (Exception e) {
//            e.printStackTrace();
//            throw new GlobalFileException("获取图片信息发生异常！", e);
//        }
//    }
//
//    /**
//     * 获取图片信息
//     *
//     * @param multipartFile
//     * @throws IOException
//     */
//    public static VirtualFile getInfo(MultipartFile multipartFile) {
//        if (null == multipartFile) {
//            return new VirtualFile();
//        }
//        try {
//            return getInfo(multipartFile.getInputStream())
//                    .setSize(multipartFile.getSize())
//                    .setOriginalFileName(multipartFile.getOriginalFilename())
//                    .setSuffix(FileUtil.getSuffix(multipartFile.getOriginalFilename()));
//        } catch (Exception e) {
//            e.printStackTrace();
//            throw new GlobalFileException("获取图片信息发生异常！", e);
//        }
//    }
//
//    /**
//     * 获取信息
//     *
//     * @param inputStream
//     * @throws IOException
//     */
//    public static VirtualFile getInfo(InputStream inputStream) {
//        try (BufferedInputStream in = new BufferedInputStream(inputStream)) {
//            //字节流转图片对象
//            Image bi = ImageIO.read(in);
//            if (null == bi) {
//                return new VirtualFile();
//            }
//            return new VirtualFile().setSize(inputStream.available());
//        } catch (Exception e) {
//            throw new GlobalFileException("获取图片信息发生异常！", e);
//        }
//    }
//}
