package util;

import java.util.List;
import java.util.Map;

import org.junit.Test;

import com.alibaba.fastjson.JSON;

/**
 * Unit test for simple App.
 */
public class AppTest {

    static String path = "D:\\liangzhenghui\\testimage\\bz.png";

    @Test
    public void test_getImageInfo() {

        Map map=ImageUtil.getImageInfo(path);
        System.out.println(JSON.toJSONString(map));
    }

    //@Test
    public void test_addTextMark() throws Exception {

        ImageUtil.addTextWatermark(path, path, "bbbbb");
    }

    //@Test
    public void test_addImgMark() throws Exception {

        ImageUtil.addImgWatermark(path, path, 100);
    }

    //@Test
    public void test_resize() throws Exception {

        ImageUtil.resize(path, path, 256, 256, 30d, false);
        //ImageUtil.jdkResize(path, 0.3f);
    }

    //@Test
    public void test_removeProfile() throws Exception {

        ImageUtil.removeProfile(path, path);
    }

    //@Test
    public void test_scaleResizeImage() throws Exception {

        ImageUtil.scaleResize(path, path, 256, null);
    }

    //@Test
    public void test_cropImage() throws Exception {

        ImageUtil.crop(path, path, 0, 100, 400, 400);
    }

    //@Test
    public void test_subsectionImage() throws Exception {

        List<String> list = ImageUtil.subsection(path, path, 200, 200);
        System.out.println(list);
    }

    //@Test
    public void test_rotate() throws Exception {

        ImageUtil.rotate(path, path, 10d);
    }

}
