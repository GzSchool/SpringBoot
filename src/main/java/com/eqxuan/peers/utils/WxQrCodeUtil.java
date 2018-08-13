package com.eqxuan.peers.utils;

import com.alibaba.fastjson.JSONObject;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Ellipse2D;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * @Auther: zheng guangjing.
 * @Date: 2018/8/12 16:20
 * @Description: 微信小程序码个性化生成工具
 */
public class WxQrCodeUtil {

    /**
     * 接口描述：从服务器上获得一个输入流（读取服务器上的图片地址）
     * @param userPhotoUrl 图片地址
     * @return
     */
    public static InputStream getInputStream(String userPhotoUrl) {
        InputStream inputStream = null;
        HttpURLConnection httpURLConnection = null;

        try {
            URL url = new URL(userPhotoUrl);
            httpURLConnection = (HttpURLConnection)url.openConnection();
            //设置网络连接超时时间
            httpURLConnection.setConnectTimeout(3000);
            //设置应用程序要从网络连接读取数据
            httpURLConnection.setDoInput(true);

            httpURLConnection.setRequestMethod("GET");
            int responseCode = httpURLConnection.getResponseCode();
            if (responseCode == 200) {
                //从服务器返回一个输入流
                inputStream = httpURLConnection.getInputStream();
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return inputStream;
    }

    /**
     * 接口描述：图片缩放，w, h 为缩放目标宽度和高度
     * @param photoInputStream 在服务器获取的输入流
     * @param w
     * @param h
     * @return
     */
    public static BufferedImage zoomPhoto(InputStream photoInputStream, int w, int h){
        try {
            if (null == photoInputStream) {
                System.out.println("photoInputStream为空");
            }
            double wr = 0, hr = 0;
            BufferedImage bufferedImage = ImageIO.read(photoInputStream);//读取图片
            Image itemp = bufferedImage.getScaledInstance(w, h, BufferedImage.SCALE_SMOOTH);//设置缩放目标图片模板
            wr = w * 1.0 / bufferedImage.getWidth(); //获取缩放比例
            hr = w * 1.0 / bufferedImage.getHeight();

            AffineTransformOp ato = new AffineTransformOp(AffineTransform.getScaleInstance(wr, hr), null);
            itemp = ato.filter(bufferedImage, null);
            return (BufferedImage)itemp;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 接口描述：将缩放以后的图片截为圆形
     * @param zoomPhoto 缩放后的图片
     * @return
     */
    public static BufferedImage circle(BufferedImage zoomPhoto){
        try {
            BufferedImage image = new BufferedImage(zoomPhoto.getWidth(), zoomPhoto.getHeight(), BufferedImage.TYPE_INT_ARGB);
            // 创建一个椭圆形的2D图像
            Ellipse2D.Double shape = new Ellipse2D.Double(0, 0, zoomPhoto.getWidth(), zoomPhoto.getHeight());
            Graphics2D g2 = image.createGraphics();
            image = g2.getDeviceConfiguration().createCompatibleImage(zoomPhoto.getWidth(), zoomPhoto.getHeight(), Transparency.TRANSLUCENT);
            g2 = image.createGraphics();
            g2.setComposite(AlphaComposite.Clear);
            g2.fill(new Rectangle(image.getWidth(), image.getHeight()));
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC, 1.0f));
            g2.setClip(shape);
            g2.drawImage(zoomPhoto, 0, 0, null);
            g2.dispose();
            return image;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 接口描述：获取微信小程序码
     * @param accessToken
     * @param scene 参数
     * @param page 扫描小程序码后跳转的位置（小程序需上线）
     * @return
     */
    public static InputStream miniQrCode(String accessToken, String scene, String page){
        try {
            URL url = new URL("https://api.weixin.qq.com/wxa/getwxacodeunlimit?access_token=" + accessToken);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod("POST");// 提交模式
            // conn.setConnectTimeout(10000);//连接超时 单位毫秒
            // conn.setReadTimeout(2000);//读取超时 单位毫秒
            // 发送POST请求必须设置如下两行
            httpURLConnection.setDoOutput(true);
            httpURLConnection.setDoInput(true);
            // 获取URLConnection对象对应的输出流
            PrintWriter printWriter = new PrintWriter(httpURLConnection.getOutputStream());
            // 发送请求参数
            JSONObject paramJson = new JSONObject();
            paramJson.put("scene", scene);
            paramJson.put("page", page);
            paramJson.put("width", 430);
            paramJson.put("auto_color", true);
            /**
             * line_color生效
             * paramJson.put("auto_color", false);
             * JSONObject lineColor = new JSONObject();
             * lineColor.put("r", 0);
             * lineColor.put("g", 0);
             * lineColor.put("b", 0);
             * paramJson.put("line_color", lineColor);
             * */

            printWriter.write(paramJson.toString());
            // flush输出流的缓冲
            printWriter.flush();
            //开始获取数据
            InputStream miniQrCodeInputStream = httpURLConnection.getInputStream();
            return miniQrCodeInputStream;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 接口描述：替换小程序码中的中央LOGO
     * @param miniQrCodeInputStream 小程序码原码
     * @param centerImg 用来替换小程序码的中央图片
     * @param pathName 保存路径
     */
    public static String makeInRound(InputStream miniQrCodeInputStream, BufferedImage centerImg, String pathName){
        try {
            //读取小程序码
            BufferedImage appletImg = ImageIO.read(miniQrCodeInputStream);
            Graphics2D g2d = appletImg.createGraphics();

            // 设置抗锯齿的属性
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

            //获取化圆后的缩放图片，用来替换小程序码的中央图案
            g2d.drawImage(centerImg.getScaledInstance(centerImg.getWidth(), centerImg.getHeight(), Image.SCALE_SMOOTH), (appletImg.getWidth() - centerImg.getWidth()) / 2, (appletImg.getHeight() - centerImg.getHeight()) / 2, null);

            // 关闭资源
            g2d.dispose();
            //生成新的小程序码

            File file = new File(pathName);
            File fileParent = file.getParentFile();
            //判断是否存在
            if (!fileParent.exists()) {
                //创建父目录文件
                fileParent.mkdirs();
            }
            file.createNewFile();

            ImageIO.write(appletImg, "png", file);
            String newWxQrCode = file.getCanonicalPath();
            return newWxQrCode;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}